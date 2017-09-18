package com.slamcode.locationbasedgamelib.persistence;

/**
 * Interface used by persistence context to supply specific operations on data bundle instance
 * when persisting or reading from source
 */

public interface GameDataBundleProvider<DataBundle extends GameDataBundle> {

    /**
     * Provides default bundle instance if none was persisted yet
     * @return Default bundle instance
     */
    DataBundle getDefaultBundleInstance();

    /**
     * Updates bundle of data after it was initialized if any modifications were made to it
     * since last persistence
     * @param dataBundle Bundle to update
     */
    void updateBundle(DataBundle dataBundle);

    /**
     * Gets the bundle type class object for helping to persist to and read
     * data from source
     * @return Class object of data bundle type
     */
    Class<DataBundle> getBundleClassType();
}
