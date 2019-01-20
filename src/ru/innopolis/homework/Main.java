
package ru.innopolis.homework;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        /** Массив для сортировки */
        Integer[] arr = new Integer[100];   ///{2, 1, 3, 5, 4, 7,8,4,3,2,3}; //new int[10];
        /** Максимальное значение для заполнения массива */
        int maxValue = 1000;

        SortArray sr = new SortArray();
        try {
            arr = sr.fillArray(arr, maxValue);
            arr = sr.sortArray(arr);
        }
        catch (NullPointerException e){
            System.out.println("массив нулевой длины");
        }



    }
}

//        Integer[] arr = new Integer[1000]; ///{2, 1, 3, 5, 4, 7,8,4,3,2,3}; //new int[10];
////        System.out.println("asf");
//        for(int i = 0; i<arr.length;i++){
//            arr[i] = (int )Math.round(Math.random()*1000);
//        }
//        for (int i = 0; i < arr.length; i++) {
//            System.out.print ("  " + arr[i]);
//        }
//        System.out.println ("  ");
//        for (int i = 1; i < arr.length; i++) {
//            int indIns = findIndex(arr[i], Arrays.copyOfRange(arr, 0, i));
//            if (indIns == i) {
//                continue;
//            } else {
//                insertToArray(arr, indIns, i); //
//            }
//        }
//        for (int i = 0; i < arr.length; i++) {
//            System.out.print ("  " + arr[i]);
//        }
//    }
//
//    static int findIndex(int a, Integer[] arrFind) {
//
//        for (int i = 0; i < arrFind.length; i++) {
//            if (a < arrFind[i]) {
//                return i;
//            }
//        }
//        return arrFind.length;
//    }
//
//    static void insertToArray(Integer[] arrIn, int indexInsert, int indexCurrent) {
//        int temp = arrIn[indexInsert];
//        arrIn[indexInsert] = arrIn[indexCurrent];
//        for (int i = indexCurrent; i > indexInsert; i--) {
//            arrIn[i] = arrIn[i - 1];
//        }
//        arrIn[indexInsert + 1] = temp;
//    }


