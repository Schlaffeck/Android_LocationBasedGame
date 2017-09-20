package com.slamcode.testgame;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.slamcode.locationbasedgamelayout.view.OnAdapterItemClickListener;
import com.slamcode.locationbasedgamelayout.view.binding.BindableTasksListRecyclerViewAdapter;
import com.slamcode.locationbasedgamelib.model.GameTaskData;
import com.slamcode.locationbasedgamelib.model.GameTaskStatus;
import com.slamcode.locationbasedgamelib.persistence.PersistenceContext;
import com.slamcode.locationbasedgamelib.view.ContentLayoutProvider;
import com.slamcode.testgame.app.ServiceNames;
import com.slamcode.testgame.app.ServiceRegistryAppCompatActivity;
import com.slamcode.testgame.data.TestGameDataBundle;
import com.slamcode.testgame.settings.AppSettingsManager;

public class GameTasksListActivity extends ServiceRegistryAppCompatActivity  {

    private AppSettingsManager appSettingsManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_tasks_list);

        this.appSettingsManager = provideServiceFromRegistry(ServiceNames.APP_SETTINGS_MANAGER);
        setupButtons();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!this.appSettingsManager.wasInfoDialogShown()) {
            this.appSettingsManager.setWasInfoDialogShown(true);
            this.showInfoDialog();
        }
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

    private void setupButtons() {
        FloatingActionButton showInfoButton = (FloatingActionButton)this.findViewById(R.id.gameTask_listItem_showInfo_button);
        showInfoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showInfoDialog();
            }
        });
    }

    private void showInfoDialog()
    {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.game_info_screen_title)
                .setMessage(R.string.game_info_screen_message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void showSimpleDialog(String title, String message)
    {
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .show();
    }

    private void setupTasksList()
    {
        final PersistenceContext<TestGameDataBundle> persistenceContext
                = provideServiceFromRegistry(ServiceNames.PERSISTENCE_CONTEXT);

        RecyclerView recyclerView = (RecyclerView) this.findViewById(R.id.testGame_tasksList_recyclerView);
        BindableTasksListRecyclerViewAdapter adapter = new BindableTasksListRecyclerViewAdapter(persistenceContext.getData().getGameTasks(),
                (ContentLayoutProvider) this.getServiceRegistryApplication().getRegistry().provideService(ServiceNames.CONTENT_LAYOUT_PROVIDER));
        adapter.addOnAdapterItemClickListener(new OnAdapterItemClickListener() {
            @Override
            public void onItemClick(RecyclerView.Adapter adapter, View itemView, int itemPosition) {
                GameTaskData previousTask = itemPosition > 0 ? persistenceContext.getData().getGameTasks().get(itemPosition -1) : null;
                GameTaskData thisTask = persistenceContext.getData().getGameTasks().get(itemPosition);

                if(previousTask != null  && previousTask.getStatus() != GameTaskStatus.Success
                        && thisTask.getStatus() == GameTaskStatus.NotStarted)
                {
                   showSimpleDialog("Najpierw zrób poprzednie zadanie", "Trzeba wszystkie zadania zrobić po kolei ;)!");
                }
                else {
                    // move to details activity
                    goToGameTaskContent(thisTask);
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }
}
