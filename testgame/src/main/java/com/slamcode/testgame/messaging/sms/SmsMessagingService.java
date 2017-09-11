package com.slamcode.testgame.messaging.sms;

import android.content.Context;
import android.telephony.SmsManager;

import com.slamcode.testgame.messaging.MessagingService;

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
        SmsManager.getDefault().sendTextMessage(parameters.getPhoneNo(), null, parameters.getMessageContent(), null, null);
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
