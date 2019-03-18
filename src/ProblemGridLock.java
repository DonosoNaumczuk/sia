import gps.api.Problem;
import gps.api.Rule;
import gps.api.State;

import java.util.List;

public class ProblemGridLock implements Problem {
    @Override
    public List<Rule> getRules() {
        return null;
    }

    @Override
    public State getInitState() {
        return null;
    }

    @Override
    public boolean isGoal(State state) {
        
        return false;
    }
}
