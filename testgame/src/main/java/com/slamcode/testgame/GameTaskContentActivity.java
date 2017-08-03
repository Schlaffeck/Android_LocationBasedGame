package com.slamcode.testgame;

import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.slamcode.locationbasedgamelayout.view.GameTaskContentSimpleLayoutProvider;
import com.slamcode.locationbasedgamelayout.view.binding.Bindings;
import com.slamcode.locationbasedgamelib.location.LocationDataProvider;
import com.slamcode.locationbasedgamelib.location.LocationTracker;
import com.slamcode.locationbasedgamelib.location.LocationTrackerConfiguration;
import com.slamcode.locationbasedgamelib.model.*;
import com.slamcode.locationbasedgamelib.model.builder.*;
import com.slamcode.locationbasedgamelib.model.content.LocationComparisonInputElement;
import com.slamcode.locationbasedgamelib.permission.PermissionRequestor;
import com.slamcode.locationbasedgamelib.persistence.PersistenceContext;
import com.slamcode.locationbasedgamelib.view.ContentLayoutProvider;
import com.slamcode.testgame.data.PersistenceContextContainer;

import java.util.ArrayList;
import java.util.List;

public class GameTaskContentActivity extends AppCompatActivity implements PermissionRequestor {

    private GameTaskData sampleGameTask;
    private ContentLayoutProvider layoutProvider;
    private List<RequestListener> requestListeners = new ArrayList<>();
    private InputContent.OnInputCommittedListener<LocationData> locationDataOnInputCommittedListener;
    private InputContent.OnInputCommittedListener<String> textOnInputCommittedListener;
    private LocationTracker locationTracker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_task_content);
        this.layoutProvider = new GameTaskContentSimpleLayoutProvider();
        this.locationTracker = new LocationTracker(this, new LocationTrackerConfiguration(1, 1_000), this);
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

        for(int i =0; i < PersistenceContextContainer.getCurrentContext().getData().getGameTasks().size() && this.sampleGameTask == null; i++)
        {
            GameTaskData data = PersistenceContextContainer.getCurrentContext().getData().getGameTasks().get(i);
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
        PersistenceContextContainer.getCurrentContext().persist();
    }


    @Override
    public void requestPermissions(PermissionRequest request) {
        ActivityCompat.requestPermissions(this, request.getPermissions(), request.getPermissionRequestCode());
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        for (RequestListener listener : this.requestListeners) {
            listener.requestFinished(requestCode, grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED);
        }
    }

    @Override
    public void addRequestListener(RequestListener listener) {
        this.requestListeners.add(listener);
    }

    @Override
    public void removeRequestListener(RequestListener listener) {
        this.requestListeners.remove(listener);
    }

    @Override
    public void clearRequestListeners() {
        this.requestListeners.clear();
    }
}
