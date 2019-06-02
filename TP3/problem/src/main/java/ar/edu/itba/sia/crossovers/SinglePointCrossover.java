package ar.edu.itba.sia.crossovers;

import ar.edu.itba.sia.CharacterChromosome;

import java.util.ArrayList;
import java.util.Random;

public class SinglePointCrossover extends DoublePointCrossover {
    
    @Override
    public ArrayList<CharacterChromosome> crossover(CharacterChromosome mom, CharacterChromosome dad) {
        ArrayList<ArrayList<Object>> childrenAlleles;
        Random rnd = new Random();
        int size   = mom.getAlleles().size();
        int locus  = rnd.nextInt(size + 1);

        childrenAlleles = exchangeAlleles(mom.getAlleles(), dad.getAlleles(), locus, size);

        return createChildrenFromAlleles(childrenAlleles, dad);
    }
}
