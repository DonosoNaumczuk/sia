package ar.edu.itba.sia.selections;

import ar.edu.itba.sia.Individual;

import java.util.ArrayList;

public class BoltzmannSelection extends AccumulativeSelection {
    @Override
    public ArrayList<Individual> select(ArrayList<Individual> individuals, int k) {

        return super.select(individuals, k);
    }

    @Override
    double[] calculateRelativeFitness() {
        double T = 1.0, average; // TODO: T should be scraped from a config file
        double[] boltzmannFitness = calculateBoltzmannFitness(T);

        average = calculateAverage(boltzmannFitness);

        for (int i = 0; i < size; i++)
            relativeFitness[i] = boltzmannFitness[i] / average;

        return relativeFitness;
    }

    private double[] calculateBoltzmannFitness(final double T) {
        double[] boltzmannFitness = new double[size];
        double f;

        for (int i = 0; i < size; i++) {
            f = individuals.get(i).getFitness();
            boltzmannFitness[i] = Math.exp(f / T);
        }

        return boltzmannFitness;
    }

    private double calculateAverage(final double[] arr) {
        double sum = 0.0;

        for (double elem : arr)
            sum += elem;

        return sum / arr.length;
    }
}
