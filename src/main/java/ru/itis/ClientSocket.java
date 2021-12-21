package ru.itis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSocket implements Runnable{

    private Socket clientSocket;
    private ChatController сhatController;
    private final String HOST = "localhost";

    private PrintWriter out;
    private BufferedReader fromServer;

    private String token;
    private String nickname;

    public void connect(ChatController сhatController, String nickname) {
        this.сhatController = сhatController;
        try {
            int PORT = 8888;
            clientSocket = new Socket(HOST, PORT);
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            fromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        } catch (IOException e) {
            System.out.println("Не удалось подсоединиться");
//            throw new RuntimeException();
        }
        this.nickname = nickname;
        Message message = new Message();
        message.setType(MessageType.CONNECT);
        message.addHeader("nickname", nickname);
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
//            try {
//                messageFromServer = fromServer.readLine();
//                System.out.println(messageFromServer);
//                message = new ObjectMapper().readValue(messageFromServer, Message.class);
//                System.out.println(message);
//            } catch (IOException e) {
//                System.out.println(e.getMessage());
//            }

            if (message != null) {
                switch (message.getType()) {
                    case CONNECT:
                        this.token = message.getHeader("token");
                        break;
                    case CHAT: {
                        Label label = new Label();
                        label.setText(message.getBody());
                        label.setFont(Font.font("Arial"));
                        Platform.runLater(() -> сhatController.getMessages().getChildren().add(label));
                        break;
                    }
                    default: {
                        System.out.println("Неизвестно!!!");
                    }
                }
            }
        }
    }

}
