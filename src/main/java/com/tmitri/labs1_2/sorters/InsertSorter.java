package com.tmitri.labs1_2.sorters;

/**
 * Created by Дмитрий on 10.09.2017.
 */
public class InsertSorter implements IntSorter{

    @Override
    public void sort(Integer[] sequence) {

        for (int i = 1; i < sequence.length; i++)
        {
            int elem = sequence[i];
            int j = i;
            while (j > 0 && elem < sequence[j - 1])
            {
                sequence[j] = sequence[j - 1];
                j--;
            }

            sequence[j] = elem;
        }
    }
}
