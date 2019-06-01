package ar.edu.itba.sia;

import ar.edu.itba.sia.interfaces.*;

public abstract class Chromosome<C extends Chromosome<C>> implements Comparable<Chromosome>, Crossable<C>, Mutable<C> {
    private double fitness;
    private CrossoverMethod<C> crossoverMethod;
    private MutationMethod<C> mutationMethod;

    public Chromosome(CrossoverMethod<C> crossoverMethod, MutationMethod<C> mutationMethod) {
        this.crossoverMethod    = crossoverMethod;
        this.mutationMethod     = mutationMethod;
        this.fitness            = 0;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness() {
        fitness = calculateFitness();
    }

    abstract double calculateFitness();

    public MutationMethod<C> getMutationMethod() {
        return mutationMethod;
    }

    public CrossoverMethod<C> getCrossoverMethod() {
        return crossoverMethod;
    }

    public int compareTo(Chromosome otherChromosome) {
        double difference = this.fitness - otherChromosome.fitness;

        if (difference < 0) {
            return -1;
        }
        else if (difference > 0) {
            return 1;
        }

        return 0;
    }
}
