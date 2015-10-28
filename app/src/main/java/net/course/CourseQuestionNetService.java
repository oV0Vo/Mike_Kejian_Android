package net.course;

import android.util.Log;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.kejian.mike.mike_kejian_android.dataType.course.question.ApplicationQuestion;
import com.kejian.mike.mike_kejian_android.dataType.course.question.BasicQuestion;
import com.kejian.mike.mike_kejian_android.dataType.course.question.ChoiceQuestion;
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

import util.DateUtil;

/**
 * Created by violetMoon on 2015/10/6.
 */
public class CourseQuestionNetService {

    private static final String TAG = "CourseQuestionNet";

    private static final String BASE_URL = NetConfig.BASE_URL + "CourseQuestion" + "/";

    private static HttpRequest httpRequest = HttpRequest.getInstance();

    public static ArrayList<BasicQuestion> getHistroryQuestions(String courseId) {
        String url = BASE_URL + "getHistoryQuestions/";
        HashMap<String, String> paraMap = new HashMap<String, String>();
        paraMap.put("courseId", courseId);
        paraMap.put("lastId", Integer.toString(Integer.MAX_VALUE));
        paraMap.put("num", Integer.toString(Integer.MAX_VALUE));
        String response = httpRequest.sentGetRequest(url, paraMap);
        if(response == null)
            return null;

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
        }
    }

    public static ArrayList<CurrentQuestion> getCurrentQuestions(String courseId) {
        String url = BASE_URL + "getCurrentQuestions/";
        HashMap<String, String> paraMap = new HashMap<String, String>();
        paraMap.put("courseId", courseId);
        String response = httpRequest.sentGetRequest(url, paraMap);
        if(response == null)
            return null;

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
            Log.e(TAG, "getCurrentQuestions json error");
            return null;
        }
    }

    private static BasicQuestion parseHistoryQuestion(JSONObject jQuestion) throws JSONException{
        try {
            BasicQuestion question = null;
            String type = jQuestion.getString("type");
            switch(type) {
                case "1":
                    question = new ApplicationQuestion();
                    parseAP(jQuestion, (ApplicationQuestion)question);
                    break;
                case "2":
                    question = new SingleChoiceQuestion();
                    parseChoiceQuestion(jQuestion, (ChoiceQuestion)question);
                    break;
                case "3":
                    question = new MultiChoiceQuestion();
                    parseChoiceQuestion(jQuestion, (ChoiceQuestion)question);
                    break;
                default:
                    break;
            }

            String content = jQuestion.getString("content");
            question.setContent(content);

            String id = jQuestion.getString("question_id");
            question.setQuestionId(id);

            String authorId = jQuestion.getString("author_id");
            question.setAuthorId(authorId);

            String beginDateStamp = jQuestion.getString("time");
            Date beginTime = DateUtil.convertPhpTimeStamp(beginDateStamp);
            question.setQuestionDate(beginTime);

            boolean answered = jQuestion.getBoolean("answered");
            question.setJoined(answered);

            question.setCourseId(null);

            return question;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.i(TAG, "parseHistoryQuestion json error");
            throw e;
        }
    }

    private static void parseAP(JSONObject jQuestion, ApplicationQuestion question) {
        return;
    }

    private static void parseChoiceQuestion(JSONObject jQuestion, ChoiceQuestion question)
            throws JSONException{
        try {
            JSONArray jOptions = jQuestion.getJSONArray("options");
            ArrayList<String> options = new ArrayList<String>(jOptions.length());
            for (int i = 0; i < jOptions.length(); ++i) {
                options.add(jOptions.getString(i));
            }
            question.setChoiceContents(options);
        } catch (JSONException e) {
            question.setChoiceContents(new ArrayList<String>());
        }
    }

    private static CurrentQuestion parseCurrentQuestion(JSONObject jQuestion) throws JSONException{
        CurrentQuestion currentQuestion = new CurrentQuestion();
        BasicQuestion question = null;

        String type = jQuestion.getString("type");
        switch(type) {
            case "1":
                question = new ApplicationQuestion();
                parseAP(jQuestion, (ApplicationQuestion)question);
                break;
            case "2":
                question = new SingleChoiceQuestion();
                parseChoiceQuestion(jQuestion, (ChoiceQuestion)question);
                break;
            case "3":
                question = new MultiChoiceQuestion();
                parseChoiceQuestion(jQuestion, (ChoiceQuestion)question);
                break;
            default:
                break;
        }

        String content = jQuestion.getString("content");
        question.setContent(content);

        String id = jQuestion.getString("question_id");
        question.setQuestionId(id);

        String authorId = jQuestion.getString("author_id");
        question.setAuthorId(authorId);

        String beginDateStamp = jQuestion.getString("time");
        Date beginTime = DateUtil.convertPhpTimeStamp(beginDateStamp);
        question.setQuestionDate(beginTime);

        long leftTime = jQuestion.getLong("left_time");
        currentQuestion.setLeftMills(leftTime);

        currentQuestion.setQuestion(question);

        return currentQuestion;
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
        else if(response.equals("nulltrue"))
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
        if(response == null)
            return null;

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
        String url = BASE_URL + "getQuestionState";
        HashMap<String, String> paraMap = new HashMap<String, String>();
        paraMap.put("questionId", questionId);
        String response = httpRequest.sentGetRequest(url, paraMap);
        if(response == null)
            return null;

        try {
            JSONObject jStats = new JSONObject(response);
            QuestionStats stats = new QuestionStats();

            String id = jStats.getString("id");
            stats.setQuestionId(id);

            int correctAnswerNum = Integer.parseInt(jStats.getString("correctAnswerNum"));
            stats.setCorrectAnswerNum(correctAnswerNum);

            int totalAnswerNum = Integer.parseInt(jStats.getString("totalAnswerNum"));
            stats.setTotalAnswerNum(totalAnswerNum);

            JSONArray jChoiceDistribute = jStats.getJSONArray("choiceDistribute");
            ArrayList<Integer> choiceDistribute = new ArrayList<Integer>();
            for(int i=0; i<jChoiceDistribute.length(); ++i) {
                choiceDistribute.add(jChoiceDistribute.getInt(i));
            }
            stats.setChoiceDistribute(choiceDistribute);

            return stats;

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "getQuestionStats json error");
            return null;
        }
    }

    public static CommitAnswerResultMessage commitAnswer(String questionId, String answer) {
        String url = BASE_URL + "answerQuestions/";
        HashMap<String, String> paraMap = new HashMap<String ,String >();
        paraMap.put("questionId", questionId);
        paraMap.put("answer", answer);//@need encode
        String response = httpRequest.sentGetRequest(url, paraMap);
        if(response == null)
            return null;

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
        String url = BASE_URL + "closeQuestion";
        HashMap<String, String> paraMap = new HashMap<String, String>();
        paraMap.put("questionId", questionId);
        String response = httpRequest.sentGetRequest(url, paraMap);
        if(response == null)
            return false;
        else if(response.equals("true"))
            return true;
        else if(response.equals("false"))
            return false;
        else
            return false;
    }
}
