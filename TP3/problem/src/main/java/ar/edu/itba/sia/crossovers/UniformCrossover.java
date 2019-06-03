package ar.edu.itba.sia.crossovers;

import ar.edu.itba.sia.CharacterChromosome;
import ar.edu.itba.sia.interfaces.CrossoverMethod;

import java.util.ArrayList;
import java.util.Random;

public class UniformCrossover implements CrossoverMethod<CharacterChromosome> {
    double probability;

    public UniformCrossover(double probability) {
        this.probability = probability;
    }

    @Override
    public ArrayList<CharacterChromosome> crossover(CharacterChromosome mom, CharacterChromosome dad) {
        ArrayList<Object> alleles1 = mom.getAlleles();
        ArrayList<Object> alleles2 = dad.getAlleles();

        if(probability > Math.random()) {
            int size = alleles1.size();
            double p = 0.5; // As specified on the class pdf
            Random rnd = new Random();

            for (int i = 0; i < size; i++) {
                if (!alleles1.get(i).equals(alleles2.get(i)) && rnd.nextDouble() < p)
                    exchangeAlleles(alleles1, alleles2, i);
            }
        }

        return createChildren(alleles1, alleles2, dad);
    }

    void exchangeAlleles(ArrayList<Object> alleles1, ArrayList<Object> alleles2, int index) {
        Object aux = alleles1.get(index);
        alleles1.set(index, alleles2.get(index));
        alleles2.set(index, aux);
    }
    
    ArrayList<CharacterChromosome> createChildren(ArrayList<Object> alleles1, ArrayList<Object> alleles2,
                                                          CharacterChromosome other) {
        ArrayList<CharacterChromosome> children = new ArrayList<>();
        CharacterChromosome child1, child2;
        
        child1 = new CharacterChromosome(this, other.getMutationMethod(), other.getTraits(),
                other.getMultipliers(), alleles1);
        child2 = new CharacterChromosome(this, other.getMutationMethod(), other.getTraits(),
                other.getMultipliers(), alleles2);

        children.add(child1);
        children.add(child2);
        
        return children;
    }
}
