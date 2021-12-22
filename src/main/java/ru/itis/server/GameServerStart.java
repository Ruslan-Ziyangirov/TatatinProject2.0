package ru.itis.server;

public class GameServerStart {
    public static void main(String[] args) {
        startGame(8888);
    }

public static void startGame(int port) {
    System.out.println("Создан сервер игры!!!");
    GameServer gameServer = GameServer.getInstance();
    gameServer.start(port);
}

}
