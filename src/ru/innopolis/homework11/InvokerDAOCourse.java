package ru.innopolis.homework11;

import ru.innopolis.homework11.dao.*;
import ru.innopolis.homework11.entity.Course;
import ru.innopolis.homework11.entity.Person;
import ru.innopolis.homework11.entity.Subject;

import java.sql.Connection;
import java.sql.SQLException;


/**
 * Класс для работы с таблицей Course
 * через DAO
 */
public class InvokerDAOCourse {

    private final CourseDAO dao;

    InvokerDAOCourse(Connection connection) {
        this.dao = new CourseDAOImpl(connection);
    }

    /**
     * Производит вставку записей в таблицу Course
     * @param person объект, по которому необходимо сделать соответствие с массивом subject
     * @param subject массив, по которому необходимо сделать соответствие
     * @return количество вставленных записей в таблицу Course
     * @throws SQLException ошибка на уровне СУБД или другая ошибка
     */
    public int linkToCourse(Person person, Subject... subject) throws SQLException {
        return dao.linkToCourse(person, subject);
    }


    /**
     * Производит вставку записей в таблицу Course
     * @param subject объект, по которому необходимо сделать соответствие с массивом person
     * @param person  массив, по которому необходимо сделать соответствие
     * @return количество вставленных записей в таблицу Course
     * @throws SQLException ошибка на уровне СУБД или другая ошибка
     */
    public int linkToCourse(Subject subject, Person... person) throws SQLException {
        return dao.linkToCourse(subject, person);
    }

    /**
     * Возвращает объект Course по заданным person_id, subject_id
     * @param person_id внешний ключ на таблицу Person
     * @param subject_id внешний ключ на таблицу Subject
     * @return  объект Course
     * @throws SQLException  ошибка на уровне СУБД или другая ошибка
     */
    Course getCourse(int person_id, int subject_id)  throws SQLException {
        return dao.getCourse(person_id, subject_id) ;
    }

}










