package ar.edu.itba.sia;

import ar.edu.itba.sia.interfaces.Selectable;

import java.util.ArrayList;

abstract class SelectionMethod<T extends Chromosome> implements Selectable<T> {
    private double proportion;
    ArrayList<T> chromosomes;

    public double getProportion() {
        return proportion;
    }

    public void setProportion(double proportion) {
        this.proportion = proportion;
    }
}