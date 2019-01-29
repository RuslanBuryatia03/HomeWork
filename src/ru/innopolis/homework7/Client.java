package ru.innopolis.homework7;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Класс реализует клиента для взаимодействия с сервером через Socket.
 */

class Client {

    private final String HOST;
    private final int PORT;
    private String login;

    private Socket socket;

    Client(String host, int port) {
        this.PORT = port;
        this.HOST = host;
        this.login = "";
    }

    /**
     * Запускает потоки на чтение сообщений от сервера и отправку
     * сообщений, введенных с консоли
     */

    void talkWithServer()  {

        try {
            socket = new Socket(HOST, PORT);
            enterLogin();
            System.out.println("соединились: " + socket);
            ReadMsg readMsg = new ReadMsg(socket);
            readMsg.join();
            readMsg.start();

            WriteMsg writeMsg = new WriteMsg(socket);
            writeMsg.join();
            writeMsg.start();
        } catch (IOException e) {
            System.out.println("Ошибка IO");
            e.printStackTrace();
        } catch (InterruptedException e) {
            System.out.println("Процесс прерван");
            e.printStackTrace();
        }
    }

    /**
     * Заносит введенное с консоли имя клиента в поле login
     */
    private void enterLogin() {
        System.out.println("Введите свое имя: ");
        Scanner scanner = new Scanner(System.in);
        String line;
        while (true) {
            line = scanner.nextLine();
            if (line.isEmpty()) {
                System.out.println("Введите свое имя: ");
                continue;
            }
            break;
        }
        this.login = line;
        System.out.println("Ваше имя: " + line);
    }

    /**
     * Класс для получения сообщений от сервера, расширяет класс Thread
     */
    private class ReadMsg extends Thread {
        private final Socket socket;

        ReadMsg(Socket socket) {
            this.socket = socket;
        }

        /**
         * Метод запускается в отдельном потоке, получает сообщения
         * от сервера.
         */

        @Override
        public void run() {
            try {
                BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                while (true) {
                    String line = in.readLine();
                    System.out.println(line);
                    if (line.equalsIgnoreCase(login + ": Закрыть " + login)) {
                        System.out.println("Закрываю чтение");
                        socket.close();
                        break;
                    }
                }
            } catch (IOException e) {
                System.out.println("Ошибка IO");
                e.printStackTrace();
            }
        }
    }

    /**
     * Отправляет введенные с консоли сообщения на сервер, расширяет класс Thread
     */
    private class WriteMsg extends Thread {

        private final Socket socket;

        WriteMsg(Socket socket) {
            this.socket = socket;
        }

        /**
         * Метод считывает сообщения введенные с консоли и отправляет на сервер
         * от сервера.
         */
        @Override
        public void run() {
            try {
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                Scanner scanner = new Scanner(System.in);
                while (true) {
                    String line = scanner.nextLine();
                    out.write(login + ": " + line + "\n");
                    out.flush();
                    if (line.equalsIgnoreCase( "Закрыть " + login)) {
                        System.out.println("Закрываю отправку");
                        break;
                    }
                }
            } catch (IOException e) {
                System.out.println("Ошибка IO");
                e.printStackTrace();
            }
        }
    }
}
