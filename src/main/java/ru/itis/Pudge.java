package ru.itis;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import ru.itis.controller.GameField;

public class Pudge implements Runnable {
    private ImageView imageView;
    private boolean isAlive;
    private int number;
    public Thread thread = new Thread(this);

    public Pudge(ImageView imageView, boolean isAlive, int number) {
        this.imageView = imageView;
        this.isAlive = isAlive;
        this.number = number;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void setAlive(boolean alive) {
        isAlive = alive;
    }


    @Override
    public void run() {
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        configurePudge();
        while (true) {
            if (isAlive) {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Platform.runLater(this::movePudge);
            } else {
                respawnPudge();
                setAlive(true);
            }
        }

    }

    private void respawnPudge() {
        imageView.setX(0.0);
    }

    public void movePudge() {
        if (imageView.getX() >= 1440) {
            getBack();
        } else {
            this.imageView.setX(imageView.getX() + 10);
        }
    }
    void  getBack() {
        while (imageView.getX() > 10) {
            this.imageView.setX(imageView.getX() - 10);
        }
    }

    public synchronized void configurePudge() {
        imageView.setImage(new Image(getClass().getResourceAsStream("/img/pudge_model.png")));
        imageView.setFitHeight(50);
        imageView.setFitWidth(50);
        imageView.setY(100 * number);
    }


}
