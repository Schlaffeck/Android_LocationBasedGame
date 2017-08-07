package com.slamcode.locationbasedgamelib.utils;

/**
 * Utility methods to facilitate usage if Iterable interface
 */

public final class IterableUtils {

    public static int count(Iterable iterable)
    {
        int counter = 0;

        for(Object o : iterable)
            counter++;

        return counter;
    }
}

