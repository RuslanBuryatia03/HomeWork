package ru.innopolis.homework3;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args)  {

        InputStream input = null;
        try {
            input = new URL("https://www.ibm.com/developerworks/ru/library/j-jtp07233/index.html").openStream();
        } catch (IOException e) {
            System.out.println("Не могу получить данные с ресурса");
            e.printStackTrace();
        }

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));

            List<String> words = new ArrayList<>();
            while (reader.ready()) {
                String line = reader.readLine();
                List<String> d = findWord(line);
                if (!(d == null)) {
                    words.addAll(findWord(line));
                }
                if (words.size()>=1000) break;
//            words.addAll(findWord(line));
                System.out.println(line);
            }

            input.close();
            reader.close();

            String[] wordArr = new String[randomInt(1000)];
            for (int i = 0; i < wordArr.length; i++) {
                wordArr[i] = words.get(i);
            }
            //words.toArray(wordArr);

            getFiles("C://111", 20, 10000, wordArr, 2);


        } catch (UnsupportedEncodingException e) {
            System.out.println("Неподдерживаемая кодировка");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Непредвиденная ошибка");
            e.printStackTrace();
        }

//        for (int i = 0; i < 20; i++) {
//            System.out.println(lengthSentence(15));
//        }

    }

    static void getFiles(String path, int n, int size, String[] words, int probability) {
        String file = "file_";
        int currentIndex = 0;
        for (int i = 0; i < n; i++) {
            String fileName = file + String.valueOf(i + 1) + ".txt";
            int fileSize = 0;
//            try (FileOutputStream fileOutputStream = new FileOutputStream(new File(path, fileName))) {
            try (FileOutputStream fileOutputStream = new FileOutputStream(new File(path, fileName))) {

                boolean beginSent = false;   // признак начала предложения
                int numberWord = 0;   // количество слов в предложении
                int currentNumberWord = 0;   // текущее количество слов
                int numberSent = randomInt(20);    // количество предложений в абзаце
                int currentNumberSent = 0;  // текущее количество предложений

                for (int j = currentIndex; j < words.length; j++) {
                    if (randomInt(probability) > 1) {
                        if (j == words.length - 1) { // находимся на последнем шаге, у нас закончился массив слов, а файл не заполнен до конца, начинаем массив слов сначала
                            j = 0;
                            currentIndex = 0;
                        }
                        continue;   // вероятность вхождения слова в предложение - у нас числа от 1 до probability, если = 1, то входит, иначе нет
                    }
                    byte[] buffer = words[j].getBytes();
                    int comma = randomInt(15);   // будем ли ставить запятую, если = 3 (наугад) то да, иначе нет.
                    if (fileSize + buffer.length + 4 <= size) {   // смотрим можем ли добавить следующее слово (с пробелом и/или с запятой)
                        if (!beginSent) {   // начало предложения с заглавной буквы
                            beginSent = true;
                            numberWord = randomInt(15);
                            byte alh = 32;
                            if (!(65 <= buffer[0] && buffer[0] <= 90)) { // если не заглавная буква, то делаем заглавной
                                buffer[0] = (byte) (buffer[0] - alh);
                            }
                        }

                        fileOutputStream.write(buffer, 0, buffer.length);
                        fileSize += buffer.length;
                        ++currentNumberWord;
                        switch (insertPrepination(currentNumberWord,numberWord, comma)) {
                            case ". ":
                                beginSent = false;
                                currentNumberWord = 0;  // текущее количество слов в предложении обнуляем
                                ++currentNumberSent;   // увеличиваем количество предложений
                                fileOutputStream.write(". ".getBytes(), 0, 2);
                                fileSize += 2;
                                break;
                            case "! ":
                                beginSent = false;
                                currentNumberWord = 0;
                                ++currentNumberSent;
                                fileOutputStream.write("! ".getBytes(), 0, 2);
                                fileSize += 2;
                                break;
                            case "? ":
                                beginSent = false;
                                currentNumberWord = 0;
                                ++currentNumberSent;
                                fileOutputStream.write("? ".getBytes(), 0, 2);
                                fileSize += 2;
                                break;
                            case ", ":
                                fileOutputStream.write(", ".getBytes(), 0, 2);
                                fileSize += 2;
                                break;
                            case " ":
                                fileOutputStream.write(" ".getBytes(), 0, 1);
                                fileSize += 1;
                                break;
                        }

                        if (numberSent == currentNumberSent) {   // сформировали абзац
                            currentNumberSent = 0;
                            numberSent = randomInt(20);
                            fileOutputStream.write("\n".getBytes(), 0, 1);
                            fileSize += 1;

                        }
                        if (j == words.length - 1) { // находимся на последнем шаге, у нас закончился массив слов, а файл не заполнен до конца, начинаем массив слов сначала
                            j = 0;
                            currentIndex = 0;
                        }
                    } else {
                        fileOutputStream.write(buffer, 0, Math.min(buffer.length,size - fileSize));
                        fileSize += Math.min(buffer.length,size - fileSize);
                        switch (size - fileSize) {
                            case 0:
                                currentIndex = j;  // запомнили индекс  на котором закончили формирование 1-го файла, чтобы продолжить формирование файла с этого индекса
//                                    fileOutputStream.close();
                                break;
                            default:    // дополняем пробелами
                                byte[] temp = new byte[size - fileSize];
                                Arrays.fill(temp, (byte) 32);       // заполняем пробелами
                                fileOutputStream.write(temp, 0, temp.length);
                        }
                        fileOutputStream.close();

                        break;          // выходим из внутреннего цикла
                    }
                }
                // предложение формируем
//                int
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    /** Возвращает либо конец предложения (, ! ?), либо конец слова (пробел, запятая+пробел)
     *
     * @param currentNumberWord  текущее количество слов
     * @param numberWord количество слов которое должно быть в предложении
     * @param comma случайное число, если оно равно 3, то в конце слова ставим запятую
     * @return либо конец предложения (, ! ?), либо конец слова (пробел, запятая+пробел)
     */
    static String insertPrepination(int currentNumberWord, int numberWord, int comma){
        if (currentNumberWord == numberWord) {   // окончание предложения, возвращаем одно из . ! ?
            int sign = randomInt(3);
            return sign == 1 ? ". " : sign == 2 ? "! ":"? ";
        } else {                // не окончание предложения пробел либо запятая пробел
            return comma == 3 ? ", ": " ";
        }

    }

    /** Ищет слова длиной не более 15 символов в строке
     *
     * @param string входящяя строка
     * @return ArrayList<String> слов
     * @throws UnsupportedEncodingException
     */
    static List<String> findWord(String string) throws UnsupportedEncodingException {
        if (string.length() == 0 || string == null) {
            return null;
        }
        /**
         * временный массив
         */
        char[] tempArray = new char[string.length()];
        string.getChars(0, string.length() - 1, tempArray, 0);

        List<String> wordsOfString = new ArrayList<>();  // слова из строки
        List<Character> oneWord = new ArrayList<>();  // одно слово
        boolean beginWord = false;
        boolean endWord = false;

        for (int i = 0; i < tempArray.length; i++) {

            if (checkChar(tempArray[i])) {
                oneWord.add(tempArray[i]);
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
    static boolean checkLengthWord(String word) {

        if (!((1 <= word.length()) && (word.length() <= 15))) {
            return false;
        }
        byte[] arrayByte = word.getBytes(); //new byte[]{1, 3, 5, 7};
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(arrayByte);
        for (int i = 0; i < arrayByte.length; i++) {
            if (!((65 <= arrayByte[i]) && (arrayByte[i] <= 90) || ((97 <= arrayByte[i]) && (arrayByte[i] <= 122)) || arrayByte[i] == 45)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param - maxValue - максимальное значение
     * @return возвращает int - произвольное целое от 1 доо maxValue
     */
    static int randomInt(int maxValue) {
        return maxValue - ((int) (Math.random() * maxValue));
    }

    /**
     * Проверяет является ли символ буквой латинского алфавита
     *
     * @param ch проверяемое значение
     * @return true - если буква латинского алфавита, инача false
     */

    static boolean checkChar(char ch) {
//        if ((65<= ch) && ( ch <= 90)  || ((97 <= ch) && ( ch <= 122)) || ch == 45) {
        if ((65 <= ch) && (ch <= 90) || ((97 <= ch) && (ch <= 122))) {
            return true;
        } else {
            return false;
        }
    }
}
