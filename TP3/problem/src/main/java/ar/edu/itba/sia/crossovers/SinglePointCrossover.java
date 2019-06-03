package ar.edu.itba.sia.crossovers;

import ar.edu.itba.sia.CharacterChromosome;

import java.util.ArrayList;
import java.util.Random;

public class SinglePointCrossover extends DoublePointCrossover {
    public SinglePointCrossover(double probability) {
        super(probability);
    }

    @Override
    public ArrayList<CharacterChromosome> crossover(CharacterChromosome mom, CharacterChromosome dad) {
        ArrayList<ArrayList<Object>> childrenAlleles;
        Random rnd = new Random();
        int size = mom.getAlleles().size();
        int locus = rnd.nextInt(size + 1);
        if(probability > Math.random()) {
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
