package ar.edu.itba.sia;

import java.util.Arrays;

public class TestMain {

    public static void main(String args[]) {
        ItemCreator.createAllFromFiles();
        ItemStorage itemStorage = ItemStorage.getInstance();
        System.out.println(itemStorage.getBoots()[0].getStrength());
    }
}
