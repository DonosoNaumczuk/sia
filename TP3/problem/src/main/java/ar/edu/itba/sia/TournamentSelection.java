package ar.edu.itba.sia;

import ar.edu.itba.sia.Chromosome;
import ar.edu.itba.sia.SelectionMethod;

import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

abstract class TournamentSelection extends SelectionMethod {
    int m; // Amount of randomly chosen individuals
    Random rnd = new Random(); // Random instance helper

    Chromosome getFittestChromosomeFromRandomSubset() {
        SortedSet<Chromosome> randoms = createRandomSubset();
        return randoms.last();
    }

    Chromosome getLeastFitChromosomeFromRandomSubset() {
        SortedSet<Chromosome> randoms = createRandomSubset();
        return randoms.first();
    }

    private SortedSet<Chromosome> createRandomSubset() {
        Chromosome randomChromosome;
        SortedSet<Chromosome> randoms = new TreeSet<>();
        Random rnd                    = new Random();

        for (int j = 0; j < m; j++) {
            randomChromosome = chromosomes.get(rnd.nextInt(chromosomes.size()));
            while (randoms.add(randomChromosome));
        }

        return randoms;
    }
}
