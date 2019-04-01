package ar.edu.itba.sia.gridlock;

import ar.edu.itba.sia.gps.api.Heuristic;
import ar.edu.itba.sia.gps.api.State;

public class HeuristicGridLockDistance implements Heuristic {

    @Override
    public Integer getValue(State state) {
        if(!(state instanceof StateGridLock)) {
            throw new RuntimeException("State must be a GridLock state");
        }
        BoardGridLock aux = ((StateGridLock)state).getBoard();

        int aux1, aux2, ans;
        if(aux.getGoalBlock().getFirstDirection() == Direction.UP) {
            aux1 = Math.abs(aux.getGoalBlock().getBegin().y - aux.getExit().y);
            aux2 = Math.abs(aux.getGoalBlock().getEnd().y - aux.getExit().y);
            ans = (aux1<aux2)?aux1:aux2;
        }
        else {
            aux1 = Math.abs(aux.getGoalBlock().getBegin().x - aux.getExit().x);
            aux2 = Math.abs(aux.getGoalBlock().getEnd().x - aux.getExit().x);
            ans = (aux1<aux2)?aux1:aux2;
        }
        return ans;
    }

    @Override
    public String toString() {
        return "Returns the distance from the goal block to the exit";
    }
}