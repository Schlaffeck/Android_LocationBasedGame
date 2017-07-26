package com.slamcode.locationbasedgamelib.model;

/**
 * Represents general information about task to do in the game
 */
public class GameTaskData {

    private GameTaskHeader gameTaskHeader;

    private GameTaskStatus status = GameTaskStatus.NotStarted;

    private GameTaskContent gameTaskContent;

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
}
