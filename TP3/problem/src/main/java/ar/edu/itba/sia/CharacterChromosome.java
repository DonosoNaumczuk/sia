package ar.edu.itba.sia;

import ar.edu.itba.sia.interfaces.CrossoverMethod;
import ar.edu.itba.sia.interfaces.MutationMethod;

public class CharacterChromosome extends Chromosome<CharacterChromosome> {

    public CharacterChromosome(CrossoverMethod<CharacterChromosome> crossoverMethod, MutationMethod<CharacterChromosome> mutationMethod) {
        super(crossoverMethod, mutationMethod);
    }

    @Override
    public CharacterChromosome mutate() {
        return super.getMutationMethod().mutate(this);
    }

    @Override
    public CharacterChromosome crossover(CharacterChromosome other) {
        return super.getCrossoverMethod().crossover(this, other);
    }
}
