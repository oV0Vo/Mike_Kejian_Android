package model.course.question;

/**
 * Created by violetMoon on 2015/9/25.
 */
public class CurrentQuestion {
    private BasicQuestion question;
    private int leftMills;

    public CurrentQuestion() {

    }

    public CurrentQuestion(BasicQuestion question, int leftMills) {

    }

    public BasicQuestion getQuestion() {
        return question;
    }

    public void setQuestion(BasicQuestion question) {
        this.question = question;
    }

    public int getLeftMills() {
        return leftMills;
    }

    public void setLeftMills(int leftMills) {
        this.leftMills = leftMills;
    }

}
