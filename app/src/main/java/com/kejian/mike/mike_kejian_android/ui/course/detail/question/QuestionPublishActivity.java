package com.kejian.mike.mike_kejian_android.ui.course.detail.question;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.PopupMenu;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.kejian.mike.mike_kejian_android.R;

import java.util.ArrayList;

import model.course.CourseModel;
import dataType.course.question.ApplicationQuestion;
import dataType.course.question.BasicQuestion;
import dataType.course.question.CurrentQuestion;
import dataType.course.question.MultiChoiceQuestion;
import dataType.course.question.SingleChoiceQuestion;

public class QuestionPublishActivity extends AppCompatActivity {

    private EditText timeLimitView;
    private RadioGroup questionTypeChoices;
    private EditText questionContent;

    private ViewGroup choiceContainer;
    private ViewGroup choiceContentContainer;

    private TextView choiceNumButton;
    private PopupMenu choiceNumMenu;
    private final int DEFAULT_CHOICE_NUM = 4;
    private final Integer[] avalableChoiceNum = {2, 3, 4, 5, 6, 7, 8};

    private ArrayList<EditText> choiceContentViews;
    private ArrayList<RadioButton> choiceButtons;
    private ArrayList<TextView> correctButtonHints;

    private Button commitButton;

    private byte typeMask;

    private byte singleChoiceMask = 0x1;
    private byte multiChoiceMask = 0x2;
    private byte otherMask = 0x4;

    //use in single choice mode
    private RadioButton currentCheckedButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_publish);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        timeLimitView = (EditText)findViewById(R.id.question_publish_time_limit_text);
        initQuestionTypeChoices();
        questionContent= (EditText)findViewById(R.id.question_publish_content_text);
        initChoiceContainer();
        initCommitButton();
    }

    private void initCommitButton() {
        commitButton = (Button)findViewById(R.id.question_publish_commit_button);
        commitButton.setOnClickListener(new CommitButtonListener());
    }

    private void initChoiceContainer() {
        choiceContainer = (ViewGroup)findViewById(R.id.question_publish_choice_container);
        initChoiceNumButton();
        choiceContentContainer = (ViewGroup)findViewById(R.id.
                question_publish_choice_content_container);

        choiceContentViews = new ArrayList();
        choiceButtons = new ArrayList();
        correctButtonHints = new ArrayList();

        setChoiceViewByNum(DEFAULT_CHOICE_NUM);
        choiceContentContainer.setVisibility(View.VISIBLE);
    }

    private void initChoiceNumButton() {
        choiceNumButton = (TextView)choiceContainer.findViewById(R.id.
                question_publish_choice_num_button);
        choiceNumButton.setText(Integer.toString(DEFAULT_CHOICE_NUM));

        choiceNumMenu = new PopupMenu(this, choiceNumButton);
        Menu realMenu = choiceNumMenu.getMenu();
        for(final Integer choiceNum: avalableChoiceNum) {
            realMenu.add(Integer.toString(choiceNum)).setOnMenuItemClickListener(new MenuItem.
                    OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    setChoiceViewByNum(choiceNum);
                    choiceNumButton.setText(Integer.toString(choiceNum));
                    return true;
                }
            });
        }

     /*   MenuInflater menuInflater = choiceNumMenu.getMenuInflater();
        menuInflater.inflate(R.menu.menu_empty, realMenu);//@?一定得加这句吗*/

        choiceNumButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choiceNumMenu.show();
            }
        });
    }

    private void initQuestionTypeChoices() {
        questionTypeChoices = (RadioGroup)findViewById(R.id.question_publish_type_choices);
        questionTypeChoices.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                boolean singleChoiceBefore = (typeMask & singleChoiceMask) != 0;
                boolean multiChoiceBefore = (typeMask & multiChoiceMask) != 0;
                boolean otherChoiceBefore = (typeMask & otherMask) != 0;

                switch (checkedId) {
                    case R.id.question_publish_type_single_choice:Log.e("QuestionPublish", "change to singleChoice");
                        if (multiChoiceBefore) {
                            clearCorrectChoices();
                        } else if (otherChoiceBefore) {
                            choiceContentContainer.setVisibility(View.VISIBLE);
                        }
                        typeMask = singleChoiceMask;
                        break;

                    case R.id.question_publish_type_multi_choice:Log.e("QuestionPublish", "change to ,multiChoice");
                        if (singleChoiceBefore) {
                            clearCorrectChoices();
                        } else if (otherChoiceBefore) {
                            choiceContentContainer.setVisibility(View.VISIBLE);
                        }
                        typeMask = multiChoiceMask;
                        break;
                    case R.id.question_publish_type_other:Log.e("QuestionPublish", "change to otherChoice");
                        if (singleChoiceBefore) {
                            choiceContentContainer.setVisibility(View.INVISIBLE);
                        } else if (multiChoiceBefore) {
                            choiceContentContainer.setVisibility(View.INVISIBLE);
                        }
                        typeMask = otherMask;
                        break;
                    default:
                        break;
                }
            }
        });
        RadioButton singleChoiceButton = (RadioButton)findViewById(R.id.
                question_publish_type_single_choice);
        singleChoiceButton.setChecked(true);
    }

    private void setChoiceViewByNum(int choiceNum) {
        clearChoiceViews();
        for(int choiceIndex=0; choiceIndex<choiceNum; ++choiceIndex)
            createNewChoiceView(choiceIndex);
    }

    private void createNewChoiceView(int choiceIndex) {
        ViewGroup newChoiceView = (ViewGroup)getLayoutInflater().inflate(
                R.layout.layout_question_choice_input, null);
        choiceContentContainer.addView(newChoiceView);
        TextView choiceIndexView = (TextView)newChoiceView.findViewById(R.id.question_choice_index);
        choiceIndexView.setText(toChoiceStr(choiceIndex));

        EditText choiceContentView = (EditText)newChoiceView.findViewById(R.id.
                question_choice_choice_content);
        choiceContentViews.add(choiceContentView);

        TextView correctHint = (TextView)newChoiceView.findViewById(R.id.
                question_choice_correct_answer_hint);
        correctButtonHints.add(correctHint);

        RadioButton choiceButton = (RadioButton)newChoiceView.findViewById(
                R.id.question_choice_radio_button);
        choiceButtons.add(choiceButton);
        choiceButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) { Log.e("QuestionPublish", "choiceButton check changed");
                if (isChecked) {
                    getCorrectHint((RadioButton) buttonView).setVisibility(View.VISIBLE);
                    //单选题的话还要把前一个选择的正确选项给消掉
                    if (isSingleChoice() && buttonView != currentCheckedButton) {
                        if (currentCheckedButton != null)
                            currentCheckedButton.setChecked(false);
                        currentCheckedButton = (RadioButton) buttonView;
                    }
                } else {
                    getCorrectHint((RadioButton) buttonView).setVisibility(View.INVISIBLE);
                }
            }
        });
    }

    private String toChoiceStr(int choiceIndex) {
        char choice = (char)('A' + choiceIndex);
        return Character.toString(choice);
    }

    private TextView getCorrectHint(RadioButton choiceButton) {
        int choiceIndex = choiceButtons.indexOf(choiceButton);
        return correctButtonHints.get(choiceIndex);
    }

    private void clearChoiceViews() {
        choiceContentContainer.removeAllViews();
        choiceContentViews.clear();
        choiceButtons.clear();
        correctButtonHints.clear();
    }

    private void clearCorrectChoices() {
        for(RadioButton choiceButton: choiceButtons)
            choiceButton.setChecked(false);
    }

    private boolean isSingleChoice() {
        return (typeMask & singleChoiceMask) != 0;
    }

    private class CommitButtonListener implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            BasicQuestion question = null;
            String timeLimitStr = timeLimitView.getText().toString().trim();
            if (!isValidTimeLimit(timeLimitStr)) {
                Toast.makeText(QuestionPublishActivity.this,
                        R.string.question_publish_invalid_time_limit_alert_message,
                        Toast.LENGTH_SHORT).show();
                return;
            }
            int timeLimit = new Integer(timeLimitStr);

            int questionTypeId = questionTypeChoices.getCheckedRadioButtonId();
            switch(questionTypeId) {
                case R.id.question_publish_type_single_choice:
                    question = new SingleChoiceQuestion();
                    break;
                case R.id.question_publish_type_multi_choice:
                    question = new MultiChoiceQuestion();
                    break;
                case R.id.question_publish_type_other:
                    question = new ApplicationQuestion();
                    break;
                default:
                    Log.e("QuestionPublish", "Invalid Type");
                    break;
            }

            boolean dealSuccess = false;
            switch(question.getQuestionType()) {
                case 单选题:
                    dealSuccess = dealSingleChoiceQuestion((SingleChoiceQuestion)question);
                    break;
                case 多选题:
                    dealSuccess = dealMultiChoiceQuestion((MultiChoiceQuestion) question);
                    break;
                case 其他:
                    dealSuccess = dealApplicationQuestion((ApplicationQuestion) question);
                    break;
                default:
                    break;
            }

            if(dealSuccess) {
                CurrentQuestion currentQuestion = new CurrentQuestion(question, timeLimit);
                new SubmitQuestionTask().execute(currentQuestion);
                Toast.makeText(QuestionPublishActivity.this, R.string.on_process,
                        Toast.LENGTH_LONG).show();
                commitButton.setEnabled(false);
                return;
            } else {
                return;
            }
        }

        private boolean isValidTimeLimit(String timeLimitStr) {
            try {
                int timeLimit = Integer.parseInt(timeLimitStr);
            } catch (NumberFormatException e) {
                return false;
            }
            return true;
        }

        private boolean dealSingleChoiceQuestion(SingleChoiceQuestion question) {
            int correctChoice = getSingleCorrectChoice();
            if(correctChoice < 0) {
                Toast.makeText(QuestionPublishActivity.this, R.string.question_publish_no_choice,
                        Toast.LENGTH_LONG).show();
                return false;
            }

            question.setCorrectChoice(correctChoice);

            ArrayList<String> choiceContents = getChoiceContents();
            question.setChoiceContents(choiceContents);
            return true;
        }

        private boolean dealMultiChoiceQuestion(MultiChoiceQuestion question) {
            ArrayList<Integer> correctChoices = getMultiCorrectChoice();
            if(correctChoices.size() == 0) {
                Toast.makeText(QuestionPublishActivity.this, R.string.question_publish_no_choice,
                        Toast.LENGTH_LONG).show();
                return false;
            }

            question.setCorrectChoices(correctChoices);

            ArrayList<String> choiceContents = getChoiceContents();
            question.setChoiceContents(choiceContents);
            return true;
        }

        private boolean dealApplicationQuestion(ApplicationQuestion question) {
            return true;
        }


        private ArrayList<String> getChoiceContents() {
            ArrayList<String> choiceContents = new ArrayList<String>();
            for(EditText choiceContentView: choiceContentViews) {
                String choiceContent = choiceContentView.getText().toString();
                choiceContents.add(choiceContent);
            }
            return choiceContents;
        }

        private int getSingleCorrectChoice() {
            for(int i=0; i<choiceButtons.size(); ++i) {
                RadioButton button = choiceButtons.get(i);
                if(button.isChecked())
                    return i;
            }
            return -1;
        }

        private ArrayList<Integer> getMultiCorrectChoice() {
            ArrayList<Integer> answers = new ArrayList<Integer>();
            for(int i=0; i<choiceButtons.size(); ++i) {
                RadioButton button = choiceButtons.get(i);
                if(button.isChecked())
                    answers.add(i);
            }
            return answers;
        }
    }

    private class SubmitQuestionTask extends AsyncTask<CurrentQuestion, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(CurrentQuestion... params) {
            CurrentQuestion newQuestion = params[0];
            return CourseModel.getInstance().addNewQuestion(newQuestion);
        }

        @Override
        protected void onPostExecute(Boolean message) {
            Toast.makeText(QuestionPublishActivity.this, R.string.question_publish_success_message,
                    Toast.LENGTH_LONG).show();
            commitButton.setEnabled(true);
        }
    }
}
