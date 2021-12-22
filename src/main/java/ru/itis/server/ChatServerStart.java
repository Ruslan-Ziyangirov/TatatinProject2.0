package ru.itis.server;

import ru.itis.server.GameServer;

public class ChatServerStart {
        public static void main(String[] args) {
            System.out.println("Создан сервер!!!");
            ChatServer chatServer = ChatServer.getInstance();
            chatServer.start();
        }
}
