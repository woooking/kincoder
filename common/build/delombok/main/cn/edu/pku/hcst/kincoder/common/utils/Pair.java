package cn.edu.pku.hcst.kincoder.common.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class Pair<L, R> {
    private final L left;
    private final R right;
}
