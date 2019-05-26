package ar.edu.itba.sia.selections;

import ar.edu.itba.sia.Individual;

import java.util.ArrayList;

public class RouletteSelection extends AccumulativeSelection {

    public ArrayList<Individual> select(final ArrayList<Individual> individuals, final int k) {
        setRandomRs();
        return doAccumulativeSelection();
    }
}
