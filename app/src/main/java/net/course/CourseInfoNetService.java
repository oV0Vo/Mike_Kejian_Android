package net.course;

import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import com.kejian.mike.mike_kejian_android.dataType.course.CourseAnnoucement;
import com.kejian.mike.mike_kejian_android.dataType.course.CourseBriefInfo;
import com.kejian.mike.mike_kejian_android.dataType.course.CourseDetailInfo;
import com.kejian.mike.mike_kejian_android.dataType.course.CourseType;
import com.kejian.mike.mike_kejian_android.dataType.course.PersonMocks;
import com.kejian.mike.mike_kejian_android.dataType.course.PostMocks;
import com.kejian.mike.mike_kejian_android.dataType.course.UserTypeInCourse;
import model.campus.Post;
import util.NetOperateResultMessage;

/**
 * Created by violetMoon on 2015/9/10.
 */
public class CourseInfoNetService {

    public static ArrayList<CourseBriefInfo> getMyCourseBrief(String sid, int beginPos, int num,
                                                              int time, TimeUnit timeUnit) {
        ArrayList<CourseBriefInfo> mocks = new ArrayList<CourseBriefInfo>();
        for(int i=0; i<num; ++i)
            mocks.add(getCourseBriefMock1());
        return mocks;
    }

    public static ArrayList<CourseBriefInfo> getAllCourseBrief(String schoolId, int beginPos, int num,
                                                               int time, TimeUnit timeUnit) {
        ArrayList<CourseBriefInfo> mocks = new ArrayList<CourseBriefInfo>();
        for(int i=0; i<num; ++i)
            mocks.add(getCourseBriefMock1());
        return mocks;
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

    public static ArrayList<Post> getCoursePosts(String courseId, int beginPos, int num,
                                                 int time, TimeUnit timeUnit) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayList<Post> postMocks = getCoursePostMocks();
        return postMocks;
    }

    public static UserTypeInCourse getUserTypeInCourse(String courseId, String userId) {
        return UserTypeInCourse.STUDENT;
    }

    public static NetOperateResultMessage addAnswerToProblem(String schoolId, String courseName, String pageId,
                                                             String answer) {
        return null;
    }

    public static NetOperateResultMessage newAnnoucement(String courseId, String personId, String title, String content) {
        return null;
    }

    public static ArrayList<CourseAnnoucement> getAnnouc(String courseId, int beginPos, int num,
                                                         int time, TimeUnit timeUnit) {
        return annoucMocks();
    }

    private static ArrayList<CourseAnnoucement> annoucMocks() {
        ArrayList<CourseAnnoucement> mocks = new ArrayList<CourseAnnoucement>();
        mocks.add(annoucMock());
        CourseAnnoucement onTopMock = annoucMock();
        onTopMock.setOnTop(true);
        mocks.add(onTopMock);
        mocks.add(annoucMock());
        mocks.add(annoucMock());
        return mocks;
    }

    private static CourseAnnoucement annoucMock() {
        CourseAnnoucement mock = new CourseAnnoucement();
        mock.setContent("这么课需要大一的“高等数学”与“Python程序设计为基础。假如你不会Python编程，" +
                "你可以重点听陈老师那部分；假如你高等数学忘得差不多了，你可以重点听范老师那部分。最好状况" +
                "是两部分都能坚持下来”");
        mock.setCourseId("sdfsd");
        mock.setDate(new Date(System.currentTimeMillis()));
        mock.setPersonId(PersonMocks.id5);
        mock.setPersonName(PersonMocks.name5);
        mock.setTitle("今天天气真好啊~");
        return mock;
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
        annoucement.setContent("这么课需要大一的“高等数学”与“Python程序设计为基础。假如你不会Python编程，" +
                "你可以重点听陈老师那部分；假如你高等数学忘得差不多了，你可以重点听范老师那部分。最好状况" +
                "是两部分都能坚持下来”");
        annoucement.setCourseId(courseMock1Id);
        annoucement.setDate(new Date(115, 8, 12));
        annoucement.setPersonId(PersonMocks.id6);
        course.setAnnoucement(annoucement);
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

        return course;
    }

    private static ArrayList<Post> getCoursePostMocks() {
        ArrayList<Post> posts = new ArrayList<Post>();
        posts.add(PostMocks.getPost1());
        posts.add(PostMocks.getPost2());
        posts.add(PostMocks.getPost3());
        posts.add(PostMocks.getPost4());
        return posts;
    }

}
