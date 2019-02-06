package ru.innopolis.homework8;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import static java.util.stream.IntStream.*;

/**
 * Класс для поиска слов в ресурсах в многопоточном режиме.
 *
 * @author KhankhasaevRV
 * @since 07.02.2019
 */
public class FinderWords {

    private static final int COUNT_SOURCES = 50;
    private static final int COUNT_THREAD = 10;
    private static final String[] WORDS = {"ALICE", "WORKING", "MIKE"};
    private static final String PATH = "./out/result.txt";
    private static final String SOURCE_NAME = "http://www.gutenberg.org/ratelimiter.php/cache/epub/{0}/pg{0}.txt";
    private final String[] sources;


    FinderWords() {
        sources = rangeClosed(1, COUNT_SOURCES)
                .mapToObj(i -> MessageFormat.format(SOURCE_NAME, i))
                .toArray(String[]::new);
    }


    /**
     * Ищет количество вхождений слов из WORDS в ресурсах sources
     */
    Optional<Date> getOccurency() {
        long timeBeg = System.currentTimeMillis();
        if (!clearResultFile()) {
            System.out.println("Не могу удалить результирующий файл!");
            return Optional.empty();
        }
        List<ThreadSource> listThreadSource = new ArrayList<>();
        List<FutureTask<List<String>>> listFutureTask = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(COUNT_THREAD);
        range(0, COUNT_SOURCES)
                .forEach(i -> {
                            listThreadSource.add(new ThreadSource(sources[i], WORDS));
                            listFutureTask.add(new FutureTask<>(listThreadSource.get(i)));
                            executorService.execute(listFutureTask.get(i));

                        }
                );
        if (isDoneAllThreads(listFutureTask)) {
            System.out.println("Done");
            executorService.shutdown();
        }
        range(0, listFutureTask.size())
                .forEach(i -> {
                    List<String> sourceSentenceList = null;
                    try {
                        sourceSentenceList = listFutureTask.get(i).get();
                    } catch (ExecutionException e) {
                        System.out.println("Что то пошло не так");
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        System.out.println("Поток был прерван");
                        e.printStackTrace();
                    }
                    writeToFile(sourceSentenceList);
                    System.out.println(OptionalInt.of(sourceSentenceList.size()).orElse(0));
                });
        long timeEnd = System.currentTimeMillis();
        return Optional.of(new Date(timeEnd - timeBeg));

    }


    /**
     * Проверяет все ли потоки завершили исполнение
     *
     * @param listFutureTask лист потоков
     * @return true, если все потоки завершены, иначе false
     */
    private boolean isDoneAllThreads(List<FutureTask<List<String>>> listFutureTask) {
        return iterate(0, i -> i + 1).mapToObj(i -> listFutureTask)
                .anyMatch(FinderWords::allTasksDone);
    }

    /**
     * Выводит в консоль информацию о времени поиска и количестве потоков
     *
     * @param dateDiffer время исполнения
     */
    void printTimeExecution(Date dateDiffer) {
        DateFormat formatt = new SimpleDateFormat("HH:mm:ss.SSS");
        formatt.setTimeZone(TimeZone.getTimeZone("UTC"));
        String dateFormatted = formatt.format(dateDiffer);
        System.out.println(
                "Количество потоков - " + COUNT_THREAD + ", Количество ресурсов - " + COUNT_SOURCES + " время - " + dateFormatted);
    }

    /**
     * Проверяет все ли потоки завершены
     *
     * @param listFutureTask лист потоков
     * @return true если все потоки завершены, иначе false
     */

    private static boolean allTasksDone(List<FutureTask<List<String>>> listFutureTask) {
        return rangeClosed(0, COUNT_THREAD)
                .allMatch(i -> listFutureTask.get(i).isDone());
    }


    /**
     * Очищает файл PATH
     *
     * @return true если файл удален, иначе false
     */
    private boolean clearResultFile() {
        File fileObj = new File(PATH);
        return (fileObj.exists() && fileObj.delete()) || !fileObj.exists();
    }

    /**
     * Записывает в файл PATH из List<String> sentenceWithWords
     *
     * @param sentenceWithWords List<String> который необходимо записать
     */
    private void writeToFile(List<String> sentenceWithWords) {
        if (sentenceWithWords == null) {
            return;
        }
        try (FileOutputStream fileOutputStream = new FileOutputStream(PATH, true)) {

            sentenceWithWords.forEach(i -> {
                byte[] buffer = (i + "\n").getBytes();
                try {
                    fileOutputStream.write(buffer, 0, buffer.length);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (FileNotFoundException e) {
            System.out.println("Путь не найден");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Что то пошло не так при записи в файл, возможно файл занят");
            e.printStackTrace();
        }
    }

}
