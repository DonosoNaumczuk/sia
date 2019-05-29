package ar.edu.itba.sia.interfaces;

import ar.edu.itba.sia.Chromosome;

public interface MutationMethod<C extends Chromosome<C>> {
    C mutate(C thisChromosome);
}
