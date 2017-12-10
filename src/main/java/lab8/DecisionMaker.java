package lab8;

import com.sun.istack.internal.Nullable;
import com.sun.org.apache.xalan.internal.xsltc.compiler.util.InternalError;
import com.sun.org.apache.xalan.internal.xsltc.util.IntegerArray;

import java.util.Arrays;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Дмитрий on 10.01.2017.
 */
public class DecisionMaker {
    //Число альтернатив
    private static final int numAlts = 5;
    //Число сценариев
    private static final int numScenarios = 5;
    private static final Random randomizer = new Random();
    //Коэффициент для критерия Гурвица
    private static final double trust = 0.5;

    public static void main(String[] args) {
        int[][] matrix = generateMatrix(numAlts, numScenarios, 10);
        System.out.println("Исходная матрица");
        printMatrix(matrix);

        int maxMaxAlt = maxMax(matrix);
        printResult("МаксМакс", matrix, maxMaxAlt);

        int maxMInAlt = wald(matrix);
        printResult("Максимин (Вальд)", matrix, maxMInAlt);

        int minMaxAlt = savidge(matrix);
        printResult("Минимакс (Сэвидж)", matrix, minMaxAlt);

        int hurwitzValue = hurwitz(matrix, trust);
        printResult("Гурвиц", matrix, hurwitzValue);
    }

    //Генерация случайной матрицы cо случайными числами не больше range
    private static int[][] generateMatrix(int rows, int columns, int range) {
        int[][] result = new int[rows][columns];
        for (int i = 0; i < rows; i++)
            for (int j = 0; j < columns; j++)
                result[i][j] = randomizer.nextInt(range);
        return result;
    }

    //"Оптимистичный" поиск лучшей альтернативы
    private static int maxMax(int[][] matrix)
    {
        int bestIndex = -1;
        int bestValue = -1;
        for (int i = 0; i < matrix.length; i++)
            for (int j = 0; j < matrix[i].length; j++)
                if (bestValue < matrix[i][j]) {
                    bestValue = matrix[i][j];
                    bestIndex = i;
                }
        return  bestIndex;
    }

    //"Осторожный" критерий Вальда - наилучший из наихудших исходов
    private static int wald(int[][] matrix)
    {
        int maxmin = -1;
        int maxminIndex = -1;
        for (int i = 0; i < matrix.length; i++) {
            int min = Integer.MAX_VALUE;
            for (int j = 0; j < matrix[i].length; j++)
                if (matrix[i][j] < min)
                    min = matrix[i][j];
            if (maxmin < min)
            {
                maxmin = min;
                maxminIndex = i;
            }
        }
        return maxminIndex;
    }

    //Критерий Сэвиджа - минимизация потерь
    private static int savidge(int[][] matrix)
    {
        // Сначала формируем матрицу потерь
        int[][] lossMatrix = new int[matrix.length][matrix[0].length];
        for (int j = 0; j < matrix[0].length; j++)
        {
            int maxProfit = -1;
            for (int[] aMatrix : matrix)
                maxProfit = Math.max(aMatrix[j], maxProfit);
            for (int i = 0; i < matrix.length; i++)
                lossMatrix[i][j] = maxProfit - matrix[i][j];
        }
        System.out.println("Матрица потерь:");
        printMatrix(lossMatrix);
        // Затем ищем в матрице потерь максимальные потери для каждой альтернативы и минимизируем их
        int minmax = Integer.MAX_VALUE;
        int minmaxIndex = -1;
        for (int i = 0; i < lossMatrix.length; i++) {
            int max = -1;
            for (int j = 0; j < lossMatrix[i].length; j++)
                max = Math.max(lossMatrix[i][j], max);

            if (max < minmax)
            {
                minmax = max;
                minmaxIndex = i;
            }
        }
        return minmaxIndex;
    }

    private static int hurwitz(int matrix[][], double trust)
    {
        double maxValue = -1;
        int maxIndex = -1;
        for (int i = 0; i < matrix.length; i++) {
            int min = Integer.MAX_VALUE;
            int max = -1;
            for (int j = 0; j < matrix[i].length; j++) {
                min = Math.min(min, matrix[i][j]);
                max = Math.max(max, matrix[i][j]);
            }
            double altValue = trust * max + (1 - trust) * max;

            if (maxValue < altValue)
            {
                maxValue = altValue;
                maxIndex = i;
            }
        }
        return maxIndex;
    }

    // Вывод в консоль результатов. При выводе номер альтернатив инкрементируется на 1, т.к. индексы в java начинаются с нуля
    private static void printResult(String methodName, int[][] matrix, int altIndex, @Nullable Integer value)
    {
        String altRow = Arrays.stream(matrix[altIndex]).boxed().map(String::valueOf).collect(Collectors.joining(" "));
        System.out.println("Лучшая альтернатива по критерию '" + methodName + "': " + (altIndex + 1));
        System.out.println("со значениями: " + altRow);
        if (value != null)
            System.out.println("с весом по критерию '" + methodName + "' " + value);
        System.out.println();
    }

    private static void printResult(String methodName, int[][] matrix, int altIndex)
    {
        printResult(methodName, matrix, altIndex, null);
    }

    private static void printMatrix(int[][] matrix)
    {
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++)
                System.out.print(matrix[i][j] + " ");
            System.out.println();
        }
        System.out.println();
    }


    private static int[] leibniz(int[][] matrix, int[] probabilities)
    {
        int[] result = new int[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            int altValue = 0;
            for (int j = 0; j < matrix[i].length; j++)
                altValue += matrix[i][j] + probabilities[j];
            result[i] = altValue;
        }
        return result;
    }
}
