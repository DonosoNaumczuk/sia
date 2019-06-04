package ar.edu.itba.sia;

import java.util.Random;

public class RandomStatic {

    private static Random randomWithSetSeed;
    private static Long seed;

    public static void initialize() {
        if (randomWithSetSeed == null) {
            if (seed == null)
                seed = System.currentTimeMillis();
            randomWithSetSeed = new Random(seed);
        }
    }

    public static void setSeed(Long randomSeed) {
        if (seed == null)
            seed = randomSeed;
    }

    public static int nextInt(int bound)  {
        return randomWithSetSeed.nextInt(bound);
    }

    public static double nextDouble() {
        return randomWithSetSeed.nextDouble();
    }

    public static String getStringRepresentation() {
        return seed.toString();
    }
}
