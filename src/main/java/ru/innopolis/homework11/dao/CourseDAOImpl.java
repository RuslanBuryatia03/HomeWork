package ru.innopolis.homework11.dao;

import ru.innopolis.homework11.entity.Course;
import ru.innopolis.homework11.entity.Person;
import ru.innopolis.homework11.entity.Subject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Реализация интерфейса CourseDAO
 */

public class CourseDAOImpl implements CourseDAO {

    private final Connection connection;

    public CourseDAOImpl(Connection connection) {
        this.connection = connection;
    }

    private static final String LINK_TO_COURSE_SQL_TEMPLATE =
            "insert into public.course (person_id, subject_id) values (?, ?)";

    private static final String SELECT_COURSE_SQL_TEMPLATE =
            "select * from public.course where person_id = ? and subject_id= ?";



    /**
     * Производит вставку записей в таблицу Course
     * @param person объект, по которому необходимо сделать соответствие с массивом subject
     * @param subject массив, по которому необходимо сделать соответствие
     * @return количество вставленных записей в таблицу Course
     * @throws SQLException ошибка на уровне СУБД или другая ошибка
     */
    @Override
    public int linkToCourse(Person person, Subject... subject) throws SQLException {
        int countInsertRow = 0;
        for (int i = 0; i < subject.length; i++) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(LINK_TO_COURSE_SQL_TEMPLATE)) {
                preparedStatement.setInt(1, person.getId());
                preparedStatement.setInt(2, subject[i].getId());
                countInsertRow = countInsertRow + preparedStatement.executeUpdate();
            }
        }
        return countInsertRow;
    }


    /**
     * Производит вставку записей в таблицу Course
     * @param subject объект, по которому необходимо сделать соответствие с массивом person
     * @param person  массив, по которому необходимо сделать соответствие
     * @return количество вставленных записей в таблицу Course
     * @throws SQLException ошибка на уровне СУБД или другая ошибка
     */
    @Override
    public int linkToCourse(Subject subject, Person... person) throws SQLException {
        int countInsertRow = 0;
        for (int i = 0; i < person.length; i++) {
            try (PreparedStatement preparedStatement = connection.prepareStatement(LINK_TO_COURSE_SQL_TEMPLATE)) {
                preparedStatement.setInt(1, person[i].getId());
                preparedStatement.setInt(2, subject.getId());
                countInsertRow = countInsertRow + preparedStatement.executeUpdate();
            }
        }
        return countInsertRow;
    }

    /**
     * Возвращает объект Course по заданным person_id, subject_id
     * @param person_id внешний ключ на таблицу Person
     * @param subject_id внешний ключ на таблицу Subject
     * @return  объект Course
     * @throws SQLException  ошибка на уровне СУБД или другая ошибка
     */
    @Override
    public Course getCourse(int person_id, int subject_id) throws SQLException {
        try (PreparedStatement preparedStatement = connection.prepareStatement(SELECT_COURSE_SQL_TEMPLATE)) {
            preparedStatement.setInt(1, person_id);
            preparedStatement.setInt(2, subject_id);
            ResultSet resultSet = preparedStatement.executeQuery();
            return addCourse(resultSet);
        }
    }

    /**
     * Формирует объект Course по данным resultSet
     * @param resultSet данные из таблицы Course
     * @return объект Course, если resultSet не пустой, иначе null
     * @throws SQLException ошибка на уровне СУБД или другая ошибка
     */
    private Course addCourse(ResultSet resultSet) throws SQLException {
        Course course = new Course();

        if (resultSet.next()) {
            course.setPerson_id(resultSet.getInt("person_id"));
            course.setSubject_id(resultSet.getInt("subject_id"));
            PersonDAO personDAO = new PersonDAOImpl(connection);

            Person person = personDAO.getPersonById(resultSet.getInt("person_id")).get();
            course.setPerson(person);

            SubjectDAO subjectDAO = new SubjectDAOImpl(connection);

            Subject subject = subjectDAO.getSubjectById(resultSet.getInt("subject_id")).get();
            course.setSubject(subject);
            return course;
        } else {
            return null;
        }
    }


}
