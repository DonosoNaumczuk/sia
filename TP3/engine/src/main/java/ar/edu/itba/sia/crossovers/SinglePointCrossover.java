package ar.edu.itba.sia.crossovers;

import ar.edu.itba.sia.Chromosome;
import ar.edu.itba.sia.interfaces.Crossable;

import java.util.ArrayList;
import java.util.Random;

public class SinglePointCrossover extends DoublePointCrossover {

    /*
    @Override
    public Chromosome crossover(Chromosome mom, Chromosome dad) {
        ArrayList<Trait> momTraits                 = mom.getTraits();
        ArrayList<Trait> dadTraits                 = dad.getTraits();
        ArrayList<ArrayList<Trait>> childrenTraits = new ArrayList<>();
        Random rnd                                 = new Random();
        int locus                                  = rnd.nextInt(momTraits.size() +  1);

        childrenTraits = exchangeTraits(momTraits, dadTraits, locus, momTraits.size());

        return createChildrenFromTraits(childrenTraits);
    }
    */
}
