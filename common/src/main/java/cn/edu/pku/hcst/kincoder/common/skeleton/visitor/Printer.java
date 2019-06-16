package cn.edu.pku.hcst.kincoder.common.skeleton.visitor;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.Arg;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.Node;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.*;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.literal.*;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.stmt.*;
import cn.edu.pku.hcst.kincoder.common.skeleton.visitor.Printer.PrintContext;
import cn.edu.pku.hcst.kincoder.common.utils.ElementUtil;
import cn.edu.pku.hcst.kincoder.common.utils.StringUtil;
import lombok.Builder;
import lombok.Value;
import lombok.experimental.Wither;

import java.util.function.Function;
import java.util.stream.Collectors;

public class Printer implements Visitor<PrintContext, String> {
    private final PrintConfig config;

    public Printer(PrintConfig config) {
        this.config = config;
    }

    public String print(Node<?> node) {
        return node.accept(this, new PrintContext(""));
    }

    @Override
    public String visit(Node<?> node, PrintContext arg) {
        throw new RuntimeException("Never");
    }

    @Override
    public String visit(Arg node, PrintContext arg) {
        return visit(node.getValue(), arg);
    }

    @Override
    public String visit(BlockStmt node, PrintContext arg) {
        var indented = arg.withIndent(arg.getIndent() + " ".repeat(config.indentWidth));

        var statements = node.getStatements().stream()
            .map(s -> visit(s, indented))
            .collect(Collectors.joining("\n"));

        return String.format("%s{\n%s\n%s}", arg.getIndent(), statements, arg.getIndent());
    }

    @Override
    public String visit(ExprStmt node, PrintContext arg) {
        return String.format("%s%s;", arg.getIndent(), visit(node.getExpr(), arg));
    }

    @Override
    public String visit(IfStmt node, PrintContext arg) {
        var indented = arg.withIndent(arg.getIndent() + " ".repeat(config.indentWidth));

        return node.getElseBody() == null
            ? String.format("%sif (%s) {\n%s\n%s}", arg.getIndent(), visit(node.getCond(), arg), visit(node.getThenBody(), indented), arg.getIndent())
            : String.format("%sif (%s) {\n%s\n%s} else {\n%s\n%s}", arg.getIndent(), visit(node.getCond(), arg), visit(node.getThenBody(), indented), arg.getIndent(), visit(node.getElseBody(), indented), arg.getIndent());
    }

    @Override
    public String visit(ForStmt node, PrintContext arg) {
        var indented = arg.withIndent(arg.getIndent() + " ".repeat(config.indentWidth));

        return String.format("%sfor (%s; %s; %s) {\n%s\n%s}",
            arg.getIndent(),
            node.getInits().stream().map(i -> visit(i, arg)).collect(Collectors.joining(", ")),
            node.getCond() == null ? "" : visit(node.getCond(), arg),
            node.getUpdates().stream().map(i -> visit(i, arg)).collect(Collectors.joining(", ")),
            visit(node.getBody(), indented),
            arg.getIndent()
        );
    }

    @Override
    public String visit(ForEachStmt node, PrintContext arg) {
        var indented = arg.withIndent(arg.getIndent() + " ".repeat(config.indentWidth));

        return String.format("%sfor (%s %s : %s) {\n%s\n%s}",
            arg.getIndent(),
            node.getType().describe(),
            node.getName(),
            visit(node.getIterable(), arg),
            visit(node.getBody(), indented),
            arg.getIndent()
        );
    }

    @Override
    public String visit(WhileStmt node, PrintContext arg) {
        var indented = arg.withIndent(arg.getIndent() + " ".repeat(config.indentWidth));

        return String.format("%swhile (%s) {\n%s\n%s}",
            arg.getIndent(),
            visit(node.getCond(), arg),
            visit(node.getBody(), indented),
            arg.getIndent()
        );
    }

    @Override
    public String visit(ReturnStmt node, PrintContext arg) {
        return String.format("%sreturn%s;",
            arg.getIndent(),
            node.getValue() == null ? "" : " " + visit(node.getValue(), arg)
        );
    }

    @Override
    public String visit(HoleExpr node, PrintContext arg) {
        return config.holeString.apply(node.getId());
    }

    @Override
    public String visit(AssignExpr node, PrintContext arg) {
        return String.format("%s = %s", visit(node.getTarget(), arg), visit(node.getSource(), arg));
    }

    @Override
    public String visit(ArrayCreationExpr node, PrintContext arg) {
        return String.format("new %s[] {%s}",
            node.getComponentType().describe(),
            node.getInits().stream().map(i -> visit(i, arg)).collect(Collectors.joining(", "))
        );
    }

    @Override
    public String visit(BinaryExpr node, PrintContext arg) {
        return String.format("%s %s %s", visit(node.getLeft(), arg), node.getOp(), visit(node.getRight(), arg));
    }

    @Override
    public String visit(EnumConstantExpr node, PrintContext arg) {
        return String.format("%s.%s", node.getEnumType().describe(), visit(node.getName(), arg));
    }

    @Override
    public String visit(MethodCallExpr node, PrintContext arg) {
        return String.format("%s.%s(%s)",
            visit(node.getReceiver().getRight(), arg),
            node.getName(),
            node.getArgs().stream().map(i -> visit(i, arg)).collect(Collectors.joining(", "))
        );
    }

    @Override
    public String visit(StaticMethodCallExpr node, PrintContext arg) {
        return String.format("%s.%s(%s)",
            node.getDeclaredType().describe(),
            node.getName(),
            node.getArgs().stream().map(i -> visit(i, arg)).collect(Collectors.joining(", "))
        );
    }

    @Override
    public String visit(ObjectCreationExpr node, PrintContext arg) {
        return String.format("new %s(%s)",
            node.getType().describe(),
            node.getArgs().stream().map(i -> visit(i, arg)).collect(Collectors.joining(", "))
        );
    }

    @Override
    public String visit(UnaryExpr node, PrintContext arg) {
        return String.format("%s%s",
            node.isPrefix() ? node.getOpe() : visit(node.getExpr(), arg),
            node.isPrefix() ? visit(node.getExpr(), arg) : node.getOpe()
        );
    }

    @Override
    public String visit(TypeNameExpr node, PrintContext arg) {
        return String.format("%s%d", StringUtil.decapitalize(ElementUtil.qualifiedName2Simple(node.getType().describe())), node.getId());
    }

    @Override
    public String visit(SimpleNameExpr node, PrintContext arg) {
        return node.getName();
    }

    @Override
    public String visit(StaticFieldAccessExpr node, PrintContext arg) {
        return String.format("%s.%s", node.getReceiverType().describe(), visit(node.getName(), arg));
    }

    @Override
    public String visit(FieldAccessExpr node, PrintContext arg) {
        return String.format("%s.%s", visit(node.getReceiver(), arg), visit(node.getName(), arg));
    }

    @Override
    public String visit(VarDeclExpr node, PrintContext arg) {
        return node.getInit() == null
            ? String.format("%s %s", node.getType().describe(), visit(node.getName(), arg))
            : String.format("%s %s = %s", node.getType().describe(), visit(node.getName(), arg), visit(node.getInit(), arg));
    }

    @Override
    public String visit(BooleanLiteral node, PrintContext arg) {
        return node.getValue().toString();
    }

    @Override
    public String visit(ByteLiteral node, PrintContext arg) {
        return node.getValue().toString();
    }

    @Override
    public String visit(ShortLiteral node, PrintContext arg) {
        return node.getValue().toString();
    }

    @Override
    public String visit(IntLiteral node, PrintContext arg) {
        return node.getValue().toString();
    }

    @Override
    public String visit(LongLiteral node, PrintContext arg) {
        return node.getValue().toString();
    }

    @Override
    public String visit(FloatLiteral node, PrintContext arg) {
        return node.getValue().toString();
    }

    @Override
    public String visit(DoubleLiteral node, PrintContext arg) {
        return node.getValue().toString();
    }

    @Override
    public String visit(CharLiteral node, PrintContext arg) {
        return "'" + node.getValue().toString() + "'";
    }

    @Override
    public String visit(StringLiteral node, PrintContext arg) {
        return "\"" + node.getValue() + "\"";
    }

    @Override
    public String visit(NullLiteral node, PrintContext arg) {
        return "null";
    }

    @Value
    @Wither
    public static class PrintContext {
        private final String indent;
    }

    @Builder
    public static class PrintConfig {
        @Builder.Default private final int indentWidth = 4;
        private final Function<Integer, String> holeString;
    }
}
