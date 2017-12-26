package com.tmitri.labs1_2.searchers;

/**
 * Created by Дмитрий on 25.12.2017.
 */
public class LinearSearch implements IntSearcher {
    @Override
    public int search(Integer[] sequence, int key) {
        for (int i = 0; i < sequence.length; i++) {
            if (sequence[i] == key)
                return i;
        }
        return -1;
    }
}
