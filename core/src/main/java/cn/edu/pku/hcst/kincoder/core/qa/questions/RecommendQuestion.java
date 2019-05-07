package cn.edu.pku.hcst.kincoder.core.qa.questions;

import cn.edu.pku.hcst.kincoder.common.skeleton.visitor.Printer;
import cn.edu.pku.hcst.kincoder.common.skeleton.visitor.Printer.PrintConfig;
import cn.edu.pku.hcst.kincoder.common.skeleton.visitor.Printer.PrintContext;
import cn.edu.pku.hcst.kincoder.core.qa.Question;
import cn.edu.pku.hcst.kincoder.core.qa.questions.choices.RecommendChoice;
import com.google.common.collect.Streams;
import lombok.AllArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
public class RecommendQuestion implements Question {
    private final Question wrappedQuestion;
    private final List<RecommendChoice> recommendations;

    @Override
    public String description() {
        var printer = new Printer(PrintConfig.builder().build());
        var choiceStringStream = recommendations.stream()
            .map(c -> printer.visit(c.getFilled(), new PrintContext("")) );
        return String.format("%s\n%s", wrappedQuestion.description(), Streams.mapWithIndex(
            choiceStringStream,
            (c, i) -> String.format("#%s. %s", i + 1, c)
        ).collect(Collectors.joining("\n")));
    }
}
