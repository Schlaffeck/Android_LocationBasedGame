package com.slamcode.testgame;

import android.app.AlertDialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.Toast;

import com.slamcode.locationbasedgamelayout.view.binding.Bindings;
import com.slamcode.locationbasedgamelib.location.LocationTracker;
import com.slamcode.locationbasedgamelib.model.*;
import com.slamcode.locationbasedgamelib.model.builder.*;
import com.slamcode.locationbasedgamelib.model.content.LocationComparisonInputElement;
import com.slamcode.locationbasedgamelib.model.content.TextComparisonInputElement;
import com.slamcode.locationbasedgamelib.multimedia.AudioPlayer;
import com.slamcode.locationbasedgamelib.multimedia.MediaServiceAudioPlayer;
import com.slamcode.locationbasedgamelib.persistence.PersistenceContext;
import com.slamcode.locationbasedgamelib.view.ContentLayoutProvider;
import com.slamcode.testgame.app.ServiceNames;
import com.slamcode.testgame.app.ServiceRegistryAppCompatActivity;
import com.slamcode.testgame.data.TestGameDataBundle;

import java.util.Locale;

public class GameTaskContentActivity extends ServiceRegistryAppCompatActivity implements AudioPlayer.Provider{

    private GameTaskData sampleGameTask;
    private ContentLayoutProvider layoutProvider;
    private InputContentElement.OnInputCommittedListener<LocationData> locationDataOnInputCommittedListener;
    private InputContentElement.OnInputCommittedListener<String> textOnInputCommittedListener;
    private LocationTracker locationTracker;
    private PersistenceContext<TestGameDataBundle> persistenceContext;
    private GameTaskData.StatusChangedListener taskStatusChangedListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_task_content);

        this.layoutProvider = (ContentLayoutProvider) this.getServiceRegistryApplication().getRegistry().provideService(ServiceNames.CONTENT_LAYOUT_PROVIDER);
        this.locationTracker = (LocationTracker) this.getServiceRegistryApplication().getRegistry().provideService(ServiceNames.LOCATION_TRACKER);
        this.persistenceContext = (PersistenceContext<TestGameDataBundle>) this.getServiceRegistryApplication().getRegistry().provideService(ServiceNames.PERSISTENCE_CONTEXT);

        this.locationTracker.addLocationListener(new LocationListener() {
            boolean locationDetermined = false;
            @Override
            public void onLocationChanged(Location location) {
                if(location != null && !locationDetermined) {
                    Toast.makeText(getApplicationContext(), String.format("Location changed: %s", location.toString()), Toast.LENGTH_SHORT).show();
                    locationDetermined = true;
                }
                else if(locationDetermined)
                {
                    Toast.makeText(getApplicationContext(), "Location lost", Toast.LENGTH_SHORT).show();
                    locationDetermined = false;
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                Toast.makeText(getApplicationContext(), String.format("'%s' provider status changed to %d", provider, status), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProviderEnabled(String provider) {
                Toast.makeText(getApplicationContext(), String.format("'%s' provider enabled", provider), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProviderDisabled(String provider) {
                Toast.makeText(getApplicationContext(), String.format("'%s' provider disabled", provider), Toast.LENGTH_SHORT).show();
            }
        });

        this.locationDataOnInputCommittedListener = new InputContentElement.OnInputCommittedListener<LocationData>() {
            @Override
            public void inputCommitting(InputContentElement<LocationData> element, InputCommitParameters<LocationData> parameters) {
                // intercept location data
                parameters.setValue(locationTracker.getLocationData());
            }

            @Override
            public void inputCommitted(InputContentElement<LocationData> element, InputResult result) {
                LocationComparisonInputElement.LocationComparisonResult locationComparisonResult
                        = (LocationComparisonInputElement.LocationComparisonResult)result;
                if(result.isInputCorrect())
                    showSimpleMessageDialog(null, "You made it, great!");
                else
                    if(locationComparisonResult.isCurrentLocationAvailable())
                    showSimpleMessageDialog(null, String.format(Locale.getDefault(), "Not quite there yet. You are about %f meters from the target", locationComparisonResult.getDistanceFromTargetMeters()));
                else
                    showSimpleMessageDialog(null, "No location available. Check your GPS settings and try again");
            }
        };

        this.textOnInputCommittedListener = new InputContentElement.OnInputCommittedListener<String>() {
            @Override
            public void inputCommitting(InputContentElement<String> element, InputCommitParameters<String> parameters) {

            }

            @Override
            public void inputCommitted(InputContentElement<String> element, InputResult result) {
                if(result.isInputCorrect())
                    showSimpleMessageDialog(null, "Answer is correct :)");
                else {
                    InputTip tip = TextComparisonInputElement.IgnoreAllComparator.findFirstMatchingTipOrNull(element.getInputTips(), element.getInputValue());
                    if(tip != null)
                        showSimpleMessageDialog(null, tip.getTipMessage());
                    else
                        showSimpleMessageDialog(null, "Incorrect answer, try again");
                }
            }
        };

        this.taskStatusChangedListener = new GameTaskData.StatusChangedListener() {
            @Override
            public void onStatusChanged(GameTaskStatus newStatus) {
                if(newStatus == GameTaskStatus.Success)
                   showSimpleMessageDialog("GREAT", "Task is finished move to the next one :)");
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

        if(this.sampleGameTask != null) {
            GameTaskBuilder.addLocationInputListener(this.sampleGameTask, this.locationDataOnInputCommittedListener);
            GameTaskBuilder.addTextInputComparisonListener(this.sampleGameTask, this.textOnInputCommittedListener);
            GameTaskBuilder.addAudioPlayers(this.sampleGameTask, this);
            this.sampleGameTask.addStatusChangedListener(this.taskStatusChangedListener);
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
        if(this.sampleGameTask != null)
        {
            GameTaskBuilder.removeLocationInputListener(this.sampleGameTask, this.locationDataOnInputCommittedListener);
            GameTaskBuilder.removeTextInputComparisonListener(this.sampleGameTask, this.textOnInputCommittedListener);
            this.sampleGameTask.removeStatusChangedListener(this.taskStatusChangedListener);
        }
        this.persistenceContext.persist();
    }

    @Override
    public AudioPlayer provideAudioPlayer(int audioResourceId) {
        return new MediaServiceAudioPlayer(this, audioResourceId);
    }

    @Override
    public AudioPlayer provideAudioPlayer(String audioFilePathUri) {
        return new MediaServiceAudioPlayer(this, Uri.parse(audioFilePathUri));
    }

    private void showSimpleMessageDialog(String title, String message)
    {
        new AlertDialog.Builder(getServiceRegistryApplication().getCurrentActivity())
                .setMessage(message)
                .setTitle(title)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create().show();
    }
}
