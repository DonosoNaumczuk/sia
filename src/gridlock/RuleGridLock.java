package gridlock;

import gps.api.Rule;
import gps.api.State;
import java.util.Optional;
import gridlock.BoardGridLock.BlockGridLock;

public class RuleGridLock implements Rule {

    private BlockGridLock block;
    private Direction direction;

    public RuleGridLock(BlockGridLock block, Direction direction) {
        this.block     = block;
        this.direction = direction;
    }

    @Override
    public Integer getCost() {
        return 1;
    }

    @Override
    public String getName() {
        String aux = "Move block #" + block.toString() + " one cell ";

        switch (direction) {
            case RIGHT:
                aux += "to the right";
                break;
            case LEFT:
                aux += "to the left";
                break;
            case DOWN:
                aux += "down";
                break;
            case UP:
                aux += "up";
                break;
        }

        return aux;
    }

    @Override
    public Optional<State> apply(State state) {
        if (state == null)
            throw new RuntimeException("State can't be null");

        if (!(state instanceof StateGridLock))
            throw new RuntimeException("State parameter must be a StateGridLock");

        StateGridLock currentState = (StateGridLock) state;

        if (currentState.canTransitionByApplying(this))
            return Optional.of(new StateGridLock(currentState, this));

        return Optional.empty();
    }

    public BlockGridLock getBlock() {
        return block;
    }

    public Direction getDirection() {
        return direction;
    }

    @Override
    public boolean equals(Object object) {
        if (object == null)
            return false;

        if (object == this)
            return true;

        if (object instanceof RuleGridLock)
            return ((RuleGridLock) object).block.equals(this.block) &&
                    ((RuleGridLock) object).direction == direction;

        return false;
    }
}
