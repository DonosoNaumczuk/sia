package ar.edu.itba.sia;

import ar.edu.itba.sia.interfaces.Selectable;

import java.util.ArrayList;

public abstract class SelectionMethod implements Selectable<Individual> {
    ArrayList<Individual> individuals;
}