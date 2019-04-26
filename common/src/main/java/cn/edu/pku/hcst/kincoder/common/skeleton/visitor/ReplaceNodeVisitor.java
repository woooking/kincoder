package cn.edu.pku.hcst.kincoder.common.skeleton.visitor;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.Arg;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.Node;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.*;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.literal.*;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.stmt.*;
import lombok.AllArgsConstructor;

import java.util.ArrayList;

@AllArgsConstructor
public class ReplaceNodeVisitor implements Visitor<Void, Node> {
    private final Node oldNode;
    private final Node newNode;


    @Override
    public Node visit(Arg node, Void arg) {
        if (node == oldNode) return newNode;
        return null;
    }

    @Override
    public Node visit(BlockStmt node, Void arg) {
        if (node == oldNode) return newNode;
        return null;
    }

    @Override
    public Node visit(ExprStmt node, Void arg) {
        if (node == oldNode) return newNode;
        return null;
    }

    @Override
    public Node visit(IfStmt node, Void arg) {
        if (node == oldNode) return newNode;
        return null;
    }

    @Override
    public Node visit(ForStmt node, Void arg) {
        if (node == oldNode) return newNode;
        return null;
    }

    @Override
    public Node visit(ForEachStmt node, Void arg) {
        if (node == oldNode) return newNode;
        return null;
    }

    @Override
    public Node visit(WhileStmt node, Void arg) {
        if (node == oldNode) return newNode;
        return null;
    }

    @Override
    public Node visit(ReturnStmt node, Void arg) {
        if (node == oldNode) return newNode;
        return null;
    }

    @Override
    public Node visit(HoleExpr node, Void arg) {
        if (node == oldNode) return newNode;
        return null;
    }

    @Override
    public Node visit(AssignExpr node, Void arg) {
        if (node == oldNode) return newNode;
        return null;
    }

    @Override
    public Node visit(ArrayCreationExpr node, Void arg) {
        if (node == oldNode) return newNode;
        return null;
    }

    @Override
    public Node visit(BinaryExpr node, Void arg) {
        if (node == oldNode) return newNode;
        return null;
    }

    @Override
    public Node visit(EnumConstantExpr node, Void arg) {
        if (node == oldNode) return newNode;
        return null;
    }

    @Override
    public Node visit(MethodCallExpr node, Void arg) {
        if (node == oldNode) return newNode;
        return null;
    }

    @Override
    public Node visit(ObjectCreationExpr node, Void arg) {
        if (node == oldNode) return newNode;
        var args = new ArrayList<Arg>();
        boolean updated = false;
        for (Arg a : args) {
            var na = a.accept(this, arg);
            if (na != null) updated = true;
            args.add((Arg) na);
        }
        return null;
    }

    @Override
    public Node visit(UnaryExpr node, Void arg) {
        if (node == oldNode) return newNode;
        var expr = node.getExpr().accept(this, arg);
        if (expr != null) return node.withExpr((Expr) expr);
        return null;
    }

    @Override
    public Node visit(TypeNameExpr node, Void arg) {
        if (node == oldNode) return newNode;
        return null;
    }

    @Override
    public Node visit(SimpleNameExpr node, Void arg) {
        if (node == oldNode) return newNode;
        return null;
    }

    @Override
    public Node visit(StaticFieldAccessExpr node, Void arg) {
        if (node == oldNode) return newNode;
        var name = node.getName().accept(this, arg);
        if (name != null) return node.withName((NameExpr) name);
        return null;
    }

    @Override
    public Node visit(FieldAccessExpr node, Void arg) {
        if (node == oldNode) return newNode;
        var name = node.getName().accept(this, arg);
        if (name != null) return node.withName((NameExpr) name);
        var receiver = node.getReceiver().accept(this, arg);
        if (receiver != null) return node.withReceiver((Expr) receiver);
        return null;
    }

    @Override
    public Node visit(VarDeclExpr node, Void arg) {
        if (node == oldNode) return newNode;
        if (node.getInit() != null) {
            var init = node.getInit().accept(this, arg);
            if (init != null) return node.withInit((Expr) init);
        }
        var name = node.getName().accept(this, arg);
        if (name != null) return node.withName((NameExpr) name);
        return null;
    }

    @Override
    public Node visit(BooleanLiteral node, Void arg) {
        if (node == oldNode) return newNode;
        return null;
    }

    @Override
    public Node visit(ByteLiteral node, Void arg) {
        if (node == oldNode) return newNode;
        return null;
    }

    @Override
    public Node visit(ShortLiteral node, Void arg) {
        if (node == oldNode) return newNode;
        return null;
    }

    @Override
    public Node visit(IntLiteral node, Void arg) {
        if (node == oldNode) return newNode;
        return null;
    }

    @Override
    public Node visit(LongLiteral node, Void arg) {
        if (node == oldNode) return newNode;
        return null;
    }

    @Override
    public Node visit(FloatLiteral node, Void arg) {
        if (node == oldNode) return newNode;
        return null;
    }

    @Override
    public Node visit(DoubleLiteral node, Void arg) {
        if (node == oldNode) return newNode;
        return null;
    }

    @Override
    public Node visit(CharLiteral node, Void arg) {
        if (node == oldNode) return newNode;
        return null;
    }

    @Override
    public Node visit(StringLiteral node, Void arg) {
        if (node == oldNode) return newNode;
        return null;
    }

    @Override
    public Node visit(NullLiteral node, Void arg) {
        if (node == oldNode) return newNode;
        return null;
    }
}
