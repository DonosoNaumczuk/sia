package ar.edu.itba.sia;

import java.util.ArrayList;

public class RouletteSelection extends SelectionMethod {
    private ArrayList<Individual> individuals;
    private int k; // Amount of random r values
    private double[] accumulativeFitness;

    public RouletteSelection(final ArrayList<Individual> individuals, final int k) {
        this.individuals         = individuals;
        this.k                   = k;
        double[] relativeFitness = calculateRelativeFitness(individuals);
        accumulativeFitness      = calculateAccumulativeFitness(relativeFitness, individuals.size());
    }

    public ArrayList<Individual> getSelection() {
        double[] randoms = getRandomRs(k);
        return doAccumulativeSelection(individuals, accumulativeFitness, randoms);
    }

    private double[] getRandomRs(int k) {
        double[] randoms = new double[k];

        for (int j = 1; j <= k; j++)
            randoms[j] = Math.random();

        return randoms;
    }
}
