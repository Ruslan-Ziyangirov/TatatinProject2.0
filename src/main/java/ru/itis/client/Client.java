package ru.itis.client;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.itis.Session;
import ru.itis.controller.EndController;
import ru.itis.controller.SecondaryController;
import ru.itis.controller.ThirdController;
import ru.itis.protocol.Message;
import ru.itis.protocol.MessageType;
import ru.itis.server.ChatServer;
import ru.itis.server.GameServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

public class Client extends Thread {


    private String token;
    private String nickname;
    private Socket client;
    // поток символов, которые мы отправляем клиенту
    private PrintWriter toClient;
    // поток символов, которые мы читаем от клиента
    private BufferedReader fromClient;

    //сервер, который хранит список других клиентов
    private ChatServer chatServer;

    private GameServer gameServer;

    private List<Client> gameClients;
    private List<Client> inGameClients;

    private Socket[] sessions;

    public Client(Socket client) {
        System.out.println("Initializing");
        this.chatServer = ChatServer.getInstance();
        this.gameServer = GameServer.getInstance();
        this.client = client;
//        this.gameClients = new ArrayList<>(gameServer.getClients());
//        this.inGameClients = new ArrayList<>(gameServer.getClients());

        try {
            // обернули потоки байтов в потоки символов
            this.toClient = new PrintWriter(client.getOutputStream(), true);
            this.fromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    // мы в любое время можем получить сообщение от клиента
    // поэтому чтение сообщения от клиента должно происходить в побочном потоке
    @Override
    public void run() {
        while (true) {
            String messageFromClient;
            try {
                // прочитали сообщение от клиента
                messageFromClient = fromClient.readLine();
                System.out.println(messageFromClient + "-----------------------------");

                if (messageFromClient != null) {
                    System.out.println("SERVER: получено сообщение от " + nickname + "<" + messageFromClient + ">");
                    Message message = new ObjectMapper().readValue(messageFromClient, Message.class);
                    System.out.println(message.toString());
                    //2
                    switch (message.getType()) {
                        case CONNECT: {
                            System.out.println("Новый игрОК!!!!");
                            nickname = message.getHeader("nickname");
                            setToken(UUID.randomUUID().toString());
                            gameServer.addInClient(this);
                            Message message1 = new Message();
                            message1.setType(MessageType.STARTSESSION);
//                            if (gameServer.getInGameclients().size()>1){
//                                for (Client client: gameServer.getInGameclients()){
//                                    client.sendMessage(message1);
//                                }
//
//                            }
                            break;
                        }

                        case CHAT: {
                            List<Client> clients = new ArrayList<>(chatServer.getClients());
                            message.setBody(this.nickname + ": " + message.getBody());
                            System.out.println(clients.toString());

                            clients.remove(this);
                            for (Client client: clients) {
                                client.sendMessage(message);
                            }
                            break;
                        }
                        case  WAITING: {
                            System.out.println();
                            System.out.println("Подключился новый игрок!");
                            System.out.println(gameServer.getClients().size());
                            gameClients = gameServer.getClients();
                            if (gameClients.size() >1 ) {
                                System.out.println("Игра готова!");
                                Message message1 = new Message();
                                message1.setType(MessageType.STARTSESSION);

                                for (Client client: gameClients) {
                                    client.sendMessage(message1);
                                }

                            }
                            break;
                        }


                        case KILLPUDGE: {
                            ArrayList<Client>  clientList = new ArrayList<>(gameServer.getClients());
                            System.out.println(clientList.size());
                            System.out.println("Убит пудж!!!!!");
                            Message message1 = new Message();
                            System.out.println("7777777");
                            message1.setType(MessageType.KILLPUDGE);
                            System.out.println("7777777");
//                            message1.setBody(message.getBody());
                            System.out.println("7777777");
                            for (Client client: clientList) {
//                                System.out.println(clientList.size() + "     7777777");
                                if (client!= this) {
                                    client.sendMessage(message1);
                                }
                            }
                            break;
                        }
                        case WINENDING: {
                            Message message1 = new Message();
                            message1.setType(MessageType.WINENDING);
                            Message message2 = new Message();
                            message2.setType(MessageType.ENDING);
                            while (true) {
                                Thread.sleep(40);
                                for (Client client: gameServer.getInGameclients()){
                                    if (client!= this) {
                                        sendMessage(message1);
                                        sendMessage(message2);
                                    }
                                }
                            }

                        }
                        case LOSEENDING: {
                            Message message1 = new Message();
                            message1.setType(MessageType.LOSEENDING);
                            Message message2 = new Message();
                            message2.setType(MessageType.ENDING);
                            while (true) {
                                Thread.sleep(40);
                                 for (Client client: gameServer.getInGameclients()){
                                     if (client!= this) {
                                         sendMessage(message1);
                                         sendMessage(message2);
                                     }
                                 }

                            }

                        }
                        case DRAWENDING: {
                            Message message1 = new Message();
                            message1.setType(MessageType.DRAWENDING);
                            Message message2 = new Message();
                            message2.setType(MessageType.ENDING);
                            while (true) {
                                Thread.sleep(40);
                                for (Client client: gameServer.getInGameclients()){
                                    if (client!= this) {
                                        sendMessage(message1);
                                        sendMessage(message2);
                                    }

                                }
                            }


                        }
                    }
                }
            } catch (IOException | InterruptedException e) {
//                System.out.println(e.getMessage());
                chatServer.removeClient(this);
                try {
                    fromClient.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                toClient.close();
            }
        }
    }

    public void sendMessage(Message message) {
        try {
            String jsonMessage = new ObjectMapper().writeValueAsString(message);
            System.out.println("SEND message: " + " " + jsonMessage);
            toClient.println(jsonMessage);
        } catch (JsonProcessingException e) {
            System.out.println(e);
        }
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}
