package com.slamcode.locationbasedgamelib.persistence.json;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.slamcode.locationbasedgamelib.model.GameTaskContentElement;
import com.slamcode.locationbasedgamelib.persistence.PersistenceContext;
import com.slamcode.locationbasedgamelib.persistence.GameDataBundle;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;

/**
 * Context persisting data bundle in json formatted file in application private resources
 */

public class JsonFilePersistenceContext implements PersistenceContext {

    private final Context applicationContext;
    private final String fileName;
    private GameDataBundle data;

    public JsonFilePersistenceContext(Context applicationContext, String fileName)
    {
        this.applicationContext = applicationContext;
        this.fileName = fileName;
    }

    @Override
    public void initializePersistedData() {
        try {
            File bundleFile = new File(this.getFilePath());
            if(bundleFile.exists()) {
                FileReader fileReader = new FileReader(this.getFilePath());

                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(GameTaskContentElement.class, new GameTaskContentElementJsonDeserializer())
                        .create();
                this.data = gson.fromJson(fileReader, GameDataBundle.class);
            }

            if(this.data == null)
            {
                this.data = new GameDataBundle();
            }
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
    }

    @Override
    public void persist() {
        FileOutputStream fileStream;
        try{
            Gson gson = new GsonBuilder()
                    .registerTypeHierarchyAdapter(GameTaskContentElement.class, new GameTaskContentElementJsonDeserializer())
                    .create();
            fileStream = this.applicationContext.openFileOutput(this.fileName, Context.MODE_PRIVATE);
            fileStream.write(gson.toJson(this.data, GameDataBundle.class).getBytes());
            fileStream.close();
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
    }

    @Override
    public GameDataBundle getData() {
        return data;
    }

    private String getFilePath()
    {
        return this.applicationContext.getFilesDir() +"/" + this.fileName;
    }
}
