package net.course;

import android.util.Log;

import com.kejian.mike.mike_kejian_android.dataType.course.CourseAnnoucement;

import net.NetConfig;
import net.httpRequest.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Random;

import model.user.Global;
import model.user.user;
import util.DateUtil;
import util.TimeFormat;

/**
 * Created by violetMoon on 2015/10/17.
 * CourseAnnouc Json format example
 * {"id":"2","course_id":"1","title":"q","content":"q","publish_time":"2015-10-07 19:29:41",
 * "user_id":"2","top":"1"}
 */
public class CourseAnnoucNetService {

    private static final String TAG = "CourseAnnounce";

    private static final String BASE_URL = NetConfig.BASE_URL + "CourseAnnounce" + "/";

    private static HttpRequest httpRequest = HttpRequest.getInstance();

    public static boolean setLatestAnnouc(String courseId, CourseAnnoucement annouc) {
        return false;
    }

    public static ArrayList<CourseAnnoucement> getAnnoucs(String courseId) {
        String url = BASE_URL + "getAnnoucementsByCourseId/";
        HashMap<String, String> paraMap = new HashMap<String, String>();
        paraMap.put("courseId", courseId);
        String response = httpRequest.sentGetRequest(url, paraMap);
        try {
            JSONArray jAnnoucs = new JSONArray(response);
            ArrayList<CourseAnnoucement> annoucs = new ArrayList<>();
            for(int i=0; i<jAnnoucs.length(); i++) {
                JSONObject jAnnouc = jAnnoucs.getJSONObject(i);
                CourseAnnoucement annouc = parseAnnouc(jAnnouc);
                annoucs.add(annouc);
            }
            return annoucs;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "getAnnoucs json error");
            return null;
        }
    }

    private static CourseAnnoucement parseAnnouc(JSONObject jAnnouc) {
        try {
            CourseAnnoucement annouc = new CourseAnnoucement();

            String annoucId = jAnnouc.getString("id");
            annouc.setAnnoucId(annoucId);

            String courseId = jAnnouc.getString("course_id");
            annouc.setCourseId(courseId);

            String title = jAnnouc.getString("title");
            annouc.setTitle(title);

            String content = jAnnouc.getString("content");
            annouc.setContent(content);

            String timeStamp = jAnnouc.getString("publish_time");
            Date publishDate = DateUtil.convertPhpTimeStamp(timeStamp);
            annouc.setDate(publishDate);

            String authorId = jAnnouc.getString("user_id");
            annouc.setPersonId(authorId);

            String authorName = jAnnouc.getString("user_name");
            annouc.setPersonName(authorName);

            int onTop = jAnnouc.getInt("top");
            annouc.setOnTop(onTop != 0);

            return annouc;

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "parseAnnouc json error");
            return null;
        }
    }

    public static boolean putOnTop(String annoucId) {
        String url = BASE_URL + "putAnnoucOnTop/";
        HashMap<String, String> paraMap = new HashMap<String, String>();
        paraMap.put("annoucId", annoucId);
        String response = httpRequest.sentGetRequest(url, paraMap);

        if(response == null)
            return false;
        else if(response.equals("false"))
            return false;
        else if(response.equals("true"))
            return true;
        else
            return false;
    }

    public static boolean newAnnouc(String courseId, String title, String content) {
        String url = BASE_URL + "newAnnouncement/";
        HashMap<String, String> paraMap = new HashMap<String, String>();
        String encodedTitle = null;
        String encodedContent = null;
        try {
            encodedTitle = URLEncoder.encode(title, "UTF-8");
            encodedContent = URLEncoder.encode(content, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        paraMap.put("courseId", courseId);
        paraMap.put("title", encodedTitle);
        paraMap.put("content", encodedContent);
        String response = httpRequest.sentGetRequest(url, paraMap);

        if(response == null)
            return false;
        else if(response.equals("false"))
            return false;
        else if(response.equals("true"))
            return true;
        else
            return false;
    }
}
