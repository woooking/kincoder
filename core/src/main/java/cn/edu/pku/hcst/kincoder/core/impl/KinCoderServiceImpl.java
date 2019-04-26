package cn.edu.pku.hcst.kincoder.core.impl;

import cn.edu.pku.hcst.kincoder.core.api.*;
import cn.edu.pku.hcst.kincoder.core.impl.session.Session;
import cn.edu.pku.hcst.kincoder.core.nlp.PatternMatcher;
import com.google.inject.Inject;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

public class KinCoderServiceImpl implements KinCoderService {
    private final ConcurrentMap<Long, Session> sessions = new ConcurrentHashMap<>();
    private final AtomicLong nextId = new AtomicLong();
    private final PatternMatcher patternMatcher;
    private final KinCoderConfig config;

    @Inject
    public KinCoderServiceImpl(PatternMatcher patternMatcher, KinCoderConfig config) {
        this.patternMatcher = patternMatcher;
        this.config = config;
    }

    @Override
    public StartSessionResponse startSession(String query, Map<String, String> variables, Set<String> extendedTypes) {
        var results = patternMatcher.match(query, config.getPatternLimit());
        var session = new Session(query, variables, extendedTypes, results);
        var sessionId = nextId.getAndIncrement();
        sessions.put(sessionId, session);
        return new StartSessionResponse(nextId.getAndIncrement(), results);
    }

    @Override
    public QAResponse selectPattern(long sessionId, int id) {
        return null;
    }

    @Override
    public QAResponse response(long sessionId, String answer) {
        return null;
    }

    @Override
    public UndoResponse undo(long sessionId) {
        return null;
    }
}
