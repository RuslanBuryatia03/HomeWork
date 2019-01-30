package ru.innopolis.homework3;


public class Main {


    private static final int COUNT_WORDS = 1000;
    private static final String SOURCE = "https://www.ibm.com/developerworks/ru/library/j-jtp07233/index.html";
    private static final String OUTPUT_DIRECTORY = "./out/";
    private static final int COUNT_FILES = 20;
    private static final int FILE_SIZE = 10000;
    private static final int PROBABILITY = 2;
    private static String[] words;


    public static void main(String[] args) {

        GeneratorTxtFile generatorTxtFile = new GeneratorTxtFile();
        words = generatorTxtFile.fillListWords(SOURCE, COUNT_WORDS);
        generatorTxtFile.getFiles(OUTPUT_DIRECTORY, COUNT_FILES, FILE_SIZE, words, PROBABILITY);
    }
}
