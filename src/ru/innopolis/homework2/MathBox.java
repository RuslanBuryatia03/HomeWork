package ru.innopolis.homework2;

import java.util.*;

public class MathBox {

    List<Integer> list;
    private final int id; // = (long) Math.random()*100000000;

    {
        id = (int) Math.random()*100000000;;
    }
//    public MathBox() {
//        id = (int) Math.random()*100000000;;
//    }


    MathBox(Integer[] arr) {
        Arrays.sort(arr);  // падает если есть null
        list = new ArrayList<Integer>(Arrays.asList(arr));

        for(int i = 0; i< arr.length; i++) {
            System.out.println(arr[i]);
        }
        System.out.println();
    }

    public int summator() {
        int sum = 0;
        for (Integer el : list) {
            sum += el.intValue();
        }
        return sum;
    }

    public List<Integer> splitter(int divider) {
        if (divider == 0) {
            System.out.println("деление на ноль");
            return null;
        }
        List<Integer> listDiv = new ArrayList<Integer>();
        for (Integer el : list) {
            listDiv.add(el/divider);
        }
        for (Integer el : listDiv) {
            System.out.println(el.intValue());
        }
        return listDiv;
    }

    public List<Double> splitter(double divider) {
        List<Double> listDiv = new ArrayList<Double>();
        for (Integer el : list) {
            listDiv.add(el/divider);
        }
        System.out.println(this.toString());
//        for (Double el : listDiv) {
//            System.out.println(el.doubleValue());
//        }
        return listDiv;
    }

    public boolean deleteEl(Integer insEl) {
        if (list.contains(insEl)){
            list.remove(insEl);
            return true;
        }
        return false;


    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else return false;
//        if (o == null || getClass() != o.getClass()) return false;
//        MathBox mathBox = (MathBox) o;
//        return this.list.equals(o);
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        String str = "";
        for (Integer el : list) {
            str = str + el+" ";
        }
        return str;
    }
}
