package cn.edu.pku.hcst.kincoder.core.impl.session;

public class WaitingState implements SessionState {
    @Override
    public SessionState selectSkeleton(int id) {

    }

    @Override
    public SessionState answer(String answer) {
        throw new UnsupportedOperationException("Select Skeleton First");
    }

    @Override
    public SessionState undo() {
        throw new UnsupportedOperationException("Select Skeleton First");
    }
}
