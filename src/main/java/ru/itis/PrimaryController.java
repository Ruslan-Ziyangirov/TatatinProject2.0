package ru.itis;

import javafx.event.ActionEvent;

import java.io.IOException;

public class PrimaryController {

//    private void switchToSecondary(ActionEvent actionEvent) throws IOException {
//        App.setRoot("secondary");
//    }

    public void switchToThird(ActionEvent actionEvent) throws IOException {
        App.setRoot("/fxml/third");

    }

    public void switchToEndController(ActionEvent actionEvent) throws IOException {
        App.setRoot("/fxml/endScreen");
    }

    public void goToChat(ActionEvent actionEvent) throws IOException {
        App.setRoot("/fxml/chat");
    }

    public void switchToSecondary(ActionEvent actionEvent) throws IOException {
        App.setRoot("/fxml/secondary");
    }
}
