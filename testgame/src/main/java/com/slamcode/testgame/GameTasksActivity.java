package com.slamcode.testgame;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.slamcode.locationbasedgamelayout.view.GameTaskContentSimpleLayoutProvider;
import com.slamcode.locationbasedgamelayout.view.binding.BindableTasksListRecyclerViewAdapter;
import com.slamcode.locationbasedgamelib.model.GameTaskData;
import com.slamcode.locationbasedgamelib.model.builder.GameTaskBuilder;

import java.util.Arrays;

public class GameTasksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_tasks);
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.setupTasksList();
    }

    private void setupTasksList()
    {
        GameTaskData[] data = new GameTaskData[]
                {
                        new GameTaskBuilder().withTitle("Task 1").withTextElement("Test text message").getTask(),
                        new GameTaskBuilder().withTitle("Task 2").withTextElement("Test text message number 2\nWith new line").getTask(),
                        new GameTaskBuilder().withTitle("Task 3").withTextElement("Test text message number 2\nWith new line\nAnd input")
                                .withTextInputComparisonElement("Check", "test", "game").getTask(),
                };
        RecyclerView recyclerView = (RecyclerView) this.findViewById(R.id.testGame_tasksList_recyclerView);
        recyclerView.setAdapter(new BindableTasksListRecyclerViewAdapter(Arrays.asList(data), new GameTaskContentSimpleLayoutProvider()));
    }
}
