package com.slamcode.testgame.data;

import com.slamcode.locationbasedgamelib.model.PlaceData;
import com.slamcode.locationbasedgamelib.persistence.GameDataBundle;

import java.util.ArrayList;
import java.util.List;

public class TestGameDataBundle extends GameDataBundle {

    private List<PlaceData> placeList = new ArrayList<>();

    public List<PlaceData> getPlaceList() {
        return this.placeList;
    }
}
