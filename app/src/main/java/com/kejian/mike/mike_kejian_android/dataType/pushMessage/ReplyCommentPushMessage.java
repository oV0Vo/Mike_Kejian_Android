package com.kejian.mike.mike_kejian_android.dataType.pushMessage;

/**
 * Created by violetMoon on 2015/10/12.
 */
public class ReplyCommentPushMessage extends PushMessage{

    private String commentId;

    private String commentTitle;

    private String commentContent;

    private String quesitonId;

    private String questionName;

    public String getCommentId() {
        return commentId;
    }

    public void setCommentId(String commentId) {
        this.commentId = commentId;
    }

    public String getCommentTitle() {
        return commentTitle;
    }

    public void setCommentTitle(String commentTitle) {
        this.commentTitle = commentTitle;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public String getQuesitonId() {
        return quesitonId;
    }

    public void setQuesitonId(String quesitonId) {
        this.quesitonId = quesitonId;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }
}
