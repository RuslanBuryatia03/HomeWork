package ru.innopolis.homework8;

import java.util.Date;


/**
 * @author KhankhasaevRV
 * @since 2019.02.06
 */
public class Main {

    public static void main(String[] args) {
        FinderWords finderWords = new FinderWords();
        Date dateDiffer = finderWords.getOccurency();
        System.out.println(finderWords.printTimeExecution(dateDiffer));
    }

}
