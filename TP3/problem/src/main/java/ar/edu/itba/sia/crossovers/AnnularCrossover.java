package ar.edu.itba.sia.crossovers;

import ar.edu.itba.sia.CharacterChromosome;

import java.util.ArrayList;
import java.util.Random;

public class AnnularCrossover extends UniformCrossover {
    @Override
    public ArrayList<CharacterChromosome> crossover(CharacterChromosome mom, CharacterChromosome dad) {
        ArrayList<Object> alleles1 = mom.getAlleles();
        ArrayList<Object> alleles2 = dad.getAlleles();
        Random rnd                 = new Random();
        int size                   = alleles1.size();
        int locus                  = rnd.nextInt(size);
        int l                      = rnd.nextInt(size / 2) + 1;
        int index;

        for (int i = 0; i < l; i++) {
            index = (locus + i) % size;
            exchangeAlleles(alleles1, alleles2, index);
        }

        return createChildren(alleles1, alleles2, dad);
    }
}
