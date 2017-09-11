package com.slamcode.locationbasedgamelib.persistence;

/**
 * Created by smoriak on 11/09/2017.
 */

public interface GameDataBundleProvider<DataBundle extends GameDataBundle> {

    DataBundle getDefaultBundleInstance();

    Class<DataBundle> getBundleClassType();
}
