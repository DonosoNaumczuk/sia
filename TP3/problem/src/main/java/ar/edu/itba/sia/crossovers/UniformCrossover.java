package ar.edu.itba.sia.crossovers;

import ar.edu.itba.sia.CharacterChromosome;
import ar.edu.itba.sia.interfaces.CrossoverMethod;

import java.util.ArrayList;
import java.util.Random;

public class UniformCrossover implements CrossoverMethod<CharacterChromosome> {

    @Override
    public ArrayList<CharacterChromosome> crossover(CharacterChromosome mom, CharacterChromosome dad) {
        ArrayList<Object> momAlleles = mom.getAlleles();
        ArrayList<Object> dadAlleles = dad.getAlleles();
        ArrayList<CharacterChromosome> children = new ArrayList<>();
        CharacterChromosome child1, child2;
        int size = momAlleles.size();
        double p = 0.5; // As specified on the class pdf
        Random rnd = new Random();

        for (int i = 0; i < size; i++) {
            if (!momAlleles.get(i).equals(dadAlleles.get(i)) && rnd.nextDouble() < p)
                exchangeAlleles(momAlleles, dadAlleles, i);
        }

        child1 = new CharacterChromosome(this, dad.getMutationMethod(), dad.getTraits(),
                dad.getMultipliers(), momAlleles);
        child2 = new CharacterChromosome(this, dad.getMutationMethod(), dad.getTraits(),
                dad.getMultipliers(), dadAlleles);

        children.add(child1);
        children.add(child2);

        return children;
    }

    private void exchangeAlleles(ArrayList<Object> momAlleles, ArrayList<Object> dadAlleles, int index) {
        Object aux = momAlleles.get(index);
        momAlleles.add(dadAlleles.get(index));
        dadAlleles.add(aux);
    }
}
