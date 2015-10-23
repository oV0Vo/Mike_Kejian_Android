package net.course;

import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

import com.kejian.mike.mike_kejian_android.dataType.course.question.ApplicationQuestion;
import com.kejian.mike.mike_kejian_android.dataType.course.question.BasicQuestion;
import com.kejian.mike.mike_kejian_android.dataType.course.question.CommitAnswerResultMessage;
import com.kejian.mike.mike_kejian_android.dataType.course.question.CurrentQuestion;
import com.kejian.mike.mike_kejian_android.dataType.course.question.MultiChoiceQuestion;
import com.kejian.mike.mike_kejian_android.dataType.course.question.QuestionShowAnswer;
import com.kejian.mike.mike_kejian_android.dataType.course.question.QuestionStats;
import com.kejian.mike.mike_kejian_android.dataType.course.question.QuestionType;
import com.kejian.mike.mike_kejian_android.dataType.course.question.SingleChoiceQuestion;

import net.NetConfig;
import net.httpRequest.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by violetMoon on 2015/10/6.
 */
public class CourseQuestionNetService {

    private static final String TAG = "CourseQuestionNet";

    private static final String BASE_URL = NetConfig.BASE_URL + "CourseQuestion" + "/";

    private static HttpRequest httpRequest = HttpRequest.getInstance();

    public static ArrayList<BasicQuestion> getHistroryQuestions(String courseId) {
        return new ArrayList<BasicQuestion>();
    /*
        String url = BASE_URL + "getHistoryQuestions/";
        HashMap<String, String> paraMap = new HashMap<String, String>();
        paraMap.put("courseId", courseId);
        paraMap.put("lastId", Integer.toString(Integer.MAX_VALUE));
        paraMap.put("num", Integer.toString(Integer.MAX_VALUE));
        String response = httpRequest.sentGetRequest(url, paraMap);

        try {
            JSONArray jQuestions = new JSONArray(response);
            ArrayList<BasicQuestion> questions = new ArrayList<BasicQuestion>();
            for(int i=0; i<jQuestions.length(); ++i) {
                JSONObject jQuestion = jQuestions.getJSONObject(i);
                BasicQuestion question = parseHistoryQuestion(jQuestion);
                questions.add(question);
            }
            return questions;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }*/
    }

    public static ArrayList<CurrentQuestion> getCurrentQuestions(String courseId) {
        return new ArrayList<CurrentQuestion>();
        /*
        String url = BASE_URL + "getCurrentQuestions/";
        HashMap<String, String> paraMap = new HashMap<String, String>();
        paraMap.put("courseId", courseId);
        String response = httpRequest.sentGetRequest(url, paraMap);

        try {
            JSONArray jQuestions = new JSONArray(response);
            ArrayList<CurrentQuestion> questions = new ArrayList<CurrentQuestion>();
            for(int i=0; i<jQuestions.length(); ++i) {
                JSONObject jQuestion = jQuestions.getJSONObject(i);
                CurrentQuestion question = parseCurrentQuestion(jQuestion);
                questions.add(question);
            }
            return questions;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }*/
    }

    private static BasicQuestion parseHistoryQuestion(JSONObject jQuestion) {
        return null;
    }

    private static CurrentQuestion parseCurrentQuestion(JSONObject jQuestion) {
        return null;
    }

    public static boolean addNewQuestion(CurrentQuestion question) {
        String url = BASE_URL + "signQuestion/";
        HashMap<String, String> paraMap = new HashMap<String, String>();
        paraMap.put("courseId", question.getQuestion().getCourseId());
        paraMap.put("surviveTime", Long.toString(question.getLeftMills()));
        paraMap.put("content", question.getQuestion().getContent());
        switch(question.getQuestion().getQuestionType()){
            case 单选题:
                url = BASE_URL + "signChoiceQuestion/";
                setSingleChoiceQuestionPara(paraMap, (SingleChoiceQuestion)question.getQuestion());
                break;
            case 多选题:
                url = BASE_URL + "signChoiceQuestion/";
                setMultiChoiceQuestionPara(paraMap, (MultiChoiceQuestion)question.getQuestion());
                break;
            case 其他:
                setApplicationQuestionPara(paraMap, (ApplicationQuestion)question.getQuestion());
                break;
            default:
                Log.e(TAG, "switch error" + question.getQuestion().getQuestionType().toString());
                break;
        }

        String response = httpRequest.sentPostRequest(url, paraMap);

        if(response == null)
            return false;
        else if(response.equals("false"))
            return false;
        else if(response.equals("true"))
            return true;
        else
            return false;
    }

    private static void setSingleChoiceQuestionPara(HashMap<String, String> paraMap,
                                                    SingleChoiceQuestion question) {
        ArrayList<String> choices = question.getChoiceContents();
        String jChoices = new JSONArray(choices).toString();
        paraMap.put("options", jChoices);
        int correctChoice = question.getCorrectChoice();
        paraMap.put("answers", Integer.toString(correctChoice));
        paraMap.put("type", "2");
    }

    private static void setMultiChoiceQuestionPara(HashMap<String, String> paraMap,
                                                   MultiChoiceQuestion question) {
        ArrayList<String> choices = question.getChoiceContents();
        String jChoices = new JSONArray(choices).toString();
        paraMap.put("options", jChoices);
        ArrayList<Integer> correctChoices = question.getCorrectChoices();
        String jAnswers = new JSONArray(correctChoices).toString();
        paraMap.put("answers", jAnswers);
        paraMap.put("type", "3");
    }

    private static void setApplicationQuestionPara(HashMap<String, String> paraMap,
                                                   ApplicationQuestion question) {
        paraMap.put("type", "1");
    }

    public static ArrayList<QuestionShowAnswer> getQuestionAnswers(String questionId) {
        String url = BASE_URL + "getQuestionAnswers/";
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
        String url = BASE_URL + "answerQuestions/";
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
