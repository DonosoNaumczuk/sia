package ar.edu.itba.sia.selections;

import ar.edu.itba.sia.Chromosome;
import ar.edu.itba.sia.interfaces.Selectable;

import java.util.ArrayList;

abstract class SelectionMethod implements Selectable<Chromosome> {
    ArrayList<Chromosome> chromosomes;
}