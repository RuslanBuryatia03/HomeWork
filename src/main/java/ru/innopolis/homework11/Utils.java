package ru.innopolis.homework11;

import ru.innopolis.homework11.entity.Person;
import ru.innopolis.homework11.entity.Subject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Utils {

    private Utils() {

    }

    /**
     * Заполняет  List<String> для сущностей Person
     * @return List<String> с именами
     */
    private static List<String> fillNamePerson() {
        Random random = new Random();
        List<String> listNamePesron = new ArrayList<>();
        listNamePesron.add("Abby"+ random.nextInt(100000));
        listNamePesron.add("Amanda"+ random.nextInt(100000));
        listNamePesron.add("Joanna"+ random.nextInt(100000));
        listNamePesron.add("Karen"+ random.nextInt(100000));
        listNamePesron.add("Kathy"+ random.nextInt(100000));
        listNamePesron.add("Patricia"+ random.nextInt(100000));
        return listNamePesron;
    }

    /**
     * Заполняет  List<String> для сущностей Subject
     * @return List<String> с названиями курсов
     */
    private static List<String> fillDescriptioSubject() {
        Random random = new Random();
        List<String> listDescriptioSubject = new ArrayList<>();
        listDescriptioSubject.add("Matan" + random.nextInt(100000));
        listDescriptioSubject.add("Geometry"+ random.nextInt(100000));
        listDescriptioSubject.add("English"+ random.nextInt(100000));
        listDescriptioSubject.add("Java"+ random.nextInt(100000));
        listDescriptioSubject.add("Python"+ random.nextInt(100000));
        listDescriptioSubject.add("JavaScript"+ random.nextInt(100000));
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
