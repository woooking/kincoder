package cn.edu.pku.hcst.kincoder.pattern.api;

import cn.edu.pku.hcst.kincoder.pattern.utils.GraphUtil;
import com.google.common.collect.Sets;
import de.parsemis.graph.Edge;
import de.parsemis.graph.Graph;
import de.parsemis.miner.environment.Settings;
import de.parsemis.miner.general.Fragment;
import de.parsemis.parsers.LabelParser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@AllArgsConstructor
public class PatternMiner<Data, N, E, G extends Graph<N, E>, R> {
    private final Data source;
    private final GraphGenerator<Data, G> graphGenerator;
    private final PatternGenerator<N, E, G, R> patternGenerator;
    private final boolean filterSubGraph;
    private final LabelParser<N> nodeParser;
    private final LabelParser<E> edgeParser;

    private Collection<Graph<N, E>> resultFilter(Collection<Fragment<N, E>> result) {
        var graphs = new ArrayList<Graph<N, E>>();
        var edgeSets = new ArrayList<Set<Edge<N, E>>>();
        result.stream()
            .map(Fragment::toGraph)
            .sorted(Comparator.<Graph<N, E>>comparingInt(Graph::getEdgeCount).reversed())
            .forEach(graph -> {
                var ite = graph.edgeIterator();
                var edges = Sets.newHashSet(ite);
                if (edgeSets.stream().noneMatch(edgeSet -> isSubset(edges, edgeSet))){
                    graphs.add(graph);
                    edgeSets.add(edges);
                }
            });
        return graphs;
    }

    private boolean edgeEquals(Edge<N, E> edgeA, Edge<N, E> edgeB) {
        return edgeA.getNodeA().getLabel() == edgeB.getNodeA().getLabel() &&
            edgeA.getNodeB().getLabel() == edgeB.getNodeB().getLabel() &&
            edgeA.getDirection() == edgeB.getDirection() ||
            edgeA.getNodeA().getLabel() == edgeB.getNodeB().getLabel() &&
                edgeA.getNodeB().getLabel() == edgeB.getNodeA().getLabel() &&
                edgeA.getDirection() == -edgeB.getDirection();
    }

    private boolean isSubset(Set<Edge<N, E>> small, Set<Edge<N, E>> big) {
        if (small.isEmpty()) return true;
        return small.stream().allMatch(edgeInSmall -> big.stream().anyMatch(edgeInBig -> edgeEquals(edgeInSmall, edgeInBig)));
    }

    public Collection<R> process(MinerSetting minerSetting) {
        var graphs = graphGenerator.generate(source);
        graphs.forEach(GraphUtil::print);
        log.info(String.format("总数据流图数: %d", graphs.size()));
        var freqFragments = mine(graphs, minerSetting.toParsemisSettings(nodeParser, edgeParser));
        var subFiltered = filterSubGraph ? resultFilter(freqFragments) : freqFragments.stream().map(Fragment::toGraph).collect(Collectors.toList());
        log.info(String.format("频繁子图数: %d", subFiltered.size()));
        subFiltered.forEach(GraphUtil::print);
        return subFiltered.stream().map(r -> patternGenerator.generate(graphs, r)).collect(Collectors.toList());
    }

    @SuppressWarnings("unchecked")
    private Collection<Fragment<N, E>> mine(Collection<? extends Graph<N, E>> dfgs, Settings<N, E> settings) {
        return de.parsemis.Miner.mine((Collection<Graph<N, E>>) dfgs, settings);
    }
}
