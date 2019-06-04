package ar.edu.itba.sia.mutations;

import ar.edu.itba.sia.CharacterChromosome;
import ar.edu.itba.sia.RandomStatic;

import java.util.ArrayList;

public class MutationMethodUniformOneGen extends MutationMethodCharacter {

    public MutationMethodUniformOneGen(double probability) {
        super(probability);
    }

    @Override
    public CharacterChromosome mutate(CharacterChromosome thisChromosome) {
        ArrayList<Object> alleles = thisChromosome.getAlleles();
        if(RandomStatic.nextDouble() < probability) {
            int position = RandomStatic.nextInt(alleles.size());
            alleles = mutateAPosition(position, alleles);
        }
        return new CharacterChromosome(thisChromosome.getCrossoverMethod(), thisChromosome.getMutationMethod(),
                thisChromosome.getTraits(), thisChromosome.getMultipliers(), alleles);    }

    @Override
    public void update() {}
}
