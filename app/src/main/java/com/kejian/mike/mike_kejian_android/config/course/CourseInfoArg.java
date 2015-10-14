package com.kejian.mike.mike_kejian_android.config.course;

/**
 * Created by violetMoon on 2015/10/13.
 */
public class CourseInfoArg {
    public static final String URL_PREFIX = "Course";

    public static class GetMyCourseBriefs {
        public static final String ACTION_NAME = "getMyCourseBriefInfos";
        public static final String INPUT_STUDENT_ID = "studentId";
        public static final String INPUT_BEGIN_POS = "beginPos";
        public static final String INPUT_NUM = "num";
    }

    public static class GetAllCourseBriefs {
        public static final String ACTION_NAME = "getAllCourseBriefInfos";
        public static final String INPUT_SCHOOL_ID = "schoolId";
        public static final String INPUT_BEGIN_POS = "beginPos";
        public static final String INPUT_NUN = "num";
    }

    public static class GetCoursePosts {
        public static final String ACTION_NAME = "getCoursePosts";
        public static final String INPUT_COURSE_ID = "courseId";
        public static final String INPUT_BEGIN_POS = "beginPos";
        public static final String INPUT_NUM = "num";
    }

    public static class GetCourseDetail {
        public static final String ACTION_NAME = "getCourseDetail";
        public static final String INPUT_COURSE_ID = "courseId";
    }

}
