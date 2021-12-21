package ru.itis;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class GameField implements Runnable, Initializable {
    @FXML
    public ImageView pudge1;
    @FXML
    public ImageView pudge2;
    @FXML
    public ImageView pudge3;
    @FXML
    public ImageView pudge4;
    @FXML
    public ImageView pudge5;
    @FXML
    public ImageView pudge6;
    @FXML
    public ImageView pudge7;
    @FXML
    public ImageView pudge8;
    @FXML
    public ImageView pudge9;
    @FXML
    public ImageView pudge10;
    @FXML
    public Label timer_str;
    @FXML
    public Label player1;

    @FXML
    public Pane myPane;

    public int width;
//    public Integer width = (int)player1.getScene().getWidth();

    private boolean isGameFinished = false;
    private  int timer = 1;

    public void initialize(URL location, ResourceBundle resources) {
        getCurrentWidth();
        width = 800;
        Thread thread = new Thread(this);
        thread.start();
        timer_str.setText("О");
        setTimer();
        movee();
//        System.out.println("fnkelnkfelwnlkw" + myPane.getWidth());

//        getPudgesReady();
//        Thread thread = new Thread(this);
//        thread.start();
//        thread.start();
//        getPudgesReady();


    }

    private void  setTimer() {
        width = (int)myPane.getWidth();
        AtomicInteger time = new AtomicInteger(60);
        Timeline timeline = new Timeline (
                new KeyFrame(
                        Duration.millis(1000), //1000 мс * 60 сек = 1 мин
                        ae -> {
                            this.width = (int)myPane.getWidth();
//                            System.out.println(width);
                            timer = time.get();
                            timer_str.setText("Осталось времени: "+ time);
                            time.getAndDecrement();
//                            System.out.println(getCurrentWidth()+ "kelwmkmewldmwmkdmewlkdwkemdwmledekmdwkmldwklemd");
                            if (time.get()==1) {
                                isGameFinished = true;
                                moveFuther();
                            }
                        }
                )
        );
        timeline.setCycleCount(60); //Ограничим число повторений
        timeline.play();
    }

    private void moveFuther() {
        try {
            App.setRoot("/fxml/endScreen");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(myPane.getWidth());
            width = (int) myPane.getWidth();
        }
    }
    private void getPudgesReady() {
        Pudge pudge11 = new Pudge(pudge1,true,1);
        Pudge pudge22 = new Pudge(pudge2,true,2);
        Pudge pudge33 = new Pudge(pudge3,true,3);
        Pudge pudge44 = new Pudge(pudge4,true,4);
        Pudge pudge55 = new Pudge(pudge5,true,5);
        pudge11.configurePudge();
        pudge22.configurePudge();
        pudge33.configurePudge();
        pudge44.configurePudge();
        pudge55.configurePudge();
        pudge11.thread.start();
        pudge22.thread.start();
        pudge33.thread.start();
        pudge44.thread.start();
        pudge55.thread.start();
//        Pudge pudge66 = new Pudge(pudge6,true,6);
//        Pudge pudge77 = new Pudge(pudge7,true,7);
//        Pudge pudge88 = new Pudge(pudge8,true,8);
//        Pudge pudge99 = new Pudge(pudge9,true,9);

        Timeline timeline = new Timeline (
                new KeyFrame (
                        Duration.millis(10), //1000 мс * 60 сек = 1 мин
                        ae -> {
                            System.out.println("heheh");
                            pudge11.movePudge();
                            pudge22.movePudge();
                            pudge33.movePudge();
                            pudge44.movePudge();
                            pudge55.movePudge();
                        }
                )
        );
        timeline.setCycleCount(100000);
        timeline.play();
    }
    public  void movee() {
        System.out.println("here!");
        Pudge pudge11 = new Pudge(pudge1,true,1);
        Pudge pudge22 = new Pudge(pudge2,true,2);
        Pudge pudge33 = new Pudge(pudge3,true,3);
        Pudge pudge44 = new Pudge(pudge4,true,4);
        Pudge pudge55 = new Pudge(pudge5,true,5);
        pudge11.thread.start();
        pudge22.thread.start();
        pudge33.thread.start();
        pudge44.thread.start();
        pudge55.thread.start();
    }

    public int getCurrentWidth() {
//        System.out.println(myPane.getWidth() + "fnjkwefkjewsjfewnjifwn");
        width = (int) myPane.getWidth();
        return width;
    }
}
