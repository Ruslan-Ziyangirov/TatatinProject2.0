package ru.itis;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.ImageCursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;



public class App extends Application{
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader();
        System.out.println("here");
        URL xmlURL = getClass().getResource("/fxml/primary.fxml");
        fxmlLoader.setLocation(xmlURL);
        System.out.println("here");
        Parent root = fxmlLoader.load();

        scene = new Scene(root, 1440, 820);
        Font.loadFont(
                App.class.getResource("/fonts.HelveticaNeueCyr/helveticaneuecyr_black.otf").toExternalForm(),
                10
        );
        scene.getStylesheets().addAll(this.getClass().getResource("/style.css").toExternalForm());
//        scene.setCursor(Cursor.CROSSHAIR);
        //stage.setScene(scene);

        Image img = new Image(getClass().getResourceAsStream("/img/crosshair.png"));
        ImageCursor cursor = new ImageCursor(img, 30, 30);
        scene.setCursor(cursor);
        stage.setScene(scene);
        stage.show();
    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}
