package ru.innopolis.homework8;

import java.util.Date;


/**
 * @author KhankhasaevRV
 * @since 2019.02.06
 */
public class Main {
    private static final String SOURCE_NAME = "http://www.gutenberg.org/ratelimiter.php/cache/epub/{0}/pg{0}.txt";
    private static final int COUNT_SOURCES = 50;
    private static final int COUNT_THREAD = 10;
//    private static final String SOURCE_NAME = "file:src/test/resources/inputData/file_{0}.txt";

    public static void main(String[] args) {
        FinderWords finderWords = new FinderWords(SOURCE_NAME, COUNT_SOURCES, COUNT_THREAD);
        Date dateDiffer = finderWords.getOccurency();
        System.out.println(finderWords.printTimeExecution(dateDiffer));
    }

}
