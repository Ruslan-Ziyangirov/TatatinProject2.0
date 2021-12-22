package ru.itis.socket;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.text.Font;
import ru.itis.controller.EndController;
import ru.itis.controller.GameField;
import ru.itis.controller.SecondaryController;
import ru.itis.protocol.Message;
import ru.itis.protocol.MessageType;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class GameWinnerSocket extends Thread {


        private Socket clientSocket;

        private EndController endController;
        private GameField gameField;
        private final String HOST = "localhost";
        int PORT = 8888;
        private String nickname;
        private String token;
        private PrintWriter out;
        private BufferedReader fromServer;


        public void connect(EndController endController, String nickname, int port) {
            try {

                PORT = port;
                clientSocket = new Socket(HOST, port);
                System.out.println("------------------------------------------------");
                this.endController = endController;
                System.out.println("Check controller - " + endController);
                System.out.println("Создан сокет!!!");
                out = new PrintWriter(clientSocket.getOutputStream(), true);
                fromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            } catch (IOException e) {
                System.out.println("Не удалось подсоединиться");
            }
            this.nickname = nickname;
            Message message = new Message();
            message.setType(MessageType.CONNECT);
            message.addHeader("nickname", "Ivan");
            sendMessage(message);
        }
        public void sendMessage(Message message) {
            try {
                System.out.println("Кто победил?");
                String jsonMessage = new ObjectMapper().writeValueAsString(message);
                System.out.println(jsonMessage);
                out.println(jsonMessage);
            } catch (JsonProcessingException e) {

            }
        }
        @Override
        public void run() {
            while (true) {
                System.out.println("Here!22222");
                String messageFromServer;
                Message message = null;
                try {
                    messageFromServer = fromServer.readLine();
                    message = new ObjectMapper().readValue(messageFromServer, Message.class);
                    System.out.println(message + " ------");
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }

                if (message.getType() != null) {
                    switch (message.getType()) {
                        case CHAT: {
                            Label label = new Label();
                            label.setText(message.getBody());
                            label.setFont(Font.font("Arial"));
                            break;
                        }
                        case WINENDING: {
                           endController.win();
                            break;
                        }
                        case LOSEENDING: {
                            endController.lose();
                            break;
                        }
                        case DRAWENDING: {
                            endController.draw();
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
