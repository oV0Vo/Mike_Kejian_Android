package net.course;

import android.util.Log;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.kejian.mike.mike_kejian_android.dataType.course.CourseNamingRecord;

import net.NetConfig;
import net.httpRequest.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import util.DateUtil;

/**
 * Created by violetMoon on 2015/9/29.
 */
public class CourseNamingNetService {

    private static final String TAG = "CourseRoll";

    private static final String BASE_URL = NetConfig.BASE_URL + "CourseRoll" + "/";

    private static HttpRequest http = HttpRequest.getInstance();

    public static ArrayList<CourseNamingRecord> getNamingRecords(String courseId) {
        String url = BASE_URL + "getHistoryRollRecord/";
        HashMap<String, String> paraMap = new HashMap<String, String>();
        paraMap.put("courseId", courseId);
        String responseContent = http.sentGetRequest(url, paraMap);
        try {
            JSONArray jNamingRecords = new JSONArray(responseContent);
            ArrayList<CourseNamingRecord> namingRecords = new ArrayList<>();
            for(int i=0; i<jNamingRecords.length(); ++i) {
                JSONObject jRecord = jNamingRecords.getJSONObject(i);
                CourseNamingRecord record = parseRecord(jRecord);
                namingRecords.add(record);
            }
            return namingRecords;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "getHistoryRollRecord json error");
            return null;
        }
    }

    private static CourseNamingRecord parseRecord(JSONObject jRecord) {
        try {
            CourseNamingRecord record = new CourseNamingRecord();

            String id = jRecord.getString("id");
            record.setNamingId(id);

            JSONArray jAbsents = jRecord.getJSONArray("absentStudents");
            ArrayList<String> absentIds = new ArrayList<>();
            ArrayList<String> absentNames = new ArrayList<>();
            for(int i=0; i<jAbsents.length(); ++i) {
                JSONObject jAbsent = jAbsents.getJSONObject(i);
                String absentId = jAbsent.getString("id");
                absentIds.add(absentId);
                String absentName = jAbsent.getString("name");
                absentNames.add(absentName);
            }

            record.setAbsentIds(absentIds);
            record.setAbsentNames(absentNames);

            String beginTimeStamp = jRecord.getString("begin_time");
            Date beginTime = DateUtil.convertPhpTimeStamp(beginTimeStamp);
            record.setBeginTime(beginTime);

            long lastTime = jRecord.getLong("last_time");
            Date endTime = DateUtil.caculatePhpTime(beginTime, lastTime);
            record.setEndTime(endTime);

            int signInNum = jRecord.getInt("signInNum");
            record.setSignInNum(signInNum);

            String teacherId = jRecord.getString("user_id");
            record.setTeacherId(teacherId);

            //@mock
            String teacherName = "王小明";/*jRecord.getString("user_name");*/
            record.setTeacherName(teacherName);

            return record;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "parseRecord json error");
            return null;
        }

    }

    public static CourseNamingRecord getCurrentNamingRecord(String courseId) {
        String url = BASE_URL + "getCurrentRollRecord/";
        HashMap<String, String> paraMap = new HashMap<String, String>();
        paraMap.put("courseId", courseId);
        String responseContent = http.sentGetRequest(url, paraMap);
        if(responseContent.equals("null")) {
            return null;
        } else {
            try {
                JSONObject jRecord = new JSONObject(responseContent);
                if (jRecord.length() == 0)
                    return null;
                CourseNamingRecord namingRecord = parseCurrentNamingRecord(jRecord);
                return namingRecord;
            } catch (JSONException e) {
                e.printStackTrace();
                Log.e(TAG, "getCurrentNamingRecord json error");
                return null;
            }
        }
    }

    public static CourseNamingRecord beginNaming(String courseId, long lastMills) {
        String url = BASE_URL + "beginCallRoll/";
        HashMap<String, String> paraMap = new HashMap<String, String>();
        paraMap.put("courseId", courseId);
        paraMap.put("lastTime", Long.toString(lastMills));
        String responseContent = http.sentGetRequest(url, paraMap);
        try {
            JSONObject jRecord = new JSONObject(responseContent);
            CourseNamingRecord record = parseCurrentNamingRecord(jRecord);
            return record;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "beginNaming json error");
            return null;
        }
    }

    private static CourseNamingRecord parseCurrentNamingRecord(JSONObject jRecord) {

        try {
            CourseNamingRecord record = new CourseNamingRecord();

            String teacherId = jRecord.getString("user_id");
            record.setTeacherId(teacherId);

            String beginTimeStamp = jRecord.getString("begin_time");
            Date beginTime = DateUtil.convertPhpTimeStamp(beginTimeStamp);
            record.setBeginTime(beginTime);

            String id = jRecord.getString("id");
            record.setNamingId(id);

            long lastTime = jRecord.getLong("last_time");
            Date endTime = DateUtil.caculatePhpTime(beginTime, lastTime);
            record.setEndTime(endTime);

            long leftMillis = jRecord.getLong("leftTime");
            record.setLeftMillis(leftMillis);

            record.setTeacherName("");
            record.setSignInNum(0);
            record.setAbsentIds(null);
            record.setAbsentNames(null);

            return record;

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "beginNaming json error");
            return null;
        }
    }

}
