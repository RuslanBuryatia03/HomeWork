package ru.innopolis.homework11.dao;

import ru.innopolis.homework11.entity.Subject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;


/**
 * Реализация интерфейса SubjectDAOImpl
 */
public class SubjectDAOImpl implements SubjectDAO {

    private final Connection connection;

    public SubjectDAOImpl(Connection connection) {
        this.connection = connection;
    }

    private static final String INSERT_SUBJECT_SQL_TEMPLATE =
            "insert into public.subject (description) values (?)";



    private static final String SELECT_SUBJECT_BY_ID_SQL_TEMPLATE =
            "select * from public.subject s " +
                    " where s.subject_id = ?";



    /**
     * Получает объет Subject по id
     *
     * @param id идентификатор записи в таблице Subject
     * @return Optional<Subject> запись по данной id
     * @throws SQLException ошибка на уровне СУБД или другая ошибка
     */
    @Override
    public Optional<Subject> getSubjectById(int id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_SUBJECT_BY_ID_SQL_TEMPLATE)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
//            Optional<Person> optionalPerson = Optional.of(addPerson(resultSet));
                return Optional.ofNullable(addSubject(resultSet));

        }
    }

    /**
     * Записывает строку в таблицу Subject
     *
     * @param subject объект Subject, для которого необходимо создать запись
     * @return true, если запись успешно вставлена, иначе false
     * @throws SQLException ошибка на уровне СУБД или другая ошибка
     */
    @Override
    public boolean createSubject(Subject subject) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_SUBJECT_SQL_TEMPLATE)) {
            statement.setString(1, subject.getDescription());
            return statement.execute();
        }
    }

    /**
     * Создает объект Subject по данным resultSet
     * @param resultSet заданный resultSet
     * @return объект Subject, если resultSet непустой, иначе null
     * @throws SQLException ошибка на уровне СУБД или другая ошибка
     */
    private Subject addSubject(ResultSet resultSet) throws SQLException {
        if(resultSet.next()) {
            return  new Subject(resultSet.getInt("subject_id"), resultSet.getString("description"));
        } else {
            return null;
        }
    }
}
