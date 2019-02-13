package ru.innopolis.homework11;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import ru.innopolis.homework11.dao.SubjectDAO;
import ru.innopolis.homework11.dao.SubjectDAOImpl;
import ru.innopolis.homework11.entity.Subject;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

/**
 * Класс для работы с таблицей Subject
 * через DAO
 *
 * @author KhankhasaevRV
 * @since 2019.02.06
 */

class InvokerDAOSubject {
    private final SubjectDAO dao;
    private final Logger LOGGER = LoggerFactory.getLogger(InvokerDAOSubject.class);

    InvokerDAOSubject(Connection connection) {
        this.dao = new SubjectDAOImpl(connection);
    }

    /**
     * Метод демонстрирует основные операции с СУБД PostgreSQL с таблицей Subject
     * с ведением логов.
     *
     * @throws SQLException ошибка на уровне СУБД или другая ошибка
     */
    public void invokeSubjectDemo() throws SQLException {

        List<Subject> listSubject = Utils.fillSubjectForTesting();
        LOGGER.trace("Начинаем добавлять записи в Subject");
        for (Subject subject : listSubject) {
            createSubject(subject.getDescription());
            MDC.put("description", subject.getDescription());
            LOGGER.trace("Вставлена запись в таблицу Subject - название курса {}", MDC.get("description"));
        }
        LOGGER.trace("Добавление записей в таблицу Subject завершено");


        LOGGER.trace("Получение записи по id из таблицы Subject");
        Optional<Subject> subjectById = getSubjectById(2);
        subjectById.ifPresent(name -> System.out.println(name.toString()));
        LOGGER.trace("Получение записи по id из таблицы Subject завершено");

        MDC.clear();
        LOGGER.trace("Демонстрация работы с таблицей Subject завершена");
    }


    /**
     * Записывает строку в таблицу Subject
     *
     * @param description описание курса
     * @return true, если запись успешно вставлена, иначе false
     * @throws SQLException ошибка на уровне СУБД или другая ошибка
     */
    private boolean createSubject(String description) throws SQLException {
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










