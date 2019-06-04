package ar.edu.itba.sia;

import java.util.ArrayList;

public class ProbabilisticTournamentSelection<C extends Chromosome<C>> extends TournamentSelection<C> {

    public ProbabilisticTournamentSelection() {
        m = 2; // Specified in the genetic algorithms class .pdf
    }

    public ArrayList<C> select(final ArrayList<C> chromosomes, final int k) {
        ArrayList<C> selectedList   = new ArrayList<>();
        this.chromosomes            = chromosomes;
        C selectedChromosome;

        for (int i = 0; i < k; i++) {
            selectedChromosome = selectChromosome();
            selectedList.add(selectedChromosome);
        }

        return selectedList;
    }

    private C selectChromosome() {
        double r = RandomStatic.nextDouble();

        if (r < 0.75) // Specified in the genetic algorithms class .pdf
            return getFittestChromosomeFromRandomSubset();
        else
            return getLeastFitChromosomeFromRandomSubset();
    }
}
