package ru.innopolis.homework11;

import ru.innopolis.homework11.entity.Course;
import ru.innopolis.homework11.entity.Person;
import ru.innopolis.homework11.entity.Subject;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public class MainHomework11 {

    private static final String url = "jdbc:postgresql://localhost:5432/postgres";
    private static final String login = "postgres";
    private static final String pass = "123";

    public static void main(String[] args) {

        try (Connection connection = DriverManager.getConnection(url, login, pass)) {

            InvokerDAOPerson invokerDAOPerson = new InvokerDAOPerson(connection);
            List<Person> listPerson = Utils.fillPersonForTesting();
            for (Person person : listPerson) {
                invokerDAOPerson.insertPerson(person.getName(), person.getBirthDate());
            }

            InvokerDAOSubject invokerDAOSubject = new InvokerDAOSubject(connection);
            List<Subject> listSubject = Utils.fillSubjectForTesting();
            for (Subject subject : listSubject) {
                invokerDAOSubject.createSubject(subject.getDescription());
            }


            Collection<Person> listAllPerson = invokerDAOPerson.getAllPerson();
            System.out.println(Arrays.toString(listAllPerson.toArray()));

            Optional<Person> personById = invokerDAOPerson.getPersonById(1);
            personById.ifPresent(name -> System.out.println(name.toString()));

            System.out.println("Количество обновленных записей - " + invokerDAOPerson.updatePersonById(2,
                    "Konor McGregor", 34472520000l));

            System.out.println("Количество удаленных записей - " + invokerDAOPerson.deletePersonById(5));


            Optional<Subject> subjectById = invokerDAOSubject.getSubjectById(2);
            subjectById.ifPresent(name -> System.out.println(name.toString()));

            InvokerDAOCourse invokerDAOCourse = new InvokerDAOCourse(connection);

            invokerDAOCourse.linkToCourse(
                    invokerDAOPerson.getPersonById(6).get(),
                    invokerDAOSubject.getSubjectById(6).get(),
                    invokerDAOSubject.getSubjectById(7).get());

            invokerDAOCourse.linkToCourse(
                    invokerDAOSubject.getSubjectById(8).get(),
                    invokerDAOPerson.getPersonById(7).get(),
                    invokerDAOPerson.getPersonById(8).get());

            Course course = invokerDAOCourse.getCourse(6, 7);
            System.out.println(course);
        } catch (SQLException e) {
            System.out.println("Что то пошло не так");
            e.printStackTrace();
        }
    }
}
