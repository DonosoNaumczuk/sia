package ar.edu.itba.sia;

import java.util.ArrayList;

public class RouletteSelection<C extends Chromosome<C>> extends SelectionMethod<C> {
    int k; // Amount of random r values
    int size; // Amount of chromosomes
    double[] randoms;
    double[] relativeFitness;
    private double[] accumulativeFitness;

    public ArrayList<C> select(final ArrayList<C> chromosomes, final int k) {
        setParametersForSelection(chromosomes, k);
        setRandomRs();
        relativeFitness     = calculateRelativeFitness();
        accumulativeFitness = calculateAccumulativeFitness();
        return doAccumulativeSelection();
    }

    private void setParametersForSelection(final ArrayList<C> chromosomes, final int k) {
        this.chromosomes = chromosomes;
        size             = chromosomes.size();
        this.k           = k;
        randoms          = new double[k];
        relativeFitness  = new double[size];
    }

    private double[] calculateAccumulativeFitness() {
        double q0 = 0;
        double accumulatedFitness;

        accumulativeFitness = new double[size + 1];
        accumulativeFitness[0] = q0;

        for (int i = 1; i <= size; i++) {
            accumulatedFitness     = accumulativeFitness[i - 1];
            accumulativeFitness[i] = relativeFitness[i - 1] + accumulatedFitness;
        }

        return accumulativeFitness;
    }

    double[] calculateRelativeFitness() {
        int fitnessSum = calculateFitnessSum();

        for (int i = 0; i < size; i++)
            relativeFitness[i] = (chromosomes.get(i).getFitness()) / fitnessSum;

        return relativeFitness;
    }

    private int calculateFitnessSum() {
        int sum = 0;

        for (C x : chromosomes)
            sum += x.getFitness();

        return sum;
    }

    private ArrayList<C> doAccumulativeSelection() {
        ArrayList<C> selectedList = new ArrayList<>();
        boolean selected;

        for (int j = 0; j < k; j++) {
            selected = false;

            for (int i = 1; i <= size && !selected; i++) {
                if (accumulativeFitness[i - 1] <= randoms[j] && randoms[j] < accumulativeFitness[i]) {
                    selectedList.add(chromosomes.get(i - 1)); // Actually adding the i-th chromosome to the selected list
                    // Subtracting 1 because the accumulative fitness have one more row than the chromosome fitness
                    selected = true;
                }
            }
        }

        return selectedList;
    }

    void setRandomRs() {
        for (int j = 0; j < k; j++)
            randoms[j] = Math.random();
    }
}
