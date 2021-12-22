package ru.itis.socket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import ru.itis.controller.ChatController;
import ru.itis.controller.SecondaryController;
import ru.itis.controller.ThirdController;
import ru.itis.protocol.Message;
import ru.itis.protocol.MessageType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ThirdClientSocket extends Thread{

    private Socket clientSocket;

    private ThirdController thirdController;
    private SecondaryController secondaryController = new SecondaryController();
    private final String HOST = "localhost";

    private PrintWriter out;
    private BufferedReader fromServer;

    private String token;
    private String nickname;

    public ThirdClientSocket() throws IOException {
    }

    public void connect(ThirdController thirdController, String nickname, int port) {
        this.thirdController = thirdController;
        try {
            clientSocket = new Socket(HOST, port);
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
        message.addHeader("nickname", "Alfred");
        sendMessage(message);
    }


    public void sendMessage(Message message) {
        try {
            String jsonMessage = new ObjectMapper().writeValueAsString(message);
            System.out.println(jsonMessage);
            out.println(jsonMessage);
        } catch (JsonProcessingException e) {

        }
    }

    //4
    @Override
    public void run() {
        while (true) {
            String messageFromServer;
            Message message = null;
            try {
                messageFromServer = fromServer.readLine();
                message = new ObjectMapper().readValue(messageFromServer, Message.class);
                System.out.println(message + "  ------");
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
                    case STARTSESSION: {

                        try {
                            this.thirdController.moveToPlay();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
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