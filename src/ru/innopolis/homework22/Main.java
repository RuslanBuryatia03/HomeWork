package ru.innopolis.homework22;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        MathBox mb = new MathBox(null);
        Float[] arr = {9223372f , 9223385f, 92233720f, 8f, 3f, 2f};   //  9223372036854775807
        MathBox mb = new MathBox(arr);
        System.out.println(arr[1].getClass().getName());
//        switch (arr[1].getClass().getName())

        Number ddj = mb.summator(arr[0].getClass().getName());
        System.out.println(ddj);
        mb.splitter(190f, arr[0].getClass().getName());
        System.out.println(mb.deleteEl(2f));
//        System.out.println(mb.toString());

    }
}
