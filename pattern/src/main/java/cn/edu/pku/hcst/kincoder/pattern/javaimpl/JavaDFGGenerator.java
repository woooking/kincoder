package cn.edu.pku.hcst.kincoder.pattern.javaimpl;

import cn.edu.pku.hcst.kincoder.pattern.api.GraphGenerator;
import cn.edu.pku.hcst.kincoder.pattern.javaimpl.dfg.DFG;

import java.io.File;
import java.util.Collection;

public class JavaDFGGenerator implements GraphGenerator<File, DFG> {
    @Override
    public Collection<DFG> generate(File source) {
//        var projectParser = new JavaProjectParser(List<String> sourceCodeDirs, CodeUtil codeUtil, PatternConfig config, DFGFactory dfgFactory);
//        var dfgs = projectParser.process(source.toPath());
//            .register(DFGNodeFilter(DFGNode(NodeType.MethodInvocation, "createHyperlink")))
//            .register(DFGNodeFilter(DFGNode(NodeType.MethodInvocation, "parse")))
//            .register(DFGNodeFilter(DFGNode(NodeType.MethodInvocation, "render")))
//            .register(DFGNodeFilter(DFGNode(NodeType.MethodInvocation, "render")))
//            .register(DFGNodeFilter(DFGNode(NodeType.MethodInvocation, "IndexWriter::init")))
//            .register(DFGSizeFilter(50))
        return null;
    }
}
