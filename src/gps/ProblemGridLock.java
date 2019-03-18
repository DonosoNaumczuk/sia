package gps;

import gps.api.Problem;
import gps.api.Rule;
import gps.api.State;

import java.awt.*;
import java.util.List;

public class ProblemGridLock implements Problem {
    private List<Rule> rules;
    private StateGridLock initState;

    @Override
    public List<Rule> getRules() {
        return rules;
    }

    @Override
    public State getInitState() {
        return initState;
    }

    @Override
    public boolean isGoal(State state) {
        //TODO: check if is instanceof after cast and assign
        StateGridLock stateGridLock = (StateGridLock) state;
        BlockGridLock goalBlock = stateGridLock.getBoard().getGoalBlock();
        Point exit = stateGridLock.getBoard().getExit();

        if(goalBlock.getBegin().equals(exit) || goalBlock.getEnd().equals(exit))
            return true;

        return false;
    }
}
