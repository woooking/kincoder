package cn.edu.pku.hcst.kincoder.core.qa.questions;

import lombok.Value;

import java.util.function.Function;

@Value
public class MethodCategory {
    private final Function<String, String> questionGenerator;
}
