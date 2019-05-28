package ar.edu.itba.sia.interfaces;

public interface CrossoverMethod<X> {
    X crossover(X one, X other);
}
