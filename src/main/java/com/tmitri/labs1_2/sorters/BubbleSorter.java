package com.tmitri.labs1_2.sorters;

/**
 * Created by Дмитрий on 10.09.2017.
 */
public class BubbleSorter implements IntSorter{
    @Override
    public void sort(Integer[] sequence) {
        for (int j = 0; j < sequence.length; j++) {
            for (int i = 0; i < sequence.length - 1 - j; i++) {
                if (sequence[i] > sequence[i + 1]) {
                    int k = sequence[i];
                    sequence[i] = sequence[i + 1];
                    sequence[i + 1] = k;
                }
            }
        }
    }
}
