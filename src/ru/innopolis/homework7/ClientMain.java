package ru.innopolis.homework7;

/**
 * Создает клиента для многопользовательского чата и вызывает метод
 * для взаимодействия с сервером чата. Для завершения работы
 * введите "закрыть имя_пользователя".
 */

public class ClientMain {

    public static void main(String[] args) {
        new Client(Server.HOST, Server.PORT).talkWithServer();
    }
}

