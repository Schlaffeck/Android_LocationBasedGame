package com.slamcode.testgame;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import com.slamcode.locationbasedgamelayout.view.GameTaskContentSimpleLayoutProvider;
import com.slamcode.locationbasedgamelayout.view.binding.Bindings;
import com.slamcode.locationbasedgamelib.model.*;
import com.slamcode.locationbasedgamelib.model.builder.*;
import com.slamcode.locationbasedgamelib.view.ContentLayoutProvider;

public class GameTaskContentActivity extends AppCompatActivity {

    private GameTaskData sampleGameTask;
    private ContentLayoutProvider layoutProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_task_content);
        this.layoutProvider = new GameTaskContentSimpleLayoutProvider();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(this.sampleGameTask != null)
            return;
        this.sampleGameTask = new GameTaskBuilder()
                .withTitle("Sample task title")
                .withPictureElement(R.drawable.sromamba)
                .withTextElement("Some freaking long text\nWith additional lines\nAnd so on")
                .withTextInputComparisonElement("Check input", "test", "game")
                .getTask();

        ViewGroup mainContent = (ViewGroup) this.findViewById(android.R.id.content);
        if(mainContent != null)
        {
            ViewDataBinding taskContentView = DataBindingUtil.inflate(this.getLayoutInflater(), this.layoutProvider.getGameTaskDataLayoutId(), mainContent, true);
            taskContentView.setVariable(Bindings.VIEW_MODEL_BINDING_VARIABLE_ID, this.sampleGameTask);
        }
    }
}
