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

    int dd = (int) 'd';
    static String path = "out\\result.txt";


    private static final String[] ABBREVIATIONS = {
            "Dr." , "Prof." , "Mr." , "Mrs." , "Ms." , "Jr." , "Ph.D."
    };

    public static void main(String[] args) throws FileNotFoundException, ExecutionException, InterruptedException {

        String[] sources = new String[10];


         for (int i = 0; i < 10; i++) {
            sources[i] = "file:out\\file_" +(i+1)+".txt";
//             sources[i] = "http://www.gutenberg.org/ratelimiter.php/cache/epub/" + (i+1) +"/pg" + (i+1) +".txt";
         }



//        for (int i = 10; i < sources.length; i++) {
//            //int id = ThreadLocalRandom.current().nextInt(50000);
////            sources[i] = "http://gutenberg.org/cache/epub/" + i + "/pg" + i + ".txt";
//            sources[i] = "http://www.gutenberg.org/ratelimiter.php/cache/epub/" + i +"/pg" +i +".txt";
//        }

        boolean[] threadDone = new boolean[5];
//        for (int i = 0; i < 5; i++) {
//            threadDone[i] = false;
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

//        ExecutorService executorService = Executors.newFixedThreadPool(5);

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



//        String testString = "петрава фыафыва фыва . фыа фыа !! . 898 ";
//
//        String dd = testString.substring(findEndLastSentence(testString)+1);
//
//        isNotEndedSentence(testString);
        //Pattern p = Pattern.compile("^[А-Яа-я,;'\"\\s-—]+[.?!]");
//        Pattern p = Pattern.compile("[А-Я].+?[.?!]");

//        Matcher m = p.matcher(testString);
//        m.groupCount();
//        System.out.println(m.group());

//        System.out.println(System.getProperty("user.dir"));



//        Pattern p = Pattern.compile(".+[.?!]");
//        String text = "Егор. Алла! Анна";
//        Pattern pattern = Pattern.compile(".+?[.?!]");
//
//        Matcher matcher = pattern.matcher(text);
//        while (matcher.find()) {
////            System.out.println(matcher.groupCount());
//            int start=matcher.start();
//            int end=matcher.end();
//            System.out.println("Найдено совпадение " + text.substring(start,end) + " с "+ start + " по " + (end-1) + " позицию");
//        }
//        System.out.println(matcher.replaceFirst("Ира"));
//        System.out.println(matcher.replaceAll("Ольга"));
//        System.out.println(text);


//
//        for (int i = 0; i < 20; i++) {
//            sources[i] = "file:out/file_" +(i+1)+".txt";
//        }
//
//        InputStream input = null;
//        try {
////            input = new URL(sources[1]).openStream();
//            input = new URL("file:out\\file_4.txt").openStream();
//
//        } catch (IOException e) {
//            System.out.println("Не могу получить данные с ресурса");
//            e.printStackTrace();
//            return;
//        }
//
//
//
//
//        List<String> sentenceWithWords = new ArrayList<>();
//        BufferedReader reader = null;
//        try {
//            reader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
//            //OutputStream fileResult = new FileOutputStream("out\\result.txt");
//            String beginSentence = "";
//
//            while (reader.ready()) {
//                String line = beginSentence + reader.readLine();
//                int lastInd = findEndLastSentence(line);
//                if (lastInd == 0) {
//                    beginSentence = beginSentence + " " +line;
//                    continue;
//                } else {
//                    beginSentence = "";
//                }
//                // если последний значащий символ .?! значит тут нет предложения, которое началось на этой строке и закочилось на следующих
////                ищем последнее вхождение окончания предложения в строку
//               // String dd = line.substring(findEndLastSentence(line));
//
//                findInString(line, words, sentenceWithWords);
//
//
////                String beginSentence = null;
//                if ((lastInd != 0) && (lastInd < line.length()) && (isNotEndedSentence(line.substring(lastInd)))) {
//                    beginSentence = line.substring(lastInd);
//                }
//
////                //findEndLastSentence(line);
////                Pattern p = Pattern.compile("[А-Я].+?[.?!]");
////                Matcher m = p.matcher(testString);
////                System.out.println(m.groupCount());
////                System.out.println(m.group());
//
//            }
//        } catch (IOException e) {
//            System.out.println("Непредвиденная ошибка");
//            e.printStackTrace();
//        }
//        writeToFile (sentenceWithWords, path);
//        System.out.println(sentenceWithWords.toString());
    }

    void getOccurencies(String[] sources, String[] words, String res) {

    }


}
