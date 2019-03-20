package gridlock;

import gps.api.Rule;
import gps.api.State;
import java.util.Optional;
import gridlock.BoardGridLock.BlockGridLock;


public class RuleGridLockMove implements Rule {

    private BlockGridLock block;
    private Direction direction;

    public RuleGridLockMove(BlockGridLock block, Direction direction) {
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
        if (state instanceof StateGridLock)
            return Optional.of(new StateGridLock((StateGridLock) state, block, direction));

        return Optional.empty();
    }
}
