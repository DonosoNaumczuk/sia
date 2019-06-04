package ar.edu.itba.sia;

import java.util.SortedSet;
import java.util.TreeSet;

abstract class TournamentSelection<C extends Chromosome<C>> extends SelectionMethod<C> {
    int m; // Amount of randomly chosen individuals

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

        for (int j = 0; j < m; j++) {
            do
                randomChromosome = chromosomes.get(RandomStatic.nextInt(chromosomes.size()));
            while (!randoms.add(randomChromosome));
        }

        return randoms;
    }
}
