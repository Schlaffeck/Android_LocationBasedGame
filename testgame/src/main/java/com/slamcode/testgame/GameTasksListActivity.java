package com.slamcode.testgame;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.slamcode.locationbasedgamelayout.view.GameTaskContentSimpleLayoutProvider;
import com.slamcode.locationbasedgamelayout.view.OnAdapterItemClickListener;
import com.slamcode.locationbasedgamelayout.view.binding.BindableTasksListRecyclerViewAdapter;
import com.slamcode.locationbasedgamelib.model.GameTaskData;
import com.slamcode.locationbasedgamelib.model.builder.GameTaskBuilder;

import java.util.Arrays;

public class GameTasksListActivity extends AppCompatActivity {

    private GameTaskData[] tasksList    ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_tasks_list);
    }

    @Override
    protected void onStart() {
        super.onStart();
        this.setupTasksList();
    }

    private void goToGameTaskContent(GameTaskData gameTask)
    {
        Intent intent = new Intent(this, GameTaskContentActivity.class);
        this.startActivity(intent);
    }

    private void setupTasksList()
    {
        if(this.tasksList != null)
            return;
        this.tasksList = new GameTaskData[]
                {
                        new GameTaskBuilder().withTitle("Task 1").withTextElement("Test text message").getTask(),
                        new GameTaskBuilder().withTitle("Task 2").withTextElement("Test text message number 2\nWith new line").getTask(),
                        new GameTaskBuilder().withTitle("Task 3").withTextElement("Test text message number 2\nWith new line\nAnd input")
                                .withTextInputComparisonElement("Check", "test", "game").getTask(),
                };
        RecyclerView recyclerView = (RecyclerView) this.findViewById(R.id.testGame_tasksList_recyclerView);
        BindableTasksListRecyclerViewAdapter adapter = new BindableTasksListRecyclerViewAdapter(Arrays.asList(this.tasksList), new GameTaskContentSimpleLayoutProvider());
        adapter.addOnAdapterItemClickListener(new OnAdapterItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.Adapter adapter, View itemView, int itemPosition) {
                // move to details activity
                goToGameTaskContent(null);
            }
        });
        recyclerView.setAdapter(adapter);
    }
}
