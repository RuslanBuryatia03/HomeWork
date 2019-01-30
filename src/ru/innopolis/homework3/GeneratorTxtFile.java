package ru.innopolis.homework3;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;



public class GeneratorTxtFile {

//    private final String url;

    private final Random randomInt = new Random();


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

                wordArr = words.toArray(new String[count_words]);  ///new String[randomInt.nextInt(1000)];
//                words.toArray(new String[words.size()])
//                for (int i = 0; i < wordArr.length; i++) {
//                    wordArr[i] = words.get(i);
//                }

                getFiles("C://111", 20, 10000, wordArr, 2);
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


    void getFiles(String path, int n, int size, String[] words, int probability) {

        String templateTxtFile = "file_";
        int currentIndex = 0;
        for (int i = 0; i < n; i++) {
            String fileName = templateTxtFile + (i + 1) + ".txt";
            int fileSize = 0;
            try (FileOutputStream fileOutputStream = new FileOutputStream(new File(path, fileName))) {

                boolean beginSent = false;   // признак начала предложения
                int numberWord = 0;   // количество слов в предложении
                int currentNumberWord = 0;   // текущее количество слов
                int numberSent = randomInt.nextInt(20) +1;    // количество предложений в абзаце
                int currentNumberSent = 0;  // текущее количество предложений

                for (int j = currentIndex; j < words.length; j++) {
                    if (randomInt.nextInt(probability) +1 > 1) {
                        if (j == words.length - 1) { // находимся на последнем шаге, у нас закончился массив слов, а файл не заполнен до конца, начинаем массив слов сначала
                            j = 0;
                            currentIndex = 0;
                        }
                        continue;   // вероятность вхождения слова в предложение - у нас числа от 1 до probability, если = 1, то входит, иначе нет
                    }
                    byte[] buffer = words[j].getBytes();
                    int comma = randomInt.nextInt(15) +1;   // будем ли ставить запятую, если = 3 (наугад) то да, иначе нет.
                    if (fileSize + buffer.length + 4 <= size) {   // смотрим можем ли добавить следующее слово (с пробелом и/или с запятой)
                        if (!beginSent) {   // начало предложения с заглавной буквы
                            beginSent = true;
                            numberWord = randomInt.nextInt(15) +1;
                            wordToUpperCase(buffer);
                        }

                        fileOutputStream.write(buffer, 0, buffer.length);
                        fileSize += buffer.length;
                        ++currentNumberWord;
                        String tokenPrepination = insertPrepination(currentNumberWord, numberWord, comma);
                        switch (tokenPrepination) {
                            case ". ":
//                                beginSent = false;
//                                currentNumberWord = 0;  // текущее количество слов в предложении обнуляем
//                                ++currentNumberSent;   // увеличиваем количество предложений
//                                fileOutputStream.write(". ".getBytes(), 0, 2);
//                                fileSize += 2;
//                                break;
                            case "! ":
//                                beginSent = false;
//                                currentNumberWord = 0;
//                                ++currentNumberSent;
//                                fileOutputStream.write("! ".getBytes(), 0, 2);
//                                fileSize += 2;
//                                break;
                            case "? ":
                                beginSent = false;
                                currentNumberWord = 0;
                                ++currentNumberSent;
                                fileOutputStream.write(tokenPrepination.getBytes(), 0, 2);
                                fileSize += 2;
                                break;
                            case ", ":
//                                fileOutputStream.write(", ".getBytes(), 0, 2);
//                                fileSize += 2;
//                                break;
                            case " ":
                                fileOutputStream.write(tokenPrepination.getBytes(), 0, 1);
                                fileSize += 1;
                                break;
                        }

                        if (numberSent == currentNumberSent) {   // сформировали абзац
                            currentNumberSent = 0;
                            numberSent = randomInt.nextInt(20) +1;
                            fileOutputStream.write("\n".getBytes(), 0, 1);
                            fileSize += 1;

                        }
                        if (j == words.length - 1) { // находимся на последнем шаге, у нас закончился массив слов, а файл не заполнен до конца, начинаем массив слов сначала
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
                            Arrays.fill(temp, (byte) 32);       // заполняем пробелами
                            fileOutputStream.write(temp, 0, temp.length);
                        }

//                        switch (size - fileSize) {
//                            case 0:
//                                currentIndex = j;  // запомнили индекс  на котором закончили формирование 1-го файла, чтобы продолжить формирование файла с этого индекса
//                                break;
//                            default:    // дополняем пробелами
//                                byte[] temp = new byte[size - fileSize];
//                                Arrays.fill(temp, (byte) 32);       // заполняем пробелами
//                                fileOutputStream.write(temp, 0, temp.length);
//                        }


                        fileOutputStream.close();

                        break;          // выходим из внутреннего цикла
                    }
                }
                // предложение формируем
//                int
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * Если в массиве первый символ не заглавная, переводит в заглавную.
     * @param buffer массив
     */
    private void wordToUpperCase(byte[] buffer) {
        byte alh = 32;
        if (!(65 <= buffer[0] && buffer[0] <= 90)) { // если не заглавная буква, то делаем заглавной
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
        if (currentNumberWord == numberWord) {   // окончание предложения, возвращаем одно из . ! ?
            int sign = randomInt.nextInt(3) +1;
            return sign == 1 ? ". " : sign == 2 ? "! " : "? ";
        } else {                // не окончание предложения пробел либо запятая пробел
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

        List<String> wordsOfString = new ArrayList<>();  // слова из строки
        List<Character> oneWord = new ArrayList<>();  // одно слово
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

                if (oneWord.size() <= 15) {
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
     * @param word - проверяет длину слова (1..15), а также наличие только латинских символов или -.
     * @return boolean - если слово удовлетворяет нашим условиям, возвращает true, иначе false.
     */
    private boolean checkLengthWord(String word) {

        if (!((1 <= word.length()) && (word.length() <= 15))) {
            return false;
        }
        byte[] arrayByte = word.getBytes(); //new byte[]{1, 3, 5, 7};
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(arrayByte);
        for (byte b : arrayByte) {
            if (!((65 <= b) && (b <= 90) || ((97 <= b) && (b <= 122)) || b == 45)) {
                return false;
            }
        }
        return true;
    }


    /**
     * Проверяет является ли символ буквой латинского алфавита
     *
     * @param ch проверяемое значение
     * @return true - если буква латинского алфавита, инача false
     */

    private boolean checkChar(char ch) {
//        if ((65<= ch) && ( ch <= 90)  || ((97 <= ch) && ( ch <= 122)) || ch == 45) {
        return (65 <= ch) && (ch <= 90) || ((97 <= ch) && (ch <= 122));
    }


}
