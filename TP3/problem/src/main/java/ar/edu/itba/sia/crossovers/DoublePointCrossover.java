package ar.edu.itba.sia.crossovers;

import ar.edu.itba.sia.CharacterChromosome;
import ar.edu.itba.sia.Chromosome;
import ar.edu.itba.sia.interfaces.Crossable;
import ar.edu.itba.sia.interfaces.CrossoverMethod;
import ar.edu.itba.sia.interfaces.MutationMethod;

import java.util.ArrayList;
import java.util.Random;

public class DoublePointCrossover implements CrossoverMethod<CharacterChromosome> {
    
    @Override
    public ArrayList<CharacterChromosome> crossover(CharacterChromosome mom, CharacterChromosome dad) {
        ArrayList<ArrayList<Object>> childrenAlleles;
        ArrayList<Object> momAlleles                       = mom.getAlleles();
        ArrayList<Object> dadAlleles                       = dad.getAlleles();
        Random rnd                                         = new Random();
        int locusFirst                                     = rnd.nextInt(momAlleles.size() +  1);
        int locusLast                                      = rnd.nextInt(momAlleles.size() +  1);

        if (locusFirst > locusLast) {
            int aux    = locusFirst;
            locusFirst = locusLast;
            locusLast  = aux;
        }

        childrenAlleles = exchangeAlleles(momAlleles, dadAlleles, locusFirst, locusLast);

        return createChildrenFromAlleles(childrenAlleles, dad);
    }

    ArrayList<ArrayList<Object>> exchangeAlleles(final ArrayList<Object> alleles1, final ArrayList<Object> alleles2,
                                                 final int locusFirst, final int locusLast) {
        ArrayList<ArrayList<Object>> childrenAlleles = new ArrayList<>();
        Object aux;

        for (int i = locusFirst; i < locusLast; i++) {
            aux = alleles1.get(i);
            alleles1.add(i, alleles2.get(i));
            alleles2.add(i, aux);
        }

        childrenAlleles.add(alleles1);
        childrenAlleles.add(alleles2);

        return childrenAlleles;
    }

    ArrayList<CharacterChromosome> createChildrenFromAlleles(ArrayList<ArrayList<Object>> alleles, CharacterChromosome other) {
        ArrayList<CharacterChromosome> children  = new ArrayList<>();
        CharacterChromosome child1, child2;
        child1 = new CharacterChromosome(this, other.getMutationMethod(), other.getTraits(),
                other.getMultipliers(), alleles.get(0));
        child2 = new CharacterChromosome(this, other.getMutationMethod(), other.getTraits(),
                other.getMultipliers(), alleles.get(1));

        children.add(child1);
        children.add(child2);

        return children;
    }
}
