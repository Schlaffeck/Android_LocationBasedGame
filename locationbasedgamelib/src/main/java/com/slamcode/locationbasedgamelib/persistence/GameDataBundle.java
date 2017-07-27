package com.slamcode.locationbasedgamelib.persistence;

import com.slamcode.locationbasedgamelib.model.GameTaskData;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple game data container class bundle
 */

public class GameDataBundle {

    private List<GameTaskData> gameTaskDataList = new ArrayList<>();

    public List<GameTaskData> getGameTasks() {
        return this.gameTaskDataList;
    }

    public void setGameTasks(List<GameTaskData> gameTasks) {
        this.gameTaskDataList = gameTasks;
    }
}
