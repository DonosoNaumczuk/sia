package ar.edu.itba.sia;

import ar.edu.itba.sia.interfaces.Selectable;

import java.util.ArrayList;

abstract class SelectionMethod implements Selectable<Chromosome> {
    private double proportion;
    ArrayList<Chromosome> chromosomes;

    public double getProportion() {
        return proportion;
    }

    public void setProportion(double proportion) {
        this.proportion = proportion;
    }
}