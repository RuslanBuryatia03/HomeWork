package ru.innopolis.Homework4;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {
    public static final int COUNT_SOURCES = 20;
    public static final int COUNT_WORDS = 100;

//    static String[] sources = new String[COUNT_SOURCES];
    static String[] words = {"NOT", "AND", "MIKE"};


    static String path = "out\\result.txt";




    public static void main(String[] args) throws FileNotFoundException, ExecutionException, InterruptedException {

        String[] sources = new String[10];


         for (int i = 0; i < 10; i++) {
//            sources[i] = "file:out\\file_" +(i+1)+".txt";
             sources[i] = "http://www.gutenberg.org/ratelimiter.php/cache/epub/" + (i+1) +"/pg" + (i+1) +".txt";
         }

//        for (int i = 10; i < sources.length; i++) {
//            //int id = ThreadLocalRandom.current().nextInt(50000);
////            sources[i] = "http://gutenberg.org/cache/epub/" + i + "/pg" + i + ".txt";
//            sources[i] = "http://www.gutenberg.org/ratelimiter.php/cache/epub/" + i +"/pg" +i +".txt";
//        }


        List<ThreadSource> listThreadSource = new ArrayList<>();
        List<FutureTask<List<String>>> listFutureTask = new ArrayList<>();


        ExecutorService executorService = Executors.newFixedThreadPool(5);
        List<Future<List<String>>> listFuture = new ArrayList<Future<List<String>>>(); // сюда результаты

        for (int i = 0; i < sources.length; i++) {
            listThreadSource.add(new ThreadSource(sources[i], words));
            listFutureTask.add(new FutureTask<List<String>>(listThreadSource.get(i)));
            //executorService.execute(listFutureTask.get(i));
        }

//        for(int i=0; i< sources.length; i++){
//            //сабмитим Callable таски, которые будут
//            //выполнены пулом потоков
//            Future<List<String>> future = executorService.submit(listThreadSource.get(i));
//            //добавляя Future в список,
//            //мы сможем получить результат выполнения
//            listFuture.add(future);
//        }

        for (int i = 0; i < 5; i++) {
            executorService.execute(listFutureTask.get(i));
        }


        List<String > resultSentence = new ArrayList<>();

        while (true) {
            try {
                if(listFutureTask.get(0).isDone() &&
                        listFutureTask.get(1).isDone() &&
                        listFutureTask.get(2).isDone() &&
                        listFutureTask.get(3).isDone() &&
                        listFutureTask.get(4).isDone()){
                    System.out.println("Done");
                    // заканчиваем работу executor service
                    executorService.shutdown();
                    for(FutureTask el: listFutureTask) {
                        List<String> dd = (List<String>) el.get();

                        System.out.println(el.get());
                    }
                    return;
                }
            }
            finally {

            }


//            catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
        }

//        for(Future el: listFuture) {
//            System.out.println(el.get());
//        }




    }

    void getOccurencies(String[] sources, String[] words, String res) {

    }


}
