package com.slamcode.locationbasedgamelib.model;

import com.slamcode.locationbasedgamelib.general.Initializable;
import com.slamcode.locationbasedgamelib.utils.IterableUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents general information about task to do in the game
 */
public class GameTaskData implements Initializable {

    public final static String ID_FIELD_NAME = "id";

    private final int id;

    private GameTaskHeader gameTaskHeader;

    private GameTaskStatus status = GameTaskStatus.NotStarted;

    private GameTaskContent gameTaskContent;

    private GameTaskContent helpTaskContent;

    private int inputTriesThreshold = -1;

    private int inputTriesCounter = 0;

    private transient GameTaskInputCommittedListener inputResultListener = new GameTaskInputCommittedListener();
    private transient HelpTaskInputCommittedListener helpInputResultListener = new HelpTaskInputCommittedListener();

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
        if(this.gameTaskContent == null)
            this.gameTaskContent = new GameTaskContent();

        return this.gameTaskContent;
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

    public GameTaskContent getHelpTaskContent() {
        if(this.helpTaskContent == null)
            this.helpTaskContent = new GameTaskContent();
        return helpTaskContent;
    }

    public void setHelpTaskContent(GameTaskContent helpTaskContent) {
        if(this.helpTaskContent == helpTaskContent)
            return;

        if(this.helpTaskContent != null)
            this.removeInputListeners();

        this.helpTaskContent = helpTaskContent;

        if(this.helpTaskContent != null)
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
        if(this.inputResultListener != null) {
            for (InputContentElement element : this.gameTaskContent.getInputContentElements()) {
                element.removeOnInputCommittedListener(this.inputResultListener);
            }
        }

        if(this.helpInputResultListener != null) {
            for (InputContentElement element : this.helpTaskContent.getInputContentElements()) {
                element.removeOnInputCommittedListener(this.helpInputResultListener);
            }
        }
    }

    private void addInputListeners()
    {
        if(this.gameTaskContent != null) {
            if (this.inputResultListener == null)
                this.inputResultListener = new GameTaskInputCommittedListener();

            this.inputResultListener.taskData = this;

            for (InputContentElement element : this.gameTaskContent.getInputContentElements()) {
                element.addOnInputCommittedListener(this.inputResultListener);
            }
        }
        if(this.helpTaskContent != null) {
            if (this.helpInputResultListener == null)
                this.helpInputResultListener = new HelpTaskInputCommittedListener();

            this.helpInputResultListener.taskData = this;

            for (InputContentElement element : this.helpTaskContent.getInputContentElements()) {
                element.addOnInputCommittedListener(this.helpInputResultListener);
            }
        }
    }

    @Override
    public void initialize() {
        this.addInputListeners();
    }

    public int getInputTriesThreshold() {
        return inputTriesThreshold;
    }

    public void setInputTriesThreshold(int inputTriesThreshold) {
        this.inputTriesThreshold = inputTriesThreshold;
    }

    public int getInputTriesCounter() {
        return inputTriesCounter;
    }

    public void setInputTriesCounter(int inputTriesCounter) {
        this.inputTriesCounter = inputTriesCounter;
    }

    public interface StatusChangedListener
    {
        void onStatusChanged(GameTaskStatus newStatus);
    }

    private class HelpTaskInputCommittedListener implements  InputContentElement.OnInputCommittedListener
    {
        private List<InputContentElement> successfulInputElements;
        private int totalInputElementsNumber;
        private GameTaskData taskData;

        @Override
        public void inputCommitting(InputContentElement element, InputCommitParameters parameters) {

        }

        @Override
        public void inputCommitted(InputContentElement element, InputResult result) {

            if(successfulInputElements == null)
                this.successfulInputElements =  new ArrayList<>();

            if(this.totalInputElementsNumber == 0 && this.taskData.getHelpTaskContent() != null)
                totalInputElementsNumber = IterableUtils.count(getGameTaskContent().getInputContentElements());

            if(result.isInputCorrect())
                this.successfulInputElements.add(element);
            else
                this.successfulInputElements.remove(element);

            if(this.successfulInputElements.size() == this.totalInputElementsNumber)
                this.taskData.setStatus(GameTaskStatus.Success);

            else if(this.successfulInputElements.size() < this.totalInputElementsNumber)
                this.taskData.setStatus(GameTaskStatus.Ongoing);
        }
    }

    private class GameTaskInputCommittedListener implements InputContentElement.OnInputCommittedListener
    {
        private List<InputContentElement> successfulInputElements;
        private int totalInputElementsNumber;
        private GameTaskData taskData;

        @Override
        public void inputCommitting(InputContentElement element, InputCommitParameters parameters) {

        }

        @Override
        public void inputCommitted(InputContentElement element, InputResult result) {

            inputTriesCounter++;

            if(successfulInputElements == null)
                this.successfulInputElements =  new ArrayList<>();

            if(this.totalInputElementsNumber == 0 && this.taskData.getGameTaskContent() != null)
                totalInputElementsNumber = IterableUtils.count(getGameTaskContent().getInputContentElements());

            if(result.isInputCorrect())
                this.successfulInputElements.add(element);
            else
                this.successfulInputElements.remove(element);

            if(this.successfulInputElements.size() == this.totalInputElementsNumber)
                this.taskData.setStatus(GameTaskStatus.Success);

            else if(this.successfulInputElements.size() < this.totalInputElementsNumber)
                this.taskData.setStatus(GameTaskStatus.Ongoing);

            if(inputTriesThreshold > 0 && inputTriesCounter >= inputTriesThreshold
                    && this.taskData.getStatus() != GameTaskStatus.Success
                    && this.taskData.getStatus() != GameTaskStatus.TriesThresholdReached)
                this.taskData.setStatus(GameTaskStatus.TriesThresholdReached);
        }
    }

}
