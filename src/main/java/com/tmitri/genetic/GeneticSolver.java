package com.tmitri.genetic;

import java.util.Random;
import java.util.function.Function;

/**
 * Created by Дмитрий on 17.12.2017.
 */
public class GeneticSolver {
    // размер популяции
    private static final  int MAX_POPULATION = 100;
    private static final Random random = new Random();
    /**
     * Обощенный генетический алгоритм поиска решения для equationLeft
     *
     * @param function - левая часть уравнения
     * @param x0 - левая граница диапазона (в области определения ф-ции)
     * @param x1 - правая граница диапазона(в области определения ф-ции)
     * @param eps - окрестность отчки, в которой приближаем ф-цию ( в некотором роде - максимальная допустимая ошибка)
     * @param maxIterations - максимальное число итераций
     * @return - лучшее решение
     */
    public static double solve(Function<Double, Double> function, double x0, double x1, double eps, int maxIterations) {
        double[] population = new double[MAX_POPULATION];
        double[] fitnessSet = new double[MAX_POPULATION];
        int iter = 0;
        // Формирование первого поколения
        for (int i = 0; i < 100; i++)
        {
            population[i] = mutation(x0, x1);
            fitnessSet[i] = function.apply(population[i]);
        }
        sortPopulationByFits(population, fitnessSet);
        // Итерации по поколениям до тех пор, пока не найден удовлетворяющий ответ
        do {
            iter++;
            crossover(population, eps, x0, x1);
            for (int i = 0; i < 100; i++)
                fitnessSet[i] = function.apply(population[i]);
            sortPopulationByFits(population, fitnessSet);
        } while (Math.abs(fitnessSet[0]) > eps && iter < maxIterations);
        System.out.println("Всего получилось " + iter + " итераций");
        return population[0];
    }

    // в качестве мутации берем генерацию случайной величины в диапазоне
    private static double mutation(double x0, double x1)
    {
        return random.nextDouble() * (x1 - x0) + x0;
    }

    // инверсия
    private static double inversion(double x, double eps)  // инверсия: поиск в окрестностях точки
    {
        int sign = 0;
        sign++;
        sign %= 2;
        if (sign == 0) return x - eps;
        else return x + eps;
    }

    // скрещиевание
    private static void crossover(double[] x, double x0, double x1, double eps)
    {
        int k = x.length - 1;
        for (int i = 0; i < 8; i++)
            for (int j = i + 1; j < 8; j++)
            {
                x[k] = (x[i] + x[j]) / 2;
                k--;
            }
        for (int i = 0; i < 8; i++)
        {
            x[k] = inversion(x[i], eps); k--;
            x[k] = inversion(x[i], eps); k--;
        }
        for (int i = 8; i < k; i++)
            x[i] = mutation(x0, x1);
    }

    // сортировка особей в популяции по "приспособляемости"
    private static void sortPopulationByFits(double[] population, double[] fits)
    { 
        for (int i = 0; i < 100; i++)
            for (int j = i + 1; j < 100; j++)
                if (Math.abs(fits[j]) < Math.abs(fits[i])) {
                    double temp = fits[i];
                    fits[i] = fits[j];
                    fits[j] = temp;
                    temp = population[i];
                    population[i] = population[j];
                    population[j] = temp;
                }
    }
}
