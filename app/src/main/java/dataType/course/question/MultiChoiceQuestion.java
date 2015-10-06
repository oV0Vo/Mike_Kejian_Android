package dataType.course.question;

import java.util.ArrayList;

/**
 * Created by violetMoon on 2015/9/24.
 */
public class MultiChoiceQuestion extends ChoiceQuestion{
    private ArrayList<Integer> correctChoices;

    public MultiChoiceQuestion() {
        setQuestionType(QuestionType.多选题);
    }

    public ArrayList<Integer> getCorrectChoices() {
        return correctChoices;
    }

    public void setCorrectChoices(ArrayList<Integer> correctChoices) {
        this.correctChoices = correctChoices;
    }

}
