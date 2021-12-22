package ru.itis.controller;

import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import ru.itis.App;
import ru.itis.socket.GameClientSocket;
import ru.itis.socket.GameWinnerSocket;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;



public class EndController implements Initializable {
    private static EndController instance;
    private Label winner;
    private GameWinnerSocket clentSocket;


    public void lose() {
        this.winner.setText("Противник");

    }
    public void draw() {
        this.winner.setText("Ничья");

    }
    public void win() {
        this.winner.setText("Вы!");
    }

    public void goMainScene(MouseEvent mouseEvent) throws IOException {
        App.setRoot("/fxml/primary");
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        for (int i = 0; i <100 ; i++) {

        }
        clentSocket = new GameWinnerSocket();
        clentSocket.connect(this,"Марат", 8888);
        clentSocket.start();
    }
}
