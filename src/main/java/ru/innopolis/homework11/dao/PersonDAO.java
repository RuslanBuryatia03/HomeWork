package ru.innopolis.homework11.dao;



import ru.innopolis.homework11.entity.Person;
import ru.innopolis.homework11.entity.Subject;

import java.sql.SQLException;
import java.util.Collection;
import java.util.Optional;

/**
 * Интерфейс DAO слоя таблицы Person
 */
public interface PersonDAO {
    

    Collection<Person> getAllPersons() throws SQLException;
    
    Collection<Person> getPersonsBySubject(Subject subject) throws SQLException;

    Optional<Person> getPersonById(int id) throws SQLException;

    boolean createPerson(Person person) throws SQLException;

    int updatePerson(int id, String name, long birthDate) throws SQLException;

    int deletePersonById(int id) throws SQLException;

}
