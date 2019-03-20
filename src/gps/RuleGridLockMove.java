package gps;

import gps.api.Rule;
import gps.api.State;

import java.util.Optional;

public class RuleGridLockMove implements Rule {

    BlockGridLock block;
    Direction     direction;

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
        String aux = "Move block " + block.toString() + " 1 block to ";
        switch (direction) {
            case RIGHT:
                aux = aux + "right";
                break;
            case LEFT:
                aux = aux + "left";
                break;
            case DOWN:
                aux = aux + "down";
                break;
            case UP:
                aux = aux + "up";
                break;
        }
        return aux;
    }

    @Override
    public Optional<State> apply(State state) {
        if (state instanceof StateGridLock)
            return Optional.of(((StateGridLock) state).move(block, direction));
        return Optional.empty();
    }
}
