package ru.innopolis.homework8;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.stream.IntStream;

public class Main {
    private static final int COUNT_SOURCES = 50;
    private static final int COUNT_THREAD = 10;

    private static final String[] words = {"ALICE", "WORKING", "MIKE"};

    private static final String path = "./out/result.txt";


    public static void main(String[] args) {

        String[] sources = IntStream.rangeClosed(1, COUNT_SOURCES)  // COUNT_SOURCES
                .mapToObj(i -> "http://www.gutenberg.org/ratelimiter.php/cache/epub/" + i + "/pg" + i + ".txt")
                .toArray(String[]::new);

        long timeBeg = System.currentTimeMillis();
        getOccurency(sources);
        long timeEnd = System.currentTimeMillis();
        Date dateDiffer = new Date(timeEnd - timeBeg);
        DateFormat formatt = new SimpleDateFormat("HH:mm:ss.SSS");
        formatt.setTimeZone(TimeZone.getTimeZone("UTC"));
        String dateFormatted = formatt.format(dateDiffer);
        System.out.println("Количество потоков - " + COUNT_THREAD + ", Количество ресурсов - " + COUNT_SOURCES + " время - " + dateFormatted);

    }

    /**
     * Проверяет все ли потоки завершены
     *
     * @param listFutureTask лист потоков
     * @return true если все потоки завершены, иначе false
     */

    private static boolean allTasksDone(List<FutureTask<List<String>>> listFutureTask) {
        return IntStream.rangeClosed(0, COUNT_THREAD)
                .allMatch(i -> listFutureTask.get(i).isDone());
    }


    /**
     * Ищет количество вхождений слов из words в ресурсах sources
     *
     * @param sources ресурсы
     */
    private static void getOccurency(String[] sources) {

        if (!clearResultFile()) {
            System.out.println("Не могу удалить результирующий файл!");
            return;
        }
        List<ThreadSource> listThreadSource = new ArrayList<>();
        List<FutureTask<List<String>>> listFutureTask = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(COUNT_THREAD);
        IntStream.range(0, COUNT_SOURCES)
                .forEach(i -> {
                            listThreadSource.add(new ThreadSource(sources[i], Main.words));
                            listFutureTask.add(new FutureTask<>(listThreadSource.get(i)));
                            executorService.execute(listFutureTask.get(i));

                        }
                );


        if (IntStream.iterate(0, i -> i + 1).mapToObj(i -> listFutureTask)
                .anyMatch(Main::allTasksDone)) {
            System.out.println("Done");
            executorService.shutdown();
        }

        clearResultFile();

        IntStream.range(0, listFutureTask.size())
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
    }


    /**
     * Очищает файл path
     *
     * @return true если файл удален, иначе false
     */
    private static boolean clearResultFile() {

        File fileObj = new File(Main.path);
        return (fileObj.exists() && fileObj.delete()) || !fileObj.exists();
    }

    /**
     * Записывает в файл path из List<String> sentenceWithWords
     *  @param sentenceWithWords List<String> который необходимо записать
     *
     */
    private static void writeToFile(List<String> sentenceWithWords) {
        if (sentenceWithWords == null){
            return;
        }
        try (FileOutputStream fileOutputStream = new FileOutputStream(Main.path, true)) {

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
