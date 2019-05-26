package ar.edu.itba.sia;

import java.util.ArrayList;

public class ProbabilisticTournamentSelection extends TournamentSelection {

    public ProbabilisticTournamentSelection(final ArrayList<Individual> individuals, final int k) {
        this.individuals = individuals;
        m                = 2; // Specified in the genetic algorithms class .pdf
        this.k           = k;
    }

    public ArrayList<Individual> select() {
        ArrayList<Individual> selectedList = new ArrayList<>();
        Individual selectedIndividual;

        for (int i = 0; i < k; i++) {
            selectedIndividual = selectIndividual();
            selectedList.add(selectedIndividual);
        }

        return selectedList;
    }

    private Individual selectIndividual() {
        // TODO: 5/26/2019 Should we select a new random number each iteration or should we set it beforehand?
        double r = rnd.nextDouble(); // TODO: r € [0,1) and should be r € [0,1]

        if (r < 0.75) // Specified in the genetic algorithms class .pdf
            return getFittestIndividualFromRandomSubset();
        else
            return getLeastFitIndividualFromRandomSubset();
    }
}
