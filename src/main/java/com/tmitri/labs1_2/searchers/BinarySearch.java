package com.tmitri.labs1_2.searchers;

/**
 * Created by Дмитрий on 16.09.2017.
 */
public class BinarySearch implements IntSearcher{
    @Override
    public int search(Integer[] sequence, int key) {
        return binarySearch(sequence, 0, sequence.length, key);
    }

    private int binarySearch(Integer[] sequence, int left, int right, int key) {
        int pos = (left + right) / 2;
        if (sequence[pos] == key)
            return pos;
        else if (left == right)
            return -1;
        else if (key > sequence[pos])
            return binarySearch(sequence, pos + 1, right, key);
        else
            return binarySearch(sequence, left, pos - 1, key);
    }
}
