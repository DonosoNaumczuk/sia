package ar.edu.itba.sia;

import ar.edu.itba.sia.Chromosome;
import ar.edu.itba.sia.SelectionMethod;

import java.util.ArrayList;

abstract class AccumulativeSelection extends SelectionMethod {
    int k; // Amount of random r values
    int size; // Amount of chromosomes
    double[] randoms                     = new double[k];
    double[] relativeFitness             = new double[size];
    private double[] accumulativeFitness = calculateAccumulativeFitness();

    public ArrayList<Chromosome> select(final ArrayList<Chromosome> chromosomes, final int k) {
        setRandomRs();
        setParametersForSelection(chromosomes, k);
        relativeFitness = calculateRelativeFitness();
        return doAccumulativeSelection();
    }

    private void setParametersForSelection(final ArrayList<Chromosome> chromosomes, final int k) {
        this.chromosomes = chromosomes;
        size             = chromosomes.size();
        this.k           = k;
    }

    private double[] calculateAccumulativeFitness() {
        accumulativeFitness = new double[size + 1];
        double q0 = 0;
        double accumulatedFitness;

        accumulativeFitness[0] = q0;

        for (int i = 0; i < size; i++) {
            accumulatedFitness     = accumulativeFitness[i];
            accumulativeFitness[i] = relativeFitness[i] + accumulatedFitness;
        }

        return accumulativeFitness;
    }

    double[] calculateRelativeFitness() {
        int fitnessSum = calculateFitnessSum();

        for (int i = 0; i < size; i++)
            relativeFitness[i] = ((double)chromosomes.get(i).getFitness()) / fitnessSum;

        return relativeFitness;
    }

    private int calculateFitnessSum() {
        int sum = 0;

        for (Chromosome x : chromosomes)
            sum += x.getFitness();

        return sum;
    }

    private ArrayList<Chromosome> doAccumulativeSelection() {
        ArrayList<Chromosome> selectedList = new ArrayList<>();
        boolean selected;

        for (int i = 1; i <= chromosomes.size(); i++) {
            selected = false;

            for (int j = 0; j < k && !selected; j++) {
                if (accumulativeFitness[i - 1] < randoms[j] && randoms[j] < accumulativeFitness[i]) {
                    selectedList.add(chromosomes.get(i - 1)); // Actually adding the i-th chromosome to the selected list
                    // Subtracting 1 because the accumulative fitness have one more row than the chromosome fitness
                    selected = true;
                }
            }
        }

        return selectedList;
    }

    void setRandomRs() {
        for (int j = 1; j <= k; j++)
            randoms[j] = Math.random();
    }
}
