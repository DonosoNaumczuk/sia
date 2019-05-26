package ar.edu.itba.sia;

import java.util.ArrayList;

public class UniversalSelection extends SelectionMethod {
    private ArrayList<Individual> individuals;
    private int k; // Amount of random r values
    private double[] accumulativeFitness;

    public UniversalSelection(final ArrayList<Individual> individuals, final int k) {
        this.individuals         = individuals;
        this.k                   = k;
        double[] relativeFitness = calculateRelativeFitness(individuals);
        accumulativeFitness      = calculateAccumulativeFitness(relativeFitness, individuals.size());
    }

    public ArrayList<Individual> getSelection() {
        double r = Math.random();
        double[] randoms = getRandomRs(r, k);

        return doAccumulativeSelection(individuals, accumulativeFitness, randoms);
    }

    private double[] getRandomRs(double r, int k) {
        double[] randoms = new double[k];
        double rSubJ;

        for (int j = 1; j <= k; j++) {
            rSubJ = (r + j - 1) / k;
            randoms[j] = rSubJ;
        }

        return randoms;
    }
}
