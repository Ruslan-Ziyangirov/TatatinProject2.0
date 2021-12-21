package ru.itis;

import javafx.event.EventType;
import javafx.scene.input.MouseEvent;

public enum Action {
    SHOOT(5,"shoot", "Игрок стреляет", MouseEvent.MOUSE_CLICKED);

    private final int code;
    private final String title;
    private final String description;
    private final EventType<MouseEvent> mouseEvent;

    Action(int code, String title, String description, EventType<MouseEvent> mouseEvent) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.mouseEvent = mouseEvent;
    }

    public int getCode() {
        return code;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public static Action typeOf(String title){
        for (Action action: Action.values()) {
            if (action.getTitle().equalsIgnoreCase(title)) {
                return action;
            }
        }
        return null;
    }
}
