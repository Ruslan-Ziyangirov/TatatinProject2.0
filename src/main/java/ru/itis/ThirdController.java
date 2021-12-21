package ru.itis;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class ThirdController {

    public void goBack(MouseEvent mouseEvent) throws IOException {
        App.setRoot("/fxml/primary");
    }

    public void goPlay(ActionEvent actionEvent) throws IOException {
        App.setRoot("/fxml/gameField");
    }

}
