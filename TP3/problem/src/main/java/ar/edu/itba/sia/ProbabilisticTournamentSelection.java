package ar.edu.itba.sia;

import java.util.ArrayList;

public class ProbabilisticTournamentSelection extends TournamentSelection {

    public ProbabilisticTournamentSelection() {
        m = 2; // Specified in the genetic algorithms class .pdf
    }

    public ArrayList<Chromosome> select(final ArrayList<Chromosome> chromosomes, final int k) {
        ArrayList<Chromosome> selectedList = new ArrayList<>();
        Chromosome selectedChromosome;
        this.chromosomes                   = chromosomes;

        for (int i = 0; i < k; i++) {
            selectedChromosome = selectChromosome();
            selectedList.add(selectedChromosome);
        }

        return selectedList;
    }

    private Chromosome selectChromosome() {
        // TODO: 5/26/2019 Should we select a new random number each iteration or should we set it beforehand?
        double r = rnd.nextDouble(); // TODO: r € [0,1) and should be r € [0,1]

        if (r < 0.75) // Specified in the genetic algorithms class .pdf
            return getFittestChromosomeFromRandomSubset();
        else
            return getLeastFitChromosomeFromRandomSubset();
    }
}
