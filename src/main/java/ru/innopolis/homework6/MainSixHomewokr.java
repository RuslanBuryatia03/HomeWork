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
//        String fileClass = "./src/ru/innopolis/homework6/SomeClass.class";
        String fileClass = "./out/SomeClass.class";
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

}
