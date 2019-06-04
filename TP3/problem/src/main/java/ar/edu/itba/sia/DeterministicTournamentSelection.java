package ar.edu.itba.sia;

import java.util.*;

public class DeterministicTournamentSelection<C extends Chromosome<C>> extends TournamentSelection<C> {
    
    public DeterministicTournamentSelection() {
        m = calculateM();
    }

    public ArrayList<C> select(final ArrayList<C> chromosomes, final int k) {
        ArrayList<C> selectedList   = new ArrayList<>();
        this.chromosomes            = chromosomes;
        C fittestInRandomSet;

        for (int i = 0; i < k; i++) {
            fittestInRandomSet = getFittestChromosomeFromRandomSubset();
            selectedList.add(fittestInRandomSet);
        }

        return selectedList;
    }

    private int calculateM() {
        int max = 3, min = 2; // Specified in the genetic algorithms class .pdf
        return min + RandomStatic.nextInt((max - min) + 1); // Added 1 because nextInt's bound is exclusive
    }
}
