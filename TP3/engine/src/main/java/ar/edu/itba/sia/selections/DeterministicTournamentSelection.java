package ar.edu.itba.sia.selections;

import ar.edu.itba.sia.Chromosome;

import java.util.*;

public class DeterministicTournamentSelection extends TournamentSelection {
    
    public DeterministicTournamentSelection() {
        m = calculateM();
    }

    public ArrayList<Chromosome> select(final ArrayList<Chromosome> chromosomes, final int k) {
        ArrayList<Chromosome> selectedList = new ArrayList<>();
        Chromosome fittestInRandomSet;
        this.chromosomes                   = chromosomes;

        for (int i = 0; i < k; i++) {
            fittestInRandomSet = getFittestChromosomeFromRandomSubset();
            selectedList.add(fittestInRandomSet);
        }

        return selectedList;
    }

    private int calculateM() {
        int max = 3, min = 2; // Specified in the genetic algorithms class .pdf
        return min + rnd.nextInt((max - min) + 1); // Added 1 because nextInt's bound is exclusive
    }
}
