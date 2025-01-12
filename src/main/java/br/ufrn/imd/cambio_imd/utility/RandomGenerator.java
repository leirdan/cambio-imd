package br.ufrn.imd.cambio_imd.utility;

import java.util.Random;

public class RandomGenerator {
    private static Random random = new Random();

    private RandomGenerator() {
    }

    public static int getInt(int max) {
        return getInt(0, max);
    }

    public static int getInt(int min, int max) {
        return random.nextInt(min, max);
    }
}
