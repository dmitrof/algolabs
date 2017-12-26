package com.tmitri.labs1_2.sorters;

/**
 * Created by Дмитрий on 16.09.2017.
 */
public class QuickSorter implements IntSorter{
    @Override
    public void sort(Integer[] sequence) {
        quickSort(sequence, 0, sequence.length - 1);
    }

    private void quickSort(Integer[] sequence, int left, int right) {
        if (left < right) {
            int pivotIndex = (right + left) / 2;
            int i = left;
            int j = right;
            while (i < j)
            {
                while (sequence[i] <= sequence[pivotIndex] && i < pivotIndex)
                    i++;
                while (sequence[j] >= sequence[pivotIndex] && j > pivotIndex)
                    j--;
                if (i < j) {
                    int temp = sequence[i];
                    sequence[i] = sequence[j];
                    sequence[j] = temp;
                    if (i == pivotIndex)
                        pivotIndex = j;
                    else if (j == pivotIndex)
                        pivotIndex = i;
                }
            }

            quickSort(sequence, left, pivotIndex);
            quickSort(sequence, pivotIndex + 1, right);
        }
    }
}
