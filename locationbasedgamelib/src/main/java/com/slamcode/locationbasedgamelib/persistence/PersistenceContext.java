package com.slamcode.locationbasedgamelib.persistence;

/**
 * Interface for service persisting and retrieving game tasks data in application
 */

public interface PersistenceContext<DataBundle extends GameDataBundle> {

    /**
     * Tries to read and initialize persisted data from source
     */
    void initializePersistedData();

    /**
     * Persists data in given context
     */
    void persist();

    /**
     * Retrieve persisted data. First need to initialize data from context.
     * @return Persisted data object or cull if not initialized
     */
    DataBundle getData();
}
