package com.slamcode.testgame.messaging;

/**
 * Simple abstraction for service sending messages over concrete channel
 */

public interface MessagingService<SpecificMessageParameters extends MessagingService.MessageParameters> {

    MessageSendResult sendMessage(SpecificMessageParameters parameters);

    class MessageParameters{

        private String messageContent;

        public String getMessageContent() {
            return messageContent;
        }

        public void setMessageContent(String messageContent) {
            this.messageContent = messageContent;
        }
    }

    class MessageSendResult
    {
        public static final int RESULT_SENT = 1;
        public static final int RESULT_FAILURE = 111;

        private int resultCode;

        public MessageSendResult(int resultCode) {
            this.resultCode = resultCode;
        }

        public int getResultCode() {
            return resultCode;
        }
    }
}
