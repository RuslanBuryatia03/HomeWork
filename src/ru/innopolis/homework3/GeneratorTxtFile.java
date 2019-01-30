package ru.innopolis.homework3;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Класс для генерации текстовых файлов
 * заданного размера
 */

public class GeneratorTxtFile {

    public static final int NUMBER_SENTENCES = 20;
    public static final int NUMBER_WORDS = 15;
    public static final int PROBABILITY_COMMA = 15;
    public static final int PROBABILITY_END_SENTENCE = 3;
    public static final int LENGTH_WORD = 15;

    private final Random randomInt = new Random();

    /**
     * Заполняет массив слов длиной count_words из sourse
     * @param sourse источник
     * @param count_words количество слов в массиве
     * @return  String[] длиной count_words
     */

    String[] fillListWords(String sourse, int count_words) {

        List<String> words = new ArrayList<>();
        String[] wordArr = new String[count_words];

        try (InputStream input = new URL(sourse).openStream()) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {

                while (reader.ready()) {
                    String line = reader.readLine();
                    List<String> d;
                    if ((d = findWord(line)) != null) {
                        words.addAll(d);
                    }
                    if (words.size() >= count_words) break;
                    System.out.println(line);
                }

                wordArr = words.toArray(new String[count_words]);
            } catch (UnsupportedEncodingException e) {
                System.out.println("Неподдерживаемая кодировка");
                e.printStackTrace();
            } catch (IOException e) {
                System.out.println("Непредвиденная ошибка");
                e.printStackTrace();
            }
        } catch (IOException e) {
            System.out.println("Не могу получить данные с ресурса");
            e.printStackTrace();
        }
        return wordArr;
    }

    /**
     * Генерирует по заданному пути path n файлов размером size из массива слов words с вероятностью
     * вхождения слова в предложение probability.
     * @param path путь для сохранения файлов
     * @param n количество файлов
     * @param size размер каждого файла
     * @param words массив слов
     * @param probability вероятность вхождения каждого слова в предложение
     */

    void getFiles(String path, int n, int size, String[] words, int probability) {

        String templateTxtFile = "file_";
        int currentIndex = 0;
        for (int i = 0; i < n; i++) {
            String fileName = templateTxtFile + (i + 1) + ".txt";
            int fileSize = 0;
            try (FileOutputStream fileOutputStream = new FileOutputStream(new File(path, fileName))) {

                boolean beginSent = false;
                int numberWord = 0;
                int currentNumberWord = 0;
                int numberSent = randomInt.nextInt(NUMBER_SENTENCES) + 1;
                int currentNumberSent = 0;

                for (int j = currentIndex; j < words.length; j++) {
                    if (randomInt.nextInt(probability) + 1 > 1) {
                        if (j == words.length - 1) {
                            j = 0;
                            currentIndex = 0;
                        }
                        continue;
                    }
                    byte[] buffer = words[j].getBytes();
                    int comma = randomInt.nextInt(PROBABILITY_COMMA) + 1;
                    if (fileSize + buffer.length + 4 <= size) {
                        if (!beginSent) {
                            beginSent = true;
                            numberWord = randomInt.nextInt(NUMBER_WORDS) + 1;
                            wordToUpperCase(buffer);
                        }

                        fileOutputStream.write(buffer, 0, buffer.length);
                        fileSize += buffer.length;
                        ++currentNumberWord;
                        String tokenPrepination = insertPrepination(currentNumberWord, numberWord, comma);
                        switch (tokenPrepination) {
                            case ". ":
                            case "! ":
                            case "? ":
                                beginSent = false;
                                currentNumberWord = 0;
                                ++currentNumberSent;
                                fileOutputStream.write(tokenPrepination.getBytes(), 0, 2);
                                fileSize += 2;
                                break;
                            case ", ":
                            case " ":
                                fileOutputStream.write(tokenPrepination.getBytes(), 0, 1);
                                fileSize += 1;
                                break;
                        }

                        if (numberSent == currentNumberSent) {
                            currentNumberSent = 0;
                            numberSent = randomInt.nextInt(NUMBER_SENTENCES) + 1;
                            fileOutputStream.write("\n".getBytes(), 0, 1);
                            fileSize += 1;

                        }
                        if (j == words.length - 1) {
                            j = 0;
                            currentIndex = 0;
                        }
                    } else {
                        fileOutputStream.write(buffer, 0, Math.min(buffer.length, size - fileSize));
                        fileSize += Math.min(buffer.length, size - fileSize);
                        if (size - fileSize == 0) {
                            currentIndex = j;
                        } else {
                            byte[] temp = new byte[size - fileSize];
                            Arrays.fill(temp, (byte) 32);
                            fileOutputStream.write(temp, 0, temp.length);
                        }
                        fileOutputStream.close();
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Если в массиве первый символ не заглавная, переводит в заглавную.
     *
     * @param buffer массив
     */
    private void wordToUpperCase(byte[] buffer) {
        byte alh = 32;
        if (!(65 <= buffer[0] && buffer[0] <= 90)) {
            buffer[0] = (byte) (buffer[0] - alh);
        }
    }

    /**
     * Возвращает либо конец предложения (, ! ?), либо конец слова (пробел, запятая+пробел)
     *
     * @param currentNumberWord текущее количество слов
     * @param numberWord        количество слов которое должно быть в предложении
     * @param comma             случайное число, если оно равно 3, то в конце слова ставим запятую
     * @return либо конец предложения (, ! ?), либо конец слова (пробел, запятая+пробел)
     */
    private String insertPrepination(int currentNumberWord, int numberWord, int comma) {
        if (currentNumberWord == numberWord) {
            int sign = randomInt.nextInt(PROBABILITY_END_SENTENCE) + 1;
            return sign == 1 ? ". " : sign == 2 ? "! " : "? ";
        } else {
            return comma == 3 ? ", " : " ";
        }

    }

    /**
     * Ищет слова длиной не более 15 символов в строке
     *
     * @param string входящяя строка
     * @return ArrayList<String> слов
     */
    private List<String> findWord(String string) {
        if (string.length() == 0) {
            return null;
        }

        char[] tempArray = new char[string.length()];
        string.getChars(0, string.length() - 1, tempArray, 0);
        List<String> wordsOfString = new ArrayList<>();
        List<Character> oneWord = new ArrayList<>();
        boolean beginWord = false;
        boolean endWord = false;

        for (char c : tempArray) {

            if (checkChar(c)) {
                oneWord.add(c);
                if (!beginWord) beginWord = true;
            } else {
                if (beginWord) endWord = true;
            }
            if (beginWord && endWord) {

                if (oneWord.size() <= LENGTH_WORD) {
                    StringBuilder t = new StringBuilder();
                    for (Character e : oneWord) {
                        t.append(e);
                    }
                    wordsOfString.add(t.toString());
                    oneWord.clear();
                }
                beginWord = false;
                endWord = false;
            }
        }
        return wordsOfString;
    }


    /**
     * Проверяет является ли символ буквой латинского алфавита
     *
     * @param ch проверяемое значение
     * @return true - если буква латинского алфавита, инача false
     */

    private boolean checkChar(char ch) {
        return (65 <= ch) && (ch <= 90) || ((97 <= ch) && (ch <= 122));
    }
}
