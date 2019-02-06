package ru.innopolis.homework11;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import ru.innopolis.homework11.dao.PersonDAO;
import ru.innopolis.homework11.dao.PersonDAOImpl;
import ru.innopolis.homework11.entity.Person;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.*;


/**
 * Класс для работы с таблицей Person
 * через DAO
 * @author KhankhasaevRV
 * @since 2019.02.06
 */
class InvokerDAOPerson {

    private final PersonDAO DAO;
    private final Logger LOGGER = LoggerFactory.getLogger(InvokerDAOPerson.class);

    InvokerDAOPerson(Connection connection) {

        this.DAO = new PersonDAOImpl(connection);
    }

    /**
     * Метод демонстрирует основные операции с СУБД PostgreSQL с таблицей Subject
     * с ведением логов.
     * @throws SQLException ошибка на уровне СУБД или другая ошибка
     */
    public void invokePersonDemo() throws SQLException {

        List<Person> listPerson = Utils.fillPersonForTesting();
        LOGGER.trace("Начинаем добавлять записи в Person");
        for (Person person : listPerson) {
            insertPerson(person.getName(), person.getBirthDate());
            MDC.put("name", person.getName());
            MDC.put("birthday", new Date(person.getBirthDate()).toString());
            LOGGER.trace("Вставлена запись в таблицу Person - имя {} , дата рождения {}", MDC.get("name"),
                    MDC.get("birthday"));
        }
        LOGGER.trace("Добавление записей в таблицу Person завершено");

        LOGGER.trace("Демонстрация получения всех записей из таблицы Person");
        Collection<Person> listAllPerson = getAllPerson();
        System.out.println(listAllPerson.toString());
        LOGGER.trace("Получение всех записей из таблицы Person завершено");

        LOGGER.trace("Получение записи по id из таблицы Person");
        Optional<Person> personById = getPersonById(1);
        personById.ifPresent(name -> System.out.println(name.toString()));
        LOGGER.trace("Получение записи по id из таблицы Person завершено");

        LOGGER.trace("Обновление записей в таблице Person по id");
        int countUpdatePerson = updatePersonById(2,
                "Konor McGregor", 34472520000L);
        System.out.println("Количество обновленных записей - " + countUpdatePerson);
        LOGGER.trace("Обновление записей в таблице Person по id завершено");

        LOGGER.trace("Удаление записей в таблице Person по id");
        int countDeletePerson = deletePersonById(5);
        System.out.println("Количество удаленных записей - " + countDeletePerson);
        MDC.clear();
        LOGGER.trace("Демонстрация работы с таблицей Person завершена");
    }

    /**
     * Записывает строку в таблицу Person
     *
     * @param name      имя студента
     * @param dateBirth дата рождения
     * @return true, если вставка успешно произведена, иначе false
     * @throws SQLException ошибка на уровне СУБД или другая ошибка
     */
    private boolean insertPerson(String name, long dateBirth) throws SQLException {
        Person person = new Person(name, dateBirth);
        return DAO.createPerson(person);
    }

    /**
     * Получает объекты всех записей таблицы Person
     *
     * @return Collection<Person>
     * @throws SQLException ошибка на уровне СУБД или другая ошибка
     */
    private Collection<Person> getAllPerson() throws SQLException {
        return DAO.getAllPersons();
    }

    /**
     * Получает объет Person по id
     *
     * @param id идентификатор записи в таблице Person
     * @return Optional<Person> запись по данной id
     * @throws SQLException ошибка на уровне СУБД или другая ошибка
     */
    Optional<Person> getPersonById(int id) throws SQLException {
        return DAO.getPersonById(id);
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
    private int updatePersonById(int id, String name, long birthDate) throws SQLException {
        return DAO.updatePerson(id, name, birthDate);
    }

    /**
     * Удаляет запись по идентификатору
     *
     * @param id идентификатор записи
     * @return true, если удаление произведено успешно, иначе false
     * @throws SQLException ошибка на уровне СУБД или другая ошибка
     */
    private int deletePersonById(int id) throws SQLException {
        return DAO.deletePersonById(id);
    }
}










