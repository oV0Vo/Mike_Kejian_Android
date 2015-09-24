package model.course.question;

/**
 * Created by violetMoon on 2015/9/24.
 */
public class SingleChoiceQuestion extends ChoiceQuestion {
    private int correctChoice;

    public int getCorrectChoice() {
        return correctChoice;
    }

    public void setCorrectChoice(int correctChoice) {
        this.correctChoice = correctChoice;
    }

}
