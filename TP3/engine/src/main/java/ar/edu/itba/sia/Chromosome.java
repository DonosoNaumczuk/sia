package ar.edu.itba.sia;

import ar.edu.itba.sia.interfaces.Crossable;
import ar.edu.itba.sia.interfaces.CrossoverMethod;
import ar.edu.itba.sia.interfaces.Mutable;
import ar.edu.itba.sia.interfaces.MutationMethod;

public abstract class Chromosome<T extends Chromosome> implements Comparable<Chromosome>, Crossable<T>, Mutable<T> {
    private double fitness;
    private CrossoverMethod<T> crossoverMethod;
    private MutationMethod<T> mutationMethod;

    public Chromosome(CrossoverMethod<T> crossoverMethod, MutationMethod<T> mutationMethod) {
        this.crossoverMethod    = crossoverMethod;
        this.mutationMethod     = mutationMethod;
    }

    public double getFitness() {
        return fitness;
    }

    public MutationMethod<T> getMutationMethod() {
        return mutationMethod;
    }

    public CrossoverMethod<T> getCrossoverMethod() {
        return crossoverMethod;
    }

    public int compareTo(Chromosome other) {
        double difference = this.fitness - other.fitness;

        if (difference < 0) {
            return -1;
        }
        else if (difference > 0) {
            return 1;
        }

        return 0;
    }
}
