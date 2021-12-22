package ru.itis.server;


import ru.itis.client.Client;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class GameServer {

    // поле, которое позволяет реализовать здесь серверное сокет-соединение
    private ServerSocket serverSocket;

    private static GameServer gameServer;

    private List<Client> clients = new CopyOnWriteArrayList<>();
    private List<Client> inGameclients = new CopyOnWriteArrayList<>();

    //Паттерн синглтон, чтобы не создавать много экземпляров
    public static GameServer getInstance() {
        if (gameServer == null) {
            gameServer = new GameServer();
        }
        return gameServer;
    }

    private GameServer(){
    }

    public void start(int port) {
        try {
            serverSocket = new ServerSocket(port);
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
            System.out.println("I am initing!!!!");
            Socket client = serverSocket.accept();
            Client player = new Client(client);
            addClient(player);
            System.out.println(clients);
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
    public void addInClient(Client client) {
        inGameclients.add(client);
    }
    public void deleteAll() {
        clients.clear();
    }
    public void deleteAllInGame() {
        clients.clear();
    }

    public void removeClient(Client client) {

        clients.remove(client);
    }

    public List<Client> getClients() {
        return clients;
    }
    public List<Client> getInGameclients() {
        return inGameclients;
    }

}
