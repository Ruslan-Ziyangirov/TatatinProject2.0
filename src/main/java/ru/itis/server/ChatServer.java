package ru.itis.server;

import ru.itis.client.Client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ChatServer {

    // поле, которое позволяет реализовать здесь серверное сокет-соединение
    private ServerSocket serverSocket;

    private static ChatServer chatServer;

    private List<Client> clients = new CopyOnWriteArrayList<>();
    //Паттерн синглтон, чтобы не создавать много экземпляров
    public static ChatServer getInstance() {
        if (chatServer == null) {
            chatServer = new ChatServer();
        }
        return chatServer;
    }

    private ChatServer(){
    }

    public void start() {
        try {
            serverSocket = new ServerSocket(8889);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    while (true) {

                        Client client = connect();
                    }
                }
            }).start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Client connect() {
        try {
            Socket client = serverSocket.accept();
            Client player = new Client(client);
            addClient(player);
            player.start();
            return player;
        } catch (IOException e) {
            //console log
            return null;
//            throw new IllegalStateException(e);
        }
    }

    public void addClient(Client client) {
        clients.add(client);
    }

    public void removeClient(Client client) {

        clients.remove(client);
    }

    public List<Client> getClients() {
        return clients;
    }
}