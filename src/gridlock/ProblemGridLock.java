package gridlock;

import gps.api.Problem;
import gps.api.Rule;
import gps.api.State;
import java.awt.Point;
import java.util.LinkedList;
import java.util.List;
import gridlock.BoardGridLock.BlockGridLock;

public class ProblemGridLock implements Problem {

    private List<Rule> rules;
    private StateGridLock initState;

    public ProblemGridLock(StateGridLock initState) {
        this.initState  = initState;
        this.rules      = new LinkedList<>();

        for (BlockGridLock block : initState.getBoard().getBlocks()) {
            rules.add(new RuleGridLock(block, block.getFirstDirection()));
            rules.add(new RuleGridLock(block, block.getSecondDirection()));
        }
    }

    public ProblemGridLock(BoardGridLock boardGridLock) {
        this(new StateGridLock(boardGridLock));
    }

    @Override
    public List<Rule> getRules() {
        return rules;
    }

    @Override
    public State getInitState() {
        return initState;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null)
            return false;

        if (object == this)
            return true;

        if (object instanceof ProblemGridLock)
            return ((ProblemGridLock) object).initState.equals(this.initState) &&
                    ((ProblemGridLock) object).rules.equals(this.rules);

        return false;
    }

    @Override
    public boolean isGoal(State state) {
        if (state == null)
            throw new IllegalArgumentException("The given state can not be null");

        if (!(state instanceof  StateGridLock))
            throw new IllegalArgumentException("The given state object must be a StateGridLock");

        StateGridLock stateGridLock = (StateGridLock) state;
        BlockGridLock goalBlock     = stateGridLock.getBoard().getGoalBlock();
        Point exit                  = stateGridLock.getBoard().getExit();

        return goalBlock.getBegin().equals(exit) || goalBlock.getEnd().equals(exit);
    }
}
