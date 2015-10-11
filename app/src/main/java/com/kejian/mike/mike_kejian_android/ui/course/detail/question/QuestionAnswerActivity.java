package com.kejian.mike.mike_kejian_android.ui.course.detail.question;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.kejian.mike.mike_kejian_android.R;

import net.course.CourseQuestionNetService;

import org.json.JSONArray;

import java.util.ArrayList;

import dataType.course.question.BasicQuestion;
import dataType.course.question.CommitAnswerResultMessage;
import dataType.course.question.MultiChoiceQuestion;
import dataType.course.question.QuestionAnswer;
import dataType.course.question.SingleChoiceQuestion;

public class QuestionAnswerActivity extends AppCompatActivity {

    public static final String ARG_QUESTION = "question";

    private BasicQuestion question;

    private RadioGroup singleChoiceGroup;

    private ArrayList<RadioButton> choiceButtons;

    private EditText replyText;

    private TextView answerActionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_answer);

        question = (BasicQuestion) getIntent().getSerializableExtra(ARG_QUESTION);

        String questionContent = question.getContent();
        setQuestionContentView(questionContent);

        switch (question.getQuestionType()) {
            case 单选题:
                setSingleChoiceQuestionView((SingleChoiceQuestion) question);
                break;
            case 多选题:
                setMultiChioceQuestionView((MultiChoiceQuestion) question);
                break;
            case 其他:
                setApplicationQuestionView(question);
                break;
        }

        setAnswerActionText();
    }

    private void setQuestionContentView(String questionContent) {
        TextView contentText = (TextView) findViewById(R.id.question_content_text);
        contentText.setText(questionContent);
    }

    private void setSingleChoiceQuestionView(SingleChoiceQuestion singleChoiceQuestion) {
        choiceButtons = new ArrayList<RadioButton>();
        singleChoiceGroup = new RadioGroup(this);
        singleChoiceGroup.setOrientation(LinearLayout.VERTICAL);

        ArrayList<String> choiceContents = singleChoiceQuestion.getChoiceContents();
        for (int i = 0; i < choiceContents.size(); ++i) {
            RadioButton choiceButton = new RadioButton(this);
            choiceButton.setSingleLine(false);
            choiceButton.setText(Character.toString((char) ('A' + i)) + " " + choiceContents.get(i));
            singleChoiceGroup.addView(choiceButton);
            choiceButtons.add(choiceButton);
        }

        ViewGroup choiceContainer = (ViewGroup) findViewById(R.id.choice_container);
        choiceContainer.addView(singleChoiceGroup);
        choiceContainer.setVisibility(View.VISIBLE);
    }

    private void setMultiChioceQuestionView(MultiChoiceQuestion multiChoiceQuestion) {
        choiceButtons = new ArrayList<RadioButton>();
        ViewGroup choiceContainer = (ViewGroup) findViewById(R.id.choice_container);

        ArrayList<String> choiceContents = multiChoiceQuestion.getChoiceContents();
        for (int i = 0; i < choiceContents.size(); ++i) {
            RadioButton choiceButton = new RadioButton(this);
            choiceButton.setSingleLine(false);
            choiceButton.setText(Character.toString((char) ('A' + i)) + " " + choiceContents.get(i));
            choiceContainer.addView(choiceButton);
        }

        choiceContainer.setVisibility(View.VISIBLE);
    }

    private void setApplicationQuestionView(BasicQuestion question) {
        replyText = (EditText) findViewById(R.id.reply_text);
        replyText.setVisibility(View.VISIBLE);
    }

    private void setAnswerActionText() {
        answerActionText = (TextView) findViewById(R.id.answer_action_text);
        answerActionText.setOnClickListener(new AnswerTextClickListener());
    }

    private class AnswerTextClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            QuestionAnswer answer = new QuestionAnswer();
            String studentNameMock = "黄崇和";
            answer.setStudentName(studentNameMock);
            String idMock = "13125056";
            answer.setStudentId(idMock);
            answer.setQuestionId(question.getQuestionId());
            String questionAnswer = new String();
            switch(question.getQuestionType()) {
                case 单选题:
                    questionAnswer = getSingleChoiceAnswer();
                    break;
                case 多选题:
                    questionAnswer = getMultiChoiceAnswer();
                    break;
                case 其他:
                    questionAnswer = getApplicationAnswer();
                    break;
                default:
                    break;
            }
            answer.setAnswer(questionAnswer);
        }

        private String getSingleChoiceAnswer() {
            String answer = new String();
            for(int i=0; i<choiceButtons.size(); ++i) {
                RadioButton choiceButton = choiceButtons.get(i);
                if(choiceButton.isChecked()) {
                    answer = Character.toString((char)('A' + i));
                    break;
                }
            }
            return answer;
        }

        private String getMultiChoiceAnswer() {
            ArrayList<String> answers = new ArrayList<String>();
            for(int i=0; i<choiceButtons.size(); ++i) {
                RadioButton choiceButton = choiceButtons.get(i);
                if(choiceButton.isChecked())
                    answers.add(Character.toString((char)('A' + i)));
            }
            JSONArray jArray = new JSONArray(answers);
            return jArray.toString();
        }

        private String getApplicationAnswer() {
            return replyText.getText().toString();
        }
    }

    private void updateViewOnNetError() {

    }

    private void updateViewOnCommitSuccess() {

    }

    private void updateViewOnQuestionShutDown() {

    }

    private void updateViewOnQuestionTimeOut() {

    }

    private class CommitAnswerTask extends AsyncTask<QuestionAnswer, Void, CommitAnswerResultMessage> {

        @Override
        protected CommitAnswerResultMessage doInBackground(QuestionAnswer... params) {
            QuestionAnswer answer = params[0];
            CommitAnswerResultMessage commitResult = CourseQuestionNetService.commitAnswer(answer);
            return commitResult;
        }

        @Override
        protected void onPostExecute(CommitAnswerResultMessage commitResult) {
            switch(commitResult) {
                case SUCCESS:
                    updateViewOnCommitSuccess();
                    break;
                case QUESITON_TIME_OUT:
                    updateViewOnQuestionTimeOut();
                    break;
                case QUESTION_SHUT_DOWN:
                    updateViewOnQuestionShutDown();
                    break;
                case NET_ERROR:
                    updateViewOnNetError();
                    break;
                default:
                    break;
            }
        }
    }
}
