package ru.itis.socket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import ru.itis.Pudge;
import ru.itis.controller.SecondaryController;
import ru.itis.controller.GameField;
import ru.itis.protocol.Message;
import ru.itis.protocol.MessageType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class GameClientSocket extends Thread{

    private Socket clientSocket;

    private SecondaryController secondaryController;
    private GameField gameField;
    private final String HOST = "localhost";
     int PORT = 8888;
    private String nickname;
    private String token;
    private PrintWriter out;
    private BufferedReader fromServer;


    public GameClientSocket() throws IOException {
    }

    public void connect(SecondaryController secondaryController, String nickname, int port) {
        try {

            PORT = port;
            clientSocket = new Socket(HOST, port);
            System.out.println("------------------------------------------------");
            this.secondaryController = secondaryController;
            System.out.println("Check controller - " + secondaryController);
            System.out.println("Создан сокет!!!");
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            fromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            System.out.println("Не удалось подсоединиться");
//            throw new RuntimeException();
        }
        this.nickname = nickname;
        Message message = new Message();
        message.setType(MessageType.WAITING);
        message.addHeader("nickname", "Ivan");
        sendMessage(message);
    }
    public void connectInGame(GameField gameField,String nickname, int port) {
        try {
            this.gameField = gameField;
            PORT = port;
            clientSocket = new Socket(HOST, port);
            System.out.println("Создан сокет Game!!!");
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            fromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            System.out.println("Не удалось подсоединиться");
//            throw new RuntimeException();
        }
        this.nickname = nickname;
        Message message = new Message();
        message.setType(MessageType.CONNECT);
        message.addHeader("nickname", "Ivan");
        sendMessage(message);
    }




    public void sendMessage(Message message) {
        try {
            System.out.println("Sending message!");
            String jsonMessage = new ObjectMapper().writeValueAsString(message);
            System.out.println(jsonMessage);
            out.println(jsonMessage);
        } catch (JsonProcessingException e) {

        }
    }

//
//    public void checkSession(Message message) {
//
//    }

    //4
    @Override
    public void run() {
        while (true) {
            String messageFromServer;
            Message message = null;
            try {
                messageFromServer = fromServer.readLine();
                message = new ObjectMapper().readValue(messageFromServer, Message.class);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }

            if (message.getType() != null) {
                switch (message.getType()) {
                    case CONNECT:
                        this.token = message.getHeader("token");
                        break;
                    case CHAT: {
                        Label label = new Label();
                        label.setText(message.getBody());
                        label.setFont(Font.font("Arial"));
//                        Platform.runLater(() -> сhatController.getMessages().getChildren().add(label));
                        break;
                    }
                    case KILLPUDGE: {
                        Message finalMessage = message;
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                gameField.makeKillPudge(finalMessage);
                            }
                        });
                        break;
                    }
                    case ENDING: {
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    gameField.moveFuther();
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        break;
                    }
                    default: {
                        break;
                    }
                }
            }
        }
    }
}