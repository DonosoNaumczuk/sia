package gps;

import gps.api.State;

public class StateGridLock implements State {
    private BoardGridLock board;

    public StateGridLock(BoardGridLock board) {
        this.board = board;
    }

    @Override
    boolean equals(Object state) {
        if(state.getClass() == StateGridLock.class)
            return this.board.equals((StateGridLock)state.board);
        return false;
    }

    @Override
    public String getRepresentation() {
        return board.toString();
    }

    StateGridLock move(BlockGridLock block, Direction direction) { //TODO: constuctor
        //StateGridLock aux = this.clone();//TODO:exeption
        //TODO: aux.board.move(block, direction);
        return aux;
    }
}
