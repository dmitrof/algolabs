package com.tmitri.genetic;

import org.junit.Assert;
import org.junit.Test;

import java.util.function.Function;

/**
 * Created by Дмитрий on 17.12.2017.
 */
//Запуск поиска решения
public class GeneticRunner {
    private static final double PI = Math.PI;
    //Окрестность
    private static final double EPS = 0.000001;
    //Погрешность валидации(можно использовать EPS вместе нее)
    private static final int MAX_ITERATIONS = 1000;
    private static double low = -100;
    private static double high = 100.0;

    //Левая часть уравнения, которую приближаем
    Function<Double, Double> equationLeft = x -> Math.sin(PI/180 * x) - 2/x;

    //строковое представление ф-ции/уравнения
    String functionString = "sin(pi * x / 180) - 2/x = 0";

    @Test
    public void runGeneticSearch() {
        System.out.println("Уравнение " + functionString);

        double x = GeneticSolver.solve(equationLeft, low, high, EPS, MAX_ITERATIONS);
        System.out.println("Найденное значение: x = " + x);
        Assert.assertTrue("найденное решение неправильно", Math.abs(equationLeft.apply(x)) < 0 + EPS);
    }
}
