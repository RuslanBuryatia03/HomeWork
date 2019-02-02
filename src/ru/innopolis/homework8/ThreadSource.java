package ru.innopolis.homework8;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.IntStream;

class ThreadSource implements Callable<List<String>> {

    private final String source;
    private final String[] words;


    ThreadSource(String source, String[] words) {
        this.source = source;
        this.words = words;
    }

    @Override
    public List<String> call() {

        List<String> sentenceWithWords = new ArrayList<>();
        try (InputStream input = new URL(source).openStream();
             BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
            StringBuilder beginSentence = new StringBuilder();

            while (reader.ready()) {
                String nextLine = reader.readLine();
                if (nextLine.trim().equals("") || !hasWords(nextLine)) {
                    continue;
                }
                StringBuilder line = new StringBuilder(beginSentence + nextLine);
                int lastInd = findEndLastSentence(line);
                if (lastInd == 0) {
                    beginSentence.append(" ").append(nextLine);
                    continue;
                } else {
                    beginSentence.setLength(0);
                }
                findInString(line.toString(), words, sentenceWithWords);
                if (lastInd < line.length() && (isNotEndedSentence(line.substring(lastInd)))) {
                    beginSentence = new StringBuilder(line.substring(lastInd));
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

        IntStream.range(0, words.length)
                .filter(i -> stringUpper.contains(words[i].toUpperCase()))
                .mapToObj(i -> Pattern.compile(words[i].toUpperCase()))
                .map(patternWord -> patternWord.matcher(stringUpper))
                .forEach(matcherWord -> addSentences(string, findSentences, matcherWord));
    }

    /**
     * Добавляет предложения, в которых найдены слова из массива
     * @param string строка, в котором содержится предложение с найденными словами
     * @param findSentences Лист с найденными предложениями
     * @param matcherWord матчер слов по строке, в которой найдены слова
     */
    private void addSentences(String string, List<String> findSentences, Matcher matcherWord) {

        while (matcherWord.find()) {
            Pattern patternSentence = Pattern.compile("[А-ЯA-Z].+?[.?!]");
            Matcher matcherSentence = patternSentence.matcher(string);
            findMatch(string, findSentences, matcherWord, matcherSentence);
        }
    }

    /**
     * Добавляет предложения, в которых найдены слова из массива
     * @param string строка, в котором содержится предложение с найденными словами
     * @param findSentences   Лист с найденными предложениями
     * @param matcherWord  матчер слов по строке, в которой найдены слова
     * @param matcherSentence матчер предложений по строке, в которой найдены слова
     */
    private void findMatch(String string, List<String> findSentences, Matcher matcherWord, Matcher matcherSentence) {
        while (matcherSentence.find()) {
            if (matcherWord.start() >= matcherSentence.start() && matcherWord.end() <= matcherSentence.end()
                    && (!findSentences.contains(string.substring(matcherSentence.start(), matcherSentence.end())))) {

                findSentences.add(string.substring(matcherSentence.start(), matcherSentence.end()));
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

    private int findEndLastSentence(StringBuilder line) {
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
