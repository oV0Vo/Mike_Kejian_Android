package com.kejian.mike.mike_kejian_android.dataType.course.question;

import java.util.List;

/**questionId(String), totalAnswerNum(int), correctAnswerNum(int), choiceDistribute(List<int>)
 * Created by violetMoon on 2015/10/6.
 */
public class QuestionStats {

    private String questionId;

    private int totalAnswerNum;

    private int correctAnswerNum;

    private List<Integer> choiceDistribute;

    public String getQuestionId() {
        return questionId;
    }

    public void setQuestionId(String questionId) {
        this.questionId = questionId;
    }

    public int getTotalAnswerNum() {
        return totalAnswerNum;
    }

    public void setTotalAnswerNum(int totalAnswerNum) {
        this.totalAnswerNum = totalAnswerNum;
    }

    public int getCorrectAnswerNum() {
        return correctAnswerNum;
    }

    public void setCorrectAnswerNum(int correctAnswerNum) {
        this.correctAnswerNum = correctAnswerNum;
    }

    public List<Integer> getChoiceDistribute() {
        return choiceDistribute;
    }

    public void setChoiceDistribute(List<Integer> choiceDistribute) {
        this.choiceDistribute = choiceDistribute;
    }

}
