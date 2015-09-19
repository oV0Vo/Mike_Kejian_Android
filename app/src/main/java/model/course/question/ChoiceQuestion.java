package model.course.question;

import java.util.ArrayList;

/**
 * Created by violetMoon on 2015/9/18.
 */
public class ChoiceQuestion extends BasicQuestion {
    private int correctChoice;
    private ArrayList<String> choiceContents;

    public int getCorrectChoice() {
        return correctChoice;
    }

    public void setCorrectChoice(int correctChoice) {
        this.correctChoice = correctChoice;
    }

    public ArrayList<String> getChoiceContents() {
        return choiceContents;
    }

    public void setChoiceContents(ArrayList<String> choiceContents) {
        this.choiceContents = choiceContents;
    }

}
