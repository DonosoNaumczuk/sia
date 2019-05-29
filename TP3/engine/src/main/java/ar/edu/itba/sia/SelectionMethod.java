package ar.edu.itba.sia;

import ar.edu.itba.sia.interfaces.Selectable;

import java.util.ArrayList;

abstract class SelectionMethod<C extends Chromosome<C>> implements Selectable<C> {
    private double proportion;
    ArrayList<C> chromosomes;

    public double getProportion() {
        return proportion;
    }

    public void setProportion(double proportion) {
        this.proportion = proportion;
    }
}