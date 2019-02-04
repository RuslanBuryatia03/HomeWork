package ru.innopolis.homework11;

import ru.innopolis.homework11.entity.Person;
import ru.innopolis.homework11.entity.Subject;

import java.util.ArrayList;
import java.util.List;

class Utils {

    private Utils() {

    }

    /**
     * Заполняет  List<String> для сущностей Person
     * @return List<String> с именами
     */
    private static List<String> fillNamePerson() {
        List<String> listNamePesron = new ArrayList<>();
        listNamePesron.add("Abby");
        listNamePesron.add("Amanda");
        listNamePesron.add("Joanna");
        listNamePesron.add("Karen");
        listNamePesron.add("Kathy");
        listNamePesron.add("Patricia");
        return listNamePesron;
    }

    /**
     * Заполняет  List<String> для сущностей Subject
     * @return List<String> с названиями курсов
     */
    private static List<String> fillDescriptioSubject() {
        List<String> listDescriptioSubject = new ArrayList<>();
        listDescriptioSubject.add("Matan");
        listDescriptioSubject.add("Geometry");
        listDescriptioSubject.add("English");
        listDescriptioSubject.add("Java");
        listDescriptioSubject.add("Python");
        listDescriptioSubject.add("JavaScript");
        return listDescriptioSubject;
    }

    /**
     * Заполняет List<Person> с датой рождения - текущая дата
     * @return  List<Person>
     */
    static List<Person> fillPersonForTesting(){
        List<Person> listPerson = new ArrayList<>();
        List<String> listName = fillNamePerson();
        for (int i = 0; i < listName.size(); i++) {
            Person person = new Person(listName.get(i), System.currentTimeMillis());
            listPerson.add(person);
        }
        return listPerson;
    }

    /**
     * Заполняет List<Subject>
     * @return  List<Subject>
     */
    static List<Subject> fillSubjectForTesting(){
        List<Subject> listSubject = new ArrayList<>();
        List<String> listDescription = fillDescriptioSubject();
        for (int i = 0; i < listDescription.size(); i++) {
            Subject subject = new Subject(listDescription.get(i));
            listSubject.add(subject);
        }
        return listSubject;
    }
}
