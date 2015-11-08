package net.course;

import android.util.Log;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import com.android.volley.toolbox.JsonArrayRequest;
import com.kejian.mike.mike_kejian_android.dataType.course.CourseAnnoucement;
import com.kejian.mike.mike_kejian_android.dataType.course.CourseBriefInfo;
import com.kejian.mike.mike_kejian_android.dataType.course.CourseDetailInfo;
import com.kejian.mike.mike_kejian_android.dataType.course.CourseType;
import com.kejian.mike.mike_kejian_android.dataType.course.PersonMocks;
import com.kejian.mike.mike_kejian_android.dataType.course.PostMocks;
import com.kejian.mike.mike_kejian_android.dataType.course.UserInterestInCourse;
import com.kejian.mike.mike_kejian_android.dataType.course.UserTypeInCourse;

import net.NetConfig;
import net.httpRequest.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;
import model.campus.Post;
import model.user.CourseBrief;
import util.NetOperateResultMessage;

/**
 * Created by violetMoon on 2015/9/10.
 */
public class CourseInfoNetService {

    private static final String TAG = "CourseInfoNetService";

    private static final String BASE_URL = NetConfig.BASE_URL + "Course" + "/";

    private static HttpRequest http = HttpRequest.getInstance();

    public static ArrayList<CourseBriefInfo> getMyCourseBrief() {
        String url = BASE_URL + "getMyCourseBriefInfos";
        Log.i(TAG, url);
        String response = http.sentGetRequest(url, null);
        if(response == null)
            return null;
        try {
            JSONArray jCourseBriefs = new JSONArray(response);
            ArrayList<CourseBriefInfo> courseBriefs = new ArrayList<CourseBriefInfo>(jCourseBriefs.length());
            for(int i=0; i<jCourseBriefs.length(); ++i) {
                JSONObject jCourseBrief = jCourseBriefs.getJSONObject(i);
                CourseBriefInfo courseBrief = parseCourseBriefInfo(jCourseBrief);
                courseBriefs.add(courseBrief);
            }
            return courseBriefs;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "getMyCourseBrief json error");
            return null;
        }

    }

    private static CourseBriefInfo parseCourseBriefInfo(JSONObject jCourseBrief) throws JSONException{
        try {
            CourseBriefInfo courseBrief = new CourseBriefInfo();

            String courseId = jCourseBrief.getString("course_id");
            courseBrief.setCourseId(courseId);

            String courseName = jCourseBrief.getString("course_name");
            courseBrief.setCourseName(courseName);

            Object academyName = jCourseBrief.get("department_name");
            if(JSONObject.NULL == academyName) {
                courseBrief.setAcademyName("暂无相关信息");
            }
            else {
                courseBrief.setAcademyName((String)academyName);
            }

            String courseType = jCourseBrief.getString("course_type");
            courseBrief.setCourseType(courseType);

            String imageUrl = jCourseBrief.getString("icon_url");
            courseBrief.setImageUrl(imageUrl);

            JSONArray jTeacherNames = jCourseBrief.getJSONArray("teachers");
            ArrayList<String> teacherNames = new ArrayList<String>(jTeacherNames.length());
            for(int i=0; i<jTeacherNames.length(); ++i)
                teacherNames.add(jTeacherNames.getString(i));
            courseBrief.setTeacherNames(teacherNames);

            return courseBrief;
        } catch (JSONException e) {


            e.printStackTrace();
            Log.e(TAG, "parseCourseBriefInfo json error");
            throw e;


        }
    }

    public static ArrayList<CourseBriefInfo> getAllCourseBrief(String schoolId, String lastCourseId
            , int num, int time, TimeUnit timeUnit) {
        String url = BASE_URL + "getAllCourses/";

        HashMap<String, String> paraMap = new HashMap<String, String>();
        String encodeSchoolId = null;
        try {
            encodeSchoolId = URLEncoder.encode(schoolId, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            //never happen
            e.printStackTrace();
        }
        paraMap.put("schoolId", encodeSchoolId);

        if(lastCourseId != null)
            paraMap.put("lastId", lastCourseId);
        else
            lastCourseId = Integer.toString(Integer.MAX_VALUE);
        paraMap.put("lastId", lastCourseId);

        paraMap.put("num", Integer.toString(num));

        String response = http.sentGetRequest(url, paraMap);
        if(response == null)
            return null;
        try {
            JSONArray jCourseBriefs = new JSONArray(response);
            ArrayList<CourseBriefInfo> courseBriefs = new ArrayList<CourseBriefInfo>(
                    jCourseBriefs.length());
            for(int i=0; i<jCourseBriefs.length(); ++i) {
                JSONObject jCourseBrief = jCourseBriefs.getJSONObject(i);
                CourseBriefInfo courseBrief = parseCourseBriefInfo(jCourseBrief);
                courseBriefs.add(courseBrief);
            }
            return courseBriefs;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "getAllCourseBrief json error");
            return null;
        }
    }

    public static Map<String, String> getAllAcademyInfos(String schoolId) {
        String url = NetConfig.BASE_URL + "School/getAllDepartmentsBySchoolId/";
        HashMap<String, String> paraMap = new HashMap<String, String>();
        paraMap.put("schoolId", schoolId);
        String response = http.sentGetRequest(url, paraMap);
        if(response == null) {
            return null;
        }

        try {
            JSONArray jAcademys = new JSONArray(response);
            Map<String, String> academys = new HashMap<String, String>(jAcademys.length());
            for(int i=0; i<jAcademys.length(); ++i) {
                JSONObject jAcademy = jAcademys.getJSONObject(i);
                String id = jAcademy.getString("id");
                String name = jAcademy.getString("name");
                academys.put(id, name);
            }
            return academys;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "getAllAcademyInfos json error");
            return null;
        }
    }

    public static ArrayList<String> getAllCourseType(String schoolId) {
        String url = BASE_URL + "getAllCourseType/";
        HashMap<String, String> paraMap = new HashMap<String, String>();
        paraMap.put("schoolId", schoolId);
        String response = http.sentGetRequest(url, paraMap);
        if(response == null)
            return null;

        try {
            JSONArray jCourseTypes = new JSONArray(response);
            ArrayList<String> courseTypes = new ArrayList<String>(jCourseTypes.length());
            for(int i=0; i<jCourseTypes.length(); ++i) {
                JSONObject jCourseType = jCourseTypes.getJSONObject(i);
                String courseType = jCourseType.getString("platform");
                courseTypes.add(courseType);
            }
            return courseTypes;
        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "getAllCourseType json error");
            return null;

        }
    }

    public static ArrayList<CourseBriefInfo> getCourseByCondition(String schoolId, String academyId,
                                                           String courseType) {
        String url = BASE_URL + "getCourseBriefByCondition/";
        HashMap<String, String> paraMap = new HashMap<String, String>();
        paraMap.put("dept", academyId);
        String encodeCourseType = null;
        try {
            encodeCourseType = URLEncoder.encode(courseType, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        paraMap.put("courseType", encodeCourseType);
        paraMap.put("schoolId", schoolId);
        String response = http.sentGetRequest(url, paraMap);
        if(response == null)
            return null;

        try {
            JSONArray jCourseBriefs = new JSONArray(response);
            ArrayList<CourseBriefInfo> courseBriefs = new ArrayList<>(jCourseBriefs.length());
            for(int i=0; i<jCourseBriefs.length(); ++i) {
                JSONObject jCourseBrief = jCourseBriefs.getJSONObject(i);
                CourseBriefInfo courseBrief = new CourseBriefInfo();

                String courseId = jCourseBrief.getString("id");
                courseBrief.setCourseId(courseId);

                String courseName = jCourseBrief.getString("name");
                courseBrief.setCourseName(courseName);

                String imageUrl = jCourseBrief.getString("icon_url");
                courseBrief.setImageUrl(imageUrl);

                String academyName = jCourseBrief.getString("department_name");
                courseBrief.setAcademyName(academyName);

                JSONArray jTeachers = jCourseBrief.getJSONArray("teachers");
                ArrayList<String> teacherNames = new ArrayList<String>(jTeachers.length());
                for(int nameIndex=0; nameIndex<jTeachers.length(); ++nameIndex)
                    teacherNames.add(jTeachers.getString(nameIndex));
                courseBrief.setTeacherNames(teacherNames);

                courseBriefs.add(courseBrief);
            }

            return courseBriefs;

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "getCourseByCondition json error");
            return null;
        }
    }

    public static CourseDetailInfo getCourseDetail(String courseId, int time, TimeUnit timeUnit) {
        String url = BASE_URL + "getCourseDetail/";
        HashMap<String, String> paraMap = new HashMap<String, String>();
        paraMap.put("courseId", courseId);
        String response = http.sentGetRequest(url, paraMap);
        if(response == null)
            return null;
        try {
            JSONObject jCourseDetail = new JSONObject(response);
            CourseDetailInfo courseDetail = new CourseDetailInfo();

            String id = jCourseDetail.getString("id");
            courseDetail.setCourseId(id);

            String name = jCourseDetail.getString("name");
            courseDetail.setCourseName(name);

            String academyId = jCourseDetail.getString("department_id");
            courseDetail.setAcademyId(null);

            String timeAndPlace = jCourseDetail.getString("time_place");
            courseDetail.setTimeAndPlace(timeAndPlace);

            Object academyName = jCourseDetail.get("department_name");
            if(JSONObject.NULL == academyName)
                courseDetail.setAccademyName("暂无相关信息");
            else {
                courseDetail.setAccademyName((String) academyName);
            }

            JSONArray jTeacherNames = jCourseDetail.getJSONArray("teachers");
            ArrayList<String> teacherNames = new ArrayList<String>(jTeacherNames.length());
            for(int i=0; i<jTeacherNames.length(); ++i)
                teacherNames.add(jTeacherNames.getString(i));
            courseDetail.setTeacherNames(teacherNames);

            courseDetail.setTeacherNames(teacherNames);

            JSONArray jAssistants = jCourseDetail.getJSONArray("assitants");
            ArrayList<String> assistantNames = new ArrayList<String>();
            ArrayList<String> assistantIds = new ArrayList<String>();
            for(int i=0; i<jAssistants.length(); ++i) {
                JSONObject jAsssitant = jAssistants.getJSONObject(i);

                String assistantId = jAsssitant.getString("id");
                assistantIds.add(assistantId);

                String assistantName = jAsssitant.getString("name");
                assistantNames.add(assistantName);
            }

            courseDetail.setAssistantNames(assistantNames);
            courseDetail.setAssistantIds(assistantIds);

            String outline = jCourseDetail.getString("introduction");
            courseDetail.setOutline(outline);

            String teachContent = jCourseDetail.getString("content");
            courseDetail.setTeachContent(teachContent);

            String reference = jCourseDetail.getString("referenced");
            ArrayList<String> references = new ArrayList<String>();
            references.add(reference);
            courseDetail.setReferences(references);

            int studentNum = jCourseDetail.getInt("students_num");
            courseDetail.setCurrentStudents(studentNum);

            return courseDetail;

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "getCourseDetail json error");
            return null;
        }

    }

    public static UserTypeInCourse getUserTypeInCourse(String courseId) {
        String url = NetConfig.BASE_URL + "CourseTeacher/getUserTypeInCourse/";
        HashMap<String, String> paraMap = new HashMap<String, String>();
        paraMap.put("courseId", courseId);
        String response = http.sentGetRequest(url ,paraMap);
        if(response == null)
            return null;
        switch(response) {
            case "\"1\"":
                return UserTypeInCourse.STUDENT;
            case "\"2\"":
                return UserTypeInCourse.TEACHER;
            case "\"3\"":
                return UserTypeInCourse.ASSISTANT;
            case "null":
                return UserTypeInCourse.VISITOR;
            default:
                return null;
        }
    }

    public static Integer getUserInterestInCourse(String courseId) {
        String url = NetConfig.BASE_URL + "UserAttentionList/isInterestedInCourse/";
        HashMap<String, String> paraMap = new HashMap<String, String>();
        paraMap.put("courseId", courseId);
        String response = http.sentGetRequest(url ,paraMap);
        if(response == null)
            return UserInterestInCourse.ERROR;
        else if(response.equals("true"))
            return UserInterestInCourse.INTEREST;
        else
            return UserInterestInCourse.NO_INTEREST;
    }

    public static boolean showInterestToCourse(String courseId) {
        String url = NetConfig.BASE_URL + "UserAttentionList/addAttentionItem/";
        HashMap<String, String> paraMap = new HashMap<String, String>();
        paraMap.put("itemId", courseId);
        paraMap.put("type", "COURSE");
        String response = http.sentGetRequest(url, paraMap);
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
