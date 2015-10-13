package com.kejian.mike.mike_kejian_android.dataType.course.question;

/**
 * Created by violetMoon on 2015/9/24.
 */
public class SingleChoiceQuestion extends ChoiceQuestion {
    private int correctChoice;

    public SingleChoiceQuestion() {
        setQuestionType(QuestionType.单选题);
    }

    public int getCorrectChoice() {
        return correctChoice;
    }

    public void setCorrectChoice(int correctChoice) {
        this.correctChoice = correctChoice;
    }

}
