
package ru.innopolis.homework;


import java.util.Arrays;

/**
 * Класс для сортировки массива целых чисел методом вставки
 */
public class Main {

    public static final int MAX_VALUE = 1000;

    public static void main(String[] args) {
        Integer[] arr = new Integer[100];

        SortArray sr = new SortArray();
        try {
            arr = sr.fillArray(arr, MAX_VALUE);
            arr = sr.sortArray(arr);
            System.out.println(Arrays.toString(arr));
        } catch (NullPointerException e) {
            System.out.println("массив нулевой длины");
        }
    }
}
