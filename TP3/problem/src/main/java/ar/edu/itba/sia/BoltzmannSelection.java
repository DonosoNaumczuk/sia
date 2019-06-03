package ar.edu.itba.sia;

public class BoltzmannSelection<C extends Chromosome<C>> extends RouletteSelection<C> {
    private int generations = 0;
    private boolean isThirdReplacementMethod, isFirstCall = true;
    private double[] boltzmannFitness;

    public BoltzmannSelection(final boolean isThirdReplacementMethod) {
        this.isThirdReplacementMethod = isThirdReplacementMethod;
    }

    @Override
    double[] calculateRelativeFitness() {
        double fitnessSum;

        calculateBoltzmannFitness();
        fitnessSum = calculateFitnessSum();

        for (int i = 0; i < size; i++)
            relativeFitness[i] = boltzmannFitness[i] / fitnessSum;

        return relativeFitness;
    }

    private void calculateBoltzmannFitness() {
        checkReplacementMethod();
        double T = Math.pow(Math.E, -0.5 * generations), average; // TODO: T should be scraped from a config file
        double[] exponentialFitness = calculateExponentialFitness(T);
        boltzmannFitness = new double[size];

        average = calculateAverage(exponentialFitness);

        for (int i = 0; i < size; i++)
            boltzmannFitness[i] = exponentialFitness[i] / average;
    }

    private double[] calculateExponentialFitness(final double T) {
        double[] exponentialFitness = new double[size];
        double f;

        for (int i = 0; i < size; i++) {
            f = chromosomes.get(i).getFitness();
            exponentialFitness[i] = Math.exp(f / T);
        }

        return exponentialFitness;
    }

    private double calculateAverage(final double[] arr) {
        double sum = 0.0;

        for (double elem : arr)
            sum += elem;

        return sum / arr.length;
    }

    private void checkReplacementMethod() {
        if (isThirdReplacementMethod) {
            if (isFirstCall) {
                isFirstCall = false;
                generations++;
            } else {
                isFirstCall = true;
            }
        } else {
            generations++;
        }
    }

    private int calculateFitnessSum() {
        int sum = 0;

        for (double x : boltzmannFitness)
            sum += x;

        return sum;
    }
}
