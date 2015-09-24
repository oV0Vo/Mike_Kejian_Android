package model.course.question;

import java.util.ArrayList;

/**
 * Created by violetMoon on 2015/9/24.
 */
public class MultiChoiceQuestion extends ChoiceQuestion{
    private ArrayList<Integer> correctChoices;

    public ArrayList<Integer> getCorrectChoices() {
        return correctChoices;
    }

    public void setCorrectChoices(ArrayList<Integer> correctChoices) {
        this.correctChoices = correctChoices;
    }

}
