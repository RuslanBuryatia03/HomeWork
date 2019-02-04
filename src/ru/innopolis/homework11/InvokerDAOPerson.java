package ru.innopolis.homework11;

import ru.innopolis.homework11.dao.PersonDAO;
import ru.innopolis.homework11.dao.PersonDAOImpl;
import ru.innopolis.homework11.entity.Person;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;


/**
 * Класс для работы с таблицей Person
 * через DAO
 */
class InvokerDAOPerson {
    private final PersonDAO dao;

    InvokerDAOPerson(Connection connection) {
        this.dao = new PersonDAOImpl(connection);
    }

    /**
     * Записывает строку в таблицу Person
     *
     * @param name      имя студента
     * @param dateBirth дата рождения
     * @return true, если вставка успешно произведена, иначе false
     * @throws SQLException ошибка на уровне СУБД или другая ошибка
     */
    boolean insertPerson(String name, long dateBirth) throws SQLException {
        Person person = new Person(name, dateBirth);
        return dao.createPerson(person);
    }

    /**
     * Получает объекты всех записей таблицы Person
     *
     * @return Collection<Person>
     * @throws SQLException ошибка на уровне СУБД или другая ошибка
     */
    Collection<Person> getAllPerson() throws SQLException {
        return dao.getAllPersons();
    }

    /**
     * Получает объет Person по id
     *
     * @param id идентификатор записи в таблице Person
     * @return Optional<Person> запись по данной id
     * @throws SQLException ошибка на уровне СУБД или другая ошибка
     */
    Optional<Person> getPersonById(int id) throws SQLException {
        return dao.getPersonById(id);
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
    int updatePersonById(int id, String name, long birthDate) throws SQLException {
        return dao.updatePerson(id, name, birthDate);
    }

    /**
     * Удаляет запись по идентификатору
     *
     * @param id идентификатор записи
     * @return true, если удаление произведено успешно, иначе false
     * @throws SQLException ошибка на уровне СУБД или другая ошибка
     */
    int deletePersonById(int id) throws SQLException {
        return dao.deletePersonById(id);
    }
}










