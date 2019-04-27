package cn.edu.pku.hcst.kincoder.common.utils;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.Arg;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.*;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.type.ReferenceType;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.type.Type;
import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class CodeBuilder {
    public Arg arg(Type type, Expr value) {
        return new Arg(type, value);
    }

    public StaticMethodCallExpr call(ReferenceType declaredType, String simpleName, Arg... args) {
        return new StaticMethodCallExpr(declaredType, simpleName, List.of(args));
    }

    public MethodCallExpr call(Expr<?> receiver, ReferenceType receiverType, String simpleName, Arg... args) {
        return new MethodCallExpr(Pair.of(receiverType, receiver), simpleName, List.of(args));
    }

    public ArrayCreationExpr create(Type componentType, List<Expr<?>> inits) {
        return new ArrayCreationExpr(componentType, inits);
    }

    public EnumConstantExpr enumerate(ReferenceType enumType, NameOrHole<?> name) {
        return new EnumConstantExpr(enumType, name);
    }

    public SimpleNameExpr str2name(String s) {
        return new SimpleNameExpr(s);
    }
}
