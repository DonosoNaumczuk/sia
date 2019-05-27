package ar.edu.itba.sia.crossovers;

import ar.edu.itba.sia.Chromosome;
import ar.edu.itba.sia.interfaces.Crossable;

import java.util.ArrayList;
import java.util.Random;

public class SinglePointCrossover {

    /*
    @Override
    public Chromosome crossover(Chromosome mom, Chromosome dad) {
        Random rnd = new Random();

        ArrayList<Trait> momTraits =  mom.getTraits();
        ArrayList<Trait> dadTraits =  dad.getTraits();
        int locus = rnd.nextInt(momTraits.size() +  1);

        return exchangeTraits(momTraits, dadTraits);

        return null;
    }

    private ArrayList<Chromosome> exchangeTraits(final ArrayList<Trait> traits1, final ArrayList<Trait> traits2,
                                                 final int locus) {
        ArrayList<Chromosome> children = new ArrayList<>();
        Chromosome child1, child2, aux;

        for (int i = locus - 1; i < traits1.size(); i++) {
            aux       = traits1.get(i);
            traits1   = traits2.get(i);
            traits2   = aux;
        }

        child1 = new Chromosome(traits1);
        child2 = newChromosome(traits2);

        children.add(child1);
        children.add(child2);

        return children;
    }
     */
}
