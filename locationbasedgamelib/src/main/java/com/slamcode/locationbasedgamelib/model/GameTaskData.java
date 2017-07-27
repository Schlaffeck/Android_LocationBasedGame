package com.slamcode.locationbasedgamelib.model;

import java.util.UUID;

/**
 * Represents general information about task to do in the game
 */
public class GameTaskData {

    public final static String ID_FIELD_NAME = "id";

    private final int id;

    private GameTaskHeader gameTaskHeader;

    private GameTaskStatus status = GameTaskStatus.NotStarted;

    private GameTaskContent gameTaskContent;

    public GameTaskData(int id)
    {
        this.id = id;
    }

    public GameTaskStatus getStatus() {
        return status;
    }

    public void setStatus(GameTaskStatus status) {
        this.status = status;
    }

    public GameTaskHeader getGameTaskHeader() {
        return gameTaskHeader;
    }

    public void setGameTaskHeader(GameTaskHeader gameTaskHeader) {
        this.gameTaskHeader = gameTaskHeader;
    }

    public GameTaskContent getGameTaskContent() {
        return gameTaskContent;
    }

    public void setGameTaskContent(GameTaskContent gameTaskContent) {
        this.gameTaskContent = gameTaskContent;
    }

    public int getId() {
        return id;
    }
}
