package ar.edu.itba.sia.interfaces;

public interface Crossable<T> {
    T crossover(T t1, T t2);
}
