package com.slamcode.locationbasedgamelib.persistence.json;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.google.gson.Gson;
import com.slamcode.locationbasedgamelib.model.GameTaskData;
import com.slamcode.locationbasedgamelib.model.builder.GameTaskBuilder;
import com.slamcode.locationbasedgamelib.persistence.SimpleDataBundle;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by smoriak on 27/07/2017.
 */
@RunWith(AndroidJUnit4.class)
public class JsonFilePersistenceContextInstrumentedTest {

    private static final String BUNDLE_FILE_NAME = "test_json.data";
    private Context appContext;
    private SimpleDataBundle bundle;

    @Before
    public void setUp() throws Exception {
        this.bundle = new SimpleDataBundle();
        List<GameTaskData> gameTasks = new ArrayList<>();
        gameTasks.add(new GameTaskBuilder().withTitle("Task 1").withTextElement("Task 1 text").getTask());
        gameTasks.add(new GameTaskBuilder().withTitle("Task 2").withTextElement("Task 2 text").getTask());
        gameTasks.add(new GameTaskBuilder().withTitle("Task 3").withTextElement("Task 3 text").getTask());
        this.bundle.setGameTasks(gameTasks);
        this.appContext = InstrumentationRegistry.getTargetContext();
        Gson gson = new Gson();

        FileOutputStream fileStream = this.appContext.openFileOutput(BUNDLE_FILE_NAME, Context.MODE_PRIVATE);
        fileStream.write(gson.toJson(this.bundle).getBytes());
        fileStream.close();
    }

    @After
    public void tearDown() throws Exception {
        File bundleFile = new File(this.appContext.getFilesDir() + "/" + BUNDLE_FILE_NAME);
        if(bundleFile.exists())
        {
            bundleFile.delete();
        }
        this.bundle = null;
    }

    @Test
    public void jsonFilePersistenceContext_initialize_nonExistingFile_test() throws IOException {

        File bundleFile = new File(this.appContext.getFilesDir() + "/" + BUNDLE_FILE_NAME);
        bundleFile.delete();
        assertFalse(bundleFile.exists());

        JsonFilePersistenceContext context = new JsonFilePersistenceContext(this.appContext, BUNDLE_FILE_NAME);
        assertNull(context.getData());

        context.initializePersistedData();
        assertNotNull(context.getData());
        assertNotNull(context.getData().getGameTasks());
        assertEquals(0, context.getData().getGameTasks().size());
    }

    @Test
    public void jsonFilePersistenceContext_initialize_emptyFile_test() throws IOException {

        FileWriter writer = new FileWriter(this.appContext.getFilesDir() + "/" + BUNDLE_FILE_NAME);
        writer.write(new char[0]);
        writer.close();

        JsonFilePersistenceContext context = new JsonFilePersistenceContext(this.appContext, BUNDLE_FILE_NAME);
        assertNull(context.getData());

        context.initializePersistedData();
        assertNotNull(context.getData());
        assertNotNull(context.getData().getGameTasks());
        assertEquals(0, context.getData().getGameTasks().size());
    }

    @Test
    public void jsonFilePersistenceContext_initialize_test()
    {
        JsonFilePersistenceContext context = new JsonFilePersistenceContext(this.appContext, BUNDLE_FILE_NAME);
        assertNull(context.getData());

        context.initializePersistedData();
        assertEquals(3, context.getData().getGameTasks().size());
    }

    @Test
    public void jsonFilePersistenceContext_changePersistRead_removeTask_test()
    {
        JsonFilePersistenceContext primalContext = new JsonFilePersistenceContext(this.appContext, BUNDLE_FILE_NAME);
        assertNull(primalContext.getData());

        primalContext.initializePersistedData();

        List<GameTaskData> gameTaskDataList = primalContext.getData().getGameTasks();
        assertEquals(3, gameTaskDataList.size());
        assertEquals("Task 1", gameTaskDataList.get(0).getGameTaskHeader().getHeaderTitle());
        assertEquals("Task 2", gameTaskDataList.get(1).getGameTaskHeader().getHeaderTitle());
        assertEquals("Task 3", gameTaskDataList.get(2).getGameTaskHeader().getHeaderTitle());

        // remove task 2
        gameTaskDataList.remove(1);

        primalContext.persist();

        // read again
        JsonFilePersistenceContext newContext = new JsonFilePersistenceContext(this.appContext, BUNDLE_FILE_NAME);
        assertNull(newContext.getData());

        newContext.initializePersistedData();

        gameTaskDataList = newContext.getData().getGameTasks();
        assertEquals(2, gameTaskDataList.size());
        assertEquals("Task 1", gameTaskDataList.get(0).getGameTaskHeader().getHeaderTitle());
        assertEquals("Task 3", gameTaskDataList.get(1).getGameTaskHeader().getHeaderTitle());
    }

    @Test
    public void jsonFilePersistenceContext_changePersistRead_addTask_test()
    {
        JsonFilePersistenceContext primalContext = new JsonFilePersistenceContext(this.appContext, BUNDLE_FILE_NAME);
        assertNull(primalContext.getData());

        primalContext.initializePersistedData();

        List<GameTaskData> gameTaskDataList = primalContext.getData().getGameTasks();
        assertEquals(3, gameTaskDataList.size());
        assertEquals("Task 1", gameTaskDataList.get(0).getGameTaskHeader().getHeaderTitle());
        assertEquals("Task 2", gameTaskDataList.get(1).getGameTaskHeader().getHeaderTitle());
        assertEquals("Task 3", gameTaskDataList.get(2).getGameTaskHeader().getHeaderTitle());

        // add task
        gameTaskDataList.add(new GameTaskBuilder().withTitle("Task 4").withTextElement("Task 4 text").getTask());
        primalContext.persist();

        // read again
        JsonFilePersistenceContext newContext = new JsonFilePersistenceContext(this.appContext, BUNDLE_FILE_NAME);
        assertNull(newContext.getData());

        newContext.initializePersistedData();

        gameTaskDataList = newContext.getData().getGameTasks();
        assertEquals(4, gameTaskDataList.size());
        assertEquals("Task 1", gameTaskDataList.get(0).getGameTaskHeader().getHeaderTitle());
        assertEquals("Task 2", gameTaskDataList.get(1).getGameTaskHeader().getHeaderTitle());
        assertEquals("Task 3", gameTaskDataList.get(2).getGameTaskHeader().getHeaderTitle());
        assertEquals("Task 4", gameTaskDataList.get(3).getGameTaskHeader().getHeaderTitle());
    }


    @Test
    public void jsonFilePersistenceContext_changePersistRead_changeTasksTitles_test()
    {
        JsonFilePersistenceContext primalContext = new JsonFilePersistenceContext(this.appContext, BUNDLE_FILE_NAME);
        assertNull(primalContext.getData());

        primalContext.initializePersistedData();

        List<GameTaskData> gameTaskDataList = primalContext.getData().getGameTasks();
        assertEquals(3, gameTaskDataList.size());
        assertEquals("Task 1", gameTaskDataList.get(0).getGameTaskHeader().getHeaderTitle());
        assertEquals("Task 2", gameTaskDataList.get(1).getGameTaskHeader().getHeaderTitle());
        assertEquals("Task 3", gameTaskDataList.get(2).getGameTaskHeader().getHeaderTitle());

        // change tasks
        gameTaskDataList.get(0).getGameTaskHeader().setHeaderTitle("new header 1");
        gameTaskDataList.get(1).getGameTaskHeader().setHeaderTitle("new header 2");
        gameTaskDataList.get(2).getGameTaskHeader().setHeaderTitle("new header 3");
        primalContext.persist();

        // read again
        JsonFilePersistenceContext newContext = new JsonFilePersistenceContext(this.appContext, BUNDLE_FILE_NAME);
        assertNull(newContext.getData());

        newContext.initializePersistedData();

        gameTaskDataList = newContext.getData().getGameTasks();
        assertEquals(4, gameTaskDataList.size());
        assertEquals("new header 1", gameTaskDataList.get(0).getGameTaskHeader().getHeaderTitle());
        assertEquals("new header 2", gameTaskDataList.get(1).getGameTaskHeader().getHeaderTitle());
        assertEquals("new header 3", gameTaskDataList.get(2).getGameTaskHeader().getHeaderTitle());
    }
}