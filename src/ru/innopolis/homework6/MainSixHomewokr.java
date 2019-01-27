package ru.innopolis.homework6;


import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;
import java.util.Scanner;

public class MainSixHomewokr {



    public static void main(String[] args) {

        String filename = "./src/ru/innopolis/homework6/SomeClass.java";
        String loadClass = "ru.innopolis.homework6.SomeClass";
        String wantedString = "public void doWork()  {";
        String fileClass = "./src/ru/innopolis/homework6/SomeClass.class";
        InvokeMethod invokeMethod = new InvokeMethod(filename, loadClass, wantedString, fileClass);

        String enteredText = invokeMethod.readConsole();
        if (enteredText == null || enteredText.trim().length() == 0) {
            System.out.println("Вы не ввели никакого текста");
            return;
        }
        try {
            invokeMethod.insertIntoFile(filename, wantedString, enteredText);
        } catch (IOException e) {
            System.out.println("Не могу записать в файл - " + filename);
            e.printStackTrace();
            return;
        }
        if (invokeMethod.compileFile(filename) != 0) {
            System.out.println("Не удалось скомпилировать файл");
            return;
        }

        try {
            invokeMethod.invokeNewMethod(fileClass, loadClass);
        } catch (ClassNotFoundException e) {
            System.out.println("Класс " + loadClass + " не найден ");
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            System.out.println("Ошибка при создании экземпляра класса" + loadClass);
            e.printStackTrace();
        } catch (InstantiationException e) {
            System.out.println("Невозможно создать экземпляр данного класса " + loadClass);
            e.printStackTrace();
        }

    }

//    /**
//     * Возвращает файл в виде строки
//     * @param filename путь к файлу
//     * @return файл в виде строки
//     */
//    private static String doAsString(String filename) {
//        String fileAsString = "";
//
//        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
//
//            while (reader.ready()) {
//                String lineInput = reader.readLine();
//                fileAsString += (lineInput + "\n");
//            }
//        } catch (IOException e) {
//            System.out.println("Не могу прочитать файл");
//            e.printStackTrace();
//            return null;
//        }
//        return fileAsString;
//    }
//
//    /**
//     * Производит чтение с консоли построчно
//     * @return строку введенную с консоли
//     */
//
//    private static String readConsole() {
//        Scanner scanner = new Scanner(System.in);
//        System.out.println("Наберите текст класса построчно, для окончания ввода ничего не вводите и нажмите \"Enter\"");
//        String bodyMethod = "";
//        while (true) {
//            String line = scanner.nextLine();
//            if (!line.trim().equals("")) {
//                bodyMethod += (line + "\n");
//            } else {
//                break;
//            }
//        }
//        return bodyMethod;
//    }
//
//    /**
//     * В файле filename заменяет строку wantedString на wantedString + bodyMethod
//     *
//     * @param filename     файл для вставки
//     * @param wantedString строка для дополнения строкой bodyMethod
//     * @param bodyMethod   строка для дополнения
//     * @throws IOException выбрасывается при неудачной записи
//     */
//
//    private static void insertIntoFile(String filename, String wantedString, String bodyMethod) throws IOException {
//        String fileAsString = doAsString(filename);
//        if (fileAsString == null) {
//            return;
//        }
//        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
//        String code = fileAsString.replace(wantedString, wantedString + "\n" + bodyMethod);
//        writer.write(code);
//        writer.close();
//    }
//
//    /**
//     * Компилирует файл filename
//     *
//     * @param filename файл для компиляции
//     * @return 0 если компиляция прошла успешно, иначе ненулевое значение
//     */
//
//    private static int compileFile(String filename) {
//        JavaCompiler javac = ToolProvider.getSystemJavaCompiler();
//        String[] javacOpts = {filename};
//        return javac.run(null, null, null, javacOpts);
//    }
//
//    /**
//     * Вызываем метод для класса loadClass
//     *
//     * @param loadClass класс, у которого производится вызов метода
//     * @throws ClassNotFoundException класс не найден
//     * @throws IllegalAccessException ошибка при создании экземпляра класса
//     * @throws InstantiationException Невозможно создать экземпляр данного класса
//     */
//    private static void invokeNewMethod(String loadClass) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
//        ClassLoader cl = new MyClassLoader();
//        Class<?> kindClass = cl.loadClass(loadClass);
//        Worker kindMagic = (Worker) kindClass.newInstance();
//        kindMagic.doWork();
//
//    }

}
