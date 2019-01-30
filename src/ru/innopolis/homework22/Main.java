package ru.innopolis.homework22;

import java.text.DecimalFormat;

public class Main {
    public static void main(String[] args) {

        Double[] arr = {92.345 , 922., 922450., 8., 3., 2.};
        MathBox mb = new MathBox<>(arr);
        Number sumArray = mb.summator();

        DecimalFormat myFormatter = new DecimalFormat("###,###.############");
        String output = myFormatter.format(sumArray);

        System.out.println("сумма " + output);
        System.out.println(mb.splitter(190.));
        System.out.println(mb.deleteElement(2.));
    }
}
