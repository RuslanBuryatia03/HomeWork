package ru.innopolis.homework11;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MainHomework11 {

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres";
    private static final String LOGIN = "postgres";
    private static final String PASSWORD = "123";
    private static final Logger APPLICATION = LoggerFactory.getLogger("APPLICATION");


    public static void main(String[] args) {
        final Logger LOGGER = LoggerFactory.getLogger(MainHomework11.class);
        APPLICATION.warn("Приложение запущено");
        try (Connection connection = DriverManager.getConnection(URL, LOGIN, PASSWORD)) {
            LOGGER.info("Установлен связь с БД");


            InvokerDAOPerson invokerDAOPerson = new InvokerDAOPerson(connection);
            invokerDAOPerson.invokePersonDemo();

            InvokerDAOSubject invokerDAOSubject = new InvokerDAOSubject(connection);
            invokerDAOSubject.invokeSubjectDemo();

            InvokerDAOCourse invokerDAOCourse = new InvokerDAOCourse(connection);
            invokerDAOCourse.invokeCourseDemo();

        } catch (SQLException e) {
            LOGGER.trace("Ошибка ", e);
            System.out.println("Что то пошло не так");
            e.printStackTrace();
        }
        APPLICATION.warn("Приложение остановлено");
    }
}
