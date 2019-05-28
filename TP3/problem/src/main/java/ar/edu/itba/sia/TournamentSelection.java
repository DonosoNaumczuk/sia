package ar.edu.itba.sia;

import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

abstract class TournamentSelection<C extends Chromosome<C>> extends SelectionMethod<C> {
    int m; // Amount of randomly chosen individuals
    Random rnd = new Random(); // Random instance helper

    C getFittestChromosomeFromRandomSubset() {
        SortedSet<C> randoms = createRandomSubset();
        return randoms.last();
    }

    C getLeastFitChromosomeFromRandomSubset() {
        SortedSet<C> randoms = createRandomSubset();
        return randoms.first();
    }

    private SortedSet<C> createRandomSubset() {
        C randomChromosome;
        SortedSet<C> randoms = new TreeSet<>();
        Random rnd                    = new Random();

        for (int j = 0; j < m; j++) {
            randomChromosome = chromosomes.get(rnd.nextInt(chromosomes.size()));
            while (randoms.add(randomChromosome)); //TODO: Plz, explain this later
        }

        return randoms;
    }
}
