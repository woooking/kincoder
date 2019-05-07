package cn.edu.pku.hcst.kincoder.core.qa;

import cn.edu.pku.hcst.kincoder.common.skeleton.Skeleton;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.type.PrimitiveType;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.type.ReferenceType;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.type.Type;
import cn.edu.pku.hcst.kincoder.common.utils.ElementUtil;
import cn.edu.pku.hcst.kincoder.common.utils.Pair;
import cn.edu.pku.hcst.kincoder.core.nlp.NlpContext;
import cn.edu.pku.hcst.kincoder.core.utils.CodeUtil;
import lombok.Value;
import lombok.experimental.Wither;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Value
public class Context {
	private final String query;
	private final Set<Pair<String, Type>> variables;
	@Wither
	private final Skeleton skeleton;
	private final Set<String> extendedTypes;
	private final NlpContext nlpCtx;

	public Set<String> findVariables(Type type) {
		return variables.stream()
			.filter(p -> CodeUtil.isAssignable(p.getRight(), type))
			.map(Pair::getLeft)
			.collect(Collectors.toSet());
	}

	public String findFreeVariableName(Type type) {
		if (type instanceof ReferenceType || type instanceof PrimitiveType) {
			var simpleName = ElementUtil.qualifiedName2Simple(type.describe());
			var chars = simpleName.toCharArray();
			chars[0] = Character.toLowerCase(chars[0]);
			var prefix = new String(chars);
			return Stream.concat(
				Stream.of(""),
				IntStream.iterate(2, i -> i + 1).mapToObj(i -> String.format("%s%d", prefix, i))
			).findFirst().orElse("");
		} else {
			var coreType = CodeUtil.coreType(type);
			var simpleName = ElementUtil.qualifiedName2Simple(coreType.describe());
			var chars = simpleName.toCharArray();
			chars[0] = Character.toLowerCase(chars[0]);
			var prefix = new String(chars);
			return Stream.concat(
				Stream.of(""),
				IntStream.iterate(2, i -> i + 1).mapToObj(i -> String.format("%s%d", prefix, i))
					.map(i -> String.format("%ss%s", prefix, i))
			).findFirst().orElse("");
		}
	}

}
