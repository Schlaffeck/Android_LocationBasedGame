package com.slamcode.locationbasedgamelib.persistence;

import com.slamcode.locationbasedgamelib.general.Initializable;
import com.slamcode.locationbasedgamelib.model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Simple game data container class bundle
 */

public class GameDataBundle implements Initializable {

    private List<GameTaskData> gameTaskDataList = new ArrayList<>();
    private List<PlaceData> placeList = new ArrayList<>();

    public List<GameTaskData> getGameTasks() {
        return this.gameTaskDataList;
    }

    public void setGameTasks(List<GameTaskData> gameTasks) {
        this.gameTaskDataList = gameTasks;
    }

    @Override
    public void initialize() {
        for (GameTaskData task :
                this.gameTaskDataList) {
                task.initialize();
        }
    }

    public List<PlaceData> getPlaceList() {
        return this.placeList;
    }
}
