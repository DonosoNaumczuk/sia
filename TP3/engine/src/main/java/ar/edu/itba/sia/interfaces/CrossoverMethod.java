package ar.edu.itba.sia.interfaces;

import ar.edu.itba.sia.Chromosome;

import java.util.ArrayList;

public interface CrossoverMethod<C extends Chromosome<C>> {
    ArrayList<C> crossover(C thisChromosome, C otherChromosome);
}
