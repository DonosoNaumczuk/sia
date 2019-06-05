package ar.edu.itba.sia;

import java.util.ArrayList;
import java.util.HashSet;

public class ProbabilisticTournamentSelection<C extends Chromosome<C>> extends TournamentSelection<C> {

    public ProbabilisticTournamentSelection() {
        m = 2; // Specified in the genetic algorithms class .pdf
    }

    public ArrayList<C> select(final ArrayList<C> chromosomes, final int k) {
        ArrayList<C> selectedList      = new ArrayList<>();
        ArrayList<C> uniqueChromosomes = new ArrayList<>(new HashSet<>(chromosomes));
        C selectedChromosome;

        for (int i = 0; i < k; i++) {
            selectedChromosome = selectChromosome(uniqueChromosomes);
            selectedList.add(selectedChromosome);
        }

        return selectedList;
    }

    private C selectChromosome(final ArrayList<C> uniqueChromosomes) {
        double r = RandomStatic.nextDouble();

        if (r < 0.75) // Specified in the genetic algorithms class .pdf
            return getFittestChromosomeFromRandomSubset(uniqueChromosomes);
        else
            return getLeastFitChromosomeFromRandomSubset(uniqueChromosomes);
    }
}
