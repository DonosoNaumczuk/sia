package ar.edu.itba.sia;

import java.util.ArrayList;
import java.util.SortedSet;
import java.util.TreeSet;

abstract class TournamentSelection<C extends Chromosome<C>> extends SelectionMethod<C> {
    int m; // Amount of randomly chosen individuals

    C getFittestChromosomeFromRandomSubset(final ArrayList<C> uniqueChromosomes) {
        SortedSet<C> randoms = createRandomSubset(uniqueChromosomes);
        return randoms.last();
    }

    C getLeastFitChromosomeFromRandomSubset(final ArrayList<C> uniqueChromosomes) {
        SortedSet<C> randoms = createRandomSubset(uniqueChromosomes);
        return randoms.first();
    }

    private SortedSet<C> createRandomSubset(final ArrayList<C> uniqueChromosomes) {
        return uniqueChromosomes.size() < m ?
                allowToFightSelfInTournament(uniqueChromosomes) :
                getRandomsFromRandomFights(uniqueChromosomes);
    }

    private SortedSet<C> allowToFightSelfInTournament(final ArrayList<C> uniqueChromosomes) {
        SortedSet<C> randoms = new TreeSet<>();

        for (int j = 0; j < m; j++)
            randoms.add(uniqueChromosomes.get(RandomStatic.nextInt(uniqueChromosomes.size())));

        return randoms;
    }

    private SortedSet<C> getRandomsFromRandomFights(final ArrayList<C> uniqueChromosomes) {
        SortedSet<C> randoms = new TreeSet<>();
        C randomChromosome;

        for (int j = 0; j < m; j++)
            do
                randomChromosome = uniqueChromosomes.get(RandomStatic.nextInt(uniqueChromosomes.size()));
            while (!randoms.add(randomChromosome));

        return randoms;
    }
}
