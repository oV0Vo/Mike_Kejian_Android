package com.kejian.mike.mike_kejian_android.ui.course.detail.question;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kejian.mike.mike_kejian_android.R;

import net.course.CourseQuestionNetService;

import org.json.JSONArray;

import java.util.ArrayList;

import com.kejian.mike.mike_kejian_android.dataType.course.question.BasicQuestion;
import com.kejian.mike.mike_kejian_android.dataType.course.question.CommitAnswerResultMessage;
import com.kejian.mike.mike_kejian_android.dataType.course.question.MultiChoiceQuestion;
import com.kejian.mike.mike_kejian_android.dataType.course.question.QuestionAnswer;
import com.kejian.mike.mike_kejian_android.dataType.course.question.QuestionType;
import com.kejian.mike.mike_kejian_android.dataType.course.question.SingleChoiceQuestion;
import model.course.CourseModel;

public class QuestionAnswerActivity extends AppCompatActivity {

    private static final String TAG = "QuestioAnswerActivity";

    private BasicQuestion question;

    private RadioGroup singleChoiceGroup;

    private ArrayList<RadioButton> choiceButtons;

    private EditText replyText;

    private TextView answerActionText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_answer);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        question = CourseModel.getInstance().getAnswerFocusQuestion();
        String questionContent = question.getContent();
        setQuestionContentView(questionContent);

        switch (question.getQuestionType()) {
            case 单选题:
                Log.i(TAG, "setSingleChoiceQuestionView");
                setSingleChoiceQuestionView((SingleChoiceQuestion) question);
                break;
            case 多选题:
                Log.i(TAG, "setMultiChioceQuestionView");
                setMultiChioceQuestionView((MultiChoiceQuestion) question);
                break;
            case 其他:
                Log.i(TAG, "setApplicationQuestionView");
                setApplicationQuestionView(question);
                break;
            default:
                Log.i(TAG, "setQuestionView switch error");
                break;
        }

        setAnswerActionText();
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch(itemId) {
            case android.R.id.home:
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    
    private void setQuestionContentView(String questionContent) {
        TextView contentText = (TextView) findViewById(R.id.question_content_text);
        contentText.setText("  " + questionContent);
    }

    private void setSingleChoiceQuestionView(SingleChoiceQuestion singleChoiceQuestion) {
        choiceButtons = new ArrayList<RadioButton>();
        singleChoiceGroup = new RadioGroup(this);
        singleChoiceGroup.setOrientation(LinearLayout.VERTICAL);

        ArrayList<String> choiceContents = singleChoiceQuestion.getChoiceContents();
        for (int i = 0; i < choiceContents.size(); ++i) {
            RadioButton choiceButton = createChoiceButton(i, choiceContents.get(i));
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
            RadioButton choiceButton = createChoiceButton(i, choiceContents.get(i));
            choiceButtons.add(choiceButton);
            choiceContainer.addView(choiceButton);

            choiceButton.setOnClickListener(new View.OnClickListener() {
                private int clickCount = 0;

                @Override
                public void onClick(View v) {
                    clickCount++;
                    RadioButton radioButton = (RadioButton) v;
                    if (clickCount % 2 == 0) {
                        radioButton.setChecked(false);
                    }
                }
            });
        }

        choiceContainer.setVisibility(View.VISIBLE);
    }

    private RadioButton createChoiceButton(int choiceIndex, String choiceContent) {
        RadioButton choiceButton = new RadioButton(this);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        choiceButton.setLayoutParams(layoutParams);
        choiceButton.setSingleLine(false);
        choiceButton.setText(Character.toString((char) ('A' + choiceIndex)) + " " + choiceContent);
        return choiceButton;
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
            String answer = null;
            switch(question.getQuestionType()) {
                case 单选题:
                    answer = getSingleChoiceAnswer();
                    if(answer == null) {
                        Toast.makeText(QuestionAnswerActivity.this, R.string.need_to_choose_choice,
                                Toast.LENGTH_SHORT).show();
                        return;
                    }
                    break;
                case 多选题:
                    answer = getMultiChoiceAnswer();
                    if(answer == null) {
                        Toast.makeText(QuestionAnswerActivity.this, R.string.need_to_choose_choice,
                            Toast.LENGTH_SHORT).show();
                        return;
                    }
                    break;
                case 其他:
                    answer = getApplicationAnswer();
                    if(answer.length() == 0) {
                        Toast.makeText(QuestionAnswerActivity.this, R.string.need_to_input_answer,
                            Toast.LENGTH_SHORT).show();
                        return;
                    }
                    break;
                default:
                    break;
            }
            String questionId = question.getQuestionId();
            answerActionText.setEnabled(false);
            Toast.makeText(QuestionAnswerActivity.this, R.string.answer_question_on_progress,
                    Toast.LENGTH_SHORT).show();
            new CommitAnswerTask().execute(questionId, answer);
        }

        private String getSingleChoiceAnswer() {
            for(int i=0; i<choiceButtons.size(); ++i) {
                RadioButton choiceButton = choiceButtons.get(i);
                if(choiceButton.isChecked()) {
                    String answer = Integer.toString(i);
                    return answer;
                }
            }
            return null;
        }

        private String getMultiChoiceAnswer() {
            ArrayList<Integer> choices = new ArrayList<Integer>();
            for(int i=0; i<choiceButtons.size(); ++i) {
                RadioButton choiceButton = choiceButtons.get(i);
                if(choiceButton.isChecked()) {
                    choices.add(i);
                }
            }

            if(choices.size() != 0) {
                StringBuilder answer = new StringBuilder();
                for(int i=0; i< choices.size(); ++i) {
                    answer.append(choices.get(i));
                    if(i != (choices.size() - 1)) {
                        answer.append("_");
                    }
                }
                return answer.toString();
            }
            else
                return null;
        }

        private String getApplicationAnswer() {
            return replyText.getText().toString();
        }
    }

    private void updateViewOnNetError() {
        Toast.makeText(this, R.string.net_disconnet, Toast.LENGTH_SHORT).show();
        answerActionText.setEnabled(true);
    }

    private void updateViewOnCommitSuccess() {
        Toast.makeText(this, R.string.answer_question_success_message, Toast.LENGTH_SHORT).
                show();
        answerActionText.setEnabled(true);
    }

    private void updateViewOnQuestionTimeOut() {
        Toast.makeText(this, R.string.answer_question_time_out, Toast.LENGTH_SHORT).show();
        answerActionText.setEnabled(true);
    }

    private class CommitAnswerTask extends AsyncTask<String, Void, CommitAnswerResultMessage> {

        @Override
        protected CommitAnswerResultMessage doInBackground(String... params) {
            String questionId = params[0];
            String answer = params[1];
            CommitAnswerResultMessage commitResult = CourseQuestionNetService.commitAnswer(
                    questionId, answer);
            return commitResult;
        }

        @Override
        protected void onPostExecute(CommitAnswerResultMessage commitResult) {
            if(answerActionText == null)
                return;
            switch(commitResult) {
                case SUCCESS:
                    updateViewOnCommitSuccess();
                    break;
                case QUESITON_TIME_OUT:
                    updateViewOnQuestionTimeOut();
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
