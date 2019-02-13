package ru.innopolis.homework3;


/**
 * Класс для генерации текстовых файлов заданного размера
 * из массива слов заданного размера
 * @author KhankhasaevRV
 * @since 07.02.2019
 */

public class Main {


    private static final int COUNT_WORDS = 1000;
    private static final String SOURCE = "https://www.ibm.com/developerworks/ru/library/j-jtp07233/index.html";
    private static final String OUTPUT_DIRECTORY = "./out/";
    private static final int COUNT_FILES = 20;
    private static final int FILE_SIZE = 10000;
    private static final int PROBABILITY = 2;

    public static void main(String[] args) {

        GeneratorTxtFile generatorTxtFile = new GeneratorTxtFile();
        String[] words = generatorTxtFile.fillListWords(SOURCE, COUNT_WORDS);
        generatorTxtFile.getFiles(OUTPUT_DIRECTORY, COUNT_FILES, FILE_SIZE, words, PROBABILITY);
    }
}
