package dataType.pushMessage;

/**
 * Created by violetMoon on 2015/10/12.
 */
public class ReplyInvitePushMessage extends PushMessage{

    private String questionId;

    private String questionName;

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestionName() {
        return questionName;
    }

    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }
}
