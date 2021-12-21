package ru.itis;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

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

    public void generatePort(){
        Random random=new Random();
        int rage=9999;
        int generator=1000+random.nextInt(rage-1000);
        String port = String.valueOf(generator);
        code.setText(port);
        System.out.println(port);

    }

//    public void onDragDetected(MouseEvent mouseEvent) {
//           codeLabel.setFill(Color.WHITE);
//
//    }
}
