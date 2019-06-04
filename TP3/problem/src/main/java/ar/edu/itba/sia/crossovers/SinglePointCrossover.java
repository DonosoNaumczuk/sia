package ar.edu.itba.sia.crossovers;

import ar.edu.itba.sia.CharacterChromosome;
import ar.edu.itba.sia.RandomStatic;

import java.util.ArrayList;

public class SinglePointCrossover extends DoublePointCrossover {
    public SinglePointCrossover(double probability) {
        super(probability);
    }

    @Override
    public ArrayList<CharacterChromosome> crossover(CharacterChromosome mom, CharacterChromosome dad) {
        ArrayList<ArrayList<Object>> childrenAlleles;
        int size = mom.getAlleles().size();
        int locus = RandomStatic.nextInt(size + 1);
        if(probability > RandomStatic.nextDouble()) {
            childrenAlleles = exchangeAlleles(mom.getAlleles(), dad.getAlleles(), locus, size);
        }
        else {
            childrenAlleles = new ArrayList<>();
            childrenAlleles.add(mom.getAlleles());
            childrenAlleles.add(dad.getAlleles());
        }

        return createChildrenFromAlleles(childrenAlleles, dad);
    }
}
