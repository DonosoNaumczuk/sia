package ar.edu.itba.sia;

// TODO: Improve implementation
public abstract class Individual implements Comparable<Individual> {
    int fitness;

    public int compareTo(Individual other) {
        return this.fitness - other.fitness;
    }
}
