package com.kejian.mike.mike_kejian_android.dataType.pushMessage;

import java.util.Date;

/**
 * Created by violetMoon on 2015/10/12.
 */
public abstract class PushMessage {

    private SenderInfo senderInfo;

    private ReceiverInfo receiverInfo;

    private Date time;

    public SenderInfo getSenderInfo() {
        return senderInfo;
    }

    public void setSenderInfo(SenderInfo senderInfo) {
        this.senderInfo = senderInfo;
    }

    public ReceiverInfo getReceiverInfo() {
        return receiverInfo;
    }

    public void setReceiverInfo(ReceiverInfo receiverInfo) {
        this.receiverInfo = receiverInfo;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

}
