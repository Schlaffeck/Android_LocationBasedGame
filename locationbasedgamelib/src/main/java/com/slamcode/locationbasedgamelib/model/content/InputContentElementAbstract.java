package com.slamcode.locationbasedgamelib.model.content;

import com.slamcode.locationbasedgamelib.model.InputCommitParameters;
import com.slamcode.locationbasedgamelib.model.InputContentElement;
import com.slamcode.locationbasedgamelib.model.InputResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by smoriak on 04/08/2017.
 */

public abstract class InputContentElementAbstract<InputValue> implements InputContentElement<InputValue> {

    private transient List<OnInputCommittedListener<InputValue>> listeners = new ArrayList<>();

    @Override
    public void onInputCommitting(InputCommitParameters<InputValue> parameters) {
        this.validateListeners();
        for (OnInputCommittedListener listener : this.listeners) {
            listener.inputCommitting(this, parameters);
        }
    }

    @Override
    public void onInputCommitted(InputResult result) {
        this.validateListeners();
        for (OnInputCommittedListener listener : this.listeners) {
            listener.inputCommitted(this, result);
        }
    }

    @Override
    public void addOnInputCommittedListener(OnInputCommittedListener<InputValue> listener) {
        this.validateListeners();
        this.listeners.add(listener);
    }

    @Override
    public void removeOnInputCommittedListener(OnInputCommittedListener<InputValue> listener) {
        this.validateListeners();
        this.listeners.remove(listener);
    }

    @Override
    public void clearOnInputCommittedListeners() {

        this.validateListeners();
        this.listeners.clear();
    }

    private void validateListeners()
    {
        if(this.listeners == null)
            this.listeners = new ArrayList<>();
    }
}
