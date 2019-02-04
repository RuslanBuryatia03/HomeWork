package ru.innopolis.homework11.dao;


import ru.innopolis.homework11.entity.Subject;

import java.sql.SQLException;
import java.util.Optional;


/**
 * Интерфейс DAO слоя таблицы Subject
 */
public interface SubjectDAO {

    Optional<Subject> getSubjectById(int id) throws SQLException;

    boolean createSubject(Subject subject) throws SQLException;


}
