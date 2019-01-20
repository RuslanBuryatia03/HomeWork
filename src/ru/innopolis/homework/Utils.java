package ru.innopolis.homework;

import java.util.Arrays;

public class Utils {

    private Utils(){
    }




    /**
     * Ищет индекс для вставки в отсортированной части массива
     *
     * @param a - значение, для которого необходимо найти индекс для вставки
     * @param arrFind - отсортированная часть массива
     * @return - возвращает индекс элемента в массиве arrFind вместо которого необходимо
     * вставить а, либо если параметр а больше всех элементов в массиве
     * arrFind, то возвращает размер массива, т.е. дополняем отсортированную часть массива справа
     */
    static int findIndex(int a, Integer[] arrFind) {

        for (int i = 0; i < arrFind.length; i++) {
            if (a < arrFind[i]) {
                return i;
            }
        }
        return arrFind.length;
    }

    /**
     * Производит вставку элемента в отсортированную часть массива
     *
     * @param arrIn - массив, в котором производим вставку
     * @param indexInsert - индекс массива, куда необходимо поместить элемент
     * @param indexCurrent - индекс массива, откуда необходимо поместить элемент
     */
    static void insertToArray(Integer[] arrIn, int indexInsert, int indexCurrent) {
        int temp = arrIn[indexInsert];
        arrIn[indexInsert] = arrIn[indexCurrent];
        for (int i = indexCurrent; i > indexInsert; i--) {
            arrIn[i] = arrIn[i - 1];
        }
        arrIn[indexInsert + 1] = temp;
    }

    /**
     * Возвращает часть массива
     *
     * @param arr - массив из которого производится выборка
     * @param indStart - начальный индекс для подмассива
     * @param indEnd - конечный индекс для подмассива
     */
    static Integer[] subArray(Integer[] arr, int indStart, int indEnd) {
        if ((indStart > indEnd) || (indStart < 0) && (indEnd < 0)){
            throw new NullPointerException();
        } else {
            return Arrays.copyOfRange(arr, indStart, indEnd);
        }
    }
}
