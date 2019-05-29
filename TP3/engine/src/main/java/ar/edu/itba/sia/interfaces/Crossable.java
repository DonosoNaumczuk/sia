package ar.edu.itba.sia.interfaces;

import ar.edu.itba.sia.Chromosome;

public interface Crossable<C extends Chromosome<C>> {
    C crossover(C chromosome);
}
