package ru.innopolis.homework7;

import java.io.*;
import java.net.Socket;

public class ListenerThread extends Thread {
    private final Socket socket;
//    private final Map<Socket, String> socketUserMap;

    private BufferedReader in; // поток чтения из сокета
    private BufferedWriter out; // поток записи в сокет

    public ListenerThread(Socket socket) {
        this.socket = socket;
//        this.socketUserMap = new ConcurrentHashMap<>();
    }

    @Override
    public void run() {
        while (!isInterrupted()) {
            try {
//                Socket socket = serverSocket.accept();
//                socketUserMap.put(socket, "");
                // todo запустить поток-читатель
                System.out.println("Подключился " + socket);

                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new BufferedWriter((new OutputStreamWriter(socket.getOutputStream())));
                // тут по условию задачи сканер не нужен
//                Scanner scanner = new Scanner(System.in);
                while (true) {
                    String messageClient = in.readLine();
                    if  (messageClient == null) { //(messageClient.equalsIgnoreCase()) {
                        this.interrupt();
                        break;
                    }
                    System.out.println("прочел от клиента" + messageClient);
//                    sendOther(messageClient + "\n" ) ;  //+System.lineSeparator());

                    for (ListenerThread o : Server.serverList) {
                        if  (o.isAlive()) {  //(o.in.readLine() != null)  { //
                            o.out.write(messageClient + "\n");
                            o.out.flush();
//                        o.sendOther(messageClient + "\n");
                        }
                    }

                }

            } catch (IOException e) {

                e.printStackTrace();
            }
        }
    }

    private void sendOther(String msg) throws IOException {
//        Server.serverList.toString();
//        for (ListenerThread o : Server.serverList) {
//            System.out.println("рассылка " + msg  + o.getName());
        out.write(msg);
        out.flush();
    }
}

