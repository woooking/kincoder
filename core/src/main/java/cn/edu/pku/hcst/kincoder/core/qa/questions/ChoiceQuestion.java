package cn.edu.pku.hcst.kincoder.core.qa.questions;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.type.ReferenceType;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.type.Type;
import cn.edu.pku.hcst.kincoder.common.utils.ElementUtil;
import cn.edu.pku.hcst.kincoder.core.Components;
import cn.edu.pku.hcst.kincoder.core.qa.Context;
import cn.edu.pku.hcst.kincoder.core.qa.Question;
import cn.edu.pku.hcst.kincoder.core.qa.questions.choices.EnumChoice;
import cn.edu.pku.hcst.kincoder.core.qa.questions.choices.MethodChoice;
import cn.edu.pku.hcst.kincoder.core.qa.questions.choices.VariableChoice;
import cn.edu.pku.hcst.kincoder.core.utils.CodeUtil;
import cn.edu.pku.hcst.kincoder.kg.entity.MethodEntity;
import cn.edu.pku.hcst.kincoder.kg.repository.Repository;
import com.google.common.collect.Lists;
import lombok.Value;

import java.lang.reflect.Method;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Value
public class ChoiceQuestion implements Question {
    private final String content;
    private final List<Choice> choices;

    private MethodCategory category(MethodEntity method) {

    }

    public static ChoiceQuestion forType(Context ctx, Type type, Boolean recommend) {
        if (type instanceof ReferenceType) {
            var vars = ctx.findVariables(type);
            var producers = CodeUtil.producers(type);

            if (recommend) {
                // t是枚举类型，则添加枚举选项
                var enumEntity = Components.getInstance(Repository.class).getEnumEntity(type.describe());
                var enumChoice = enumEntity != null ? List.<Choice>of(new EnumChoice(enumEntity)) : List.<Choice>of();

                var simpleName = ElementUtil.qualifiedName2Simple(type.describe()).toLowerCase();
                var content = String.format("Which %s?", simpleName);

                var choices = Stream.concat(
                    Stream.concat(
                        vars.stream().map(VariableChoice::new),
                        enumChoice.stream()
                    ),
                    producers.stream().map(MethodChoice::new)
                ).collect(Collectors.toList());
                return new ChoiceQuestion(content, choices);
            } else {
                var cases = producers.stream().collect(Collectors.groupingBy())
                val cases:Map[MethodCategory, Set[MethodEntity]] =producers.groupBy {
                    case m
                        if CreateMethodJudger.judge(m) =>MethodCategory.Create
                    case m
                        if LoadMethodJudger.judge(m) =>MethodCategory.Load
                    case m
                        if GetMethodJudger.judge(m) =>MethodCategory.Get
                    case _ =>
                        OtherType
                }
                val methodCategoryChoices = cases.flatMap {
                    case (OtherType,m) =>
                        if (Config.printUnCategorisedMethods) {
                            println("----- UnCategorised -----")
                            m.foreach(f = > {
                                println(f.getQualifiedSignature)
                                println(Option(f.getJavadoc).getOrElse(""))
                            })
                            println("-------------------------")
                        }
                        Seq()
                    case (category,ms) =>Seq(MethodCategoryChoice(bt, category, ms))
                }

                // t是枚举类型，则添加枚举选项
                val enumChoice = methodEntityRepository.getType(t) match {
                    case e:
                        EnumEntity =>
                        EnumChoice(e)::Nil
                    case _ =>
                        Nil
                }

                val simpleName = CodeUtil.qualifiedClassName2Simple(t).toLowerCase
                val q = s "Which $simpleName?"
                ChoiceQuestion(q, vars.toSeq.map(VariableChoice.apply)++enumChoice++methodCategoryChoices)
            }
        } else {
            val vars = context.findVariables(at)

            val simpleName = CodeUtil.qualifiedClassName2Simple(at.componentType.toString).toLowerCase
            val q = s "Which ${simpleName}s?"
            ChoiceQuestion(q, vars.toSeq.map(VariableChoice.apply) :+CreateArrayChoice(at))
        }
    }
}
