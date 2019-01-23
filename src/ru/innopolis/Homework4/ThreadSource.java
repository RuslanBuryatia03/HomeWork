package ru.innopolis.Homework4;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ThreadSource implements Callable<List<String>> {

    private String source;
    private String[] words;


    ThreadSource(String source, String[] words) {
        this.source = source;
        this.words = words;
    }

    @Override
    public List<String> call() {

        List<String> sentenceWithWords = new ArrayList<>();
        try (InputStream input = new URL(source).openStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
            String beginSentence = "";

            while (reader.ready()) {
                String nextLine = reader.readLine();
                if (nextLine.trim().equals("") || !hasWords(nextLine)) {
                    continue;
                }
                String line = beginSentence + nextLine;
                int lastInd = findEndLastSentence(line);
                if (lastInd == 0) {
                    beginSentence = beginSentence + " " + nextLine;
                    continue;
                } else {
                    beginSentence = "";
                }
                findInString(line, words, sentenceWithWords);
                if ((lastInd != 0) && (lastInd < line.length()) && (isNotEndedSentence(line.substring(lastInd)))) {
                    beginSentence = line.substring(lastInd);
                }
            }
        } catch (IOException e) {
            System.out.println("Не могу получить данные с ресурса - " + source);
        }
        return sentenceWithWords;
    }


    /**
     * Заполняет List<String> findSentences предложениями из строки string, в
     * которых найдены слова из массива words
     *
     * @param string        строка, в которой производится поиск
     * @param words         массив слов, которые необходимо искать string
     * @param findSentences List<String> который заполняем предложениями
     */
    private void findInString(String string, String[] words, List<String> findSentences) {
        String stringUpper = string.toUpperCase();

        for (int i = 0; i < words.length; i++) {
            if (stringUpper.contains(words[i].toUpperCase())) {

                Pattern patternWord = Pattern.compile(words[i].toUpperCase());
                Matcher matcherWord = patternWord.matcher(stringUpper);
                while (matcherWord.find()) {
                    Pattern patternSentence = Pattern.compile("[А-ЯA-Z].+?[.?!]");
                    Matcher matcherSentence = patternSentence.matcher(string);
                    while (matcherSentence.find()) {
                        if (matcherWord.start() >= matcherSentence.start() && matcherWord.end() <= matcherSentence.end()
                                && (!findSentences.contains(string.substring(matcherSentence.start(), matcherSentence.end())))) {

                            findSentences.add(string.substring(matcherSentence.start(), matcherSentence.end()));
                        }
                    }
                }
            }
        }
    }


    /**
     * Возвращает индекс конца последнего предложения в строке
     * Необходим если начало одного предложения находится на одной строке, а конец на другой.
     *
     * @param line анализируемая строка
     * @return индекс последнего конца предложения или 0 если нет окончания предложения
     */

    private int findEndLastSentence(String line) {
        Pattern p = Pattern.compile("[.?!]");
        Matcher matcher = p.matcher(line);
        int lastEndSentence = 0;
        while (matcher.find()) {
            lastEndSentence = matcher.end() - 1;
        }
        return lastEndSentence;
    }

    /**
     * Проверяет есть  значащие символы в строке после окончания последнего полного предложения
     *
     * @param line анализируемая строка
     * @return true если есть значащие символы, иначе false
     */
    private boolean isNotEndedSentence(String line) {
        Pattern p = Pattern.compile("[.?!].+?[А-Яа-яA-Za-z0-9]");
        Matcher matcher = p.matcher(line);
        return matcher.find();
    }

    /**
     * Проверяет есть  значащие символы в строке
     *
     * @param line анализируемая строка
     * @return true если есть значащие символы, иначе false
     */
    private boolean hasWords(String line) {
        Pattern p = Pattern.compile("[А-Яа-яA-Za-z]");
        Matcher matcher = p.matcher(line);
        return matcher.find();
    }
}
