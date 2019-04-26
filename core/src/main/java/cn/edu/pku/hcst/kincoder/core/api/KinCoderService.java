package cn.edu.pku.hcst.kincoder.core.api;

import java.util.Map;
import java.util.Set;

public interface KinCoderService {
    StartSessionResponse startSession(String query, Map<String, String> variables, Set<String> extendedTypes);

    QAResponse selectPattern(long sessionId, int id);

    QAResponse response(long sessionId, String answer);

    UndoResponse undo(long sessionId);
}
