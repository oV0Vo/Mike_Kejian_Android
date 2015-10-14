package com.kejian.mike.mike_kejian_android.dataType.pushMessage;

/**
 * Created by violetMoon on 2015/10/12.
 */
public class ReplyPraisePushMessage extends PushMessage{

    private String replyId;

    private String replyName;

    public String getReplyId() {
        return replyId;
    }

    public void setReplyId(String replyId) {
        this.replyId = replyId;
    }

    public String getReplyName() {
        return replyName;
    }

    public void setReplyName(String replyName) {
        this.replyName = replyName;
    }
}
