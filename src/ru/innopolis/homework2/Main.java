package ru.innopolis.homework2;

public class Main {
    public static void main(String[] args) {
//        MathBox mb = new MathBox(null);
        MathBox mb = new MathBox(new Integer[]{2 , 5, 7, 8, 3, 0});
        int ddj = mb.summator();
        System.out.println(ddj);
        mb.splitter(0.1);
        System.out.println(mb.deleteEl(2));
        System.out.println(mb.toString());

    }
}
