package com.slamcode.locationbasedgamelib.persistence.json;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.google.gson.Gson;
import com.slamcode.locationbasedgamelib.model.GameTaskContentElement;
import com.slamcode.locationbasedgamelib.model.GameTaskData;
import com.slamcode.locationbasedgamelib.model.builder.GameTaskBuilder;
import com.slamcode.locationbasedgamelib.model.content.DisplayAudioPlayerElement;
import com.slamcode.locationbasedgamelib.model.content.DisplayPictureElement;
import com.slamcode.locationbasedgamelib.model.content.DisplayTextElement;
import com.slamcode.locationbasedgamelib.model.content.LocationComparisonInputElement;
import com.slamcode.locationbasedgamelib.model.content.TextComparisonInputElement;
import com.slamcode.locationbasedgamelib.persistence.GameDataBundle;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by smoriak on 27/07/2017.
 */
@RunWith(AndroidJUnit4.class)
public class JsonFilePersistenceContextInstrumentedTest {

    private static final String BUNDLE_FILE_NAME = "test_json.data";
    private Context appContext;
    private GameDataBundle bundle;

    @Before
    public void setUp() throws Exception {
        this.bundle = new GameDataBundle();
        List<GameTaskData> gameTasks = new ArrayList<>();
        gameTasks.add(new GameTaskBuilder(1).withTitle("Task 1").withTextElement("Task 1 text").getTask());
        gameTasks.add(new GameTaskBuilder(2).withTitle("Task 2").withTextElement("Task 2 text").getTask());
        gameTasks.add(new GameTaskBuilder(3).withTitle("Task 3").withTextElement("Task 3 text").getTask());
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

        JsonFilePersistenceContext context = new JsonFilePersistenceContext<>(this.appContext, BUNDLE_FILE_NAME, new GameDataBundle.Provider());
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

        JsonFilePersistenceContext context = new JsonFilePersistenceContext<>(this.appContext, BUNDLE_FILE_NAME, new GameDataBundle.Provider());
        assertNull(context.getData());

        context.initializePersistedData();
        assertNotNull(context.getData());
        assertNotNull(context.getData().getGameTasks());
        assertEquals(0, context.getData().getGameTasks().size());
    }

    @Test
    public void jsonFilePersistenceContext_initialize_test()
    {
        JsonFilePersistenceContext context = new JsonFilePersistenceContext<>(this.appContext, BUNDLE_FILE_NAME, new GameDataBundle.Provider());;
        assertNull(context.getData());

        context.initializePersistedData();
        assertEquals(3, context.getData().getGameTasks().size());
    }

    @Test
    public void jsonFilePersistenceContext_changePersistRead_removeTask_test()
    {
        JsonFilePersistenceContext primalContext = new JsonFilePersistenceContext<>(this.appContext, BUNDLE_FILE_NAME, new GameDataBundle.Provider());;
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
        JsonFilePersistenceContext newContext = new JsonFilePersistenceContext<>(this.appContext, BUNDLE_FILE_NAME, new GameDataBundle.Provider());;
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
        JsonFilePersistenceContext primalContext = new JsonFilePersistenceContext<>(this.appContext, BUNDLE_FILE_NAME, new GameDataBundle.Provider());;
        assertNull(primalContext.getData());

        primalContext.initializePersistedData();

        List<GameTaskData> gameTaskDataList = primalContext.getData().getGameTasks();
        assertEquals(3, gameTaskDataList.size());
        assertEquals("Task 1", gameTaskDataList.get(0).getGameTaskHeader().getHeaderTitle());
        assertEquals("Task 2", gameTaskDataList.get(1).getGameTaskHeader().getHeaderTitle());
        assertEquals("Task 3", gameTaskDataList.get(2).getGameTaskHeader().getHeaderTitle());

        // add task
        gameTaskDataList.add(new GameTaskBuilder(4).withTitle("Task 4").withTextElement("Task 4 text").getTask());
        primalContext.persist();

        // read again
        JsonFilePersistenceContext newContext = new JsonFilePersistenceContext<>(this.appContext, BUNDLE_FILE_NAME, new GameDataBundle.Provider());;
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
        JsonFilePersistenceContext primalContext = new JsonFilePersistenceContext<>(this.appContext, BUNDLE_FILE_NAME, new GameDataBundle.Provider());;
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
        JsonFilePersistenceContext newContext = new JsonFilePersistenceContext<>(this.appContext, BUNDLE_FILE_NAME, new GameDataBundle.Provider());;
        assertNull(newContext.getData());

        newContext.initializePersistedData();

        gameTaskDataList = newContext.getData().getGameTasks();
        assertEquals(3, gameTaskDataList.size());
        assertEquals("new header 1", gameTaskDataList.get(0).getGameTaskHeader().getHeaderTitle());
        assertEquals("new header 2", gameTaskDataList.get(1).getGameTaskHeader().getHeaderTitle());
        assertEquals("new header 3", gameTaskDataList.get(2).getGameTaskHeader().getHeaderTitle());
    }

    @Test
    public void jsonFilePersistenceContext_saveRestoreChange_textElementContent_test() throws IOException {
        final String testFileName = "sr_text.data";
        final String testFilePath = this.appContext.getFilesDir() + "/" + testFileName;

        try {
            // setup bundle
            JsonFilePersistenceContext persistenceContext = new JsonFilePersistenceContext<>(this.appContext, testFileName, new GameDataBundle.Provider());;
            assertNull(persistenceContext.getData());
            persistenceContext.initializePersistedData();

            assertNotNull(persistenceContext.getData());
            assertNotNull(persistenceContext.getData().getGameTasks());
            assertEquals(0, persistenceContext.getData().getGameTasks().size());

            // add game task with text element
            persistenceContext.getData().getGameTasks().add(new GameTaskBuilder(1).withTitle("Text element task").withTextElement("Display text").getTask());
            persistenceContext.persist();

            // read again
            JsonFilePersistenceContext newContext = new JsonFilePersistenceContext<>(this.appContext, testFileName, new GameDataBundle.Provider());;
            newContext.initializePersistedData();

            assertNotNull(newContext.getData());
            assertNotNull(newContext.getData().getGameTasks());
            assertEquals(1, newContext.getData().getGameTasks().size());

            GameTaskData gameTaskData = newContext.getData().getGameTasks().get(0);
            assertEquals(1, gameTaskData.getId());
            assertEquals("Text element task", gameTaskData.getGameTaskHeader().getHeaderTitle());
            List<GameTaskContentElement> gameTaskContentElements = gameTaskData.getGameTaskContent().getContentElements();
            assertTrue(gameTaskContentElements.iterator().hasNext());
            GameTaskContentElement element = gameTaskContentElements.get(0);
            assertNotNull(element);
            assertTrue(element instanceof DisplayTextElement);
            DisplayTextElement typedElement = (DisplayTextElement) element;
            assertEquals("Display text", typedElement.getText());

            // change and save
            typedElement.setText("Changed text to display");
            newContext.persist();

            // another context
            JsonFilePersistenceContext thirdContext = new JsonFilePersistenceContext<>(this.appContext, testFileName, new GameDataBundle.Provider());;
            thirdContext.initializePersistedData();

            assertNotNull(thirdContext.getData());
            assertNotNull(thirdContext.getData().getGameTasks());
            assertEquals(1, thirdContext.getData().getGameTasks().size());

            gameTaskData = thirdContext.getData().getGameTasks().get(0);
            assertEquals(1, gameTaskData.getId());
            assertEquals("Text element task", gameTaskData.getGameTaskHeader().getHeaderTitle());
            gameTaskContentElements = gameTaskData.getGameTaskContent().getContentElements();
            element = gameTaskContentElements.get(0);
            assertNotNull(element);
            assertTrue(element instanceof DisplayTextElement);
            typedElement = (DisplayTextElement) element;
            assertEquals("Changed text to display", typedElement.getText());

        }
        finally {
            // cleanup
            File bundleFile = new File(testFilePath);
            if (bundleFile.exists()) {
                bundleFile.delete();
            }
        }
    }

    @Test
    public void jsonFilePersistenceContext_saveRestoreChange_pictureElementContent_test() throws IOException {
        final String testFileName = "sr_pic.data";
        final String testFilePath = this.appContext.getFilesDir() + "/" + testFileName;

        try {
            // setup bundle
            JsonFilePersistenceContext persistenceContext = new JsonFilePersistenceContext<>(this.appContext, testFileName, new GameDataBundle.Provider());;
            assertNull(persistenceContext.getData());
            persistenceContext.initializePersistedData();

            assertNotNull(persistenceContext.getData());
            assertNotNull(persistenceContext.getData().getGameTasks());
            assertEquals(0, persistenceContext.getData().getGameTasks().size());

            // add game task with text element
            persistenceContext.getData().getGameTasks().add(new GameTaskBuilder(1).withTitle("Pic element task").withPictureElement(38).getTask());
            persistenceContext.persist();

            // read again
            JsonFilePersistenceContext newContext = new JsonFilePersistenceContext<>(this.appContext, testFileName, new GameDataBundle.Provider());;
            newContext.initializePersistedData();

            assertNotNull(newContext.getData());
            assertNotNull(newContext.getData().getGameTasks());
            assertEquals(1, newContext.getData().getGameTasks().size());

            GameTaskData gameTaskData = newContext.getData().getGameTasks().get(0);
            assertEquals(1, gameTaskData.getId());
            assertEquals("Pic element task", gameTaskData.getGameTaskHeader().getHeaderTitle());
            List<GameTaskContentElement> gameTaskContentElements = gameTaskData.getGameTaskContent().getContentElements();
            assertTrue(gameTaskContentElements.iterator().hasNext());
            GameTaskContentElement element = gameTaskContentElements.get(0);
            assertNotNull(element);
            assertTrue(element instanceof DisplayPictureElement);
            DisplayPictureElement typedElement = (DisplayPictureElement) element;
            assertEquals(38, typedElement.getPictureResourceId());
            assertEquals(null, typedElement.getPicturePath());

            // change and save
            typedElement.setPicturePath("path");
            typedElement.setPictureResourceId(33);
            newContext.persist();

            // another context
            JsonFilePersistenceContext thirdContext = new JsonFilePersistenceContext<>(this.appContext, testFileName, new GameDataBundle.Provider());;
            thirdContext.initializePersistedData();

            assertNotNull(thirdContext.getData());
            assertNotNull(thirdContext.getData().getGameTasks());
            assertEquals(1, thirdContext.getData().getGameTasks().size());

            gameTaskData = thirdContext.getData().getGameTasks().get(0);
            assertEquals(1, gameTaskData.getId());
            assertEquals("Pic element task", gameTaskData.getGameTaskHeader().getHeaderTitle());
            gameTaskContentElements = gameTaskData.getGameTaskContent().getContentElements();
            element = gameTaskContentElements.get(0);
            assertNotNull(element);
            assertTrue(element instanceof DisplayPictureElement);
            typedElement = (DisplayPictureElement) element;
            assertEquals(33, typedElement.getPictureResourceId());
            assertEquals("path", typedElement.getPicturePath());

        }
        finally {
            // cleanup
            File bundleFile = new File(testFilePath);
            if (bundleFile.exists()) {
                bundleFile.delete();
            }
        }
    }

    @Test
    public void jsonFilePersistenceContext_saveRestoreChange_textInputElementContent_test() throws IOException {
        final String testFileName = "sr_textInput.data";
        final String testFilePath = this.appContext.getFilesDir() + "/" + testFileName;

        try {
            // setup bundle
            JsonFilePersistenceContext persistenceContext = new JsonFilePersistenceContext<>(this.appContext, testFileName, new GameDataBundle.Provider());;
            assertNull(persistenceContext.getData());
            persistenceContext.initializePersistedData();

            assertNotNull(persistenceContext.getData());
            assertNotNull(persistenceContext.getData().getGameTasks());
            assertEquals(0, persistenceContext.getData().getGameTasks().size());

            // add game task with text element
            persistenceContext.getData().getGameTasks().add(new GameTaskBuilder(1).withTitle("text input element task")
                    .withTextInputComparisonElement("Check value", "A1", "A2").getTask());
            persistenceContext.persist();

            // read again
            JsonFilePersistenceContext newContext = new JsonFilePersistenceContext<>(this.appContext, testFileName, new GameDataBundle.Provider());;
            newContext.initializePersistedData();

            assertNotNull(newContext.getData());
            assertNotNull(newContext.getData().getGameTasks());
            assertEquals(1, newContext.getData().getGameTasks().size());

            GameTaskData gameTaskData = newContext.getData().getGameTasks().get(0);
            assertEquals(1, gameTaskData.getId());
            assertEquals("text input element task", gameTaskData.getGameTaskHeader().getHeaderTitle());
            List<GameTaskContentElement> gameTaskContentElements = gameTaskData.getGameTaskContent().getContentElements();
            GameTaskContentElement element = gameTaskContentElements.get(0);
            assertNotNull(element);
            assertTrue(element instanceof TextComparisonInputElement);
            TextComparisonInputElement typedElement = (TextComparisonInputElement) element;
            assertEquals("Check value", typedElement.getCommitCommandName());
            assertEquals(null, typedElement.getInputValue());
            Iterator<String> iterator = typedElement.getAcceptableInputValues().iterator();
            assertEquals("A1", iterator.next());
            assertEquals("A2", iterator.next());

            // change and save
            typedElement.setInputValue("some input text");
            newContext.persist();

            // another context
            JsonFilePersistenceContext thirdContext = new JsonFilePersistenceContext<>(this.appContext, testFileName, new GameDataBundle.Provider());;
            thirdContext.initializePersistedData();

            assertNotNull(thirdContext.getData());
            assertNotNull(thirdContext.getData().getGameTasks());
            assertEquals(1, thirdContext.getData().getGameTasks().size());

            gameTaskData = thirdContext.getData().getGameTasks().get(0);
            assertEquals(1, gameTaskData.getId());
            assertEquals("text input element task", gameTaskData.getGameTaskHeader().getHeaderTitle());
            gameTaskContentElements = gameTaskData.getGameTaskContent().getContentElements();
            element = gameTaskContentElements.get(0);
            assertNotNull(element);
            assertTrue(element instanceof TextComparisonInputElement);
            typedElement = (TextComparisonInputElement) element;
            assertEquals("Check value", typedElement.getCommitCommandName());
            assertEquals("some input text", typedElement.getInputValue());
            iterator = typedElement.getAcceptableInputValues().iterator();
            assertEquals("A1", iterator.next());
            assertEquals("A2", iterator.next());

        }
        finally {
            // cleanup
            File bundleFile = new File(testFilePath);
            if (bundleFile.exists()) {
                bundleFile.delete();
            }
        }
    }

    @Test
    public void jsonFilePersistenceContext_saveRestore_locationComparisonElementContent_test() throws IOException {
        final String testFileName = "sr_locationComp.data";
        final String testFilePath = this.appContext.getFilesDir() + "/" + testFileName;

        try {
            // setup bundle
            JsonFilePersistenceContext persistenceContext = new JsonFilePersistenceContext<>(this.appContext, testFileName, new GameDataBundle.Provider());;
            assertNull(persistenceContext.getData());
            persistenceContext.initializePersistedData();

            assertNotNull(persistenceContext.getData());
            assertNotNull(persistenceContext.getData().getGameTasks());
            assertEquals(0, persistenceContext.getData().getGameTasks().size());

            // add game task with text element
            persistenceContext.getData().getGameTasks().add(new GameTaskBuilder(1).withTitle("location comparison element task")
                    .withLocationComparisonElement("Check location", 53.00f, 17.00f, 20f, null).getTask());
            persistenceContext.persist();

            // read again
            JsonFilePersistenceContext newContext = new JsonFilePersistenceContext<>(this.appContext, testFileName, new GameDataBundle.Provider());;
            newContext.initializePersistedData();

            assertNotNull(newContext.getData());
            assertNotNull(newContext.getData().getGameTasks());
            assertEquals(1, newContext.getData().getGameTasks().size());

            GameTaskData gameTaskData = newContext.getData().getGameTasks().get(0);
            assertEquals(1, gameTaskData.getId());
            assertEquals("location comparison element task", gameTaskData.getGameTaskHeader().getHeaderTitle());
            List<GameTaskContentElement> gameTaskContentElements = gameTaskData.getGameTaskContent().getContentElements();
            GameTaskContentElement element = gameTaskContentElements.get(0);
            assertNotNull(element);
            assertTrue(element instanceof LocationComparisonInputElement);
            LocationComparisonInputElement typedElement = (LocationComparisonInputElement) element;
            assertEquals("Check location", typedElement.getCommitCommandName());

        }
        finally {
            // cleanup
            File bundleFile = new File(testFilePath);
            if (bundleFile.exists()) {
                bundleFile.delete();
            }
        }
    }

    @Test
    public void jsonFilePersistenceContext_saveRestore_AudioPlayerElement_test() throws IOException {
        final String testFileName = "sr_locationComp.data";
        final String testFilePath = this.appContext.getFilesDir() + "/" + testFileName;

        try {
            // setup bundle
            JsonFilePersistenceContext persistenceContext = new JsonFilePersistenceContext<>(this.appContext, testFileName, new GameDataBundle.Provider());;
            assertNull(persistenceContext.getData());
            persistenceContext.initializePersistedData();

            assertNotNull(persistenceContext.getData());
            assertNotNull(persistenceContext.getData().getGameTasks());
            assertEquals(0, persistenceContext.getData().getGameTasks().size());

            // add game task with text element
            persistenceContext.getData().getGameTasks().add(new GameTaskBuilder(1).withTitle("Audio player element task")
                    .withAudioPlayerElement(38, "Audio title", this.appContext).getTask());
            persistenceContext.persist();

            // read again
            JsonFilePersistenceContext newContext = new JsonFilePersistenceContext<>(this.appContext, testFileName, new GameDataBundle.Provider());;
            newContext.initializePersistedData();

            assertNotNull(newContext.getData());
            assertNotNull(newContext.getData().getGameTasks());
            assertEquals(1, newContext.getData().getGameTasks().size());

            GameTaskData gameTaskData = newContext.getData().getGameTasks().get(0);
            assertEquals(1, gameTaskData.getId());
            assertEquals("Audio player element task", gameTaskData.getGameTaskHeader().getHeaderTitle());
            List<GameTaskContentElement> gameTaskContentElements = gameTaskData.getGameTaskContent().getContentElements();
            GameTaskContentElement element = gameTaskContentElements.get(0);
            assertNotNull(element);
            assertTrue(element instanceof DisplayAudioPlayerElement);
            DisplayAudioPlayerElement typedElement = (DisplayAudioPlayerElement) element;
            assertEquals("Audio title", typedElement.getAudioTitle());
            assertEquals(38, typedElement.getAudioFileResourceId());
            assertEquals(null, typedElement.getAudioFileUriString());

            // change
            typedElement.setAudioFileUriString(testFilePath);
            typedElement.setAudioTitle("New title");
            typedElement.setAudioFileResourceId(34);
            newContext.persist();

            // read once again
            JsonFilePersistenceContext thirdContext = new JsonFilePersistenceContext<>(this.appContext, testFileName, new GameDataBundle.Provider());;
            thirdContext.initializePersistedData();

            assertNotNull(thirdContext.getData());
            assertNotNull(thirdContext.getData().getGameTasks());
            assertEquals(1, thirdContext.getData().getGameTasks().size());

            gameTaskData = newContext.getData().getGameTasks().get(0);
            assertEquals(1, gameTaskData.getId());
            assertEquals("Audio player element task", gameTaskData.getGameTaskHeader().getHeaderTitle());
            gameTaskContentElements = gameTaskData.getGameTaskContent().getContentElements();
            element = gameTaskContentElements.get(0);
            assertNotNull(element);
            assertTrue(element instanceof DisplayAudioPlayerElement);
            typedElement = (DisplayAudioPlayerElement) element;
            assertEquals("New title", typedElement.getAudioTitle());
            assertEquals(34, typedElement.getAudioFileResourceId());
            assertEquals(testFilePath, typedElement.getAudioFileUriString());
        }
        finally {
            // cleanup
            File bundleFile = new File(testFilePath);
            if (bundleFile.exists()) {
                bundleFile.delete();
            }
        }
    }

    private String readFile(String file) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader (file));
        String         line = null;
        StringBuilder  stringBuilder = new StringBuilder();
        String         ls = System.getProperty("line.separator");

        try {
            while((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }

            return stringBuilder.toString();
        } finally {
            reader.close();
        }
    }
}