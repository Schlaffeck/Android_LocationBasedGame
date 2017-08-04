package com.slamcode.testgame;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.slamcode.locationbasedgamelayout.view.OnAdapterItemClickListener;
import com.slamcode.locationbasedgamelayout.view.binding.BindableTasksListRecyclerViewAdapter;
import com.slamcode.locationbasedgamelib.model.GameTaskData;
import com.slamcode.locationbasedgamelib.persistence.PersistenceContext;
import com.slamcode.locationbasedgamelib.view.ContentLayoutProvider;
import com.slamcode.testgame.app.ServiceNames;
import com.slamcode.testgame.app.ServiceRegistryAppCompatActivity;

public class GameTasksListActivity extends ServiceRegistryAppCompatActivity  {

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
        ((PersistenceContext)this.getServiceRegistryApplication().getRegistry().provideService(ServiceNames.PERSISTENCE_CONTEXT)).persist();
    }

    private void goToGameTaskContent(GameTaskData gameTask)
    {
        Intent intent = new Intent(this, GameTaskContentActivity.class);
        intent.putExtra(GameTaskData.ID_FIELD_NAME, gameTask.getId());
        this.startActivity(intent);
    }

    private void setupTasksList()
    {
        final PersistenceContext persistenceContext
                = (PersistenceContext) this.getServiceRegistryApplication().getRegistry().provideService(ServiceNames.PERSISTENCE_CONTEXT);

        RecyclerView recyclerView = (RecyclerView) this.findViewById(R.id.testGame_tasksList_recyclerView);
        BindableTasksListRecyclerViewAdapter adapter = new BindableTasksListRecyclerViewAdapter(persistenceContext.getData().getGameTasks(),
                (ContentLayoutProvider) this.getServiceRegistryApplication().getRegistry().provideService(ServiceNames.CONTENT_LAYOUT_PROVIDER));
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
