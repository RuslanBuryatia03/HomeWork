package ru.innopolis.homework5;

public class Main {

    public static final String OUT_XML = "./out/person.xml";

    public static void main(String[] args) {
        Person person = new Person(1787212, 30, 85.3, "Tom", "high scholl");
        try {
            person.serialize(person, OUT_XML);
        } catch (IllegalAccessException e) {
            System.out.println("Входные данные для сериализации неверные");
            e.printStackTrace();
        }
        Object deSerializeObject = person.deSerialize(OUT_XML);
        System.out.println("deSerializeObject = " + deSerializeObject);
        System.out.println("выполнено");

    }
}
