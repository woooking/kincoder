package cn.edu.pku.hcst.kincoder.common.utils;

import cn.edu.pku.hcst.kincoder.common.skeleton.NameFinder;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.Arg;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.*;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.stmt.BlockStmt;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.stmt.ExprStmt;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.stmt.ForEachStmt;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.stmt.Stmt;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.type.ReferenceType;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.type.Type;

import java.util.List;

public final class CodeBuilder {
    private CodeBuilder() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }

    public static Arg arg(Type type, Expr<?> value) {
        return new Arg(type, value);
    }

    public static AssignExpr assign(NameExpr name, Expr<?> target) {
        return new AssignExpr(name, target);
    }

    public static BlockStmt block(Stmt<?>... stmts) {
        return new BlockStmt(List.of(stmts));
    }

    public static BlockStmt block(List<Stmt<?>> stmts) {
        return new BlockStmt(stmts);
    }

    public static BinaryExpr binary(String op, Expr<?> left, Expr<?> right) {
        return new BinaryExpr(op, left, right);
    }

    public static StaticMethodCallExpr call(ReferenceType declaredType, String simpleName, Arg... args) {
        return new StaticMethodCallExpr(declaredType, simpleName, List.of(args));
    }

    public static MethodCallExpr call(Expr<?> receiver, ReferenceType receiverType, String simpleName, Arg... args) {
        return new MethodCallExpr(Pair.of(receiverType, receiver), simpleName, List.of(args));
    }

    public static ArrayCreationExpr create(Type componentType, List<Expr<?>> inits) {
        return new ArrayCreationExpr(componentType, inits);
    }

    public static ExprStmt expr2stmt(Expr<?> expr) {
        return new ExprStmt(expr);
    }

    public static EnumConstantExpr enumerate(ReferenceType enumType, NameOrHole<?> name) {
        return new EnumConstantExpr(enumType, name);
    }

    public static FieldAccessExpr field(ReferenceType receiverType, Expr<?> receiver, NameOrHole<?> name) {
        return new FieldAccessExpr(receiverType, receiver, name);
    }

    public static ForEachStmt foreach(Type iteratedType, String variable, Expr<?> iterable, BlockStmt block) {
        return new ForEachStmt(iteratedType, variable, iterable, block);
    }

    public static TypeNameExpr name(Type ty, NameFinder finder) {
        return new TypeNameExpr(ty, finder.nextIdForType(ty));
    }


    public static SimpleNameExpr str2name(String s) {
        return new SimpleNameExpr(s);
    }

    public static UnaryExpr unary(String op, Expr<?> expr, boolean isPrefix) {
        return new UnaryExpr(op, expr, isPrefix);
    }

    public static VarDeclExpr v(Type type, NameExpr<?> name, Expr<?> init) {
        return new VarDeclExpr(type, name, init);
    }

}
