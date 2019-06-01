package ar.edu.itba.sia.interfaces;

import ar.edu.itba.sia.Chromosome;

import java.util.ArrayList;

public interface Crossable<C extends Chromosome<C>> {
    ArrayList<C> crossover(C chromosome);
}
