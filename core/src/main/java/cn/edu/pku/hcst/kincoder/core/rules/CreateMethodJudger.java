package cn.edu.pku.hcst.kincoder.core.rules;

import cn.edu.pku.hcst.kincoder.kg.entity.MethodEntity;
import cn.edu.pku.hcst.kincoder.kg.entity.MethodJavadocEntity;

import java.util.List;
import java.util.Optional;

public class CreateMethodJudger extends PositiveJudger<MethodEntity> {
    private Rule<MethodEntity> nameRule = methodEntity -> methodEntity.getSimpleName().matches("^(create|new)([A-Z].*|$)");

    // javadoc的第一句话中包含create
    private Rule<MethodEntity> javadocRule = methodEntity -> Optional.ofNullable(methodEntity.getJavadoc())
        .map(MethodJavadocEntity::getDescription)
        .map(JavadocUtil.extractFirstSentence)
        .getOrElse("")
        .toLowerCase.contains("create")

    public CreateMethodJudger() {
        this.rules = List.of(nameRule, javadocRule);
    }
}
