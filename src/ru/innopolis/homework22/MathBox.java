package ru.innopolis.homework22;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MathBox<T extends Number> {

    List<T> list;
    private final int id; // = (long) Math.random()*100000000;

    {
        id = (int) Math.random()*100000000;;
    }
//    public MathBox() {
//        id = (int) Math.random()*100000000;;
//    }


    MathBox(T[] arr) {
        Arrays.sort(arr);  // падает если есть null
        list = new ArrayList<T>(Arrays.asList(arr));

        for (int i = 0; i < arr.length; i++) {
            System.out.println(arr[i]);
        }
        System.out.println();
    }

    //public static<T> T identity(T arg) { return arg; };

//    public BigDecimal summator() {
//        BigDecimal sum = BigDecimal.valueOf(0);                //identity(Integer.valueOf(0));
//
//        for (T el : list) {
//            //double dd = (el instanceof Double) ? el.doubleValue(): el.intValue();
//            sum = sum.add(BigDecimal.valueOf(el.doubleValue()));
//        }
//        return sum;
//    }


    public Number summator(String numberClassName) {
        BigDecimal sum = BigDecimal.valueOf(0);                //identity(Integer.valueOf(0));

        for (T el : list) {
            //double dd = (el instanceof Double) ? el.doubleValue(): el.intValue();
            sum = sum.add(BigDecimal.valueOf(el.doubleValue()));
        }
//        Integer sumInt = null;
//        Long sumLong = null;
//        Byte sumByte = null;
//        Short sumShort  = null;
//        Double sumDouble = null;
//        Float  sumFloat  = null;


        switch (numberClassName) {
            case "java.lang.Integer":
                return sum.intValue();
            case "java.lang.Long":
                return sum.longValue();
            case "java.lang.Byte":
                return sum.byteValue();
            case "java.lang.Short":
                return sum.shortValue();
            case "java.lang.Double":
                return sum.doubleValue();
            case "java.lang.Float":
                return sum.floatValue();

//            default:
//                return  null;
        }

        return null;
    }

    public List<Number> splitter(T divider, String numberClassName) {
        List<Number> listDiv = null;
        switch (numberClassName) {
            case "java.lang.Integer":
                if (divider.intValue() == 0) {
                    System.out.println("деление на ноль");
                    return null;
                } else {
                    listDiv = new ArrayList<>();
                    for (T el : list) {
                        listDiv.add(((Integer) el) / (Integer) divider);
                    }
                    return listDiv;
                }

            case "java.lang.Long":
                if (divider.longValue() == 0) {
                    System.out.println("деление на ноль");
                    return null;
                } else {
                    listDiv = new ArrayList<>();
                    for (T el : list) {
                        listDiv.add(((Long) el) / (Long) divider);
                    }
                    return listDiv;
                }
            case "java.lang.Byte":
                if (divider.byteValue() == 0) {
                    System.out.println("деление на ноль");
                    return null;
                } else {
                    listDiv = new ArrayList<>();
                    for (T el : list) {
                        listDiv.add(((Byte) el) / (Byte) divider);
                    }
                    return listDiv;
                }
            case "java.lang.Short":
                if (divider.shortValue() == 0) {
                    System.out.println("деление на ноль");
                    return null;
                }else {
                    listDiv = new ArrayList<>();
                    for (T el : list) {
                        listDiv.add(((Short) el) / (Short) divider);
                    }
                    return listDiv;
                }
            case "java.lang.Double":
                if (divider.doubleValue() == 0) {
                    System.out.println("деление на ноль");
                    return null;
                }else {
                    listDiv = new ArrayList<>();
                    for (T el : list) {
                        listDiv.add(((Double) el) / (Double) divider);
                    }
                    return listDiv;
                }
            case "java.lang.Float":
                if (divider.floatValue() == 0) {
                    System.out.println("деление на ноль");
                    return null;
                }else {
                    listDiv = new ArrayList<>();
                    for (T el : list) {
                        listDiv.add(((Float) el) / (Float) divider);
                    }
                    return listDiv;
                }

//            default:
//                return  null;
        }


//        List<BigDecimal> listDiv = new ArrayList<>();
//        for (T el : list) {
//            listDiv.add(((BigDecimal) el).divide((BigDecimal)divider));
//        }

//        List<T> listDiv = new ArrayList<>();
//        for (T el : list) {
//            listDiv.add( (T)((BigDecimal) el).divide((BigDecimal)divider));
//        }


        for (Number el : listDiv) {
            System.out.println(el.toString());
        }
        return listDiv;
    }
//
//    public List<Double> splitter(double divider) {
//        List<Double> listDiv = new ArrayList<Double>();
//        for (Integer el : list) {
//            listDiv.add(el/divider);
//        }
//        System.out.println(this.toString());
////        for (Double el : listDiv) {
////            System.out.println(el.doubleValue());
////        }
//        return listDiv;
//    }
//
    public boolean deleteEl(Number insEl) {
        if (list.contains(insEl)){
            list.remove(insEl);
            return true;
        }
        return false;


    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } else return false;
//        if (o == null || getClass() != o.getClass()) return false;
//        MathBox mathBox = (MathBox) o;
//        return this.list.equals(o);
    }

    @Override
    public int hashCode() {
        return id;
    }

    @Override
    public String toString() {
        String str = "";
        for (Number el : list) {
            str = str + el+" ";
        }
        return str;
    }
}
