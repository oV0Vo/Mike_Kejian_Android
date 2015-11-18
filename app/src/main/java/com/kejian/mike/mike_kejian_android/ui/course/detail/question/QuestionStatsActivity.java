package com.kejian.mike.mike_kejian_android.ui.course.detail.question;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.kejian.mike.mike_kejian_android.R;
import com.kejian.mike.mike_kejian_android.dataType.course.question.QuestionType;
import com.kejian.mike.mike_kejian_android.ui.util.MyImageCache;
import com.kejian.mike.mike_kejian_android.ui.widget.ColorBar;
import com.kejian.mike.mike_kejian_android.ui.widget.TextExpandListener;

import net.course.CourseQuestionNetService;

import java.util.ArrayList;
import java.util.List;

import com.kejian.mike.mike_kejian_android.dataType.course.question.BasicQuestion;
import com.kejian.mike.mike_kejian_android.dataType.course.question.ChoiceQuestion;
import com.kejian.mike.mike_kejian_android.dataType.course.question.QuestionShowAnswer;
import com.kejian.mike.mike_kejian_android.dataType.course.question.QuestionStats;
import model.course.CourseModel;
import util.NumberUtil;

public class QuestionStatsActivity extends AppCompatActivity {

    private static final String TAG = "QuestionStats";

    private BasicQuestion question;

    private ArrayList<QuestionShowAnswer> answers;

    private CourseModel courseModel;

    private static final int ANSWER_INIT_NUM = 50;
    private static final int ANSWER_UPDATE_NUM = 10;

    private ProgressBar progressBar;
    private ViewGroup mainLayout;
    private View netErrorView;

    private ViewGroup statsTitleLayout;
    private ViewGroup statsContentLayout;
    private static final int[] choiceColors = new int[8];

    private ViewGroup answerListTitleLayout;
    private ViewGroup answerListLayout;
    private ListView answerListView;

    private QuestionAnswerAdapter answerListAdapter;

    private int taskCountDown;
    private boolean netError = false;

    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_stats);
        if(getSupportActionBar() != null)
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initAttrs();

        requestQueue = Volley.newRequestQueue(getApplicationContext());//@不应该放这里的

        answers = new ArrayList();
        courseModel = CourseModel.getInstance();
        question = courseModel.getStatsFocusQuestion();

        progressBar = (ProgressBar)findViewById(R.id.progress_bar);
        mainLayout = (ViewGroup)findViewById(R.id.main_layout);
        netErrorView = findViewById(R.id.net_error_text);

        initQuestionContentView();

        initStatsLayout();

        initAnswerLayout();
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
    
    private void initAttrs() {
        choiceColors[0] = getResources().getColor(R.color.blue_light);
        choiceColors[1] = getResources().getColor(R.color.blue);
        choiceColors[2] = getResources().getColor(R.color.green_light);
        choiceColors[3] = getResources().getColor(R.color.green);
        choiceColors[4] = getResources().getColor(R.color.pink);
        choiceColors[5] = getResources().getColor(R.color.purple);
        choiceColors[6] = getResources().getColor(R.color.orange);
        choiceColors[7] = getResources().getColor(R.color.yellow);
    }

    private void initQuestionContentView() {
        TextView questionContentText = (TextView)findViewById(R.id.question_content_text);
        String content = question.getContent();

        if(question instanceof  ChoiceQuestion) {
            StringBuilder strBuilder = new StringBuilder(content);
            ChoiceQuestion choiceQuestion = (ChoiceQuestion)question;
            ArrayList<String> choiceContents = choiceQuestion.getChoiceContents();
            for(int i=0; i<choiceContents.size(); ++i) {
                strBuilder.append("\n");
                strBuilder.append("  ");
                String indexStr = Character.toString((char)('A' + i));
                strBuilder.append(indexStr);
                strBuilder.append("  ");
                String choiceContent = choiceContents.get(i);
                strBuilder.append(choiceContent);
            }
            content = strBuilder.toString();
        }

        questionContentText.setText(content);

        initQCTextExpandView(questionContentText);
    }

    private void initQCTextExpandView(TextView questionContentText) {
        ViewGroup zhankaiLayout = (ViewGroup)getLayoutInflater().inflate(R.layout.layout_zhankai, null);
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        zhankaiLayout.setLayoutParams(layoutParams);

        TextView zhankaiText = (TextView)zhankaiLayout.findViewById(R.id.zhankai_text);
        ImageView zhankaiImage = (ImageView)zhankaiLayout.findViewById(R.id.zhankai_image);
        TextExpandListener textListener = new TextExpandListener(questionContentText, zhankaiText,
                zhankaiImage, 4);
        zhankaiLayout.setOnClickListener(textListener);

        ViewGroup zhankaiContainer = (ViewGroup)findViewById(R.id.zhankai_container);
        zhankaiContainer.addView(zhankaiLayout);
    }

    private void initStatsLayout() {
        statsContentLayout = (ViewGroup)findViewById(R.id.question_stats_container);
        statsTitleLayout = (ViewGroup)findViewById(R.id.question_answer_stats_title);
        final ImageView actionImageView = (ImageView)findViewById(R.id.stats_zhankai_image);
        statsTitleLayout.setOnClickListener(new View.OnClickListener() {

            private boolean isShow = false;

            @Override
            public void onClick(View v) {
                if (isShow) {
                    isShow = false;
                    statsContentLayout.setVisibility(View.GONE);
                    actionImageView.setImageDrawable(getResources().getDrawable(R.drawable.down));
                } else {
                    isShow = true;
                    statsContentLayout.setVisibility(View.VISIBLE);
                    actionImageView.setImageDrawable(getResources().getDrawable(R.drawable.up1));
                }
            }
        });

        new GetQuestionStatsTask().execute();
        taskCountDown++;
    }

    private void initAnswerLayout() {
        answerListTitleLayout = (ViewGroup)findViewById(R.id.all_answer_title_layout);
        final ImageView actionImageView = (ImageView)findViewById(R.id.all_answer_zhankai_image);
        answerListTitleLayout.setOnClickListener(new View.OnClickListener() {

            private boolean isShow = false;

            @Override
            public void onClick(View v) {
                if (isShow) {
                    isShow = false;
                    answerListLayout.setVisibility(View.GONE);
                    actionImageView.setImageDrawable(getResources().getDrawable(R.drawable.down));
                } else {
                    isShow = true;
                    answerListLayout.setVisibility(View.VISIBLE);
                    actionImageView.setImageDrawable(getResources().getDrawable(R.drawable.up1));
                }
            }
        });

        answerListLayout = (ViewGroup)findViewById(R.id.answer_list_layout);
        answerListView = (ListView)findViewById(R.id.question_answer_answer_list);
        answerListAdapter = new QuestionAnswerAdapter(this, android.R.layout.simple_list_item_1,
                answers);
        answerListView.setAdapter(answerListAdapter);

        new InitQuestionAnswerTask().execute();
        taskCountDown++;
    }

    public void setListViewHeightInScrollView(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter();
        if (listAdapter == null) {
            return;
        }
        int totalHeight = 0;
        for (int i = 0, len = listAdapter.getCount(); i < len; i++) {
            View listItem = listAdapter.getView(i, null, listView);
            listItem.measure(0, 0);
            totalHeight += listItem.getMeasuredHeight();
        }
        ViewGroup.LayoutParams params = listView.getLayoutParams();
        params.height = totalHeight + (listView.getDividerHeight() * (listAdapter.getCount() - 1));
        listView.setLayoutParams(params);
    }

    private void updateViewOnGetInitAnswers(boolean success) {
        if(success) {
            answerListAdapter.notifyDataSetChanged();
            setListViewHeightInScrollView(answerListView);
        } else
            netError = true;
        updateViewIfInitTaskFinish();
    }

    private void updateViewOnGetQuestionStats(QuestionStats stats) {
        if(stats == null) {
            netError = true;
            return;
        }

        int joinNum = stats.getTotalAnswerNum();
        int totalNum = courseModel.getCurrentCourseDetail().getCurrentStudents();
        String joinNumStr = Integer.toString(joinNum) + "/" + Integer.toString(totalNum);
        TextView joinNumText = (TextView)findViewById(R.id.join_num_text);
        joinNumText.setText(joinNumStr);

        double joinRate = (totalNum != 0)? ((double)joinNum) / totalNum: 0.0;
        ColorBar joinColorRateBar = ColorBar.getDefaultStyleColorBar(this, joinRate);
        ViewGroup joinRateColorBarContainer = (ViewGroup)findViewById(R.id.join_rate_color_bar);
        joinRateColorBarContainer.addView(joinColorRateBar);

        TextView joinRateText = (TextView)findViewById(R.id.join_rate_text);
        joinRateText.setText(Double.toString(NumberUtil.round(joinRate, 3) * 100) + "%");
        setTextColorAccordingToRate(joinRateText, joinRate);

        int correctNum = stats.getCorrectAnswerNum();
        String correctNumStr = Integer.toString(correctNum) + "/" + Integer.toString(joinNum);
        TextView correctNumText = (TextView)findViewById(R.id.correct_num_text);
        correctNumText.setText(correctNumStr);

        double correctRate = (joinNum != 0)? ((double)correctNum) / joinNum: 0.0;
        ColorBar correctRateColorBar = ColorBar.getDefaultStyleColorBar(this, correctRate);
        ViewGroup correctRateColorBarContainer = (ViewGroup)findViewById(R.id.correct_rate_color_bar);
        correctRateColorBarContainer.addView(correctRateColorBar);

        TextView correctRateText = (TextView)findViewById(R.id.correct_rate_text);
        correctRateText.setText(Double.toString(NumberUtil.round(correctRate, 3) * 100) + "%");
        setTextColorAccordingToRate(correctRateText, correctRate);

        QuestionType questionType = stats.getQuestionType();
        if(questionType == QuestionType.其他) {
            TextView titleText = (TextView)findViewById(R.id.distribute_title_text);
            ViewGroup distributeContainer = (ViewGroup)findViewById(R.id.distribute_container);
            titleText.setVisibility(View.GONE);
            distributeContainer.setVisibility(View.GONE);
        } else {
            initChoiceDistributeView(stats.getChoiceDistribute());
        }

        updateViewIfInitTaskFinish();
    }

    private void initChoiceDistributeView(List<Integer> distributes) {
        ViewGroup distributeContainer = (ViewGroup)findViewById(R.id.distribute_container);
        int sum = caculateSum(distributes);

        for(int i=0; i<distributes.size(); ++i) {
            int distribute = distributes.get(i);

            ViewGroup choiceLayout = (ViewGroup)getLayoutInflater().inflate(
                    R.layout.layout_choice_distirbute, null);
            TextView choiceIndexText = (TextView)choiceLayout.findViewById(R.id.choice_index_text);
            choiceIndexText.setText(Character.toString((char)('A' + i)));

            int barWidth = (int)getResources().getDimension(R.dimen.choice_distribute_bar_width);
            double barHeight = getResources().getDimension(R.dimen.choice_distribute_bar_max_height);
            double colorHeight = (int)(barHeight * ((double)distribute) / sum );

            ImageView valueBarImage = (ImageView)choiceLayout.findViewById(R.id.value_bar_image);
            int choiceColor = choiceColors[i];
            GradientDrawable colorDrawable = new GradientDrawable(GradientDrawable.Orientation.LEFT_RIGHT,
                    new int[]{choiceColor, choiceColor, choiceColor});
            colorDrawable.setSize(barWidth, (int) colorHeight);
            valueBarImage.setImageDrawable(colorDrawable);

            TextView distributeNumText = (TextView)choiceLayout.findViewById(
                    R.id.choice_distribute_num_text);
            distributeNumText.setText(Integer.toString(distribute));

            choiceLayout.setVisibility(View.VISIBLE);
            distributeContainer.addView(choiceLayout);
        }
    }

    private int caculateSum(List<Integer> nums) {
        int sum = 0;
        for(Integer num: nums)
            sum += num;
        return sum;
    }

    private void setTextColorAccordingToRate(TextView rateText, double rate) {
        if (rate < 0.6)
            rateText.setTextColor(getResources().getColor(R.color.red));
        else
            rateText.setTextColor(getResources().getColor(R.color.green));
    }

    private void updateViewIfInitTaskFinish() {
        if(taskCountDown == 0) {
            progressBar.setVisibility(View.GONE);
            if(!netError)
                mainLayout.setVisibility(View.VISIBLE);
            else
                netErrorView.setVisibility(View.VISIBLE);
        }
    }

    private class GetQuestionStatsTask extends AsyncTask<Void, Void, QuestionStats> {

        @Override
        protected QuestionStats doInBackground(Void... params) {
            return CourseQuestionNetService.getQuestionStats(question.getQuestionId());
        }

        @Override
        protected void onPostExecute(QuestionStats stats) {
            taskCountDown--;
            updateViewOnGetQuestionStats(stats);
        }
    }

    private class InitQuestionAnswerTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            ArrayList<QuestionShowAnswer> initAnswers = CourseQuestionNetService.getQuestionAnswers
                    (question.getQuestionId(), question.getQuestionType());
            if(initAnswers != null) {
                answers.addAll(initAnswers);
                return true;
            } else {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean success) {
            taskCountDown--;
            updateViewOnGetInitAnswers(success);
        }
    }

    private class QuestionAnswerAdapter extends ArrayAdapter<QuestionShowAnswer> {

        public QuestionAnswerAdapter(Context context, int resource, List<QuestionShowAnswer> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            Log.i(TAG, "getView " + Integer.toString(position) + Boolean.toString(convertView==null));
            AnswerViewHolder viewHolder = null;
            QuestionShowAnswer answer = getItem(position);
            if(convertView == null) {
                convertView = View.inflate(QuestionStatsActivity.this, R.layout.
                        layout_question_answer_brief, null);
                viewHolder = new AnswerViewHolder();

                ImageView userImageView = (ImageView) convertView.findViewById(R.id.user_image);
                if(answer.getHeadIconUrl() != null) {
                    ImageLoader imageLoader = new ImageLoader(requestQueue, MyImageCache.getInstance
                            (QuestionStatsActivity.this));
                    ImageLoader.ImageListener imageListener = ImageLoader.getImageListener(userImageView,
                            R.drawable.default_user, R.drawable.default_user);
                    imageLoader.get(answer.getHeadIconUrl(), imageListener);
                } else {
                    userImageView.setImageDrawable(getContext().getDrawable(R.drawable.default_user));
                }
                viewHolder.userImage = userImageView;

                TextView userNameText = (TextView)convertView.findViewById(R.id.user_name);
                viewHolder.userNameText = userNameText;
                TextView answerContentText = (TextView)convertView.findViewById(R.id.answer_content);
                viewHolder.answerText = answerContentText;
                ViewGroup zhankaiContainer = (ViewGroup)convertView.findViewById(R.id.zhankai_container);
                initAnswerExpandLayout(viewHolder.answerText, zhankaiContainer);
                viewHolder.zhankaiContainer = zhankaiContainer;

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (AnswerViewHolder)convertView.getTag();
            }

            viewHolder.userNameText.setText(answer.getStudentName());
            viewHolder.answerText.setText(answer.getAnswer());
            return convertView;
        }

        private void initAnswerExpandLayout(TextView questionContentText,
                                            ViewGroup zhankaiContainer)
        {
            ViewGroup zhankaiLayout = (ViewGroup)getLayoutInflater().inflate(R.layout.layout_zhankai, null);
            RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(
                    ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
            zhankaiLayout.setLayoutParams(layoutParams);

            TextView zhankaiText = (TextView)zhankaiLayout.findViewById(R.id.zhankai_text);
            ImageView zhankaiImage = (ImageView)zhankaiLayout.findViewById(R.id.zhankai_image);
            TextExpandListener textListener = new TextExpandListener(questionContentText, zhankaiText,
                    zhankaiImage, 2);
            zhankaiLayout.setOnClickListener(textListener);

            zhankaiContainer.addView(zhankaiLayout);
        }
    }

    static class AnswerViewHolder {
        public ImageView userImage;
        public TextView userNameText;
        public TextView answerText;
        public ViewGroup zhankaiContainer;
    }
}
