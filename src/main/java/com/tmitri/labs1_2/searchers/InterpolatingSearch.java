package com.tmitri.labs1_2.searchers;

/**
 * Created by Дмитрий on 16.09.2017.
 */
public class InterpolatingSearch implements IntSearcher{
    @Override
    public int search(Integer[] sequence, int key) {
        int x = 0;
        int a = 0;
        int b = 9;

        for (boolean found = false; (sequence[a] < key) && (sequence[b] > key) && !found; )
        {
            x = a + ((key - sequence[a]) * (b - a)) / (sequence[b] - sequence[a]);
            if (sequence[x] < key)
                a = x + 1;
            else if (sequence[x] > key)
                b = x - 1;
            else
                found = true;
        }

        if (sequence[a] == key)
            return a;
        else if (sequence[b] == key)
            return b;
        else
            return - 1;
    }
}
