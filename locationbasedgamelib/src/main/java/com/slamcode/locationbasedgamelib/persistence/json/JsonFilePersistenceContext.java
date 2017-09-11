package com.slamcode.locationbasedgamelib.persistence.json;

import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.slamcode.locationbasedgamelib.model.GameTaskContentElement;
import com.slamcode.locationbasedgamelib.persistence.GameDataBundleProvider;
import com.slamcode.locationbasedgamelib.persistence.PersistenceContext;
import com.slamcode.locationbasedgamelib.persistence.GameDataBundle;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;

/**
 * Context persisting data bundle in json formatted file in application private resources
 */

public class JsonFilePersistenceContext<DataBundle extends GameDataBundle> implements PersistenceContext<DataBundle> {

    private final Context applicationContext;
    private final String fileName;
    private final GameDataBundleProvider<DataBundle> dataBundleProvider;
    private DataBundle data;

    public JsonFilePersistenceContext(Context applicationContext, String fileName, GameDataBundleProvider<DataBundle> dataBundleProvider)
    {
        this.applicationContext = applicationContext;
        this.fileName = fileName;
        this.dataBundleProvider = dataBundleProvider;
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
                this.data = gson.fromJson(fileReader, this.dataBundleProvider.getBundleClassType());
            }

            if(this.data == null)
                this.data = this.dataBundleProvider.getDefaultBundleInstance();

            if(this.data != null)
                this.data.initialize();
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
            fileStream.write(gson.toJson(this.data, this.dataBundleProvider.getBundleClassType()).getBytes());
            fileStream.close();
        }
        catch(Exception exception)
        {
            exception.printStackTrace();
        }
    }

    @Override
    public DataBundle getData() {
        return data;
    }

    private String getFilePath()
    {
        return this.applicationContext.getFilesDir() +"/" + this.fileName;
    }
}
