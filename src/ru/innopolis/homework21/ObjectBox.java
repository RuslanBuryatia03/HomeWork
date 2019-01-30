package ru.innopolis.homework21;

import java.util.List;

/**
 * Класс для операцией с List<Object>
 */
public class ObjectBox {

    List<Object> list;

    /**
     * Добавляет в List<Object> элемент
     * @param o добавляемый элемент
     * @return true при успешном добавлении, иначе false
     */
    public boolean addObject(Object o) {
        if (o == null)  {
            return false;
        } else {
            list.add(o);
            return true;
        }
    }

    /**
     * Удаляет из коллекции объект
     * @param o объект, который хотим удалить
     * @return true при успешном удалении, иначе false
     */
    public boolean deleteObject(Object o) {
        return list.remove(o);
    }

    /**
     * Выводит на консоль коллекцию в строку
     * @return коллекцию в строку
     */
    public String dump(){
        StringBuilder str = new StringBuilder("");
        if (list.isEmpty()){
            return str.toString();
        }

        for(Object e : list) {
            str.append(e.toString());
        }
        return str.toString();
    }
}


