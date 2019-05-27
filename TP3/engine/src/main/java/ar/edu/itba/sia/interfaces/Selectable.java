package ar.edu.itba.sia.interfaces;

import ar.edu.itba.sia.Individual;

import java.util.ArrayList;

public interface Selectable<T> {
    ArrayList<T> select(final ArrayList<Individual> individuals, final int k);
}
