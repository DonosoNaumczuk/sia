package ar.edu.itba.sia.selections;

import ar.edu.itba.sia.Individual;

import java.util.ArrayList;

abstract class AccumulativeSelection extends SelectionMethod {
    int k; // Amount of random r values
    int size; // Amount of individuals
    double[] randoms                     = new double[k];
    double[] relativeFitness             = calculateRelativeFitness();
    private double[] accumulativeFitness = calculateAccumulativeFitness();

    private void setParametersForSelection(final ArrayList<Individual> individuals, final int k) {
        this.individuals = individuals;
        size             = individuals.size();
        this.k           = k;
    }

    private double[] calculateAccumulativeFitness() {
        accumulativeFitness = new double[size + 1];
        double q0 = 0;
        double accumulatedFitness;

        accumulativeFitness[0] = q0;

        for (int i = 0; i < size; i++) {
            accumulatedFitness = accumulativeFitness[i];
            accumulativeFitness[i] = relativeFitness[i] + accumulatedFitness;
        }

        return accumulativeFitness;
    }

    double[] calculateRelativeFitness() {
        int fitnessSum = calculateFitnessSum();
        relativeFitness = new double[size];

        for (int i = 0; i < size; i++)
            relativeFitness[i] = individuals.get(i).getFitness() / fitnessSum;

        return relativeFitness;
    }

    private int calculateFitnessSum() {
        int sum = 0;

        for (Individual x : individuals)
            sum += x.getFitness();

        return sum;
    }

    ArrayList<Individual> doAccumulativeSelection() {
        ArrayList<Individual> selectedList = new ArrayList<>();
        boolean selected;

        for (int i = 1; i <= individuals.size(); i++) {
            selected = false;

            for (int j = 0; j < k && !selected; j++) {
                if (accumulativeFitness[i - 1] < randoms[j] && randoms[j] < accumulativeFitness[i]) {
                    selectedList.add(individuals.get(i - 1)); // Actually adding the i-th individual to the selected list
                    // Subtracting 1 because the accumulative fitness have one more row than the individual fitness
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
