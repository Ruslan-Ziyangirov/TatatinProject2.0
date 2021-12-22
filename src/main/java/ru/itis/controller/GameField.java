package ru.itis.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import ru.itis.App;
import ru.itis.Pudge;
import ru.itis.protocol.Message;
import ru.itis.protocol.MessageType;
import ru.itis.socket.GameClientSocket;
import ru.itis.socket.ThirdClientSocket;

import java.io.IOException;
import java.net.Socket;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class GameField implements Runnable, Initializable {




    public GameClientSocket clentSocket;

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

Pudge pudge11;
Pudge pudge22;
Pudge pudge33;
Pudge pudge44;
Pudge pudge55;

    @FXML
    public Label timer_str;
    @FXML
    public Label player2;
    @FXML
    public Label player1;

    @FXML
    public Pane myPane;

    @FXML
    public Label p1Points;

    public int win;

    @FXML
    public Label p2Points;

    private boolean isGameFinished = false;
    private  int timer = 1;



    public void initialize(URL location, ResourceBundle resources) {
        player1.setText("Вы");
        player2.setText("Противник");
        timer_str.setText("О");
        setTimer();

        try {
            clentSocket = new GameClientSocket();
        } catch (IOException e) {
            e.printStackTrace();
        }
        clentSocket.connectInGame(this,player1.getText(),8888);
        clentSocket.start();

        movee();

    }

    private void  setTimer() {
        System.out.println("Nfqvth");
        AtomicInteger time = new AtomicInteger(60);

        Timeline timeline = new Timeline (

                new KeyFrame(
                        Duration.millis(1000), //1000 мс * 60 сек = 1 мин
                        ae -> {

//                            System.out.println(width);
                            timer = time.get();
                            timer_str.setText("Осталось времени: "+ time);
                            time.getAndDecrement();
                            if (time.get()==1) {
                                isGameFinished = true;
                                sendMessage();
                                try {
                                    App.setRoot("/fxml/endScreen");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                )
        );
        timeline.setCycleCount(60); //Ограничим число повторений
        timeline.play();
    }

    public void moveFuther() throws IOException {
          EndController endController = new EndController();
            App.setRoot("/fxml/endScreen");

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
        }
    }

    public  void movee() {
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                pudge11 =new Pudge(pudge1,true,1);
         pudge22 = new Pudge(pudge2,true,2);
         pudge33 = new Pudge(pudge3,true,3);
         pudge44 = new Pudge(pudge4,true,4);
         pudge55 = new Pudge(pudge5,true,5);
        pudge11.thread.start();
        pudge22.thread.start();
        pudge33.thread.start();
        pudge44.thread.start();
        pudge55.thread.start();
            }
        });
    }


    public void KillPudge1(MouseEvent mouseEvent) {
        increaseYour();
        Message message = new Message();
        message.setType(MessageType.KILLPUDGE);
        message.setBody("1");
        clentSocket.sendMessage(message);
    }

    public void KillPudge2(MouseEvent mouseEvent) {
        increaseYour();
        Message message = new Message();
        message.setType(MessageType.KILLPUDGE);
        message.setBody("2");
        clentSocket.sendMessage(message);

    }

    public void KillPudge3(MouseEvent mouseEvent) {
        increaseYour();
        Message message = new Message();
        message.setType(MessageType.KILLPUDGE);
        message.setBody("3");
        clentSocket.sendMessage(message);

    }

    public void KillPudge4(MouseEvent mouseEvent) {
        increaseYour();
        Message message = new Message();
        message.setType(MessageType.KILLPUDGE);
        message.setBody("4");
        clentSocket.sendMessage(message);

    }

    public void KillPudge5(MouseEvent mouseEvent) {
        increaseYour();
        Message message = new Message();
        message.setType(MessageType.KILLPUDGE);
        message.setBody("5");
        clentSocket.sendMessage(message);

    }


    public void makeKillPudge(Message message) {
        increaseEnemy();
        int number = Integer.parseInt(message.getBody());
        switch (number) {
            case 1: {
                pudge11.setAlive(false);
                pudge1.setX(0);
                break;
            }
            case 2: {
                pudge22.setAlive(false);
                pudge2.setX(0);

                break;
            }
            case 3: {
                pudge33.setAlive(false);
                pudge3.setX(0);

                break;
            }
            case 4: {
                pudge44.setAlive(false);
                pudge4.setX(0);

                break;
            }
            case 5: {
                pudge55.setAlive(false);
                pudge5.setX(0);

                break;
            }
        }
    }

    public void increaseYour() {
        int score = Integer.parseInt(p1Points.getText());
        score++;
        p1Points.setText(String.valueOf(score));
    }
    public void increaseEnemy() {
        int score = Integer.parseInt(p2Points.getText());
        score++;
        p2Points.setText(String.valueOf(score));
    }
    public void sendMessage() {
        Message message = new Message();
        int score = Integer.parseInt(p1Points.getText());
        int score2 = Integer.parseInt(p2Points.getText());
        if (score>score2) {
            win = 1;
            message.setType(MessageType.WINENDING);
            clentSocket.sendMessage(message);
        } else if (score == score2) {
            win = 2;
            message.setType(MessageType.DRAWENDING);
            clentSocket.sendMessage(message);

        } else if (score<score2) {
            win = 3;
            message.setType(MessageType.LOSEENDING);
            clentSocket.sendMessage(message);
        }
    }



}
