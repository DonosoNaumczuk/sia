package ar.edu.itba.sia.selections;

import ar.edu.itba.sia.Individual;
import ar.edu.itba.sia.interfaces.Selectable;

import java.util.ArrayList;

abstract class SelectionMethod implements Selectable<Individual> {
    ArrayList<Individual> individuals;
}