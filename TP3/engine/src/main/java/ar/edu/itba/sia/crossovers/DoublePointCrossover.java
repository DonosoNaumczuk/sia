package ar.edu.itba.sia.crossovers;

import ar.edu.itba.sia.Chromosome;
import ar.edu.itba.sia.interfaces.Crossable;

import java.util.ArrayList;
import java.util.Random;

public class DoublePointCrossover {

    /*
    @Override
    public Chromosome crossover(Chromosome mom, Chromosome dad) {
        ArrayList<Trait> momTraits                 = mom.getTraits();
        ArrayList<Trait> dadTraits                 = dad.getTraits();
        ArrayList<ArrayList<Trait>> childrenTraits = new ArrayList<>();
        Random rnd                                 = new Random();
        int locusFirst                             = rnd.nextInt(momTraits.size() +  1);
        int locusLast                              = rnd.nextInt(momTraits.size() +  1);

        if (locusFirst > locusLast) {
            int aux    = locusFirst;
            locusFirst = locusLast;
            locusLast  = locusFirst;
        }

        childrenTraits = exchangeTraits(momTraits, dadTraits, locusFirst, locusLast);

        return createChildrenFromTraits(childrenTraits);
    }

    ArrayList<ArrayList<Trait>> exchangeTraits(final ArrayList<Trait> traits1, final ArrayList<Trait> traits2,
                                                 final int locusFirst, final int locusLast) {
        ArrayList<ArrayList<Trait>> childrenTraits = new ArrayList<>();
        Chromosome aux;

        for (int i = locusFirst; i < locusLast; i++) {
            aux       = traits1.get(i);
            traits1   = traits2.get(i);
            traits2   = aux;
        }

        childrenTraits.add(traits1);
        childrenTraits.add(traits2);

        return childrenTraits;
    }

    ArrayList<Chromosome> createChildrenFromTraits(ArrayList<ArrayList<Trait>> traits) {
        ArrayList<Chromosome> children  = new ArrayList<>();
        Chromosome child1, child2;

        child1 = new Chromosome(traits.get(0));
        child2 = newChromosome(traits.get(1));

        children.add(child1);
        children.add(child2);

        return children;
    }
     */
}
