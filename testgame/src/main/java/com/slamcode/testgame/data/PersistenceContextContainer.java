package com.slamcode.testgame.data;

import android.content.Context;

import com.slamcode.locationbasedgamelib.model.GameTaskData;
import com.slamcode.locationbasedgamelib.model.builder.GameTaskBuilder;
import com.slamcode.locationbasedgamelib.multimedia.AudioPlayer;
import com.slamcode.locationbasedgamelib.persistence.PersistenceContext;
import com.slamcode.locationbasedgamelib.persistence.json.JsonFilePersistenceContext;
import com.slamcode.testgame.R;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by smoriak on 27/07/2017.
 */

public final class PersistenceContextContainer {

    private final static String FILE_NAME = "game_tasks.data";

    private static PersistenceContext currentContext;

    public static synchronized PersistenceContext initializePersistenceContext(Context applicationContext)
    {
        if(currentContext == null)
        {
            currentContext = new JsonFilePersistenceContext(applicationContext, FILE_NAME);
            currentContext.initializePersistedData();
            addSampleTasks(applicationContext);
        }

        return currentContext;
    }

    public static PersistenceContext getCurrentContext()
    {
        return currentContext;
    }

    private static void addSampleTasks(Context applicationContext)
    {
        //if(currentContext.getData().getGameTasks() == null || currentContext.getData().getGameTasks().isEmpty()) {
            GameTaskData[] tasksList = new GameTaskData[]
                    {
                            new GameTaskBuilder(1).withTitle("Simple task 1").withTextElement("Test text message")
                                    .withPictureElement(R.drawable.sromamba).getTask(),
                            new GameTaskBuilder(2).withTitle("Multiline task 2").withTextElement("Test text message number 2\nWith new line").getTask(),
                            new GameTaskBuilder(3).withTitle("Text Input Task 3").withTextElement("Test text message number 3\nWith new line\nAnd input")
                                    .withTextInputComparisonElement("Check", "test", "game").getTask(),
                            new GameTaskBuilder(4).withTitle("Location input task 4")
                                    .withTextElement("Some test upper text input")
                                    .withTextElement("Go to 'Feniks DH' on old market square and try the button")
                                    .withLocationComparisonElement("Are you there yet?", 51.109460f, 17.033041f, 15f, null).getTask(),
                            new GameTaskBuilder(5).withTitle("Audio task 5")
                                    .withTextElement("Play some sound!")
                                    .withAudioPlayerElement(R.raw.vengeance, "Music", applicationContext)
                                    .getTask()
                    };

            currentContext.getData().setGameTasks(Arrays.asList(tasksList));
        //}
    }
}
