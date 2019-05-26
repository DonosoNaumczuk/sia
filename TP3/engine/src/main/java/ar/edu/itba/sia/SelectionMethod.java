package ar.edu.itba.sia;

import java.util.ArrayList;

public abstract class SelectionMethod {

    double[] calculateAccumulativeFitness(double[] relativeFitness, int size) {
        double[] accumulativeFitness = new double[size + 1];
        double q0 = 0;
        double accumulatedFitness;

        accumulativeFitness[0] = q0;

        for (int i = 0; i < size; i++) {
            accumulatedFitness = accumulativeFitness[i];
            accumulativeFitness[i] = relativeFitness[i] + accumulatedFitness;
        }

        return accumulativeFitness;
    }

    double[] calculateRelativeFitness(ArrayList<Individual> individuals) {
        int aptitudeSum           = calculateFitnessSum(individuals);
        int n                     = individuals.size();
        double[] relativeFitness = new double[n];

        for (int i = 0; i < n; i++)
            relativeFitness[i] = individuals.get(i).fitness / aptitudeSum;

        return relativeFitness;
    }

    private int calculateFitnessSum(ArrayList<Individual> individuals) {
        int sum = 0;

        for (Individual x : individuals)
            sum += x.fitness;

        return sum;
    }

    ArrayList<Individual> doAccumulativeSelection(ArrayList<Individual> individuals, double[] accumulativeFitness,
                                                  double[] randoms) {
        ArrayList<Individual> selectedList = new ArrayList<>();
        int k                              = randoms.length;
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
}