package com.slamcode.locationbasedgamelib.persistence;

import com.slamcode.locationbasedgamelib.model.GameTaskData;
import com.slamcode.locationbasedgamelib.persistence.GameDataBundle;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple game data container class bundle
 */

public class SimpleDataBundle implements GameDataBundle {

    List<GameTaskData> gameTaskDataList = new ArrayList<>();

    @Override
    public List<GameTaskData> getGameTasks() {
        return this.gameTaskDataList;
    }

    @Override
    public void setGameTasks(List<GameTaskData> gameTasks) {
        this.gameTaskDataList = gameTasks;
    }
}
