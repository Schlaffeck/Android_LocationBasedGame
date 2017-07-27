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
import com.slamcode.locationbasedgamelib.persistence.PersistenceContext;
import com.slamcode.testgame.data.PersistenceContextContainer;

import java.util.Arrays;

public class GameTasksListActivity extends AppCompatActivity {

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

    @Override
    protected void onStop() {
        super.onStop();
        PersistenceContextContainer.getCurrentContext().persist();
    }

    private void goToGameTaskContent(GameTaskData gameTask)
    {
        Intent intent = new Intent(this, GameTaskContentActivity.class);
        intent.putExtra(GameTaskData.ID_FIELD_NAME, gameTask.getId());
        this.startActivity(intent);
    }

    private void setupTasksList()
    {
        final PersistenceContext persistenceContext = PersistenceContextContainer.initializePersistenceContext(this);

        RecyclerView recyclerView = (RecyclerView) this.findViewById(R.id.testGame_tasksList_recyclerView);
        BindableTasksListRecyclerViewAdapter adapter = new BindableTasksListRecyclerViewAdapter(persistenceContext.getData().getGameTasks(), new GameTaskContentSimpleLayoutProvider());
        adapter.addOnAdapterItemClickListener(new OnAdapterItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.Adapter adapter, View itemView, int itemPosition) {
                // move to details activity
                goToGameTaskContent(persistenceContext.getData().getGameTasks().get(itemPosition));
            }
        });
        recyclerView.setAdapter(adapter);
    }
}
