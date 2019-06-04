package ar.edu.itba.sia.crossovers;

import ar.edu.itba.sia.CharacterChromosome;
import ar.edu.itba.sia.RandomStatic;
import ar.edu.itba.sia.interfaces.CrossoverMethod;

import java.util.ArrayList;

public class DoublePointCrossover implements CrossoverMethod<CharacterChromosome> {
    double probability;

    public DoublePointCrossover(double probability) {
        this.probability = probability;
    }

    @Override
    public ArrayList<CharacterChromosome> crossover(CharacterChromosome mom, CharacterChromosome dad) {
        ArrayList<ArrayList<Object>> childrenAlleles;
        ArrayList<Object> momAlleles = mom.getAlleles();
        ArrayList<Object> dadAlleles = dad.getAlleles();
        int locusFirst               = RandomStatic.nextInt(momAlleles.size() +  1);
        int locusLast                = RandomStatic.nextInt(momAlleles.size() +  1);

        if (locusFirst > locusLast) {
            int aux    = locusFirst;
            locusFirst = locusLast;
            locusLast  = aux;
        }

        if(probability > RandomStatic.nextDouble()) {
            childrenAlleles = exchangeAlleles(momAlleles, dadAlleles, locusFirst, locusLast);
        }
        else {
            childrenAlleles = new ArrayList<>();
            childrenAlleles.add(mom.getAlleles());
            childrenAlleles.add(dad.getAlleles());
        }

        return createChildrenFromAlleles(childrenAlleles, dad);
    }

    ArrayList<ArrayList<Object>> exchangeAlleles(final ArrayList<Object> alleles1, final ArrayList<Object> alleles2,
                                                 final int locusFirst, final int locusLast) {
        ArrayList<ArrayList<Object>> childrenAlleles = new ArrayList<>();
        Object aux;

        for (int i = locusFirst; i < locusLast; i++) {
            aux = alleles1.get(i);
            alleles1.set(i, alleles2.get(i));
            alleles2.set(i, aux);
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
