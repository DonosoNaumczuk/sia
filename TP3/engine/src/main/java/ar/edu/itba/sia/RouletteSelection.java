package ar.edu.itba.sia;

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

    private double[] getRandomRs() {
        double[] randoms = new double[k];

        for (int j = 1; j <= k; j++)
            randoms[j] = Math.random();

        return randoms;
    }
}
