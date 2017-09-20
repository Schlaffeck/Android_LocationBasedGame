package com.slamcode.testgame;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.location.Location;
import android.location.LocationListener;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
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
import com.slamcode.testgame.messaging.sms.SmsMessagingService;

import java.util.Locale;

public class GameTaskContentActivity extends ServiceRegistryAppCompatActivity implements AudioPlayer.Provider{

    private GameTaskData taskData;
    private ContentLayoutProvider layoutProvider;
    private InputContentElement.OnInputCommittedListener<LocationData> locationDataOnInputCommittedListener;
    private InputContentElement.OnInputCommittedListener<String> textOnInputCommittedListener;
    private LocationTracker locationTracker;
    private PersistenceContext<TestGameDataBundle> persistenceContext;
    private GameTaskData.StatusChangedListener taskStatusChangedListener;
    private SmsMessagingService smsMessagingService;
    private ViewDataBinding taskContentViewBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_task_content);

        this.layoutProvider = (ContentLayoutProvider) this.getServiceRegistryApplication().getRegistry().provideService(ServiceNames.CONTENT_LAYOUT_PROVIDER);
        this.locationTracker = provideServiceFromRegistry(ServiceNames.LOCATION_TRACKER);
        this.persistenceContext = provideServiceFromRegistry(ServiceNames.PERSISTENCE_CONTEXT);
        this.smsMessagingService = provideServiceFromRegistry(ServiceNames.SMS_MESSAGING_SERVICE);

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
                    showSimpleMessageDialog(null, "Dotarłaś, super!");
                else
                    if(locationComparisonResult.isCurrentLocationAvailable())
                    showSimpleMessageDialog(null, String.format(Locale.getDefault(), "To jeszcze nie to miejsce. Jestes jakieś %f metrów od celu.", locationComparisonResult.getDistanceFromTargetMeters()));
                else
                    showSimpleMessageDialog(null, "Brak lokalizacji. Sprawdź GPS w telefonie i spróbuj jeszcze raz.");
            }
        };

        this.textOnInputCommittedListener = new InputContentElement.OnInputCommittedListener<String>() {
            @Override
            public void inputCommitting(InputContentElement<String> element, InputCommitParameters<String> parameters) {

            }

            @Override
            public void inputCommitted(InputContentElement<String> element, InputResult result) {
                if(result.isInputCorrect())
                    showSimpleMessageDialog(null, "Odpowiedź poprawna :)");
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
                if(newStatus == GameTaskStatus.TriesThresholdReached) {
                    taskContentViewBinding.setVariable(Bindings.VIEW_MODEL_BINDING_VARIABLE_ID, taskData);
                    showSimpleMessageDialog(null, "Coś Ci nie idzie. Sprawdź zadanie pomocnicze niżej");
                }
                else if(newStatus == GameTaskStatus.Success) {
                    sendSmsMessageToDefaultNumber(String.format(Locale.ENGLISH, "Done task %d: %s", taskData.getId(), taskData.getGameTaskHeader().getHeaderTitle()));
                    showSimpleMessageDialog(null, "Udało się, czas na kolejne zadanie :)", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            startNextTaskActivity();
                        }
                    });
                }
            }
        };
    }

    private void startNextTaskActivity() {

        this.finish();
//        int currentIndex = persistenceContext.getData().getGameTasks().indexOf(this.taskData);
//        if(currentIndex != persistenceContext.getData().getPlaceList().size() -1) {
//
//            GameTaskData nextTask = persistenceContext.getData().getGameTasks().get(currentIndex + 1);
//            Intent intent = new Intent(this, GameTaskContentActivity.class);
//            intent.putExtra(GameTaskData.ID_FIELD_NAME, nextTask.getId());
//            this.startActivity(intent);
//        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        int taskId = this.getIntent().getIntExtra(GameTaskData.ID_FIELD_NAME, 0);

        for(int i = 0; i < persistenceContext.getData().getGameTasks().size() && this.taskData == null; i++)
        {
            GameTaskData data = persistenceContext.getData().getGameTasks().get(i);
            if(data.getId() == taskId)
                this.taskData = data;
        }

        if(this.taskData != null) {
            GameTaskBuilder.addLocationInputListener(this.taskData, this.locationDataOnInputCommittedListener);
            GameTaskBuilder.addTextInputComparisonListener(this.taskData, this.textOnInputCommittedListener);
            GameTaskBuilder.addAudioPlayers(this.taskData, this);
            this.taskData.addStatusChangedListener(this.taskStatusChangedListener);
        }

        ViewGroup mainContent = (ViewGroup) this.findViewById(android.R.id.content);
        if(mainContent != null)
        {
            this.taskContentViewBinding = DataBindingUtil.inflate(this.getLayoutInflater(), this.layoutProvider.getGameTaskDataLayoutId(), mainContent, true);
            this.taskContentViewBinding.setVariable(Bindings.VIEW_MODEL_BINDING_VARIABLE_ID, this.taskData);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(this.taskData != null)
        {
            GameTaskBuilder.removeLocationInputListener(this.taskData, this.locationDataOnInputCommittedListener);
            GameTaskBuilder.removeTextInputComparisonListener(this.taskData, this.textOnInputCommittedListener);
            this.taskData.removeStatusChangedListener(this.taskStatusChangedListener);
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
       this.showSimpleMessageDialog(title, message, null);
    }

    private void showSimpleMessageDialog(String title, String message, DialogInterface.OnClickListener onClickListener)
    {
        new AlertDialog.Builder(getServiceRegistryApplication().getCurrentActivity())
                .setMessage(message)
                .setTitle(title)
                .setPositiveButton("OK", onClickListener != null ?
                        onClickListener :
                        new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create().show();
    }

    private void sendSmsMessageToDefaultNumber(String message)
    {
        SmsMessagingService.SmsMessageParameters smsMessage = new SmsMessagingService.SmsMessageParameters();
        smsMessage.setPhoneNo((String)provideServiceFromRegistry(ServiceNames.SMS_MESSAGE_PHONE_NO));
        smsMessage.setMessageContent(message);
        this.smsMessagingService.sendMessage(smsMessage);
    }
}
