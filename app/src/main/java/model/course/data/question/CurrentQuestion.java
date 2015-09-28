package model.course.data.question;

/**
 * Created by violetMoon on 2015/9/25.
 */
public class CurrentQuestion {
    private BasicQuestion question;
    private long leftMills;

    public CurrentQuestion() {

    }

    public CurrentQuestion(BasicQuestion question, int leftMills) {
        this.question = question;
        this.leftMills = leftMills;
    }

    public BasicQuestion getQuestion() {
        return question;
    }

    public void setQuestion(BasicQuestion question) {
        this.question = question;
    }

    public long getLeftMills() {
        return leftMills;
    }

    public void setLeftMills(long leftMills) {
        this.leftMills = leftMills;
    }

}
