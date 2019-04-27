package cn.edu.pku.hcst.kincoder.core.nlp;

import com.google.inject.Inject;

public class NlpService {
    private final StanfordCoreNLPClient client;

    @Inject
    public NlpService(NlpServerConfig config) {
        val props = new Properties()
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma, depparse, ner")
        props.setProperty("ner.buildEntityMentions", "false")
        val nlp = new StanfordCoreNLPClient(props, "http://162.105.88.236", 9000)
    }
}
