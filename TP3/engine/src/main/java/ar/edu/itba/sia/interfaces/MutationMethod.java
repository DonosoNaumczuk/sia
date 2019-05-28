package ar.edu.itba.sia.interfaces;

public interface MutationMethod<T> {
    T mutate(T chromosome);
}
