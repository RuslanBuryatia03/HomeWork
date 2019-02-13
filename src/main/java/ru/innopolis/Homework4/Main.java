package ru.innopolis.Homework4;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.*;

public class Main {
    private static final int COUNT_SOURCES = 50;
    private static final int COUNT_THREAD = 10;

    private static String[] words = {"ALICE", "WORKING", "MIKE"};

    private static String path = "./out/result.txt";


    public static void main(String[] args) {

        String[] sources = new String[COUNT_SOURCES];

        for (int i = 0; i < COUNT_SOURCES; i++) {
            sources[i] = "http://www.gutenberg.org/ratelimiter.php/cache/epub/" + (i + 1) + "/pg" + (i + 1) + ".txt";
        }

        try {
            long timeBegin = System.currentTimeMillis();
            getOccurencies(sources, words, path);
            long timeEnd = System.currentTimeMillis();
            Date date = new Date(timeEnd - timeBegin);
            DateFormat formatter = new SimpleDateFormat("HH:mm:ss.SSS");
            formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
            String dateFormatted = formatter.format(date);
            System.out.println("Количество потоков - " + COUNT_THREAD + ", Количество ресурсов - " + COUNT_SOURCES + " время - " + dateFormatted);
        } catch (FileNotFoundException e) {
            System.out.println("Не найден файл");
            e.printStackTrace();
        } catch (ExecutionException e) {
            System.out.println("Что то пошло не так");
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("Поток был прерван");
            e.printStackTrace();
        }
    }

    /**
     * Проверяет все ли потоки завершены
     *
     * @param listFutureTask лист потоков
     * @return true если все потоки завершены, иначе false
     */

    private static boolean allTasksDone(List<FutureTask<List<String>>> listFutureTask) {
        boolean isDone = true;
        for (int i = 0; i < COUNT_THREAD; i++) {
            if (!listFutureTask.get(i).isDone()) {
                isDone = false;
                break;
            }
        }
        return isDone;
    }


    /**
     * Ищет количество вхождений слов из words в ресурсах sources
     *
     * @param sources ресурсы
     * @param words   искомые слова
     * @param res     результиющий файл, куда записывают предложения со словами
     * @throws FileNotFoundException - если
     * @throws ExecutionException
     * @throws InterruptedException
     */
    private static void getOccurencies(String[] sources, String[] words, String res) throws FileNotFoundException, ExecutionException, InterruptedException {

        if (!clearResultFile(path)){
            System.out.println("Не могу удалить результирующий файл");
            return;
        }
        List<ThreadSource> listThreadSource = new ArrayList<>();
        List<FutureTask<List<String>>> listFutureTask = new ArrayList<>();
        ExecutorService executorService = Executors.newFixedThreadPool(COUNT_THREAD);

        for (int i = 0; i < COUNT_SOURCES; i++) {
            listThreadSource.add(new ThreadSource(sources[i], words));
            listFutureTask.add(new FutureTask<List<String>>(listThreadSource.get(i)));
            executorService.execute(listFutureTask.get(i));
        }


        while (true) {
            if (allTasksDone(listFutureTask)) {
                System.out.println("Done");
                executorService.shutdown();
                break;
            }
        }

        clearResultFile(path);
        for (FutureTask el : listFutureTask) {
            List<String> sourceSentenceList = (List<String>) el.get();
            writeToFile(sourceSentenceList, path);
            System.out.println(sourceSentenceList.size());
        }
    }


    /**
     * Очищает файл path
     * @param path путь к файлу для очистки
     * @return true если файл удален, иначе false
     */
    static boolean clearResultFile(String path)  {

        File fileobj = new File(path);
        if (fileobj.exists()) {
            return fileobj.delete();
        }
        return false;
    }

    /**
     * Записывает в файл path из List<String> sentenceWithWords
     *
     * @param sentenceWithWords List<String> который необходимо записать
     * @param path              путь к файлу, в который надо записать
     * @throws FileNotFoundException если путь не найден или файл занят
     */
    private static void writeToFile(List<String> sentenceWithWords, String path) {
        try (FileOutputStream fileOutputStream = new FileOutputStream(path, true)) {

            for (String el : sentenceWithWords) {
                byte[] buffer = (el + "\n").getBytes();
                fileOutputStream.write(buffer, 0, buffer.length);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Путь не найден");
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println("Что то пошло не так при записи в файл, возможно файл занят");
            e.printStackTrace();
        }
    }


}
