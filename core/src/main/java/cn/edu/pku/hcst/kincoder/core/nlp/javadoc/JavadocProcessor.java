package cn.edu.pku.hcst.kincoder.core.nlp.javadoc;

import cn.edu.pku.hcst.kincoder.core.nlp.NlpService;
import com.google.inject.Inject;

import java.util.List;

public class JavadocProcessor {
	private final List<JavadocDescriptionPreFilter> javadocDescriptionPreFilters;
	private final NlpService nlpService;

	@Inject
	public JavadocProcessor(List<JavadocDescriptionPreFilter> javadocDescriptionPreFilters, NlpService nlpService) {
		this.javadocDescriptionPreFilters = javadocDescriptionPreFilters;
		this.nlpService = nlpService;
	}

	public String extractFirstSentence(String rawDescription) {
		var description = javadocDescriptionPreFilters.stream().reduce(
			rawDescription,
			(s, f) -> f.filter(s),
			(s1, s2) -> s1 + s2
		);
		return nlpService.getFirstSentence(description);
	}
}
