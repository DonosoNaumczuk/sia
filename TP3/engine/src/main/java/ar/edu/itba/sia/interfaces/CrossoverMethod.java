package ar.edu.itba.sia.interfaces;

import ar.edu.itba.sia.Chromosome;

public interface CrossoverMethod<C extends Chromosome<C>> {
    C crossover(C thisChromosome, C otherChromosome);
}
