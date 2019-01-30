package ru.innopolis.homework22;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

/**
 * Класс с параметром, расширяющим Number. Содержит методы суммирования элементов
 * List<T> , деления каждого элемента на заданное число, удаления элемента из List<T>.
 *
 * @param <T>
 */

public class MathBox<T extends Number> {

    private List<T> list;
    private final int id;
    private final static Random random = new Random();

    private MathBox() {
        id = random.nextInt(100000000);
    }

    MathBox(T[] arr) {
        this();
        Arrays.sort(arr);
        list = new ArrayList<>(Arrays.asList(arr));
        DecimalFormat myFormatter = new DecimalFormat("### ###.##############");
        String output;
        for (T t : arr) {
            output = myFormatter.format(t);
            System.out.println(output);
        }
    }

    /**
     * Суммирует все элементы
     *
     * @return сумму всех элементов
     */

    T summator() {

        BigDecimal sum = BigDecimal.valueOf(0);
        for (T el : list) {
            sum = sum.add(BigDecimal.valueOf(el.doubleValue()));
        }
        return (T) sum;
    }

    /**
     * Делит все элементы List<Number> на делитель
     *
     * @param divider делитель
     * @return List<Number>
     */

    List<Number> splitter(T divider) {

        List<Number> listDiv = new ArrayList<>();
        String classList = divider.getClass().getCanonicalName();
        if (classList.equals("java.lang.Long")          ||
                classList.equals("java.lang.Integer")   ||
                classList.equals("java.lang.Byte")      ||
                classList.equals("java.lang.Short")) {
            Long dividerLong = divider.longValue();
            if (dividerLong == 0) {
                System.out.println("деление на ноль");
                return null;
            }
            for (T el : list) {
                Long elementLong = el.longValue();
                listDiv.add(elementLong  / dividerLong);
            }
            return listDiv;
        }

        BigDecimal dividerBigDecimal = BigDecimal.valueOf(divider.doubleValue());
        for (T el : list) {
            BigDecimal elementBigDecimal = BigDecimal.valueOf(el.doubleValue()).setScale(10, RoundingMode.HALF_UP  );
            listDiv.add(elementBigDecimal.divide(dividerBigDecimal, 10,RoundingMode.HALF_UP ));
        }
        return listDiv;

    }

    /**
     * Удаления элемента из List<T>.
     *
     * @param element элемент, который хотим удалить.
     * @return true если удаление успешно выполнено, иначе false
     */
    boolean deleteElement(Number element) {
        return list.remove(element);
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
        StringBuilder str = new StringBuilder("");
        for (Number el : list) {
            str.append(el + " ");
        }
        return str.toString();
    }
}
