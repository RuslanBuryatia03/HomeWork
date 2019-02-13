package ru.innopolis.homework6;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class MyClassLoader extends ClassLoader {

    private String fileClass;
    private String loadClass;

    public MyClassLoader(ClassLoader parent, String fileClass, String loadClass) {
        super(parent);
        this.fileClass = fileClass;
        this.loadClass = loadClass;
    }

    public MyClassLoader(String fileClass, String loadClass) {
        this.fileClass = fileClass;
        this.loadClass = loadClass;
    }

    public String getFileClass() {
        return fileClass;
    }

    public void setFileClass(String fileClass) {
        this.fileClass = fileClass;
    }

    public String getLoadClass() {
        return loadClass;
    }

    public void setLoadClass(String loadClass) {
        this.loadClass = loadClass;
    }

    /**
     * Производит загрузку файла name
     *
     * @param name файл для загрузки
     * @return Class по заданному имени
     * @throws ClassNotFoundException вызывается если класс не найден
     */

    @Override
    public Class<?> loadClass(String name) throws ClassNotFoundException {
        if (loadClass.equals(name)) {
            return findClass(name);
        }
        return super.loadClass(name);
    }

    /**
     * Конвертирет массив байт в экземпляр класса
     *
     * @param name если совпадает имя класса, то массив байт берется из определенного класаа
     * @return Class по заданному имени
     * @throws ClassNotFoundException вызывается если класс не найден
     */

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
//        System.out.println("findClass starts work: " + name);
//        if ("ru.innopolis.homework6.SomeClass".equals(name)) {
        if (loadClass.equals(name)) {
            try {
//                byte[] bytes = Files.readAllBytes(Paths.get("./src/ru/innopolis/homework6/SomeClass.class"));
                byte[] bytes = Files.readAllBytes(Paths.get(fileClass));
                return defineClass(name, bytes, 0, bytes.length);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return super.findClass(name);
    }
}
