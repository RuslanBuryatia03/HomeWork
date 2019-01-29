package ru.innopolis.homework;

import java.util.Arrays;
import java.util.Random;

/**
 * Класс с массивов Integer и методом сортировки вставками
 *
 * @version 1.0
 * @autor Ruslan Khankhasaev
 */

public class SortArray {

    /**
     * Метод для заполнения массива случайными значениями от 0 до 1000
     *
     * @throws NullPointerException - генерирует исключения, если массив нулевой длины
     */
    public Integer[] fillArray(Integer[] arr, int maxValue) throws NullPointerException {

        final Random random = new Random();
        if (arr.length == 0) {
            throw new NullPointerException();
        } else {
            for (int i = 0; i < arr.length; i++) {
                arr[i] = random.nextInt(maxValue);
            }
        }
        System.out.println(Arrays.toString(arr));
        System.out.println("  ");
        return arr;
    }

    /**
     * Сортирует массив типа Integer методом вставка
     * @param arr массив для сортировки
     * @return отсортированный массив
     */

    public Integer[] sortArray(Integer[] arr) {

        for (int i = 1; i < arr.length; i++) {
            try {
                int indIns = Utils.findIndex(arr[i], Utils.subArray(arr, 0, i));
                if (indIns != i) {
                    Utils.insertToArray(arr, indIns, i); //
                }
            } catch (IllegalArgumentException e) {
                System.out.println("неправильные входные параметры");
            }
        }
        return arr;
    }
}
