package ar.edu.itba.sia.interfaces;

import ar.edu.itba.sia.Chromosome;

public interface Mutable<C extends Chromosome<C>> {
    C mutate();
}
