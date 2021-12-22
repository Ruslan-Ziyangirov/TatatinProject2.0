package ru.itis.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import ru.itis.App;
import ru.itis.server.GameServerStart;
import ru.itis.socket.GameClientSocket;
import ru.itis.socket.ThirdClientSocket;

import java.io.IOException;
import java.util.Random;

public class SecondaryController {
    @FXML
    public Label code;

    @FXML
    private void switchToPrimary(Stage stage) throws IOException {

    }

    public void goBack(MouseEvent mouseEvent) throws IOException {
        App.setRoot("/fxml/primary");
    }

    public void generatePort() throws IOException, InterruptedException {
        Random random=new Random();
        int range=999;
        int generator=8000+random.nextInt(range);
        String port = String.valueOf(generator);
        code.setText(port);

        ThirdClientSocket gameClientSocket = new ThirdClientSocket();

    }

    public void goPlay() throws IOException {
        App.setRoot("/fxml/primary");
    }
}
