package ru.itis.protocol;

public enum MessageType {

    CONNECT("connect", "Игрок присоединение игрока"),
    STOP("stop", "Остановка игры"),
    ACTION("action", "Действие игрока"),
    CHAT( "chat", "Сообщение другим участникам"),
    WAITING("lobby","Подключен новый игрок, старт игры"),
    WAITING2("lobby2","Подключен новый игрок, старт игры2"),
    GETSESSION("get_session", "получаем информацию сессии"),
    STARTSESSION("start", "Начало игры!"),
    STARTSESSION2("start2", "Начало игры!2"),
    KILLPUDGE("kill", "Убит юнит"),
    WINENDING("win","Я победил!"),
    DRAWENDING("win","Я победил!"),
    LOSEENDING("lose","Я проиграл!!"),
    ENDING("end"," Конец игры!");

    private final String title;
    private final String description;

    MessageType(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public static MessageType typeOf(String title){
        for (MessageType messageType: MessageType.values()) {
            if (messageType.getTitle().equals(title)) {
                return messageType;
            }
        }
        return null;
    }
    @Override
    public String toString() {
        return "MessageType{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

}
