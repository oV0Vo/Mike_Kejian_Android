package dataType.course.question;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by violetMoon on 2015/9/18.
 */
public abstract class BasicQuestion implements Serializable{
    private String questionId;
    private String courseId;
    private String authorId;
    //这个属性有点多余
    private QuestionType questionType;
    private String content;
    private Date questionDate;
    private boolean IJoined;

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public QuestionType getQuestionType() {
        return questionType;
    }

    protected void setQuestionType(QuestionType questionType) {
        this.questionType = questionType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getQuestionDate() {
        return questionDate;
    }

    public void setQuestionDate(Date questionDate) {
        this.questionDate = questionDate;
    }

    public boolean IJoined() {
        return IJoined;
    }

    public void setJoined(boolean IJoined) {
        this.IJoined = IJoined;
    }
}
