package ar.edu.itba.sia.interfaces;

import java.util.ArrayList;

public interface Selectable<T> {
    ArrayList<T> select(final ArrayList<T> individuals, final int k);
}
