package ru.innopolis.homework7;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;

/**
 * Класс создает сервер для многопользоовательского чата
 */
public class Server {
    public static final int PORT = 3232;
    public static final String HOST = "localhost";
    public static final LinkedList<ListenerThread> SERVER_LIST = new LinkedList<>();
    private static ServerSocket serverSocket;

    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            System.out.println("Ошибка создания сервера");
            e.printStackTrace();
        }
        System.out.println("start waiting on port " + PORT);

        while (true) {
            Socket socket;
            try {
                socket = serverSocket.accept();
                ListenerThread listenerThread = new ListenerThread(socket);
                SERVER_LIST.add(listenerThread);
                listenerThread.join();
                listenerThread.start();
            } catch (IOException e) {
                try {
                    serverSocket.close();
                } catch (IOException e1) {
                    System.out.println("Ошибка закрыия сервера");
                    e1.printStackTrace();
                }
                System.out.println("Ошибка IO");
                e.printStackTrace();
            } catch (InterruptedException e) {
                System.out.println("Поток прерван");
                e.printStackTrace();
            }
        }
    }
}
