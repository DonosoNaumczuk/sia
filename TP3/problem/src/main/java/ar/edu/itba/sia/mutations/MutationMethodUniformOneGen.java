package ar.edu.itba.sia.mutations;

import ar.edu.itba.sia.CharacterChromosome;
import ar.edu.itba.sia.interfaces.MutationMethod;

import java.util.ArrayList;
import java.util.Random;

public class MutationMethodUniformOneGen extends MutationMethodCharacter {

    public MutationMethodUniformOneGen(double probability) {
        super(probability);
    }

    @Override
    public CharacterChromosome mutate(CharacterChromosome thisChromosome) {
        ArrayList<Object> alleles = thisChromosome.getAlleles();
        if(Math.random() < probability) {
            int position = new Random().nextInt(alleles.size());
            alleles = mutateAPosition(position, alleles);
        }
        return new CharacterChromosome(thisChromosome, alleles);
    }

    @Override
    public void update() {}
}
