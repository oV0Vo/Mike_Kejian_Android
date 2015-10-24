package net.course;

import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.kejian.mike.mike_kejian_android.dataType.course.CourseSignInRecord;

import net.NetConfig;
import net.httpRequest.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import util.DateUtil;

/**
 * Created by violetMoon on 2015/9/29.
 */
public class CourseSignInNetService {

    private static final String TAG = "CourseSignInNet";

    private static final String BASE_URL = NetConfig.BASE_URL + "CourseRoll" + "/";

    private static HttpRequest http = HttpRequest.getInstance();

    public static ArrayList<CourseSignInRecord> getSignInRecords(String courseId) {
        String url = BASE_URL + "getHistorySignRecords/";
        HashMap<String, String> paraMap = new HashMap<String, String>();
        paraMap.put("courseId", courseId);
        String responseContent = http.sentGetRequest(url, paraMap);
        try {
            JSONArray jRecords = new JSONArray(responseContent);
            ArrayList<CourseSignInRecord> records = new ArrayList<>();
            for(int i=0; i<jRecords.length(); ++i) {
                JSONObject jRecord = jRecords.getJSONObject(i);
                CourseSignInRecord record = parseRecord(jRecord);
                records.add(record);
            }
            return records;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "getSignInRecords json error");
            return null;
        }
    }

    public static CourseSignInRecord getCurrentSignInRecord(String courseId) {
        String url = BASE_URL + "getCurrentSignRecord/";
        HashMap<String, String> paraMap = new HashMap<String, String>();
        paraMap.put("courseId", courseId);
        String responseContent = http.sentGetRequest(url, paraMap);

        if(responseContent.equals("null")) {
            return null;
        } else {
            try {
                JSONObject jRecord = new JSONObject(responseContent);
                CourseSignInRecord record = new CourseSignInRecord();

                String id = jRecord.getString("id");
                record.setNamingId(id);

                String beginTimeStamp = jRecord.getString("begin_time");
                Date beginTime = DateUtil.convertPhpTimeStamp(beginTimeStamp);
                record.setBeginTime(beginTime);

                long lastTime = jRecord.getLong("last_time");
                Date endTime = DateUtil.caculatePhpTime(beginTime, lastTime);
                record.setEndTime(endTime);

                long leftMillis = jRecord.getLong("leftTime");
                record.setLeftMillis(leftMillis);

                boolean hasSignIn = jRecord.getBoolean("hasSignIn");
                record.setHasSignIn(hasSignIn);

                String teacherId = jRecord.getString("user_id");
                record.setTeacherId(teacherId);

                return record;
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e(TAG, "getCurrentSignInRecord json error");
                return null;
            }
        }
    }

    private static CourseSignInRecord parseRecord(JSONObject jRecord) {
        try {
            CourseSignInRecord record = new CourseSignInRecord();

            String beginTimeStamp = jRecord.getString("begin_time");
            Date beginTime = DateUtil.convertPhpTimeStamp(beginTimeStamp);
            record.setBeginTime(beginTime);

            long lastTime = jRecord.getLong("last_time");
            Date endTime = DateUtil.caculatePhpTime(beginTime, lastTime);
            record.setEndTime(endTime);

            boolean hasSignIn = jRecord.getBoolean("hasSignIn");
            record.setHasSignIn(hasSignIn);

            String teacherName = jRecord.getString("teacher_name");
            record.setTeacherName(teacherName);

            String teacherId = jRecord.getString("user_id");
            record.setTeacherId(teacherId);

            return record;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "parseRecord json error");
            return null;
        }
    }

    public static boolean signIn(String namingId) {
        String url = BASE_URL + "courseSign/";
        HashMap<String, String> paraMap = new HashMap<String, String>();
        paraMap.put("rollId", namingId);
        String responseContent = http.sentGetRequest(url , paraMap);
        try {
            JSONObject jResult = new JSONObject(responseContent);
            int successState = jResult.getInt("result");
            boolean success = successState != 0;
            return success;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "signIn json error");
            return false;
        }
    }
}
