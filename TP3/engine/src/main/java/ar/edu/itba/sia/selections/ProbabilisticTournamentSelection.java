package ar.edu.itba.sia.selections;

import ar.edu.itba.sia.Individual;

import java.util.ArrayList;

public class ProbabilisticTournamentSelection extends TournamentSelection {

    public ProbabilisticTournamentSelection() {
        m = 2; // Specified in the genetic algorithms class .pdf
    }

    public ArrayList<Individual> select(final ArrayList<Individual> individuals, final int k) {
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
