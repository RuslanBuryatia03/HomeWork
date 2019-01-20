package ru.innopolis.homework21;

import java.util.List;

public class ObjectBox {

    List<Object> list;

    public boolean addObject(Object o) {
        if (o == null)  {
            return false;
        } else {
            list.add(o);
            return true;
        }
    }

    public boolean deleteObject(Object o) {
        if (o == null)  {
            return false;
        }
        if (list.contains(o)) {
            list.remove(o);
            return true;
        }
        return false;
    }

    public String dump(){
        String str = "";
        if (list.isEmpty()){
            return str;
        }

        for(Object e : list) {
            str +=e.toString();
        }
        return str;
    }
}


