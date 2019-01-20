package ru.innopolis.homework;

import java.util.Arrays;



/**
 * Класс с массивов Integer и методом сортировки вставками
 * @autor Ruslan Khankhasaev
 * @version 1.0
 */

public class SortArray {


    /**
     * Метод для заполнения массива случайными значениями от 0 до 1000
     * @exception NullPointerException - генерирует исключения, если массив нулевой длины
     */
    public Integer[] fillArray(Integer[] arr, int maxValue) throws NullPointerException{

        if (arr.length == 0) {
            throw new NullPointerException();
        } else {
            for (int i = 0; i < arr.length; i++) {
                arr[i] = (int) Math.round(Math.random() * maxValue);
            }
        }

        for (int i = 0; i < arr.length; i++) {
            System.out.print("  " + arr[i]);
        }
        System.out.println("  ");
        return arr;
    }


    public Integer[] sortArray(Integer[] arr) {

        for (int i = 1; i < arr.length; i++) {
            try {
                int indIns = Utils.findIndex(arr[i], Utils.subArray(arr, 0, i));
                if (indIns == i) {
                    continue;
                } else {
                    Utils.insertToArray(arr, indIns, i); //
                }
            } catch (NullPointerException e) {
                System.out.println("неправильные входные параметры");
            }

        }

        for (int i = 0; i < arr.length; i++) {
            System.out.print("  " + arr[i]);
        }
        return arr;
    }

//    static Integer[] subArray(Integer[] arr, int indStart, int indEnd) {
//        if ((indStart > indEnd) || (indStart < 0) && (indEnd < 0)){
//            throw new NullPointerException();
//        } else {
//            return Arrays.copyOfRange(arr, indStart, indEnd);
//        }
//    }

//    /**
//     * Ищет индекс для вставки в отсортированной части массива
//     *
//     * @param a - значение, для которого необходимо найти индекс для вставки
//     * @param arrFind - отсортированная часть массива
//     * @return - возвращает индекс в массиве arrFind, либо если параметр а больше всех элементов в массиве
//     * arrFind, то возвращает размер массива, т.е. дополняем отсортированную часть массива справа
//     */
//    static int findIndex(int a, Integer[] arrFind) {
//
//        for (int i = 0; i < arrFind.length; i++) {
//            if (a < arrFind[i]) {
//                return i;
//            }
//        }
//        return arrFind.length;
//    }


//    /**
//     * Производит вставку элемента в отсортированную часть массива
//     *
//     * @param arrIn - массив, в котором производим вставку
//     * @param indexInsert - индекс массива, куда необходимо поместить элемент
//     * @param indexCurrent - индекс массива, откуда необходимо поместить элемент
//     */
//    static void insertToArray(Integer[] arrIn, int indexInsert, int indexCurrent) {
//        int temp = arrIn[indexInsert];
//        arrIn[indexInsert] = arrIn[indexCurrent];
//        for (int i = indexCurrent; i > indexInsert; i--) {
//            arrIn[i] = arrIn[i - 1];
//        }
//        arrIn[indexInsert + 1] = temp;
//    }
}
