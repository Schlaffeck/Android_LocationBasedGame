package com.slamcode.locationbasedgamelib.model;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by smoriak on 24/07/2017.
 */
public class LocationDataTest {

    @Test
    public void countDistanceFrom_test() {
        LocationData locationData = new LocationData(34.731323f, 113.628419f); // zhengzou agricultural bank of china

        LocationData otherLocation = new LocationData(34.730662f, 113.628398); // zhengzou bainian laoma

        float expected = 79.4f;
        float actual = locationData.countDistanceFrom(otherLocation);
        assertEquals(expected, actual, 0.6f); // about 80 meters length
    }
}