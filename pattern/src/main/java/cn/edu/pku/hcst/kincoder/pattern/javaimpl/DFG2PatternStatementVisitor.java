package cn.edu.pku.hcst.kincoder.pattern.javaimpl;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.NameExpr;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.stmt.Stmt;
import cn.edu.pku.hcst.kincoder.common.utils.Pair;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.visitor.GenericVisitorWithDefaults;
import org.apache.commons.lang3.NotImplementedException;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class DFG2PatternStatementVisitor extends GenericVisitorWithDefaults<Pair<List<Stmt<?>>, Map<String, NameExpr<?>>>, Map<String, NameExpr<?>>> {
    private final Set<Node> nodes;

    public DFG2PatternStatementVisitor(Set<Node> nodes) {
        this.nodes = nodes;
    }

    @Override
    public Pair<List<Stmt<?>>, Map<String, NameExpr<?>>> defaultAction(Node n, Map<String, NameExpr<?>> arg) {
        throw new NotImplementedException(String.format("%s not implemented", n.getClass()));
    }

    @Override
    public Pair<List<Stmt<?>>, Map<String, NameExpr<?>>> defaultAction(NodeList n, Map<String, NameExpr<?>> arg) {
        throw new NotImplementedException(String.format("%s not implemented", n.getClass()));
    }

    @Override
    public Pair<List<Stmt<?>>, Map<String, NameExpr<?>>> visit(BlockStmt n, Map<String, NameExpr<?>> arg) {
        return super.visit(n, arg);
    }

    @Override
    public Pair<List<Stmt<?>>, Map<String, NameExpr<?>>> visit(BreakStmt n, Map<String, NameExpr<?>> arg) {
        return super.visit(n, arg);
    }

    @Override
    public Pair<List<Stmt<?>>, Map<String, NameExpr<?>>> visit(ContinueStmt n, Map<String, NameExpr<?>> arg) {
        return super.visit(n, arg);
    }

    @Override
    public Pair<List<Stmt<?>>, Map<String, NameExpr<?>>> visit(DoStmt n, Map<String, NameExpr<?>> arg) {
        return super.visit(n, arg);
    }

    @Override
    public Pair<List<Stmt<?>>, Map<String, NameExpr<?>>> visit(EmptyStmt n, Map<String, NameExpr<?>> arg) {
        return super.visit(n, arg);
    }

    @Override
    public Pair<List<Stmt<?>>, Map<String, NameExpr<?>>> visit(ForEachStmt n, Map<String, NameExpr<?>> arg) {
        return super.visit(n, arg);
    }

    @Override
    public Pair<List<Stmt<?>>, Map<String, NameExpr<?>>> visit(ForStmt n, Map<String, NameExpr<?>> arg) {
        return super.visit(n, arg);
    }

    @Override
    public Pair<List<Stmt<?>>, Map<String, NameExpr<?>>> visit(LabeledStmt n, Map<String, NameExpr<?>> arg) {
        return super.visit(n, arg);
    }

    @Override
    public Pair<List<Stmt<?>>, Map<String, NameExpr<?>>> visit(SwitchStmt n, Map<String, NameExpr<?>> arg) {
        return super.visit(n, arg);
    }
}
