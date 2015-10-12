package dataType.pushMessage;

/**
 * Created by violetMoon on 2015/10/12.
 */
public class IdolQuestionPublishPM extends PushMessage{

    private String questionId;

    private String questionTitle;

    private String questionContent;

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getQuestionTitle() {
        return questionTitle;
    }

    public void setQuestionTitle(String questionTitle) {
        this.questionTitle = questionTitle;
    }

    public String getQuestionContent() {
        return questionContent;
    }

    public void setQuestionContent(String questionContent) {
        this.questionContent = questionContent;
    }

}
