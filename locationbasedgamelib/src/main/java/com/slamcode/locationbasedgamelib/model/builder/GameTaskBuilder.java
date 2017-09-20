package com.slamcode.locationbasedgamelib.model.builder;

import android.content.Context;
import android.net.Uri;

import com.slamcode.locationbasedgamelib.model.GameTaskContent;
import com.slamcode.locationbasedgamelib.model.GameTaskContentElement;
import com.slamcode.locationbasedgamelib.model.GameTaskData;
import com.slamcode.locationbasedgamelib.model.GameTaskHeader;
import com.slamcode.locationbasedgamelib.model.InputContentElement;
import com.slamcode.locationbasedgamelib.model.InputTip;
import com.slamcode.locationbasedgamelib.model.LocationData;
import com.slamcode.locationbasedgamelib.model.content.DisplayAudioPlayerElement;
import com.slamcode.locationbasedgamelib.model.content.DisplayPictureElement;
import com.slamcode.locationbasedgamelib.model.content.DisplayTextElement;
import com.slamcode.locationbasedgamelib.model.content.LocationComparisonInputElement;
import com.slamcode.locationbasedgamelib.model.content.TextComparisonInputElement;
import com.slamcode.locationbasedgamelib.multimedia.AudioPlayer;
import com.slamcode.locationbasedgamelib.multimedia.MediaServiceAudioPlayer;

import java.util.Arrays;
import java.util.List;

/**
 * Builder class facilitating creating complex game task elements
 */

public class GameTaskBuilder {

    private GameTaskData buildingTask;

    private GameTaskBuilder helpTaskBuilder;

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

    public GameTaskBuilder withTriesThreshold(int triesThreshold)
    {
        this.buildingTask.setInputTriesThreshold(triesThreshold);
        return this;
    }

    public GameTaskBuilder getHelpTaskBuilder()
    {
        if(this.helpTaskBuilder == null) {
            this.helpTaskBuilder = new GameTaskBuilder(0);
            this.buildingTask.setHelpTaskContent(this.helpTaskBuilder.getTask().getGameTaskContent());
        }
        return this.helpTaskBuilder;
    }

    public GameTaskBuilder withTextElement(String displayText)
    {
        GameTaskContent content = this.buildingTask.getGameTaskContent();
        if(content == null)
            this.buildingTask.setGameTaskContent((content = new GameTaskContent()));
        DisplayTextElement textElement = new DisplayTextElement();
        textElement.setText(displayText);
        content.getContentElements().add(textElement);
        return this;
    }

    public GameTaskBuilder withPictureElement(int pictureResourceId)
    {
        GameTaskContent content = this.buildingTask.getGameTaskContent();
        if(content == null)
            this.buildingTask.setGameTaskContent((content = new GameTaskContent()));
        DisplayPictureElement pictureElement = new DisplayPictureElement();
        pictureElement.setPictureResourceId(pictureResourceId);
        content.getContentElements().add(pictureElement);
        return this;
    }

    /**
     * Adds audio player content element to the game task with default MediaServiceAudioPlayer
     * from given context.
     * @param audioFileResourceId
     * @param context
     * @return This builder to use further
     */
    public GameTaskBuilder withAudioPlayerElement(int audioFileResourceId, String audioTitleOptional, Context context)
    {
        return this.withAudioPlayerElement(audioFileResourceId, audioTitleOptional, new MediaServiceAudioPlayer(context, audioFileResourceId));
    }

    public GameTaskBuilder withAudioPlayerElement(int audioFileResourceId, String audioTitleOptional, AudioPlayer audioPlayer)
    {
        GameTaskContent content = this.buildingTask.getGameTaskContent();
        if(content == null)
            this.buildingTask.setGameTaskContent((content = new GameTaskContent()));
        DisplayAudioPlayerElement audioElement = new DisplayAudioPlayerElement();
        audioElement.setAudioFileResourceId(audioFileResourceId);
        audioElement.setAudioTitle(audioTitleOptional);
        audioElement.useAudioPlayer(audioPlayer);
        content.getContentElements().add(audioElement);
        return this;
    }

    public GameTaskBuilder withAudioPlayerElement(Uri audioFileUri, String audioTitleOptional, Context context)
    {
        return this.withAudioPlayerElement(audioFileUri, audioTitleOptional, new MediaServiceAudioPlayer(context, audioFileUri));
    }

    public GameTaskBuilder withAudioPlayerElement(Uri audioFileUri, String audioTitleOptional, AudioPlayer audioPlayer)
    {
        GameTaskContent content = this.buildingTask.getGameTaskContent();
        if(content == null)
            this.buildingTask.setGameTaskContent((content = new GameTaskContent()));
        DisplayAudioPlayerElement audioElement = new DisplayAudioPlayerElement();
        audioElement.setAudioFileUriString(audioFileUri.toString());
        audioElement.setAudioTitle(audioTitleOptional);
        audioElement.useAudioPlayer(audioPlayer);
        content.getContentElements().add(audioElement);
        return this;
    }

    public GameTaskBuilder withTextInputComparisonElement(String commitMessage, String... acceptableInputValues)
    {
        GameTaskContent content = this.buildingTask.getGameTaskContent();
        if(content == null)
            this.buildingTask.setGameTaskContent((content = new GameTaskContent()));
        TextComparisonInputElement comparisonElement = new TextComparisonInputElement(Arrays.asList(acceptableInputValues), commitMessage);
        content.getContentElements().add(comparisonElement);
        return this;
    }

    public GameTaskBuilder withTipForPreviousTextInputElement(String input, String tipMessage)
    {
        TextComparisonInputElement textComparisonInputElement = findLastTextComparisonInputElementOrNull(this.buildingTask.getGameTaskContent());

        if(textComparisonInputElement == null)
            return this;

        InputTip<String> tip = new InputTip<>();
        tip.setInputValue(input);
        tip.setInputValueAssigned(true);
        tip.setTipMessage(tipMessage);
        textComparisonInputElement.getInputTips().add(tip);

        return this;
    }

    public GameTaskBuilder withTipsForPreviousTextInputElement(String[] inputs, String[] tipMessages)
    {
        TextComparisonInputElement textComparisonInputElement = findLastTextComparisonInputElementOrNull(this.buildingTask.getGameTaskContent());

        if(textComparisonInputElement == null)
            return this;

        for(int i = 0; i < Math.min(inputs.length, tipMessages.length); i++) {
            InputTip<String> tip = new InputTip<>();
            tip.setInputValue(inputs[i]);
            tip.setInputValueAssigned(true);
            tip.setTipMessage(tipMessages[i]);
            textComparisonInputElement.getInputTips().add(tip);
        }

        return this;
    }

    public GameTaskBuilder withTipForPreviousTextInputElement(String generalTipMessage)
    {
        TextComparisonInputElement textComparisonInputElement = findLastTextComparisonInputElementOrNull(this.buildingTask.getGameTaskContent());

        if(textComparisonInputElement == null)
            return this;

        InputTip<String> tip = new InputTip<>();
        tip.setInputValueAssigned(false);
        tip.setTipMessage(generalTipMessage);
        textComparisonInputElement.getInputTips().add(tip);

        return this;
    }

    public GameTaskBuilder withTipsForPreviousTextInputElement(String[] generalTipMessages)
    {
        TextComparisonInputElement textComparisonInputElement = findLastTextComparisonInputElementOrNull(this.buildingTask.getGameTaskContent());

        if(textComparisonInputElement == null)
            return this;

        for(int i = 0; i < generalTipMessages.length; i++) {
            InputTip<String> tip = new InputTip<>();
            tip.setInputValueAssigned(false);
            tip.setTipMessage(generalTipMessages[i]);
            textComparisonInputElement.getInputTips().add(tip);
        }

        return this;
    }

    public GameTaskBuilder withLocationComparisonElement(String commitMessage, float latitude, float longitude, float acceptanceDistanceMeters, InputContentElement.OnInputCommittedListener<LocationData> listener)
    {
        GameTaskContent content = this.buildingTask.getGameTaskContent();
        if(content == null)
            this.buildingTask.setGameTaskContent((content = new GameTaskContent()));
        LocationComparisonInputElement comparisonElement = new LocationComparisonInputElement(new LocationData(latitude, longitude), acceptanceDistanceMeters, commitMessage);

        if(listener != null)
            comparisonElement.addOnInputCommittedListener(listener);

        content.getContentElements().add(comparisonElement);
        return this;
    }

    public GameTaskBuilder withCustomContentElement(GameTaskContentElement element)
    {
        GameTaskContent content = this.buildingTask.getGameTaskContent();
        if(content == null)
            this.buildingTask.setGameTaskContent((content = new GameTaskContent()));
        content.getContentElements().add(element);
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

    public static void addLocationInputListener(GameTaskData data, InputContentElement.OnInputCommittedListener<LocationData> listener)
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

    public static void removeLocationInputListener(GameTaskData data, InputContentElement.OnInputCommittedListener<LocationData> listener)
    {
        for(GameTaskContentElement element : data.getGameTaskContent().getContentElements())
        {
            if(element instanceof LocationComparisonInputElement)
            {
                LocationComparisonInputElement locationComparisonElement = (LocationComparisonInputElement)element;
                locationComparisonElement.removeOnInputCommittedListener(listener);
            }
        }
    }

    public static void addTextInputComparisonListener(GameTaskData data, InputContentElement.OnInputCommittedListener<String> listener)
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

    public static void removeTextInputComparisonListener(GameTaskData data, InputContentElement.OnInputCommittedListener<String> listener)
    {
        for(GameTaskContentElement element : data.getGameTaskContent().getContentElements())
        {
            if(element instanceof TextComparisonInputElement)
            {
                TextComparisonInputElement textComparisonInputElement = (TextComparisonInputElement)element;
                textComparisonInputElement.removeOnInputCommittedListener(listener);
            }
        }
    }

    public static void addAudioPlayers(GameTaskData data, AudioPlayer.Provider audioPlayerProvider)
    {
        for(GameTaskContentElement element : data.getGameTaskContent().getContentElements())
        {
            if(element instanceof DisplayAudioPlayerElement)
            {
                DisplayAudioPlayerElement typedElement = (DisplayAudioPlayerElement)element;
                AudioPlayer player = audioPlayerProvider.provideAudioPlayer(typedElement.getAudioFileResourceId());
                if(player == null)
                    player = audioPlayerProvider.provideAudioPlayer(typedElement.getAudioFileUriString());

                typedElement.useAudioPlayer(player);
            }
        }
    }

    private TextComparisonInputElement findLastTextComparisonInputElementOrNull(GameTaskContent gameTaskContent)
    {
        for(int i = gameTaskContent.getContentElements().size() - 1; i >= 0; i--)
        {
            GameTaskContentElement element = gameTaskContent.getContentElements().get(i);
            if(element instanceof TextComparisonInputElement)
                return (TextComparisonInputElement) element;
        }

        return null;
    }
}
