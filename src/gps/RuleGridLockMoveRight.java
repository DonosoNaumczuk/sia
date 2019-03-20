package gps;

import gps.api.Rule;
import gps.api.State;

import java.util.Optional;

import static gps.Direction.RIGHT;

public class RuleGridLockMoveRight implements Rule {
    BlockGridLock block;

    @Override
    public Integer getCost() {
        return 1;
    }

    @Override
    public String getName() {
        return "Move block " + block.toString() + " 1 block to right";
    }

    @Override
    public Optional<State> apply(State state) {
        if(state.getClass() == StateGridLock.class)
            return Optional.of(((StateGridLock) state).move(block, RIGHT));
        return Optional.empty();
    }
}
