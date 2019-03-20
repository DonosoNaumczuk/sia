package gps;

import gps.api.State;

public class StateGridLock implements State {
    private BoardGridLock board;

    public StateGridLock(BoardGridLock board) {
        this.board = board;
    }

    @Override
    public boolean equals(Object object) {
        if(object == null)
            return false;

        if(object == this)
            return true;

        if(object instanceof StateGridLock)
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

    StateGridLock move(BlockGridLock block, Direction direction) { //TODO: constuctor
        //StateGridLock aux = this.clone();//TODO:exeption
        //TODO: aux.board.move(block, direction);
        //return aux;
        return null;
    }
}
