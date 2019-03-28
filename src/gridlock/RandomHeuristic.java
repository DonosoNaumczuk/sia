package gridlock;

import gps.api.Heuristic;
import gps.api.State;

import java.util.Random;

public class RandomHeuristic implements Heuristic {
    @Override
    public Integer getValue(State state) {
        return new Random().nextInt(10) + 1;
    }

    @Override
    public String toString() {
        return "Return random integers";
    }
}
