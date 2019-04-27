package cn.edu.pku.hcst.kincoder.core.nlp;

import com.google.inject.Inject;
import edu.stanford.nlp.pipeline.CoreDocument;
import edu.stanford.nlp.pipeline.StanfordCoreNLPClient;

import java.util.Properties;

public class NlpService {
    private final StanfordCoreNLPClient client;

    @Inject
    public NlpService(NlpServerConfig config) {
        var props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, depparse, ner");
        props.setProperty("ner.buildEntityMentions", "false");
        this.client = new StanfordCoreNLPClient(props, config.getHost(), config.getPort());
    }

    public String getFirstSentence(String text) {
        var annotation = client.process(text);
        var document = new CoreDocument(annotation);
        var sentences = document.sentences();
        return sentences != null && sentences.size() > 0 ? sentences.get(0).text() : "";
    }
}
