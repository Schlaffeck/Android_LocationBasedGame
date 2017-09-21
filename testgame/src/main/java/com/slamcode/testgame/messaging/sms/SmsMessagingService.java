package com.slamcode.testgame.messaging.sms;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.telephony.SmsManager;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.widget.Toast;

import com.slamcode.testgame.messaging.MessagingService;

import java.util.ArrayList;
import java.util.List;

/**
 * Service sending messages via sms
 */

public final class SmsMessagingService implements MessagingService<SmsMessagingService.SmsMessageParameters> {

    private final Context applicationContext;

    public SmsMessagingService(Context applicationContext)
    {
        this.applicationContext = applicationContext;
    }

    @Override
    public MessageSendResult sendMessage(SmsMessageParameters parameters) {
//        SmsManager.getDefault().sendTextMessage(parameters.getPhoneNo(), null, parameters.getMessageContent(), null, null);
        String SENT = "SMS_SENT";
        String DELIVERED = "SMS_DELIVERED";

        PendingIntent sentPI = PendingIntent.getBroadcast(this.applicationContext, 0,
                new Intent(SENT), 0);

        PendingIntent deliveredPI = PendingIntent.getBroadcast(this.applicationContext, 0,
                new Intent(DELIVERED), 0);

//        //---when the SMS has been sent---
//        this.applicationContext.registerReceiver(new BroadcastReceiver(){
//            @Override
//            public void onReceive(Context arg0, Intent arg1) {
//                switch (getResultCode())
//                {
//                    case Activity.RESULT_OK:
//                        Toast.makeText(applicationContext, "SMS sent",
//                                Toast.LENGTH_SHORT).show();
//                        break;
//                    case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
//                        Toast.makeText(applicationContext, "Generic failure",
//                                Toast.LENGTH_SHORT).show();
//                        break;
//                    case SmsManager.RESULT_ERROR_NO_SERVICE:
//                        Toast.makeText(applicationContext, "No service",
//                                Toast.LENGTH_SHORT).show();
//                        break;
//                    case SmsManager.RESULT_ERROR_NULL_PDU:
//                        Toast.makeText(applicationContext, "Null PDU",
//                                Toast.LENGTH_SHORT).show();
//                        break;
//                    case SmsManager.RESULT_ERROR_RADIO_OFF:
//                        Toast.makeText(applicationContext, "Radio off",
//                                Toast.LENGTH_SHORT).show();
//                        break;
//                }
//            }
//        }, new IntentFilter(SENT));
//
//        //---when the SMS has been delivered---
//        this.applicationContext.registerReceiver(new BroadcastReceiver(){
//            @Override
//            public void onReceive(Context arg0, Intent arg1) {
//                switch (getResultCode())
//                {
//                    case Activity.RESULT_OK:
//                        Toast.makeText(applicationContext, "SMS delivered",
//                                Toast.LENGTH_SHORT).show();
//                        break;
//                    case Activity.RESULT_CANCELED:
//                        Toast.makeText(applicationContext, "SMS not delivered",
//                                Toast.LENGTH_SHORT).show();
//                        break;
//                }
//            }
//        }, new IntentFilter(DELIVERED));

        SmsManager sms = SmsManager.getDefault();
        if(Build.VERSION.SDK_INT >= 22)
        {
            sms = SmsManager.getSmsManagerForSubscriptionId(0);
        }
        sms.sendTextMessage(parameters.getPhoneNo(), null, parameters.getMessageContent(), sentPI, deliveredPI);

        return new MessageSendResult(MessageSendResult.RESULT_SENT);
    }

    public static class SmsMessageParameters extends MessagingService.MessageParameters
    {
        private String phoneNo;

        public String getPhoneNo() {
            return phoneNo;
        }

        public void setPhoneNo(String phoneNo) {
            this.phoneNo = phoneNo;
        }
    }
}
