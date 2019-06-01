package ar.edu.itba.sia.crossovers;

import ar.edu.itba.sia.CharacterChromosome;

import java.util.ArrayList;
import java.util.Random;

public class SinglePointCrossover extends DoublePointCrossover {
    
    @Override
    public ArrayList<CharacterChromosome> crossover(CharacterChromosome mom, CharacterChromosome dad) {
        ArrayList<Object> momAlleles = mom.getAlleles();
        ArrayList<Object> dadAlleles = dad.getAlleles();
        ArrayList<ArrayList<Object>> childrenAlleles;
        Random rnd = new Random();
        int locus = rnd.nextInt(momAlleles.size() + 1);

        childrenAlleles = exchangeAlleles(momAlleles, dadAlleles, locus, momAlleles.size());

        return createChildrenFromAlleles(childrenAlleles, dad);
    }
}
