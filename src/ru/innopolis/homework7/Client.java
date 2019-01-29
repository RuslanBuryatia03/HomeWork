package ru.innopolis.homework7;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

/**
 * Класс реализует клиента для общения с сервером через Socket.
 */

public class Client {

    private final String HOST;
    private final int PORT;
    private String login;

    private Socket socket;

    Client(String host, int port) {
        this.PORT = port;
        this.HOST = host;
//        this.login = "";
    }


    public void talkWithServer() throws InterruptedException {

        try {
            socket = new Socket(HOST, PORT);
            enterLogin();
//            System.out.println("соединились" + socket);
            ReadMsg readMsg = new ReadMsg(socket);
            readMsg.join();
            readMsg.start();

            WriteMsg writeMsg = new WriteMsg(socket);
            writeMsg.join();
            writeMsg.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println("client done");
    }

    /**
     *
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

    private class ReadMsg extends Thread {

        private final Socket socket;

        ReadMsg(Socket socket) {
            this.socket = socket;
        }

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
//                        Server.serverList.remove();
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private class WriteMsg extends Thread {

        private final Socket socket;
//        private login

        WriteMsg(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
                Scanner scanner = new Scanner(System.in);
                while (true) {
                    String line = scanner.nextLine();
//                    System.out.println("отправка " + line);
                    out.write(login + ": " + line + "\n");
                    out.flush();
                    if (line.equalsIgnoreCase( "Закрыть " + login)) {
                        System.out.println("Закрываю отправку");
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
