package ru.innopolis.homework7;

public class ClientMain {

    public static void main(String[] args) {
        Client client = new Client(Server.HOST, Server.PORT);
        try {
            client.talkWithServer();
        } catch (InterruptedException e) {
            System.out.println("Процесс прерван");
            e.printStackTrace();
        }
    }
}
