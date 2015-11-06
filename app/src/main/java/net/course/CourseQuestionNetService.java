package net.course;

import android.util.JsonReader;
import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
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
            e.printStackTrace();
            question.setChoiceContents(new ArrayList<String>());
            return;
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
                parseChoiceQuestion(jQuestion, (ChoiceQuestion) question);
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
        BasicQuestion questionInfo = question.getQuestion();

        String url = BASE_URL + "signQuestion/";
        HashMap<String, Object> paraMap = new HashMap<>();
        paraMap.put("courseId", questionInfo.getCourseId());
        paraMap.put("surviveTime", Long.toString(question.getLeftMills()));
        paraMap.put("content", questionInfo.getContent());
        String response = null;
        switch(question.getQuestion().getQuestionType()){
            case 单选题:
                url = BASE_URL + "signChoiceQuestion/";
                setSingleChoiceQuestionPara(paraMap, (SingleChoiceQuestion) question.getQuestion());
                response = httpRequest.sentPostRequest(url, paraMap);
                return dealChoiceQuestionReturn(response);
            case 多选题:
                url = BASE_URL + "signChoiceQuestion/";
                setMultiChoiceQuestionPara(paraMap, (MultiChoiceQuestion)question.getQuestion());
                response = httpRequest.sentPostRequest(url, paraMap);
                return dealChoiceQuestionReturn(response);
            case 其他:
                try {
                    String encodedContent = URLEncoder.encode(question.getQuestion().getContent(),
                            "UTF-8");
                    paraMap.put("content", encodedContent);
                    setApplicationQuestionPara(paraMap, (ApplicationQuestion) question.getQuestion());
                    response = httpRequest.sentGetRequest(url, toStringMap(paraMap));
                    return dealAPResponse(response);
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    return false;
                }
            default:
                Log.e(TAG, "switch error" + question.getQuestion().getQuestionType().toString());
                return false;
        }

    }

    private static HashMap<String, String> toStringMap(HashMap<String, Object> paraMap) {
        HashMap<String, String> newMap = new HashMap<>();
        Iterator<Map.Entry<String, Object>> iter = paraMap.entrySet().iterator();
        while(iter.hasNext()) {
            Map.Entry<String, Object> entry = iter.next();
            newMap.put(entry.getKey(), entry.getValue().toString());
        }
        return newMap;
    }

    private static boolean dealChoiceQuestionReturn(String response) {
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

    private static boolean dealAPResponse(String response) {
        if(response == null)
            return false;
        return true;
    }

    private static void setSingleChoiceQuestionPara(HashMap<String, Object> paraMap,
                                                    SingleChoiceQuestion question) {
        ArrayList<String> choices = question.getChoiceContents();
        paraMap.put("options", new JSONArray(choices));
        int correctChoice = question.getCorrectChoice();
        paraMap.put("answers", Integer.toString(correctChoice));
        paraMap.put("type", "2");
    }

    private static void setMultiChoiceQuestionPara(HashMap<String, Object> paraMap,
                                                   MultiChoiceQuestion question) {
        ArrayList<String> choices = question.getChoiceContents();
        paraMap.put("options", new JSONArray(choices));
        ArrayList<Integer> correctChoices = question.getCorrectChoices();
        paraMap.put("answers", new JSONArray(correctChoices));
        paraMap.put("type", "3");
    }

    private static void setApplicationQuestionPara(HashMap<String, Object> paraMap,
                                                   ApplicationQuestion question) {
        paraMap.put("type", "1");
    }

    public static ArrayList<QuestionShowAnswer> getQuestionAnswers(String questionId,
                                                                   QuestionType questionType) {
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
                QuestionShowAnswer answer = parseAnswer(jAnswer, questionType);
                answers.add(answer);
            }
            return answers;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "getQuestionAnswers json error");
            return null;
        }
    }

    private static QuestionShowAnswer parseAnswer(JSONObject jAnswer, QuestionType questionType)
            throws JSONException
    {
        QuestionShowAnswer answer = new QuestionShowAnswer();

        String studentId = jAnswer.getString("user_id");
        answer.setStudentId(studentId);

        String answerContent = jAnswer.getString("answer");
        if(questionType == QuestionType.其他) {
            answer.setAnswer(answerContent);
        } else {
            String[] answerChoicesStr = answerContent.split("_");
            StringBuilder answerBuilder = new StringBuilder();
            for(int i=0; i<answerChoicesStr.length - 1; ++i) {
                answerBuilder.append(Character.toString((char)('A' + Integer.parseInt(
                        answerChoicesStr[i]))));
                answerBuilder.append(" ");
            }
            if(answerChoicesStr.length != 0) {
                String lastChoiceStr = answerChoicesStr[answerChoicesStr.length - 1];
                answerBuilder.append(Character.toString((char) ('A' + Integer.parseInt(
                        lastChoiceStr))));
            }
            answerContent = answerBuilder.toString();
            answer.setAnswer(answerContent);
        }

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

            int type = Integer.parseInt(jStats.getString("type"));
            QuestionType questionType = null;
            switch(type) {
                case 1:
                    questionType = QuestionType.其他;
                    break;
                case 2:
                    questionType = QuestionType.单选题;
                    break;
                case 3:
                    questionType = QuestionType.多选题;
                    break;
                default:
                    Log.e(TAG, "switch error");
                    break;
            }
            stats.setQuestionType(questionType);
            if(QuestionType.其他 != questionType) {
                JSONArray jChoiceDistribute = jStats.getJSONArray("choiceDistribute");
                ArrayList<Integer> choiceDistribute = new ArrayList<Integer>();
                for (int i = 0; i < jChoiceDistribute.length(); ++i) {
                    choiceDistribute.add(jChoiceDistribute.getInt(i));
                }
                stats.setChoiceDistribute(choiceDistribute);
            }

            return stats;

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "getQuestionStats json error");
            return null;
        }
    }

    public static CommitAnswerResultMessage commitAnswer(String questionId, String answer) {
        Log.i(TAG, "commit answer " + answer);
        String url = BASE_URL + "answerQuestions/";
        HashMap<String, String> paraMap = new HashMap<String ,String >();
        paraMap.put("questionId", questionId);
        paraMap.put("answer", answer);//@need encode
        String response = httpRequest.sentGetRequest(url, paraMap);
        if(response == null)
            return null;

        try {
            JSONObject jResult = new JSONObject(response);
            int resultState = jResult.getInt("result");
            if(resultState == 1)
                return CommitAnswerResultMessage.SUCCESS;
            else
                return CommitAnswerResultMessage.QUESITON_TIME_OUT;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "commitAnswer json error");
            return CommitAnswerResultMessage.NET_ERROR;
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
