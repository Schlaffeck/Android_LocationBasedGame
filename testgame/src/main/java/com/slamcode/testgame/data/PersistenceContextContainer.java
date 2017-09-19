package com.slamcode.testgame.data;

import android.content.Context;

import com.slamcode.locationbasedgamelib.model.GameTaskData;
import com.slamcode.locationbasedgamelib.model.builder.GameTaskBuilder;
import com.slamcode.locationbasedgamelib.multimedia.AudioPlayer;
import com.slamcode.locationbasedgamelib.persistence.GameDataBundleProvider;
import com.slamcode.locationbasedgamelib.persistence.PersistenceContext;
import com.slamcode.locationbasedgamelib.persistence.json.JsonFilePersistenceContext;
import com.slamcode.testgame.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Created by smoriak on 27/07/2017.
 */

public final class PersistenceContextContainer {

    private final static String FILE_NAME = "game_tasks.data";

    private static PersistenceContext<TestGameDataBundle> currentContext;

    public static synchronized PersistenceContext initializePersistenceContext(final Context applicationContext)
    {
        if(currentContext == null)
        {
            GameDataBundleProvider<TestGameDataBundle> bundleProvider = new TestGameDataBundleProvider(applicationContext);

            currentContext = new JsonFilePersistenceContext<>(applicationContext, FILE_NAME, bundleProvider);

            currentContext.initializePersistedData();
        }

        return currentContext;
    }

    public static PersistenceContext<TestGameDataBundle> getCurrentContext()
    {
        return currentContext;
    }
}
