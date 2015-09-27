package model.course.data.question;

import java.util.ArrayList;

/**
 * Created by violetMoon on 2015/9/24.
 */
public class ChoiceQuestion extends BasicQuestion{

    private ArrayList<String> choiceContents;

    public ArrayList<String> getChoiceContents() {
        return choiceContents;
    }

    public void setChoiceContents(ArrayList<String> choiceContents) {
        this.choiceContents = choiceContents;
    }

}
