package ar.edu.itba.sia.selections;

import ar.edu.itba.sia.Individual;

import java.util.*;

public class DeterministicTournamentSelection extends TournamentSelection {
    
    public DeterministicTournamentSelection() {
        m = calculateM();
    }

    public ArrayList<Individual> select(final ArrayList<Individual> individuals, final int k) {
        ArrayList<Individual> selectedList = new ArrayList<>();
        Individual fittestInRandomSet;

        for (int i = 0; i < k; i++) {
            fittestInRandomSet = getFittestIndividualFromRandomSubset();
            selectedList.add(fittestInRandomSet);
        }

        return selectedList;
    }

    private int calculateM() {
        int max = 3, min = 2; // Specified in the genetic algorithms class .pdf
        return min + rnd.nextInt((max - min) + 1); // Added 1 because nextInt's bound is exclusive
    }
}
