package com.slamcode.locationbasedgamelib.model;

import com.slamcode.locationbasedgamelib.utils.IterableUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents general information about task to do in the game
 */
public class GameTaskData {

    public final static String ID_FIELD_NAME = "id";

    private final int id;

    private GameTaskHeader gameTaskHeader;

    private GameTaskStatus status = GameTaskStatus.NotStarted;

    private GameTaskContent gameTaskContent;

    private GameTaskInputCommittedListener inputResultListener = new GameTaskInputCommittedListener();

    private transient List<StatusChangedListener> statusChangedListeners = new ArrayList<>();

    public GameTaskData(int id)
    {
        this.id = id;
    }

    public GameTaskStatus getStatus() {
        return status;
    }

    public void setStatus(GameTaskStatus status) {
        if(this.status == status)
            return;

        this.status = status;
        this.onStatusChanged();
    }

    public GameTaskHeader getGameTaskHeader() {
        return gameTaskHeader;
    }

    public void setGameTaskHeader(GameTaskHeader gameTaskHeader) {
        this.gameTaskHeader = gameTaskHeader;
    }

    public GameTaskContent getGameTaskContent() {
        return gameTaskContent;
    }

    public void setGameTaskContent(GameTaskContent gameTaskContent) {
        if(this.gameTaskContent == gameTaskContent)
            return;

        if(this.gameTaskContent != null)
            this.removeInputListeners();

        this.gameTaskContent = gameTaskContent;

        if(this.gameTaskContent != null)
            this.addInputListeners();
    }

    public int getId() {
        return id;
    }

    public void addStatusChangedListener(StatusChangedListener listener)
    {
        this.validateListeners();
        this.statusChangedListeners.add(listener);
    }

    public void removeStatusChangedListener(StatusChangedListener listener)
    {
        this.validateListeners();
        this.statusChangedListeners.remove(listener);
    }

    public void clearStatusChangedListeners()
    {
        this.validateListeners();
        this.statusChangedListeners.clear();
    }

    private void validateListeners()
    {
        if(this.statusChangedListeners == null)
            this.statusChangedListeners = new ArrayList<>();
    }

    private void onStatusChanged() {

        this.validateListeners();
        for(StatusChangedListener listener : this.statusChangedListeners)
            listener.onStatusChanged(this.status);
    }

    private void removeInputListeners()
    {
        if(this.inputResultListener == null)
            return;

        for(InputContentElement element : this.gameTaskContent.getInputContentElements())
        {
            element.removeOnInputCommittedListener(this.inputResultListener);
        }
    }

    private void addInputListeners()
    {
        if(this.inputResultListener == null)
            this.inputResultListener = new GameTaskInputCommittedListener();

        for(InputContentElement element : this.gameTaskContent.getInputContentElements())
        {
            element.addOnInputCommittedListener(this.inputResultListener);
        }
    }

    public interface StatusChangedListener
    {
        void onStatusChanged(GameTaskStatus newStatus);
    }

    private class GameTaskInputCommittedListener implements InputContentElement.OnInputCommittedListener
    {
        private List<InputContentElement> successfulInputElements;
        private int totalInputElementsNumber;

        private GameTaskInputCommittedListener()
        {
            if(getGameTaskContent() != null)
            {
                totalInputElementsNumber = IterableUtils.count(getGameTaskContent().getInputContentElements());
            }
        }

        @Override
        public void inputCommitting(InputContentElement element, InputCommitParameters parameters) {

        }

        @Override
        public void inputCommitted(InputContentElement element, InputResult result) {
            if(result.isInputCorrect())
                this.successfulInputElements.add(element);
            else
                this.successfulInputElements.remove(element);

            if(this.successfulInputElements.size() == this.totalInputElementsNumber)
                setStatus(GameTaskStatus.Success);

            else if(this.successfulInputElements.size() < this.totalInputElementsNumber)
                setStatus(GameTaskStatus.Ongoing);
        }
    }

}
