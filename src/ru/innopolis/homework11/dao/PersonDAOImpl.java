package ru.innopolis.homework11.dao;

import ru.innopolis.homework11.entity.Person;
import ru.innopolis.homework11.entity.Subject;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * Реализация интерфейса CourseDAO
 */

public class PersonDAOImpl implements PersonDAO {

    private final Connection connection;

    public PersonDAOImpl(Connection connection) {
        this.connection = connection;
    }

    private static final String INSERT_PERSON_SQL_TEMPLATE =
            "insert into person (name, birth_date) values (?, ?)";


    private static final String SELECT_ALL_PERSON_SQL_TEMPLATE =
            "select * from public.person";

    private static final String SELECT_PERSON_BY_SUBJECT_SQL_TEMPLATE =
            "select * from public.person p inner join public.course c on p.person_id = c.person_id" +
                    " where c.subject_id = ?";

    private static final String SELECT_PERSON_BY_ID_SQL_TEMPLATE =
            "select * from public.person p " +
                    " where p.person_id = ?";

    private static final String UPDATE_PERSON_BY_ID_SQL_TEMPLATE =
            "update public.person " +
                    "set name = ?, birth_date = ?" +
                    " where person_id = ? ";

    private static final String DELETE_PERSON_BY_ID_SQL_TEMPLATE =
            "delete from public.person p " +
                    " where p.person_id = ? ";


    /**
     * Получает объекты всех записей таблицы Person
     *
     * @return Collection<Person>
     * @throws SQLException ошибка на уровне СУБД или другая ошибка
     */
    @Override
    public Collection<Person> getAllPersons() throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_ALL_PERSON_SQL_TEMPLATE)) {
            ResultSet resultSet = statement.executeQuery();
            return addToCollection(resultSet);
        }
    }

    /**
     * Получает Collection<Person> по Subject
     *
     * @param subject объект Subject, для которого необходимо получить записи в таблице Person
     * @return Collection<Person> запись по данной id
     * @throws SQLException ошибка на уровне СУБД или другая ошибка
     */
    @Override
    public Collection<Person> getPersonsBySubject(Subject subject) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_PERSON_BY_SUBJECT_SQL_TEMPLATE)) {
            statement.setInt(1, subject.getId());
            ResultSet resultSet = statement.executeQuery();
            return addToCollection(resultSet);

        }
    }

    /**
     * Получает объет Person по id
     *
     * @param id идентификатор записи в таблице Person
     * @return Optional<Person> запись по данной id
     * @throws SQLException ошибка на уровне СУБД или другая ошибка
     */
    @Override
    public Optional<Person> getPersonById(int id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(SELECT_PERSON_BY_ID_SQL_TEMPLATE)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            return addPerson(resultSet);

        }
    }

    /**
     * Записывает строку в таблицу Person
     *
     * @param person объект, для которого необходимо вставить запись
     * @return true, если вставка успешно произведена, иначе false
     * @throws SQLException ошибка на уровне СУБД или другая ошибка
     */
    @Override
    public boolean createPerson(Person person) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(INSERT_PERSON_SQL_TEMPLATE)) {
            statement.setString(1, person.getName());
            statement.setDate(2, new Date(person.getBirthDate()));
            return statement.execute();
        }

    }

    /**
     * Изменяет запись в таблице Person
     *
     * @param id        идентификатор записи
     * @param name      новое значение поля name
     * @param birthDate новое значение поля birthDate
     * @return true, если изменение произведено успешно, иначе false
     * @throws SQLException ошибка на уровне СУБД или другая ошибка
     */
    @Override
    public int updatePerson(int id, String name, long birthDate) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(UPDATE_PERSON_BY_ID_SQL_TEMPLATE)) {
            statement.setString(1, name);
            statement.setDate(2, new Date(birthDate));
            statement.setInt(3, id);
            return statement.executeUpdate();
        }
    }

    /**
     * Удаляет запись по идентификатору
     *
     * @param id идентификатор записи
     * @return true, если удаление произведено успешно, иначе false
     * @throws SQLException ошибка на уровне СУБД или другая ошибка
     */
    @Override
    public int deletePersonById(int id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(DELETE_PERSON_BY_ID_SQL_TEMPLATE)) {
            statement.setInt(1, id);
            return statement.executeUpdate();
        }
    }

    /**
     * Создает Collection<Person> по данным resultSet
     * @param resultSet заданный resultSet
     * @return Collection<Person>
     * @throws SQLException ошибка на уровне СУБД или другая ошибка
     */
    private Collection<Person> addToCollection(ResultSet resultSet) throws SQLException {
        List<Person> listPerson = new ArrayList<>();
        while (resultSet.next()) {
            Person person = new Person(resultSet.getInt("person_id"), resultSet.getString("name"),
                    resultSet.getTimestamp("birth_date").getTime());
            listPerson.add(person);
        }
        return listPerson;
    }


    /**
     * Создает объект Person по данным resultSet
     * @param resultSet заданный resultSet
     * @return объект Person, если resultSet непустой, иначе null
     * @throws SQLException ошибка на уровне СУБД или другая ошибка
     */
    private Optional<Person> addPerson(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return Optional.of(new Person(resultSet.getInt("person_id"), resultSet.getString("name"),
                    resultSet.getTimestamp("birth_date").getTime()));
        } else {
            return Optional.empty();
        }
    }


}
