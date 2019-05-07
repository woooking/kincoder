package cn.edu.pku.hcst.kincoder.core.qa.questions;

import cn.edu.pku.hcst.kincoder.common.skeleton.model.type.PrimitiveType;
import cn.edu.pku.hcst.kincoder.common.skeleton.model.type.Type;
import cn.edu.pku.hcst.kincoder.core.qa.Question;
import lombok.Value;
import org.jetbrains.annotations.Nullable;

@Value
public class PrimitiveQuestion implements Question {
    @Nullable
    private final String hint;
    private final Type type; // 可能是PrimitiveType或ReferenceType(java.lang.String)

    @Override
    public String description() {
        if (hint == null) {
            if (type instanceof PrimitiveType) return String.format("Please input a %s:", type.describe());
            return "Please input a string:";
        }
        if (type instanceof PrimitiveType) return String.format("Please input a %s(%s):", type.describe(), hint);
        return String.format("Please input %s:", hint);
    }
}
