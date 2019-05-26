package ar.edu.itba.sia.selections;

import ar.edu.itba.sia.Individual;

import java.util.ArrayList;
import java.util.Collections;

public class RankSelection extends AccumulativeSelection {

    public RankSelection(final ArrayList<Individual> individuals, final int k) {
        this.individuals = individuals;
        this.k           = k;
    }

    public ArrayList<Individual> select() {
        double[] randoms = getRandomRs();
        return doAccumulativeSelection(randoms);
    }

    @Override
    double[] calculateRelativeFitness() {
        int fitnessSum = gaussSum(size);
        Collections.sort(individuals);

        for (int i = 0; i < size; i++)
            relativeFitness[i] = (i + 1) / fitnessSum;

        return relativeFitness;
    }

    private int gaussSum(final int n) {
        return (n * (n + 1)) / 2;
    }
}
