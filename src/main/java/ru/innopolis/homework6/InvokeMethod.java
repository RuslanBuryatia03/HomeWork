package ru.innopolis.homework6;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;
import java.io.*;
import java.util.Scanner;

public class InvokeMethod {
    private String filename;
    private String loadClass;
    private String wantedString;
    private String fileClass;

    public InvokeMethod(String filename,  String loadClass, String wantedString, String fileClass ) {
        this.filename = filename;
        this.loadClass = loadClass;
        this.wantedString = wantedString;
        this.fileClass = fileClass;
    }

    /**
     * Возвращает файл в виде строки
     * @param filename путь к файлу
     * @return файл в виде строки
     */
    private String doAsString(String filename) {
        String fileAsString = "";

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {

            while (reader.ready()) {
                String lineInput = reader.readLine();
                fileAsString += (lineInput + "\n");
            }
        } catch (IOException e) {
            System.out.println("Не могу прочитать файл");
            e.printStackTrace();
            return null;
        }
        return fileAsString;
    }

    /**
     * Производит чтение с консоли построчно
     * @return строку введенную с консоли
     */

    public String readConsole() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Наберите текст класса построчно, для окончания ввода ничего не вводите и нажмите \"Enter\"");
        String bodyMethod = "";
        while (true) {
            String line = scanner.nextLine();
            if (!line.trim().equals("")) {
                bodyMethod += (line + "\n");
            } else {
                break;
            }
        }
        return bodyMethod;
    }

    /**
     * В файле filename заменяет строку wantedString на wantedString + bodyMethod
     *
     * @param filename     файл для вставки
     * @param wantedString строка для дополнения строкой bodyMethod
     * @param bodyMethod   строка для дополнения
     * @throws IOException выбрасывается при неудачной записи
     */

    public void insertIntoFile(String filename, String wantedString, String bodyMethod) throws IOException {
        String fileAsString = doAsString(filename);
        if (fileAsString == null) {
            return;
        }
        BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
        String code = fileAsString.replace(wantedString, wantedString + "\n" + bodyMethod);
        writer.write(code);
        writer.close();
    }

    /**
     * Компилирует файл filename
     *
     * @param filename файл для компиляции
     * @return 0 если компиляция прошла успешно, иначе ненулевое значение
     */

    public int compileFile(String filename) {
        JavaCompiler javac = ToolProvider.getSystemJavaCompiler();
        String[] javacOpts = {filename};
        return javac.run(null, null, null, javacOpts);
    }

    /**
     * Вызываем метод для класса loadClass
     *
     * @param loadClass класс, у которого производится вызов метода
     * @throws ClassNotFoundException класс не найден
     * @throws IllegalAccessException ошибка при создании экземпляра класса
     * @throws InstantiationException Невозможно создать экземпляр данного класса
     */
    public void invokeNewMethod(String fileClass, String loadClass) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        ClassLoader cl = new MyClassLoader(fileClass, loadClass);
        Class<?> kindClass = cl.loadClass(loadClass);
        Worker kindMagic = (Worker) kindClass.newInstance();
        kindMagic.doWork();

    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getLoadClass() {
        return loadClass;
    }

    public void setLoadClass(String loadClass) {
        this.loadClass = loadClass;
    }

    public String getWantedString() {
        return wantedString;
    }

    public void setWantedString(String wantedString) {
        this.wantedString = wantedString;
    }
}
