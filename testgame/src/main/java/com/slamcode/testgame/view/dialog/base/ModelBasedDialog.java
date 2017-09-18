package com.slamcode.testgame.view.dialog.base;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

public abstract class ModelBasedDialog<ModelType> extends DialogFragment {

    private View associatedView;

    private ModelType model;

    private boolean confirmed;

    private ModelBasedDialog.DialogStateChangedListener dialogStateChangedListener;

    public View getAssociatedView()
    {
        return this.associatedView;
    }

    public ModelType getModel() {
        return model;
    }

    public void setModel(ModelType model) {
        this.model = model;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    protected void setConfirmed(boolean newValue)
    {
        this.confirmed = newValue;
    }

    public void setDialogStateChangedListener(ModelBasedDialog.DialogStateChangedListener dialogStateChangedListener) {
        this.dialogStateChangedListener = dialogStateChangedListener;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstance)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());

        LayoutInflater inflater = this.getActivity().getLayoutInflater();

        if(this.associatedView == null)
        {
            this.associatedView = this.initializeView(inflater);
        }

        builder.setView(this.associatedView);
        return builder.create();
    }

    protected abstract View initializeView(LayoutInflater inflater);

    public void cancelChanges()
    {
        this.confirmed = false;
        this.getDialog().cancel();
        this.onDialogClosed();
    }

    public void commitChanges()
    {
        this.setConfirmed(true);
        this.getDialog().dismiss();
        this.onDialogClosed();
    }

    protected void onDialogClosed()
    {
        if(this.dialogStateChangedListener != null)
        {
            this.dialogStateChangedListener.onDialogClosed(this.confirmed);
        }
    }

    public interface DialogStateChangedListener
    {
        void onDialogClosed(boolean confirmed);
    }
}
