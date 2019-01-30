package ru.innopolis.homework2;

import java.util.*;

/**
 * Класс реализующий методы деления массива Integer на число, а также метод
 * суммирующий все элементы.
 */

public class MathBox {

    private List<Integer> list;
    private final int id;
    private final Random random = new Random();
    {
        id = random.nextInt(100000000);
    }

    MathBox(Integer[] arr) {
        Arrays.sort(arr);
        list = new ArrayList<>(Arrays.asList(arr));
        System.out.println(Arrays.toString(arr));
    }

    /**
     * Суммирует все элементы массива
     * @return сумму всех элементов
     */
    int summator() {
        int sum = 0;
        for (Integer el : list) {
            sum += el;
        }
        return sum;
    }

    /**
     * Производит деление каждого элемента массива на
     * целое число divider
     * @param divider делитель
     * @return List<Integer> целых чисел
     * @throws ArithmeticException выбрасывается, если делитель равен 0
     */
    List<Integer> splitter(int divider) throws ArithmeticException {
        if (divider == 0) {
            throw new ArithmeticException("Деление на ноль");
        }
        List<Integer> listDiv = new ArrayList<>();
        for (Integer el : list) {
            listDiv.add(el/divider);
        }
        return listDiv;
    }

    /**
     * Метод выводит на консоль List<Integer>
     * @param listDiv List<Integer>
     */
    void printList(List<Integer> listDiv) {
        System.out.println(Arrays.toString(listDiv.toArray(new Integer[0])));
    }


    /**
     * Удаляет из List<Integer> элемент
     * @param insEl элементы, который хотим удалить
     * @return true, если элемент найден и успешно удалено, иначе false
     */
    boolean deleteEl(Integer insEl) {
        if (list.contains(insEl)){
            list.remove(insEl);
            return true;
        }
        return false;


    }

    @Override
    public boolean equals(Object o) {
        return this == o;
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        return list.toString();
    }
}
