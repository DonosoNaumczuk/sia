package ar.edu.itba.sia;

import java.util.Collections;

public class RankSelection extends AccumulativeSelection {

    @Override
    double[] calculateRelativeFitness() {
        int fitnessSum = gaussSum(size);
        Collections.sort(chromosomes);

        for (int i = 0; i < size; i++)
            relativeFitness[i] = ((double)(i + 1)) / fitnessSum;

        return relativeFitness;
    }

    private int gaussSum(final int n) {
        return (n * (n + 1)) / 2;
    }
}
