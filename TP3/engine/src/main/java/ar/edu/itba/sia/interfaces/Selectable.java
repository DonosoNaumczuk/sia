package ar.edu.itba.sia.interfaces;

import ar.edu.itba.sia.Chromosome;

import java.util.ArrayList;

public interface Selectable<C extends Chromosome<C>> {
    ArrayList<C> select(final ArrayList<C> individuals, final int k);
}
