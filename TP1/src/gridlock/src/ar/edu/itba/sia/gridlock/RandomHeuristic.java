package ar.edu.itba.sia.gridlock;

import ar.edu.itba.sia.gps.api.Heuristic;
import ar.edu.itba.sia.gps.api.State;

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
