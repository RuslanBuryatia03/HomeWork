package ru.innopolis.homework2;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        MathBox mb = new MathBox(new Integer[]{2 , 5, 7, 8, 3, 0});
        int sumArray = mb.summator();
        System.out.println(sumArray);
        List<Integer> list = mb.splitter(2);
        mb.printList(list);
        System.out.println(mb.deleteEl(2));
        System.out.println(mb.toString());

    }
}
