package net.course;

import android.util.Log;

import net.NetConfig;
import net.httpRequest.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

/**
 * Created by violetMoon on 2015/10/15.
 */
public class CourseTeacherNetService {

    private static final String TAG = "CourseTeacherNetService";

    private static final String BASE_URL = NetConfig.BASE_URL + "CourseTeacher" + "/";

    private static HttpRequest http = HttpRequest.getInstance();

    public static final boolean addAssistant(String courseId, String assistantId) {
        String url = BASE_URL + "addAssistant";
        HashMap<String, String> paraMap = new HashMap<String, String>();
        paraMap.put("userId", assistantId);
        paraMap.put("courseId", courseId);
        String response = http.sentGetRequest(url, paraMap);

        try {
            JSONObject jResult = new JSONObject(response);
            int resultState = jResult.getInt("result");
            boolean success = resultState != 0;
            return success;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.i(TAG, "addAssistant json error");
            return false;
        }
    }

    public static final boolean deleteAssistant(String courseId, String assistantId) {
        String url = BASE_URL + "deleteAssistant";
        HashMap<String, String> paraMap = new HashMap<String, String>();
        paraMap.put("userId", assistantId);
        paraMap.put("courseId", courseId);
        String response = http.sentGetRequest(url, paraMap);

        try {
            JSONObject jResult = new JSONObject(response);
            int resultState = jResult.getInt("result");
            boolean success = resultState != 0;
            return success;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.i(TAG, "deleteAssistant json error");
            return false;
        }
    }

/*
    public static final boolean addTeacher(String courseId, String teacherId) {
        String url = BASE_URL + "setTeacher";
        HashMap<String, String> paraMap = new HashMap<String, String>();
        paraMap.put("userId", teacherId);
        paraMap.put("courseId", courseId);

        String responseContent = http.sentGetRequest(url, paraMap);
        return true;
    }

    public static final boolean deleteTeacher(String courseId, String teacherId) {
        String url = BASE_URL + "unsignTeacher";
        HashMap<String, String> paraMap = new HashMap<String, String>();
        paraMap.put("userId", teacherId);
        paraMap.put("courseId", courseId);

        String responseContent = http.sentGetRequest(url, paraMap);
        return true;
    }*/
}
