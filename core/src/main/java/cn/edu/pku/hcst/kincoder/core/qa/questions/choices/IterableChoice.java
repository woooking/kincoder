package cn.edu.pku.hcst.kincoder.core.qa.questions.choices;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.HoleExpr;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.type.ReferenceType;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.type.Type;
import cn.edu.pku.hcst.kincoder.core.qa.Context;
import cn.edu.pku.hcst.kincoder.core.qa.questions.Choice;
import cn.edu.pku.hcst.kincoder.core.qa.questions.ChoiceQuestion;
import cn.edu.pku.hcst.kincoder.core.qa.questions.ChoiceResult;
import cn.edu.pku.hcst.kincoder.core.qa.questions.NewQuestion;
import cn.edu.pku.hcst.kincoder.kg.entity.TypeEntity;
import lombok.Value;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Optional;

@Value
public class IterableChoice implements Choice {
    private final List<TypeEntity> path;
    @Nullable
    private final String recommendVar;
    private final boolean recommend;

    private def buildForEachStmt(context:Context, hole:HoleExpr, outer:TypeEntity, inner:TypeEntity, remains:List[TypeEntity]):(ForEachStmt,String)=

    {
        remains match {
        case Nil =>
            val iterableName = context.findFreeVariableName(BasicType(outer.getQualifiedName))
            val varName = context.findFreeVariableName(BasicType(inner.getQualifiedName))
            val forEachStmt = foreach(inner.getQualifiedName, varName, iterableName, block(context.pattern.parentStmtOf(hole)))
            (FillHoleVisitor.fillHole(forEachStmt, hole, varName), iterableName)
        case head::tail =>
            val(innerForEach, innerName) = buildForEachStmt(context, hole, inner, head, tail)
            val iterableName = context.findFreeVariableName(BasicType(outer.getQualifiedName))
            val forEachStmt = ForEachStmt(inner.getQualifiedName, innerName, iterableName, block(innerForEach))
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
                Resolved(context.copy(pattern = context.pattern.fillHole(hole, recommendVar)), recommendVar)
            }

        } else {
            val pattern = context.pattern
            val stmt = pattern.parentStmtOf(hole)
            val blockStmt = pattern.parentOf(stmt).asInstanceOf[BlockStmt]
            val init = pattern.holeFactory.newHole()
            val(innerForEach, innerName) = buildForEachStmt(context, hole, path.head, path.tail.head, path.tail.tail)
            val varDecl = recommendVar match {
                case Some(name) =>
                    v(targetType, innerName, name)
                case None =>
                    v(targetType, innerName, init)
            }
            Resolved(context.copy(pattern = pattern.replaceStmtInBlock(blockStmt, stmt, varDecl, innerForEach)), varDecl)
        }
    }
}
