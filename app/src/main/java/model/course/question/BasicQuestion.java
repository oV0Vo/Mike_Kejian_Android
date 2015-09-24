package model.course.question;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by violetMoon on 2015/9/18.
 */
public abstract class BasicQuestion {
    private String courseId;
    private String authorId;
    private QuestionType questionType;
    private String content;
    private Date questionDate;
    private boolean iJoined;

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

    public void setQuestionType(QuestionType questionType) {
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

    public boolean isiJoined() {
        return iJoined;
    }

    public void setiJoined(boolean iJoined) {
        this.iJoined = iJoined;
    }
}
