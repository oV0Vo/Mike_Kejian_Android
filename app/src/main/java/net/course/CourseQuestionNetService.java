package net.course;

import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.kejian.mike.mike_kejian_android.dataType.course.PersonMocks;
import com.kejian.mike.mike_kejian_android.dataType.course.question.BasicQuestion;
import com.kejian.mike.mike_kejian_android.dataType.course.question.CommitAnswerResultMessage;
import com.kejian.mike.mike_kejian_android.dataType.course.question.CurrentQuestion;
import com.kejian.mike.mike_kejian_android.dataType.course.question.QuestionAnswer;
import com.kejian.mike.mike_kejian_android.dataType.course.question.QuestionShowAnswer;
import com.kejian.mike.mike_kejian_android.dataType.course.question.QuestionStats;
import com.kejian.mike.mike_kejian_android.dataType.course.question.SingleChoiceQuestion;

import net.NetConfig;
import net.httpRequest.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import util.NetOperateResultMessage;

/**
 * Created by violetMoon on 2015/10/6.
 */
public class CourseQuestionNetService {

    private static final String TAG = "CourseQuestionNet";

    private static final String BASE_URL = NetConfig.BASE_URL + "CourseQuestion" + "/";

    private static HttpRequest httpRequest = HttpRequest.getInstance();

    public static ArrayList<BasicQuestion> getHistroryQuestions(String courseId, int beginPos, int num,
                                                                int time, TimeUnit timeUnit) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static ArrayList<CurrentQuestion> getCurrentQuestions(String courseId, int beginPos,
                                                                 int num, int time, TimeUnit timeUnit) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static BasicQuestion parseQuestion() {
        return null;
    }

    public static boolean addNewQuestion(CurrentQuestion question) {
        String url = BASE_URL + "signQuestion";
        HashMap<String, String> paraMap = new HashMap<String, String>();
        paraMap.put("courseId", question.getQuestion().getCourseId());
        String response = httpRequest.sentPostRequest(url, paraMap);

        try {
            JSONObject jResult = new JSONObject(response);
            int resultState = jResult.getInt("result");
            boolean success = resultState != 0;
            return success;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "addNewQuestion json error");
            return false;
        }
    }
 
    public static ArrayList<QuestionShowAnswer> getQuestionAnswers(String questionId) {
        String url = BASE_URL + "getQuestionAnswers";
        HashMap<String, String> paraMap = new HashMap<String, String>();
        paraMap.put("questionId", questionId);
        String response = httpRequest.sentGetRequest(url, paraMap);

        try {
            JSONArray jAnswers = new JSONArray(response);
            ArrayList<QuestionShowAnswer> answers = new ArrayList<>();
            for(int i=0; i<jAnswers.length(); ++i) {
                JSONObject jAnswer = jAnswers.getJSONObject(i);
                QuestionShowAnswer answer = parseAnswer(jAnswer);
                answers.add(answer);
            }
            return answers;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "getQuestionAnswers json error");
            return null;
        }
    }

    private static QuestionShowAnswer parseAnswer(JSONObject jAnswer) throws JSONException{
        QuestionShowAnswer answer = new QuestionShowAnswer();

        String studentId = jAnswer.getString("user_id");
        answer.setStudentId(studentId);

        String answerContent = jAnswer.getString("answer");
        answer.setAnswer(answerContent);

        String studentName = jAnswer.getString("user_name");
        answer.setStudentName(studentName);

        String headIconUrl = jAnswer.getString("icon_url");
        answer.setHeadIconUrl(headIconUrl);
        return answer;
    }

    public static QuestionStats getQuestionStats(String questionId) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static CommitAnswerResultMessage commitAnswer(String questionId, String answer) {
        String url = BASE_URL + "answerQuestions";
        HashMap<String, String> paraMap = new HashMap<String ,String >();
        paraMap.put("questionId", questionId);
        paraMap.put("answer", answer);
        String response = httpRequest.sentGetRequest(url, paraMap);

        try {
            JSONObject jResult = new JSONObject(response);
            String operateMessage = jResult.getString("result");
            CommitAnswerResultMessage resultMessage = CommitAnswerResultMessage.valueOf(operateMessage);
            return resultMessage;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "commitAnswer json error");
            return null;
        }
    }

    public static boolean shutDownQuestion(String questionId) {
        return true;
    }
}
