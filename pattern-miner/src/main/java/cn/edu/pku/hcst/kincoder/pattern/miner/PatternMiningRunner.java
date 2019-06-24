package cn.edu.pku.hcst.kincoder.pattern.miner;

import cn.edu.pku.hcst.kincoder.common.skeleton.visitor.Printer;
import cn.edu.pku.hcst.kincoder.common.skeleton.visitor.Printer.PrintConfig;
import cn.edu.pku.hcst.kincoder.kg.KnowledgeGraphSessionFactory;
import cn.edu.pku.hcst.kincoder.kg.utils.CodeUtil;
import cn.edu.pku.hcst.kincoder.pattern.api.MinerSetting;
import cn.edu.pku.hcst.kincoder.pattern.api.PatternConfig;
import cn.edu.pku.hcst.kincoder.pattern.api.PatternMiner;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.DFG2Pattern;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.JavaDFGGenerator;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.dfg.DFGEdge;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.dfg.DFGFactory;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.dfg.DFGNode;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.dfg.DFGNode.Type;
import com.google.common.collect.Streams;
import com.google.inject.Guice;
import de.parsemis.graph.Graph;
import de.parsemis.graph.Node;
import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
public class PatternMiningRunner {
    private static class GraphNodeSetMapping {
        private final Graph<DFGNode, DFGEdge> graph;
        private final Set<DFGNode> nodes;

        private GraphNodeSetMapping(Graph<DFGNode, DFGEdge> graph) {
            this.graph = graph;
            this.nodes = Streams.stream(graph.nodeIterator())
                .map(Node::getLabel)
                .filter(l -> l.getType() != Type.TYPE)
                .collect(Collectors.toSet());
        }
    }

    private static List<Graph<DFGNode, DFGEdge>> resultFilter(List<Graph<DFGNode, DFGEdge>> result) {
        var graphs = new ArrayList<Graph<DFGNode, DFGEdge>>();
        var nodeSets = new ArrayList<Set<DFGNode>>();
        result.stream()
            .map(GraphNodeSetMapping::new)
            .sorted(Comparator.<GraphNodeSetMapping>comparingInt(m -> m.nodes.size()).reversed())
            .forEach(m -> {
                if (nodeSets.stream().noneMatch(nodeSet -> isSubset(m.nodes, nodeSet))) {
                    graphs.add(m.graph);
                    nodeSets.add(m.nodes);
                }
            });
        return graphs;
    }

    private static boolean isSubset(Set<DFGNode> small, Set<DFGNode> big) {
        if (small.isEmpty()) return true;
        return small.stream().allMatch(nodeInSmall -> big.stream().anyMatch(nodeInBig -> Objects.equals(nodeInSmall, nodeInBig)));
    }

    public static void main(String[] args) throws IOException {
        var yaml = new Yaml();

        try (var configFile = PatternMiningRunner.class.getResourceAsStream("/application.yaml")) {
            var config = yaml.loadAs(configFile, PatternMiningConfig.class);
            var injector = Guice.createInjector(new MinerModule(config));

            try {
                var codeUtil = injector.getInstance(CodeUtil.class);
                var patternConfig = injector.getInstance(PatternConfig.class);
                var dfgFactory = injector.getInstance(DFGFactory.class);

                var clientCodeRoot = new File(config.getClientCodeDir());
                var graphGenerator = new JavaDFGGenerator(codeUtil, patternConfig, dfgFactory);

                var miner = new PatternMiner<>(
                    clientCodeRoot,
                    graphGenerator,
                    new DFG2Pattern(codeUtil),
                    new DFGNode.Parser(),
                    new DFGEdge.Parser(),
                    PatternMiningRunner::resultFilter
                );

                var result = miner.process(MinerSetting.builder().build());
                result.forEach(r -> {
                    System.out.println("----------");
                    var printer = new Printer(PrintConfig.builder().holeString(id -> "<HOLE>").build());
                    log.info(printer.print(r.getStmts()));
                });
                injector.getInstance(PatternSaver.class).saveSkeleton(result);
            } finally {
                var factory = injector.getInstance(KnowledgeGraphSessionFactory.class);
                factory.close();
            }
        }
    }

}
