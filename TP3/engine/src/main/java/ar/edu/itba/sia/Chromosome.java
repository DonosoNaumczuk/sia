package ar.edu.itba.sia;

abstract public class Chromosome implements Comparable<Chromosome>{
    private int fitness;

    public int getFitness() {
        return fitness;
    }

    public int compareTo(Chromosome other) {
        return this.fitness - other.fitness;
    }
}
