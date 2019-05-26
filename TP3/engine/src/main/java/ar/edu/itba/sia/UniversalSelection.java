package ar.edu.itba.sia;

import java.util.ArrayList;

public class UniversalSelection extends AccumulativeSelection {

    public UniversalSelection(final int k) {
        size   = individuals.size();
        this.k = k;
    }

    public ArrayList<Individual> select() {
        double[] randoms = getRandomRs();
        return doAccumulativeSelection(randoms);
    }

    private double[] getRandomRs() {
        double[] randoms = new double[k];
        double r = Math.random();
        double rSubJ;

        for (int j = 1; j <= k; j++) {
            rSubJ = (r + j - 1) / k;
            randoms[j] = rSubJ;
        }

        return randoms;
    }
}
