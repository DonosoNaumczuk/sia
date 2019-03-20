package gridlock;

import gps.api.State;
import gridlock.BoardGridLock.BlockGridLock;

public class StateGridLock implements State {

    private BoardGridLock board;

    public StateGridLock(BoardGridLock board) {
        this.board = board;
    }

    public StateGridLock(StateGridLock state, BlockGridLock block, Direction direction) {
        this.board = state.board.move(block, direction);
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
