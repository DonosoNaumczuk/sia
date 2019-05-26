package ar.edu.itba.sia;

import ar.edu.itba.sia.interfaces.Crossable;
import ar.edu.itba.sia.interfaces.Mutable;

public class WarriorChromosome implements Crossable<WarriorChromosome>, Mutable<WarriorChromosome> {
    //TODO: define attributes

    @Override
    public WarriorChromosome crossover(WarriorChromosome other) {
        //TODO: cross-over with other warrior chromosome to generate a new one and return it
        return null;
    }

    @Override
    public WarriorChromosome mutate() {
        //TODO: mutate own chromosome to return a new one
        return null;
    }
}
