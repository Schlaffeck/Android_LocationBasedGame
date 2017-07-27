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
import com.slamcode.locationbasedgamelib.persistence.PersistenceContext;
import com.slamcode.locationbasedgamelib.view.ContentLayoutProvider;
import com.slamcode.testgame.data.PersistenceContextContainer;

import java.util.List;

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
        int taskId = this.getIntent().getIntExtra(GameTaskData.ID_FIELD_NAME, 0);

        for(int i =0; i < PersistenceContextContainer.getCurrentContext().getData().getGameTasks().size() && this.sampleGameTask == null; i++)
        {
            GameTaskData data = PersistenceContextContainer.getCurrentContext().getData().getGameTasks().get(i);
            if(data.getId() == taskId)
                this.sampleGameTask = data;
        }

        ViewGroup mainContent = (ViewGroup) this.findViewById(android.R.id.content);
        if(mainContent != null)
        {
            ViewDataBinding taskContentView = DataBindingUtil.inflate(this.getLayoutInflater(), this.layoutProvider.getGameTaskDataLayoutId(), mainContent, true);
            taskContentView.setVariable(Bindings.VIEW_MODEL_BINDING_VARIABLE_ID, this.sampleGameTask);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        PersistenceContextContainer.getCurrentContext().persist();
    }
}
