package ru.itis.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import ru.itis.App;
import ru.itis.server.GameServer;
import ru.itis.socket.GameClientSocket;
import ru.itis.socket.ThirdClientSocket;

import java.io.IOException;
import java.net.Socket;

public class ThirdController {
    @FXML
    public TextField codeTextField;
    private GameClientSocket clientSocket;

    public void goBack(MouseEvent mouseEvent) throws IOException {
        App.setRoot("/fxml/primary");
    }

    public void goPlay(ActionEvent actionEvent) throws IOException {
        Integer port = Integer.parseInt(codeTextField.getText());
        ThirdClientSocket gameClientSocket = new ThirdClientSocket();
        System.out.println("socket3");
        gameClientSocket.connect(this, "Alfred",port);
        System.out.println("socket35");
        gameClientSocket.start();
        System.out.println("socket36");
    }
    public void moveToPlay() throws IOException {
        App.setRoot("/fxml/gameField");
    }




}
