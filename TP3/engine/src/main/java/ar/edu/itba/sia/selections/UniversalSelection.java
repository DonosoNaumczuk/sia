package ar.edu.itba.sia.selections;

import ar.edu.itba.sia.Individual;

import java.util.ArrayList;

public class UniversalSelection extends AccumulativeSelection {

    public ArrayList<Individual> select(final ArrayList<Individual> individuals, final int k) {
        setRandomRs();
        return doAccumulativeSelection();
    }

    @Override
    void setRandomRs() {
        double r = Math.random();
        double rSubJ;

        for (int j = 1; j <= k; j++) {
            rSubJ = (r + j - 1) / k;
            randoms[j] = rSubJ;
        }
    }
}
