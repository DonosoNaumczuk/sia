package gridlock;

import gps.api.State;

public class StateGridLock implements State {

    private BoardGridLock board;

    public StateGridLock(BoardGridLock board) {
        this.board = board;
    }

    public StateGridLock(StateGridLock state, RuleGridLock rule) {
        this(state.board.move(rule.getBlock(), rule.getDirection()));
    }

    @Override
    public boolean equals(Object object) {
        if (object == null)
            return false;

        if (object == this)
            return true;

        if (object instanceof StateGridLock)
            return ((StateGridLock) object).board.equals(this.board);

        return false;
    }

    public boolean canTransitionByApplying(RuleGridLock rule) {
        return board.canMove(rule.getBlock(), rule.getDirection());
    }

    public BoardGridLock getBoard() {
        return board;
    }

    @Override
    public String getRepresentation() {
        return board.toString();
    }

    @Override
    public int hashCode() {
        return board.hashCode();
    }
}
