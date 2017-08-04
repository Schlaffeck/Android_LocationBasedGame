package com.slamcode.testgame;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Toast;

import com.slamcode.locationbasedgamelayout.view.binding.Bindings;
import com.slamcode.locationbasedgamelib.location.LocationTracker;
import com.slamcode.locationbasedgamelib.model.*;
import com.slamcode.locationbasedgamelib.model.builder.*;
import com.slamcode.locationbasedgamelib.model.content.LocationComparisonInputElement;
import com.slamcode.locationbasedgamelib.persistence.PersistenceContext;
import com.slamcode.locationbasedgamelib.view.ContentLayoutProvider;
import com.slamcode.testgame.app.ServiceNames;
import com.slamcode.testgame.app.ServiceRegistryAppCompatActivity;

public class GameTaskContentActivity extends ServiceRegistryAppCompatActivity{

    private GameTaskData sampleGameTask;
    private ContentLayoutProvider layoutProvider;
    private InputContent.OnInputCommittedListener<LocationData> locationDataOnInputCommittedListener;
    private InputContent.OnInputCommittedListener<String> textOnInputCommittedListener;
    private LocationTracker locationTracker;
    private PersistenceContext persistenceContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_task_content);

        this.layoutProvider = (ContentLayoutProvider) this.getServiceRegistryApplication().getRegistry().provideService(ServiceNames.CONTENT_LAYOUT_PROVIDER);
        this.locationTracker = (LocationTracker) this.getServiceRegistryApplication().getRegistry().provideService(ServiceNames.LOCATION_TRACKER);
        this.persistenceContext = (PersistenceContext) this.getServiceRegistryApplication().getRegistry().provideService(ServiceNames.PERSISTENCE_CONTEXT);

        this.locationDataOnInputCommittedListener = new InputContent.OnInputCommittedListener<LocationData>() {
            @Override
            public void inputCommitting(InputCommitParameters<LocationData> parameters) {
                // intercept location data
                parameters.setValue(locationTracker.getLocationData());
            }

            @Override
            public void inputCommitted(InputResult result) {
                LocationComparisonInputElement.LocationComparisonResult locationComparisonResult
                        = (LocationComparisonInputElement.LocationComparisonResult)result;
                if(result.isInputCorrect())
                    Toast.makeText(getApplicationContext(), "You made it, great!", Toast.LENGTH_SHORT).show();
                else
                    if(locationComparisonResult.isCurrentLocationAvailable())
                    Toast.makeText(getApplicationContext(),
                            String.format("Not quite there yet. You are about %f meters from the target", locationComparisonResult.getDistanceFromTargetMeters()),
                            Toast.LENGTH_LONG).show();
                else
                        Toast.makeText(getApplicationContext(),
                                "No location available. Check your GPS settings and try again",
                                Toast.LENGTH_LONG).show();
            }
        };

        this.textOnInputCommittedListener = new InputContent.OnInputCommittedListener<String>() {
            @Override
            public void inputCommitting(InputCommitParameters<String> parameters) {

            }

            @Override
            public void inputCommitted(InputResult result) {
                if(result.isInputCorrect())
                    Toast.makeText(getApplicationContext(), "Answer is correct :)", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(getApplicationContext(), "Incorrect answer, try again", Toast.LENGTH_LONG).show();
            }
        };
    }

    @Override
    protected void onStart() {
        super.onStart();
        int taskId = this.getIntent().getIntExtra(GameTaskData.ID_FIELD_NAME, 0);


        for(int i =0; i < persistenceContext.getData().getGameTasks().size() && this.sampleGameTask == null; i++)
        {
            GameTaskData data = persistenceContext.getData().getGameTasks().get(i);
            if(data.getId() == taskId)
                this.sampleGameTask = data;
        }

        if(this.sampleGameTask != null)
            GameTaskBuilder.addLocationInputListener(this.sampleGameTask, this.locationDataOnInputCommittedListener);
            GameTaskBuilder.addTextInputComparisonListener(this.sampleGameTask, this.textOnInputCommittedListener);

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
        this.persistenceContext.persist();
    }

}
