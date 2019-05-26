package ar.edu.itba.sia.selections;

import ar.edu.itba.sia.Individual;

import java.util.ArrayList;

public class RouletteSelection extends AccumulativeSelection {

    public RouletteSelection(final int k) {
        size   = individuals.size();
        this.k = k;
    }

    public ArrayList<Individual> select() {
        double[] randoms = getRandomRs();
        return doAccumulativeSelection(randoms);
    }
}
