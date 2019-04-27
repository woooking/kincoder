package cn.edu.pku.hcst.kincoder.core.qa.questions;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.type.ArrayType;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.type.ReferenceType;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.type.Type;
import cn.edu.pku.hcst.kincoder.common.utils.ElementUtil;
import cn.edu.pku.hcst.kincoder.core.Components;
import cn.edu.pku.hcst.kincoder.core.qa.Context;
import cn.edu.pku.hcst.kincoder.core.qa.Question;
import cn.edu.pku.hcst.kincoder.core.qa.questions.choices.*;
import cn.edu.pku.hcst.kincoder.core.rules.CreateMethodJudger;
import cn.edu.pku.hcst.kincoder.core.rules.GetMethodJudger;
import cn.edu.pku.hcst.kincoder.core.rules.LoadMethodJudger;
import cn.edu.pku.hcst.kincoder.core.utils.CodeUtil;
import cn.edu.pku.hcst.kincoder.kg.entity.MethodEntity;
import cn.edu.pku.hcst.kincoder.kg.repository.Repository;
import com.google.common.collect.Streams;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toSet;

@Slf4j
@Value
public class ChoiceQuestion implements Question {
	private final String content;
	private final List<Choice> choices;

	private static MethodCategory category(MethodEntity method) {
		CreateMethodJudger createMethodJudger = Components.getInstance(CreateMethodJudger.class);
		GetMethodJudger getMethodJudger = Components.getInstance(GetMethodJudger.class);
		LoadMethodJudger loadMethodJudger = Components.getInstance(LoadMethodJudger.class);

		if (createMethodJudger.judge(method)) return MethodCategory.CREATE;
		if (getMethodJudger.judge(method)) return MethodCategory.GET;
		if (loadMethodJudger.judge(method)) return MethodCategory.LOAD;
		return MethodCategory.UNKNOWN;
	}

	// type非基本类型
	public static ChoiceQuestion forType(Context ctx, Type type, Boolean recommend) {
		var repository = Components.getInstance(Repository.class);

		if (type instanceof ReferenceType) {
			var vars = ctx.findVariables(type);
			var producers = CodeUtil.producers(type);

			// t是枚举类型，则添加枚举选项
			var enumEntity = repository.getEnumEntity(type.describe());
			var enumChoice = enumEntity != null ? List.<Choice>of(new EnumChoice(enumEntity)) : List.<Choice>of();
			var simpleName = ElementUtil.qualifiedName2Simple(type.describe()).toLowerCase();
			var content = String.format("Which %s?", simpleName);

			if (recommend) {
				var choices = Streams.concat(
					vars.stream().map(VariableChoice::new),
					enumChoice.stream(),
					producers.stream().map(MethodChoice::new)
				).collect(Collectors.toList());
				return new ChoiceQuestion(content, choices);
			} else {
				var cases = producers.stream().collect(groupingBy(ChoiceQuestion::category, toSet()));

				var methodCategoryChoices = cases.entrySet().stream().flatMap(e -> {
					var category = e.getKey();
					var ms = e.getValue();
					if (category == MethodCategory.UNKNOWN) {
						log.debug("----- UnCategorised -----");
						ms.forEach(m -> {
							log.debug(m.getQualifiedSignature());
							if (m.getJavadoc() != null) {
								log.debug(m.getJavadoc().getDescription());
							}
						});
						log.debug("-------------------------");
						return Stream.of();
					}

					return Stream.of(new MethodCategoryChoice((ReferenceType) type, category, ms));
				});

				var choices = Streams.concat(
					vars.stream().map(VariableChoice::new),
					enumChoice.stream(),
					methodCategoryChoices
				).collect(Collectors.toList());
				return new ChoiceQuestion(content, choices);
			}
		} else {
			// TODO: nested ArrayType
			var vars = ctx.findVariables(type);
			var simpleName = ElementUtil.qualifiedName2Simple(((ArrayType) type).getComponentType().describe()).toLowerCase();
			var content = String.format("Which %s?", simpleName);

			var choices = Streams.concat(
				vars.stream().map(VariableChoice::new),
				Stream.of(new CreateArrayChoice(((ArrayType) type)))
			).collect(Collectors.toList());

			return new ChoiceQuestion(content, choices);
		}
	}
}
