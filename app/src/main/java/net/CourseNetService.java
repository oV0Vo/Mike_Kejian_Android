package net;

import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import model.campus.Post;
import model.course.data.CourseAnnoucement;
import model.course.data.CourseBriefInfo;
import model.course.data.CourseDetailInfo;
import model.course.data.CourseType;
import model.course.data.PersonMocks;
import model.course.data.PostMocks;
import model.course.data.question.BasicQuestion;
import model.course.data.question.CurrentQuestion;
import model.course.data.question.SingleChoiceQuestion;
import util.NetOperateResultMessage;

/**
 * Created by violetMoon on 2015/9/10.
 */
public class CourseNetService {

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

    public static NetOperateResultMessage addViewToPost(String schoolId, String courseName, String postId) {
        return null;
    }

    public static NetOperateResultMessage addAnswerToPost(String schoolId, String courseName, String postId,
                                                          String answer) {
        return null;
    }

    public static NetOperateResultMessage addAnswerToProblem(String schoolId, String courseName, String pageId,
                                                             String answer) {
        return null;
    }

    public static NetOperateResultMessage newAnnoucement(String courseId, String personId, String title, String content) {
        return null;
    }

    public static ArrayList<BasicQuestion> getHistroryQuestions(String courseId, int beginPos, int num,
                                                                int time, TimeUnit timeUnit) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return getHistoryQuestionMocks();
    }

    public static ArrayList<CurrentQuestion> getCurrentQuestions(String courseId, int beginPos,
                                                                 int num, int time, TimeUnit timeUnit) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return getCurrentQuestionMocks();
    }

    public static NetOperateResultMessage addNewQuestion(CurrentQuestion question) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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

    private static ArrayList<BasicQuestion> getHistoryQuestionMocks() {
        ArrayList<BasicQuestion> mocks = new ArrayList<BasicQuestion>();
        mocks.add(getQuestionMock());
        mocks.add(getQuestionMock());
        mocks.add(getQuestionMock());
        mocks.add(getQuestionMock());

        return mocks;
    }

    private static ArrayList<CurrentQuestion> getCurrentQuestionMocks() {
        ArrayList<CurrentQuestion> currentQuestions = new ArrayList<CurrentQuestion>();
        Random random = new Random(System.currentTimeMillis());
        int limit = 1000;
        currentQuestions.add(new CurrentQuestion(getQuestionMock(), random.nextInt(limit) * 1000));
        return currentQuestions;
    }

    private static BasicQuestion getQuestionMock() {
        SingleChoiceQuestion q = new SingleChoiceQuestion();
        q.setCorrectChoice(1);
        q.setAuthorId(PersonMocks.id9);
        ArrayList<String> contents = new ArrayList<String>();
        contents.add("为了更接地气,微软在中国用百度取代了“亲儿子”必应");
        contents.add("为了更接地气,微软在中国用百度取代了“亲儿子”必应");
        contents.add("为了更接地气,微软在中国用百度取代了“亲儿子”必应");
        contents.add("为了更接地气,微软在中国用百度取代了“亲儿子”必应");
        q.setChoiceContents(contents);
        q.setContent("百度已经是中国用户上网操作的使用习惯,甚至是不少中国用户为数不多的几个入口之一。");
        q.setCourseId("zhe bu shi yi ge id");
        q.setQuestionDate(new Date(System.currentTimeMillis()));
        return q;
    }
}
