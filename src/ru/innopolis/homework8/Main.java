package ru.innopolis.homework8;

import java.util.Date;
import java.util.Optional;


/**
 * @author KhankhasaevRV
 * @since 2019.02.06
 */
public class Main {

    public static void main(String[] args) {
        FinderWords finderWords = new FinderWords();
        Optional<Date> dateDiffer = finderWords.getOccurency();
        if (dateDiffer.isPresent()) {
            finderWords.printTimeExecution(dateDiffer.get());
        } else {
            System.out.println("Не удалось произвести оценку");
        }

    }

}
