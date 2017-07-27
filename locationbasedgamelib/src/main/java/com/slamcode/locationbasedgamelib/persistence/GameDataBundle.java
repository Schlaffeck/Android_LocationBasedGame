package com.slamcode.locationbasedgamelib.persistence;

import com.slamcode.locationbasedgamelib.model.GameTaskData;

import java.util.List;

/**
 * General bundle interface for persisted game data
 */

public interface GameDataBundle {

    /**
     * Retrieves persisted game tasks data list
     * @return All game tasks in list
     */
    List<GameTaskData> getGameTasks();

    /**
     * Sets given list as base for all game tasks
     * @param gameTasks list to set
     */
    void setGameTasks(List<GameTaskData> gameTasks);
}
