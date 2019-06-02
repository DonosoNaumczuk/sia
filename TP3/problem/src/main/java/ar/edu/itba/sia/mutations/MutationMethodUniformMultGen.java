package ar.edu.itba.sia.mutations;

import ar.edu.itba.sia.CharacterChromosome;

import java.util.ArrayList;

public class MutationMethodUniformMultGen extends MutationMethodCharacter {

    public MutationMethodUniformMultGen(double probability) {
        super(probability);
    }

    @Override
    public CharacterChromosome mutate(CharacterChromosome thisChromosome) {
        ArrayList<Object> alleles = thisChromosome.getAlleles();
        int position = 0;
        while (position < alleles.size()) {
            if (Math.random() < probability) {
                alleles = mutateAPosition(position, alleles);
            }
            position++;
        }
        return new CharacterChromosome(thisChromosome.getCrossoverMethod(), thisChromosome.getMutationMethod(),
                thisChromosome.getTraits(), thisChromosome.getMultipliers(), alleles);
    }

    @Override
    public void update() {}
}
