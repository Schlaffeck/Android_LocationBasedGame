package com.slamcode.locationbasedgamelib.model.builder;

import com.slamcode.locationbasedgamelib.model.GameTaskContent;
import com.slamcode.locationbasedgamelib.model.GameTaskContentElement;
import com.slamcode.locationbasedgamelib.model.GameTaskData;
import com.slamcode.locationbasedgamelib.model.GameTaskHeader;
import com.slamcode.locationbasedgamelib.model.InputContent;
import com.slamcode.locationbasedgamelib.model.LocationData;
import com.slamcode.locationbasedgamelib.model.content.DisplayPictureElement;
import com.slamcode.locationbasedgamelib.model.content.DisplayTextElement;
import com.slamcode.locationbasedgamelib.model.content.LocationComparisonInputElement;
import com.slamcode.locationbasedgamelib.model.content.TextComparisonInputElement;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Builder class facilitating creating complex game task elements
 */

public class GameTaskBuilder {

    private GameTaskData buildingTask;

    public GameTaskBuilder(int taskId){
        this.buildingTask = new GameTaskData(taskId);
    }

    public GameTaskBuilder withTitle(String taskTitle)
    {
        GameTaskHeader header = this.buildingTask.getGameTaskHeader();
        if(header == null)
            this.buildingTask.setGameTaskHeader((header = new GameTaskHeader()));
        header.setHeaderTitle(taskTitle);
        return this;
    }

    public GameTaskBuilder withTextElement(String displayText)
    {
        GameTaskContent content = this.buildingTask.getGameTaskContent();
        if(content == null)
            this.buildingTask.setGameTaskContent((content = new GameTaskContent()));
        DisplayTextElement textElement = new DisplayTextElement();
        textElement.setText(displayText);
        content.addContentElement(textElement);
        return this;
    }

    public GameTaskBuilder withPictureElement(int pictureResourceId)
    {
        GameTaskContent content = this.buildingTask.getGameTaskContent();
        if(content == null)
            this.buildingTask.setGameTaskContent((content = new GameTaskContent()));
        DisplayPictureElement pictureElement = new DisplayPictureElement();
        pictureElement.setPictureResourceId(pictureResourceId);
        content.addContentElement(pictureElement);
        return this;
    }

    public GameTaskBuilder withTextInputComparisonElement(String commitMessage, String... acceptableInputValues)
    {
        GameTaskContent content = this.buildingTask.getGameTaskContent();
        if(content == null)
            this.buildingTask.setGameTaskContent((content = new GameTaskContent()));
        TextComparisonInputElement comparisonElement = new TextComparisonInputElement(Arrays.asList(acceptableInputValues), commitMessage);
        content.addContentElement(comparisonElement);
        return this;
    }


    public GameTaskBuilder withLocationComparisonElement(String commitMessage, float latitude, float longitude, float acceptanceDistanceMeters, InputContent.OnInputCommittedListener<LocationData> listener)
    {
        GameTaskContent content = this.buildingTask.getGameTaskContent();
        if(content == null)
            this.buildingTask.setGameTaskContent((content = new GameTaskContent()));
        LocationComparisonInputElement comparisonElement = new LocationComparisonInputElement(new LocationData(latitude, longitude), acceptanceDistanceMeters, commitMessage);

        if(listener != null)
            comparisonElement.addOnInputCommittedListener(listener);

        content.addContentElement(comparisonElement);
        return this;
    }

    public GameTaskBuilder withCustomContentElement(GameTaskContentElement element)
    {
        GameTaskContent content = this.buildingTask.getGameTaskContent();
        if(content == null)
            this.buildingTask.setGameTaskContent((content = new GameTaskContent()));
        content.addContentElement(element);
        return this;
    }

    /**
     * Returns built game task and resets the builder
     * @return Built game task data object
     */
    public GameTaskData getTask()
    {
        return this.buildingTask;
    }

    public static void addLocationInputListener(GameTaskData data, InputContent.OnInputCommittedListener<LocationData> listener)
    {
        for(GameTaskContentElement element : data.getGameTaskContent().getContentElements())
        {
            if(element instanceof LocationComparisonInputElement)
            {
                LocationComparisonInputElement locationComparisonElement = (LocationComparisonInputElement)element;
                locationComparisonElement.addOnInputCommittedListener(listener);
            }
        }
    }

    public static void addTextInputComparisonListener(GameTaskData data, InputContent.OnInputCommittedListener<String> listener)
    {
        for(GameTaskContentElement element : data.getGameTaskContent().getContentElements())
        {
            if(element instanceof TextComparisonInputElement)
            {
                TextComparisonInputElement textComparisonInputElement = (TextComparisonInputElement)element;
                textComparisonInputElement.addOnInputCommittedListener(listener);
            }
        }
    }
}
