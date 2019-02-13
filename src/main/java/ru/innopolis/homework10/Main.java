package ru.innopolis.homework10;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {
        List<String> word = new ArrayList<>();
        for (int i = 0; i < 10_000_000; i++) {
            word.add(i, "Съешь ещё этих мягких французских булок, да выпей же чаю");
            System.out.println(i);
        }
        System.out.println("Все сделано");
    }
}