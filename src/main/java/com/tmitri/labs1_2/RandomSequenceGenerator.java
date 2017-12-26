package com.tmitri.labs1_2;

import java.util.Random;
import java.util.stream.IntStream;

/**
 * Created by Дмитрий on 10.09.2017.
 */
public class RandomSequenceGenerator {
    private static final Random random = new Random();

    public static Integer[] generate(int quantity, int lowerBound, int upperBound) {
        return IntStream.range(0, quantity).boxed()
                .map(i -> random.nextInt(upperBound - lowerBound) + lowerBound).toArray(Integer[]::new);
    }

}
