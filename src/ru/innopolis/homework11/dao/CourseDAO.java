package ru.innopolis.homework11.dao;

import ru.innopolis.homework11.entity.Course;
import ru.innopolis.homework11.entity.Person;
import ru.innopolis.homework11.entity.Subject;

import java.sql.SQLException;

/**
 * Интерфейс DAO слоя таблицы Course
 */
public interface CourseDAO {

    int linkToCourse(Person person, Subject... subject) throws SQLException;

    int linkToCourse(Subject subject, Person... person) throws SQLException;

    Course getCourse(int person_id, int subject_id) throws SQLException;

}
