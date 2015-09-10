package util;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import model.course.CourseBriefInfo;
import model.course.CourseDetailInfo;

/**
 * Created by violetMoon on 2015/9/10.
 */
public class CourseNetService {

    public static ArrayList<CourseBriefInfo> getMyCourseBrief(String sid) {
        return null;
    }

    public static ArrayList<CourseBriefInfo> getMyCourseBrief(String sid, int time, TimeUnit timeUnit) {
        return null;
    }

    //sid?
    public static ArrayList<CourseBriefInfo> getAllCourseBrief(String schoolId) {
        return null;
    }

    public static ArrayList<CourseBriefInfo> getAllCourseBrief(String schoolId, int time, TimeUnit timeUnit) {
        return null;
    }

    public static ArrayList<CourseDetailInfo> getMyCourseDetail(String sid) {
        return null;
    }

    public static ArrayList<CourseDetailInfo> getMyCourseDetail(String sid ,int time, TimeUnit timeUnit) {
        return null;
    }

    public static ArrayList<CourseDetailInfo> getAllCourseDetail(String schoolId) {
        return null;
    }

    public static ArrayList<CourseDetailInfo> getAllCourseDetail(String schoolId, int time, TimeUnit timeUnit) {
        return null;
    }

    public static NetOperateResultMessage addViewToPage(String schoolId, String courseName, String pageId) {
        return null;
    }

    public static NetOperateResultMessage addAnswerToPage(String schoolId, String courseName, String pageId,
                                                          String answer) {
        return null;
    }
//sid?
    public static NetOperateResultMessage addAnswerToProblem(String schoolId, String courseName, String pageId,
                                                             String answer) {
        return null;
    }

    public static NetOperateResultMessage newAnnoucement(String schoolId, String courseName, String title,
                                                         String content, String sid) {
        return null;
    }

    private static final class CourseNetArg {
        public static final String url = "";
        public static final String arg1Name = "";
        public static final String arg2Name = "";
    }
}
