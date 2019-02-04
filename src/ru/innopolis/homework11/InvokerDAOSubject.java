package ru.innopolis.homework11;

import ru.innopolis.homework11.dao.SubjectDAO;
import ru.innopolis.homework11.dao.SubjectDAOImpl;
import ru.innopolis.homework11.entity.Subject;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

/**
 * Класс для работы с таблицей Subject
 * через DAO
 */

class InvokerDAOSubject {
    private final SubjectDAO dao;


    InvokerDAOSubject(Connection connection) {
        this.dao = new SubjectDAOImpl(connection);
    }

    /**
     * Записывает строку в таблицу Subject
     *
     * @param description описание курса
     * @return true, если запись успешно вставлена, иначе false
     * @throws SQLException ошибка на уровне СУБД или другая ошибка
     */
    boolean createSubject(String description) throws SQLException {
        Subject subject = new Subject(description);
        return dao.createSubject(subject);
    }


    /**
     * Получает объет Subject по id
     *
     * @param id идентификатор записи в таблице Subject
     * @return Optional<Subject> запись по данной id
     * @throws SQLException ошибка на уровне СУБД или другая ошибка
     */
    Optional<Subject> getSubjectById(int id) throws SQLException {
        return dao.getSubjectById(id);
    }
}










