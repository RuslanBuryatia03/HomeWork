package ru.innopolis.homework11;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.innopolis.homework11.dao.CourseDAO;
import ru.innopolis.homework11.dao.CourseDAOImpl;
import ru.innopolis.homework11.entity.Course;
import ru.innopolis.homework11.entity.Person;
import ru.innopolis.homework11.entity.Subject;

import java.sql.Connection;
import java.sql.SQLException;


/**
 * Класс для работы с таблицей Course
 * через DAO
 *
 * @author KhankhasaevRV
 * @since 2019.02.06
 */
public class InvokerDAOCourse {

    private final CourseDAO dao;
    private final Logger LOGGER = LoggerFactory.getLogger(InvokerDAOCourse.class);
    private final Connection connection;

    InvokerDAOCourse(Connection connection) {
        this.dao = new CourseDAOImpl(connection);
        this.connection = connection;
    }


    public void invokeCourseDemo() throws SQLException {
        InvokerDAOSubject invokerDAOSubject = new InvokerDAOSubject(connection);
        InvokerDAOPerson invokerDAOPerson = new InvokerDAOPerson(connection);

        LOGGER.trace("Запись в таблицу Course - одной записи Person соответствует несколько записей Subject");
        linkToCourse(
                invokerDAOPerson.getPersonById(13).get(),
                invokerDAOSubject.getSubjectById(13).get(),
                invokerDAOSubject.getSubjectById(14).get());
        LOGGER.trace("Вставлена запись в таблицу Course  - одной записи Person соответствует несколько записей Subject");

        LOGGER.trace("Запись в таблицу Course - одной записи Subject соответствует несколько записей Person");
        linkToCourse(
                invokerDAOSubject.getSubjectById(15).get(),
                invokerDAOPerson.getPersonById(14).get(),
                invokerDAOPerson.getPersonById(15).get());
        LOGGER.trace("Вставлена запись в таблицу Course - одной записи Subject соответствует несколько записей Person");

        LOGGER.trace("Получение записи из таблицы Course");
        Course course = getCourse(6, 7);
        System.out.println(course);

    }

    /**
     * Производит вставку записей в таблицу Course
     *
     * @param person  объект, по которому необходимо сделать соответствие с массивом subject
     * @param subject массив, по которому необходимо сделать соответствие
     * @return количество вставленных записей в таблицу Course
     * @throws SQLException ошибка на уровне СУБД или другая ошибка
     */
    private int linkToCourse(Person person, Subject... subject) throws SQLException {
        return dao.linkToCourse(person, subject);
    }


    /**
     * Производит вставку записей в таблицу Course
     *
     * @param subject объект, по которому необходимо сделать соответствие с массивом person
     * @param person  массив, по которому необходимо сделать соответствие
     * @return количество вставленных записей в таблицу Course
     * @throws SQLException ошибка на уровне СУБД или другая ошибка
     */
    private int linkToCourse(Subject subject, Person... person) throws SQLException {
        return dao.linkToCourse(subject, person);
    }

    /**
     * Возвращает объект Course по заданным person_id, subject_id
     *
     * @param person_id  внешний ключ на таблицу Person
     * @param subject_id внешний ключ на таблицу Subject
     * @return объект Course
     * @throws SQLException ошибка на уровне СУБД или другая ошибка
     */
    private Course getCourse(int person_id, int subject_id) throws SQLException {
        return dao.getCourse(person_id, subject_id);
    }

}










