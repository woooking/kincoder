package cn.edu.pku.hcst.kincoder.core.qa.questions.choices;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.HoleExpr;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.stmt.BlockStmt;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.stmt.ForEachStmt;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.type.ReferenceType;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.type.Type;
import cn.edu.pku.hcst.kincoder.common.skeleton.visitor.ReplaceNodeVisitor;
import cn.edu.pku.hcst.kincoder.common.utils.Pair;
import cn.edu.pku.hcst.kincoder.core.qa.Context;
import cn.edu.pku.hcst.kincoder.core.qa.questions.*;
import cn.edu.pku.hcst.kincoder.kg.entity.TypeEntity;
import lombok.Value;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import static cn.edu.pku.hcst.kincoder.common.utils.CodeBuilder.foreach;
import static cn.edu.pku.hcst.kincoder.common.utils.CodeBuilder.str2name;

@Value
public class IterableChoice implements Choice {
	private final List<TypeEntity> path;
	@Nullable
	private final String recommendVar;
	private final boolean recommend;

	private Pair<ForEachStmt, String> buildForEachStmt(Context ctx, HoleExpr hole, TypeEntity outer, TypeEntity inner, List<TypeEntity> remains) {
		if (remains.isEmpty()) {
			var iterableName = ctx.findFreeVariableName(Type.fromString(outer.getQualifiedName()));
			var varName = ctx.findFreeVariableName(Type.fromString(inner.getQualifiedName()));
			var forEachStmt = foreach(inner.getQualifiedName(), varName, iterableName, block(ctx..getStatements().parentStmtOf(hole)));
			var varExpr = str2name(varName);
			var visitor = new ReplaceNodeVisitor(hole, varExpr);
			return Pair.of(forEachStmt.accept(visitor, null), iterableName);
		} else {

			var (innerForEach, innerName) = buildForEachStmt(ctx, hole, inner, head, tail);
			var iterableName = context.findFreeVariableName(BasicType(outer.getQualifiedName))
			var forEachStmt = ForEachStmt(inner.getQualifiedName, innerName, iterableName, block(innerForEach))
			(forEachStmt, iterableName)
		}
	}

	@Override
	public ChoiceResult action(Context ctx, HoleExpr hole) {
		var targetType = (ReferenceType) Type.fromString(path.get(0).getQualifiedName());
		if (path.size() == 1) {
			if (recommendVar == null) {
				return new NewQuestion(ChoiceQuestion.forType(ctx, targetType, recommend));
			} else {
				var name = str2name(recommendVar);
				return new Filled(ctx.withSkeleton(ctx.getSkeleton().fillHole(hole, name)), name);
			}

		} else {
			var skeleton = ctx.getSkeleton();
			var stmt = skeleton.parentStmtOf(hole);
			var blockStmt = (BlockStmt) skeleton.parentOf(stmt);
			var init = skeleton.getHoleFactory().create();
			var(innerForEach, innerName) = buildForEachStmt(context, hole, path.head, path.tail.head, path.tail.tail)
			var varDecl = recommendVar match {
				case Some(name) =>
					v(targetType, innerName, name)
				case None =>
					v(targetType, innerName, init)
			}
			Resolved(context.copy(pattern = pattern.replaceStmtInBlock(blockStmt, stmt, varDecl, innerForEach)), varDecl)
		}
	}
}
