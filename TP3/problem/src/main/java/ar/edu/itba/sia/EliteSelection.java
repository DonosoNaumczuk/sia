package ar.edu.itba.sia;

import java.util.ArrayList;
import java.util.Comparator;

public class EliteSelection<C extends Chromosome<C>> extends SelectionMethod<C> {
    @Override
    public ArrayList<C> select(ArrayList<C> individuals, int k) {
        ArrayList<C> elite = new ArrayList<>();
        
        individuals.sort(Comparator.reverseOrder());

        for (int i = 0; i < k; i++)
            elite.add(individuals.get(i));
            
        return elite;
    }
}
