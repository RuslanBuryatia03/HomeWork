package ru.innopolis.homework7;

import java.io.*;
import java.net.Socket;


/**
 * Класс предназначен для взаимодействия с клиентом чата.
 * Принимает соединия, осуществляет рассылку всем клиентам.
 */
public class ListenerThread extends Thread {
    private final Socket socket;
    private BufferedReader in;
    private BufferedWriter out;

    ListenerThread(Socket socket) {
        this.socket = socket;
    }

    /**
     * Принимает сообщения от клиентов и рассылает его все участникам чата.
     */
    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
                System.out.println("Подключился " + socket);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new BufferedWriter((new OutputStreamWriter(socket.getOutputStream())));
                while (true) {
                    String messageClient = in.readLine();
                    if  (messageClient == null) {
                        this.interrupt();
                        break;
                    }
                    System.out.println("прочел от клиента" + messageClient);
                    sendOther(messageClient +  System.lineSeparator());
                }
            } catch (IOException e) {
                System.out.println("Ошибка IO");
                e.printStackTrace();
            }
        }
    }

    /**
     * Осуществляет рассылку сообщения всем участникам чата
     * @param msg сообщения для рассылки
     * @throws IOException ошибка IO
     */
    private synchronized void sendOther(String msg)  throws IOException {
        for (ListenerThread o : Server.SERVER_LIST) {
            if  (o.isAlive()) {
                o.out.write(msg );
                o.out.flush();
            }
        }
    }
}

