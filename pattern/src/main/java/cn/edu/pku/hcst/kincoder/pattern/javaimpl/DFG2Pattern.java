package cn.edu.pku.hcst.kincoder.pattern.javaimpl;

import cn.edu.pku.hcst.kincoder.common.skeleton.HoleFactory;
import cn.edu.pku.hcst.kincoder.common.skeleton.Skeleton;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.expr.NameExpr;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.stmt.BlockStmt;
import cn.edu.pku.hcst.kincoder.common.utils.CodeBuilder;
import cn.edu.pku.hcst.kincoder.common.utils.Pair;
import cn.edu.pku.hcst.kincoder.kg.utils.CodeUtil;
import cn.edu.pku.hcst.kincoder.pattern.api.PatternGenerator;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.dfg.DFG;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.dfg.DFGEdge;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.dfg.DFGNode;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import de.parsemis.graph.Graph;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class DFG2Pattern implements PatternGenerator<DFGNode, DFGEdge, DFG, Skeleton> {
    private final CodeUtil codeUtil;

    private final HoleFactory holeFactory = new HoleFactory();

    public DFG2Pattern(CodeUtil codeUtil) {
        this.codeUtil = codeUtil;
    }

    @Override
    public Skeleton generate(Collection<DFG> originalGraph, Graph<DFGNode, DFGEdge> graph) {
        var pair = originalGraph.stream()
            .map(d -> Pair.of(d, d.isSuperGraph(graph)))
            .filter(p -> p.getRight().getLeft())
            .findFirst()
            .get();
        return generateCode(pair.getLeft(), pair.getRight().getRight());
    }

    private Skeleton generateCode(DFG dfg, Set<de.parsemis.graph.Node<DFGNode, DFGEdge>> nodes) {
        var recoverNodes = dfg.recover(nodes);
        Map<String, NameExpr<?>> ctx = new HashMap<>();
        var block = generateCode(dfg.getCfg().getDecl(), recoverNodes, ctx);
        return Skeleton.create(holeFactory, block);
    }


    private BlockStmt generateCode(Node node, Set<Node> nodes, Map<String, NameExpr<?>> names) {
        assert node instanceof MethodDeclaration && ((MethodDeclaration) node).getBody().isPresent();
        var stmts = ((MethodDeclaration) node).getBody().get().accept(new DFG2PatternStatementVisitor(codeUtil, nodes, holeFactory), names);
        return CodeBuilder.block(stmts.getLeft());
    }
}
