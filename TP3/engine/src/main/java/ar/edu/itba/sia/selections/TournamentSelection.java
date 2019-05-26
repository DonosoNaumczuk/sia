package ar.edu.itba.sia.selections;

import ar.edu.itba.sia.Individual;

import java.util.ArrayList;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

abstract class TournamentSelection extends SelectionMethod {
    int m; // Amount of randomly chosen individuals
    int k; // Iterations
    Random rnd = new Random(); // Random instance helper

    private void setParametersForSelection(final ArrayList<Individual> individuals, final int k) {
        this.individuals = individuals;
        this.k           = k;
    }

    Individual getFittestIndividualFromRandomSubset() {
        SortedSet<Individual> randoms = createRandomSubset();
        return randoms.last();
    }

    Individual getLeastFitIndividualFromRandomSubset() {
        SortedSet<Individual> randoms = createRandomSubset();
        return randoms.first();
    }

    private SortedSet<Individual> createRandomSubset() {
        Individual randomIndividual;
        SortedSet<Individual> randoms = new TreeSet<>();
        Random rnd                    = new Random();

        for (int j = 0; j < m; j++) {
            randomIndividual = individuals.get(rnd.nextInt(individuals.size()));
            while (randoms.add(randomIndividual));
        }

        return randoms;
    }
}
