package com.tmitri.labs1_2;


import com.tmitri.labs1_2.searchers.BinarySearch;
import com.tmitri.labs1_2.searchers.IntSearcher;
import com.tmitri.labs1_2.searchers.InterpolatingSearch;
import com.tmitri.labs1_2.searchers.LinearSearch;
import com.tmitri.labs1_2.sorters.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;
import java.util.stream.IntStream;

/**
 * Created by Дмитрий on 10.09.2017.
 */
public class AlgoRunner {
    private  Integer[] testSequenceSample;

    private static final int seqQuantity = 20;

    private static final int seqLowerBound = 1;

    private static final int seqUpperBOund = 100;

    private static final boolean SHOWOUTPUT = true;

    private static final Map<String, IntSorter> sorters = new HashMap<>();

    private static final Map<String, IntSearcher> searchers = new HashMap<>();

    private static final Random random = new Random();

    public static void main(String[] args) {

    }

    @Before
    public void prepare()
    {
        testSequenceSample = RandomSequenceGenerator.generate(seqQuantity, seqLowerBound, seqUpperBOund);
        sorters.put("Bubble Sort", new BubbleSorter());
        sorters.put("Insert Sort", new InsertSorter());
        sorters.put("Quick Sort", new QuickSorter());

        searchers.put("Linear Search", new LinearSearch());
        searchers.put("Binary Search", new BinarySearch());
        searchers.put("Interpolating Search", new InterpolatingSearch());
    }

    @Test
    public void runSortersBenchMark()
    {
        if (SHOWOUTPUT) {
            System.out.println("Сортируемая выборка:");
            System.out.println(Arrays.toString(testSequenceSample));
        }

        sorters.forEach((sorterName, sorter) -> {
            Integer[] sortedExample = new Integer[testSequenceSample.length];
            List<Long> times = new LinkedList<>();
            for (int i = 0; i < 10; i++)
            {
                Integer[] testSequence = testSequenceSample.clone();
                long timeNanosStart = System.nanoTime();
                sorter.sort(testSequence);
                long timeNanos = System.nanoTime() - timeNanosStart;
                Assert.assertTrue(sorterName + ": sequence is sorted ", isSortedAscending(testSequence));
                times.add(timeNanos);
                sortedExample = testSequence;
            }
            System.out.println(sorterName);
            if (SHOWOUTPUT)
            {
                System.out.println("algorithm output: " );
                System.out.println(Arrays.toString(sortedExample));
            }

            //System.out.println("Best time is " + times.stream().min(Comparator.naturalOrder()).orElse(0L) + " nanoseconds;");
            System.out.println("Average time is " + Math.round(times.stream().mapToLong(Long::longValue).average().orElse(0)) + " nanoseconds;");
        });
    }

    @Test
    public void runSearchersBenchmark()
    {
        Integer[] sortedArray = testSequenceSample.clone();
        new QuickSorter().sort(sortedArray);
        if (SHOWOUTPUT) {
            System.out.println("Выборка для поиска:");
            System.out.println(Arrays.toString(sortedArray));
        }
        int searchedIndex = random.nextInt(sortedArray.length);
        int searchedValue = sortedArray[searchedIndex];
        System.out.println("Искомое число - " + searchedValue);

        searchers.forEach((searchName, searcher) -> {
            long timeNanosStart = System.nanoTime();
            System.out.println(searchName);
            int foundIndex = searcher.search(sortedArray, searchedValue);
            long timeNanos = System.nanoTime() - timeNanosStart;
            if (foundIndex == -1)
            {
                System.out.println("value " + searchedValue + " was not found");
            }
            else
            {
                Assert.assertTrue(sortedArray[foundIndex] == searchedValue);
                System.out.println("Value " + searchedValue + " is found on position " + (foundIndex + 1));
                System.out.println("Time is " + timeNanos + " nanoseconds;");
            }

        });





        //System.out.println("Best time is " + times.stream().min(Comparator.naturalOrder()).orElse(0L) + " nanoseconds;");

    }


    private static boolean isSortedAscending(Integer[] sequence) {
        return IntStream.range(0, sequence.length - 1).allMatch(i -> sequence[i] <= sequence[i + 1]);
    }

}
