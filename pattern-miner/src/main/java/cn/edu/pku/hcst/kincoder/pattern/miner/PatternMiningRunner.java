package cn.edu.pku.hcst.kincoder.pattern.miner;

import cn.edu.pku.hcst.kincoder.pattern.api.PatternConfig;
import cn.edu.pku.hcst.kincoder.pattern.api.PatternMiner;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.DFG2Pattern;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.JavaDFGGenerator;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.dfg.DFGEdge;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.dfg.DFGNode;

import java.io.File;

public class PatternMiningRunner {
    public static void main(String[] args) {
//        implicit val setting: Settings[DFGNode, DFGEdge] = Setting.create(DFGNode.parser, DFGEdge.parser, minFreq = 4)
        var patternConfig = PatternConfig.builder().build();
        var clientCodeRoot = new File(patternConfig.getClientCodeDir());
        var graphGenerator = new JavaDFGGenerator();
//
        var miner = new PatternMiner(
            clientCodeRoot,
            graphGenerator,
            new DFG2Pattern(),
            true,
            new DFGNode.Parser(),
            new DFGEdge.Parser()
        );
//        val result = cosyn.process()
//        result.foreach(p => log.info(p.stmts.generateCode("")))
//        PatternSaver.savePatterns(result)
//        KnowledgeGraph.close()
    }

}
