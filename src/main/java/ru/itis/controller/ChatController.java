package ru.itis.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import ru.itis.*;
import ru.itis.protocol.Message;
import ru.itis.protocol.MessageType;
import ru.itis.socket.ClientSocket;
import ru.itis.socket.GameClientSocket;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ChatController implements Initializable {


    @FXML
    public TextField name;
    @FXML
    private VBox messages;
    @FXML
    private TextField messageText;
    @FXML
    private ScrollPane messageView;
    @FXML
    private Button enterChat;
    @FXML
    private Button sendMessage;
    @FXML
    private StackPane chatField;



    private ClientSocket clientSocket;

    public void initialize(URL location, ResourceBundle resources){
        Platform.runLater( () -> chatField.requestFocus() );
        enterChat.setOnMouseClicked(event -> {
            String nickname = name.getText();
            Label messageLabel = new Label();
            messageLabel.setText(nickname +" Присоединился!");
            messages.getChildren().add(messageLabel);
            name.setEditable(false);
//            helloLabel.setText("Привет, " + nickname + "!");
            clientSocket = new ClientSocket();
            clientSocket.connect(this, nickname);
            clientSocket.start();
        });
        sendMessage.setOnMouseClicked(event -> {
            sendMessage();
        });

    }

    private Message createActionMessage(Action action) {
        Message message = new Message();
        message.setType(MessageType.ACTION);
        message.setBody(action.getTitle());
        return message;
    }

    private Message createChatMessage(String text) {
        Message message = new Message();
        message.setType(MessageType.CHAT);
        message.setBody(text);
        return message;
    }

    private void sendMessage() {
        System.out.println(messageText.getText());
        Label messageLabel = new Label();
        messageLabel.setText(name.getText() +":"+ messageText.getText());
        messages.getChildren().add(messageLabel);
        clientSocket.sendMessage(createChatMessage(messageText.getText()));
        messageText.clear();
    }

    public VBox getMessages() {
        return messages;
    }


    public void goBack(javafx.scene.input.MouseEvent mouseEvent) throws IOException {
        App.setRoot("/fxml/primary");
    }
}
