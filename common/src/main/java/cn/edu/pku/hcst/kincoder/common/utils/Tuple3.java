package cn.edu.pku.hcst.kincoder.common.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class Tuple3<V1, V2, V3> {
    private final V1 v1;
    private final V2 v2;
    private final V3 v3;
}
