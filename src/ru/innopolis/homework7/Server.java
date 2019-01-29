package ru.innopolis.homework7;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.LinkedList;
import java.util.Scanner;


public class Server { //extends Thread {
    public static final int PORT = 3232;
    public static final String HOST = "localhost";
    public static LinkedList<ListenerThread> serverList = new LinkedList<>();
    private static ServerSocket serverSocket;


    public static void main(String[] args) {
        try {
            serverSocket = new ServerSocket(PORT);
        } catch (IOException e) {
            System.out.println("Ошибка создания сервера");
            e.printStackTrace();
            return;
        }
        System.out.println("start waiting on port " + PORT);
        Scanner scanner = new Scanner(System.in);

//        Server server = new Server();
//        try {
//            server.join();
//        } catch (InterruptedException e) {
//            System.out.println("Поток прерван");
//            e.printStackTrace();
//        }
//        server.start();
//        scanner.nextLine().

        while (true) {
            Socket socket = null;
            try {
                socket = serverSocket.accept();
                ListenerThread listenerThread = new ListenerThread(socket);
                serverList.add(listenerThread);
                listenerThread.join();
                listenerThread.start();

            } catch (IOException e) {
                try {
                    serverSocket.close();
                } catch (IOException e1) {
                    System.out.println("Ошибка закрыия сервера");
                    e1.printStackTrace();
                }
                e.printStackTrace();
            } catch (InterruptedException e) {
                System.out.println("Поток прерван");
                e.printStackTrace();
            }
        }


//        System.out.println("stopping server...");
////        serverSocket.close();
////  todo      listener.interrupt();
////        listener.join();
//        System.out.println("server done");
    }
//
//    @Override
//    public void run() {
//        while (true) {
//            try {
//                Thread.sleep(60000);
//
//                int i = 0;
//                for (ListenerThread listenerThread : serverList) {
//                    if (listenerThread.isAlive() && !listenerThread.isInterrupted()) {
//                        i++;
//                    }
//                }
//                if (i == 0) {
//                    for (ListenerThread listenerThread : serverList) {
//                        listenerThread.interrupt();
//                    }
//                    serverSocket.close();
//                    break;
//                }
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
//    }
}
