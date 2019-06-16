package cn.edu.pku.hcst.kincoder.pattern.miner;

import cn.edu.pku.hcst.kincoder.common.skeleton.visitor.Printer;
import cn.edu.pku.hcst.kincoder.common.skeleton.visitor.Printer.PrintConfig;
import cn.edu.pku.hcst.kincoder.kg.utils.CodeUtil;
import cn.edu.pku.hcst.kincoder.pattern.api.MinerSetting;
import cn.edu.pku.hcst.kincoder.pattern.api.PatternConfig;
import cn.edu.pku.hcst.kincoder.pattern.api.PatternMiner;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.DFG2Pattern;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.JavaDFGGenerator;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.dfg.DFGEdge;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.dfg.DFGNode;
import com.google.inject.Guice;
import lombok.extern.slf4j.Slf4j;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.IOException;

@Slf4j
public class PatternMiningRunner {
    public static void main(String[] args) throws IOException {
        var yaml = new Yaml();

        try (var configFile = PatternMiningRunner.class.getResourceAsStream("/application.yaml")) {
//            var config = yaml.loadAs(configFile, PatternMiningRunner.class);
            var injector = Guice.createInjector(new MinerModule());
            var codeUtil = injector.getInstance(CodeUtil.class);
//            builder.build();

            var patternConfig = PatternConfig.builder().build();
            var clientCodeRoot = new File(patternConfig.getClientCodeDir());
            var graphGenerator = new JavaDFGGenerator();
//
            var miner = new PatternMiner<>(
                clientCodeRoot,
                graphGenerator,
                new DFG2Pattern(codeUtil),
                true,
                new DFGNode.Parser(),
                new DFGEdge.Parser()
            );

            var result = miner.process(MinerSetting.builder().build());
            result.forEach(r -> {
                var printer = new Printer(PrintConfig.builder().holeString(id -> "<HOLE>").build());
                log.info(printer.print(r.getStmts()));
            });
        }

//        implicit val setting: Settings[DFGNode, DFGEdge] = Setting.create(DFGNode.parser, DFGEdge.parser, minFreq = 4)

//        val result = cosyn.process()
//        result.foreach(p => log.info(p.stmts.generateCode("")))
//        PatternSaver.savePatterns(result)
//        KnowledgeGraph.close()
    }

}
