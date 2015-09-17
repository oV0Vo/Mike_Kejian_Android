package net;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import model.campus.Post;
import model.course.CourseAnnoucement;
import model.course.CourseBriefInfo;
import model.course.CourseDetailInfo;
import model.course.CourseType;
import model.course.PersonMocks;
import model.course.PostMocks;
import util.NetOperateResultMessage;

/**
 * Created by violetMoon on 2015/9/10.
 */
public class CourseNetService {

    public static ArrayList<CourseBriefInfo> getMyCourseBrief(String sid, int beginPos, int num) {
        ArrayList<CourseBriefInfo> mocks = new ArrayList<CourseBriefInfo>();
        for(int i=0; i<num; ++i)
            mocks.add(getCourseBriefMock1());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mocks;
    }

    public static ArrayList<CourseBriefInfo> getMyCourseBrief(String sid, int beginPos, int num, int time, TimeUnit timeUnit) {
        ArrayList<CourseBriefInfo> mocks = new ArrayList<CourseBriefInfo>();
        for(int i=0; i<num; ++i)
            mocks.add(getCourseBriefMock1());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mocks;
    }

    //sid?
    public static ArrayList<CourseBriefInfo> getAllCourseBrief(String schoolId, int beginPos, int num) {
        ArrayList<CourseBriefInfo> mocks = new ArrayList<CourseBriefInfo>();
        for(int i=0; i<num; ++i)
            mocks.add(getCourseBriefMock1());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mocks;
    }

    public static ArrayList<CourseBriefInfo> getAllCourseBrief(String schoolId, int beginPos, int num, int time, TimeUnit timeUnit) {
        ArrayList<CourseBriefInfo> mocks = new ArrayList<CourseBriefInfo>();
        for(int i=0; i<num; ++i)
            mocks.add(getCourseBriefMock1());
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mocks;
    }

/*    public static ArrayList<CourseDetailInfo> getMyCourseDetail(String sid) {
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
    }*/

    public static CourseDetailInfo getCourseDetail(String courseId) {
        CourseDetailInfo courseDetailMock = getCourseDetailMock1();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return courseDetailMock;
    }

    public static CourseDetailInfo getCourseDetail(String courseId, int time, TimeUnit timeUnit) {
        CourseDetailInfo courseDetailMock = getCourseDetailMock1();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return courseDetailMock;
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

    public static NetOperateResultMessage newAnnoucement(String courseId, String personId, String title, String content) {
        return null;
    }

    private static final class CourseNetArg {
        public static final String url = "";
        public static final String arg1Name = "";
        public static final String arg2Name = "";
    }


    private static final String courseMock1Id = "course1Id";
    private static final String courseMock2Id = "course2Id";

    private static CourseBriefInfo getCourseBriefMock1() {
        CourseBriefInfo course = new CourseBriefInfo();
        course.setCourseId(courseMock1Id);
        course.setAcademyName("软件学院");
        //course.setAnnoucement("隔壁老王说再也不偷情了");
        //course.setBriefIntro("与隔壁王太太偷情时被王先生抓奸在床，王先生揍了我一顿，对我说：“说好只爱我一个人的呢？”");
        course.setCourseName("软件需求工程");
        course.setProgressWeek(1);
        course.setCourseImage(null);
        //course.setTeachContent("专业美容学校，学美容必选的化妆名校");
        //course.setTeacherName("麦乐鸡");
        // ArrayList<String> references = new ArrayList<String>();
        // references.add("java虚拟机规范");
        // references.add("java并发编程");
        //course.setReferences(references);
        return course;
    }

    private static CourseDetailInfo getCourseDetailMock1() {
        CourseDetailInfo course = new CourseDetailInfo();
        course.setCourseId(courseMock1Id);
        ArrayList<String> references = new ArrayList<String>();
        references.add("java虚拟机规范");
        references.add("java并发编程");
        course.setReferences(references);
        CourseAnnoucement annoucement = new CourseAnnoucement();
        annoucement.setContent("隔壁老王说再也不偷情了");
        annoucement.setCourseId(courseMock1Id);
        annoucement.setDate(new Date(115, 8, 12));
        annoucement.setPersonId(PersonMocks.id6);
        course.setCourseType(CourseType.专业课);
        course.setCurrentStudents(115);
        course.setOutline("NJU最牛逼的课程，没有之一");
        course.setTeachContent("专业美容学校，学美容必选的化妆名校");
        ArrayList<String> teacherIds = new ArrayList<String>();
        ArrayList<String> teacherNames = new ArrayList<String>();
        teacherIds.add(PersonMocks.id1);
        teacherNames.add(PersonMocks.name1);
        teacherIds.add(PersonMocks.id2);
        teacherNames.add(PersonMocks.name2);
        course.setTeacherIds(teacherIds);
        course.setTeacherNames(teacherNames);
        ArrayList<Post> posts = new ArrayList<Post>();
        posts.add(PostMocks.getPost1());
        posts.add(PostMocks.getPost2());
        posts.add(PostMocks.getPost3());
        posts.add(PostMocks.getPost4());
        course.setPosts(posts);

        return course;
    }
}
