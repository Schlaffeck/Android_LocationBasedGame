package com.slamcode.testgame.view.dialog;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.slamcode.testgame.R;
import com.slamcode.testgame.view.dialog.base.ModelBasedDialog;

import java.util.Calendar;

public final class EntryPasswordDialog extends ModelBasedDialog<String> {

    private EditText passwordEditText;
    private Button confirmButton;
    private final String key = "ababa";

    @Override
    protected View initializeView(LayoutInflater inflater) {
        View inflated = inflater.inflate(R.layout.activity_game_tasks_password_dialog, null);
        this.confirmButton = (Button)inflated.findViewById(R.id.entry_password_check_button);
        this.confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendarNow = Calendar.getInstance();
                Calendar toCompare = Calendar.getInstance();
                toCompare.set(2017, 8, 21, 9, 30, 0);
                if(calendarNow.after(toCompare) || key.equals(passwordEditText.getText().toString()))
                    commitChanges();
                else
                    passwordEditText.setError("Złe hasło");
            }
        });
        this.passwordEditText = (EditText)inflated.findViewById(R.id.entry_password_edittext);
        return inflated;
    }
}
