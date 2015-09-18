package model.course.question;

import java.util.ArrayList;

/**
 * Created by violetMoon on 2015/9/18.
 */
public class QuestionSet {
    private ArrayList<BasicQuestion> currentQuestions;
    private ArrayList<Long> currentQuestionLeftMills;
    private ArrayList<BasicQuestion> historyQuestions;

    public ArrayList<BasicQuestion> getCurrentQuestions() {
        return currentQuestions;
    }

    public void setCurrentQuestions(ArrayList<BasicQuestion> currentQuestions) {
        this.currentQuestions = currentQuestions;
    }

    public ArrayList<Long> getCurrentQuestionLeftMills() {
        return currentQuestionLeftMills;
    }

    public void setCurrentQuestionLeftMills(ArrayList<Long> currentQuestionLeftMills) {
        this.currentQuestionLeftMills = currentQuestionLeftMills;
    }

    public ArrayList<BasicQuestion> getHistoryQuestions() {
        return historyQuestions;
    }

    public void setHistoryQuestions(ArrayList<BasicQuestion> historyQuestions) {
        this.historyQuestions = historyQuestions;
    }
}
