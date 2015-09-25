package bl;

import net.CourseNetService;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import model.course.CourseAnnoucement;
import model.course.CourseBriefInfo;
import model.course.CourseDetailInfo;
import model.course.question.BasicQuestion;
import model.course.question.CurrentQuestion;
import model.course.question.QuestionSet;
import model.helper.ResultMessage;
import util.NeedAsyncAnnotation;
import util.NetOperateResultMessage;

/**
 * Created by violetMoon on 2015/9/8.
 */
public class CourseBLService {

    private static CourseBLService instance;

    private CourseBLService() {
    }

    public static CourseBLService getInstance() {
        return instance;
    }

    /**
     * 初始化缓存数据
     */
    @NeedAsyncAnnotation
    public static void initInstance() {
        if (instance == null) {
            instance = new CourseBLService();
            instance.init();
        }
    }

    private void init() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<CourseBriefInfo> getMyCourseBriefs(String studentId, int beginPos, int num) {
        return getMyCourseBriefs(studentId, beginPos, num, Integer.MAX_VALUE, TimeUnit.SECONDS);
    }

    public ArrayList<CourseBriefInfo> getAllCourseBriefs(String schoolId, int beginPos, int num) {
        return getAllCourseBriefsInfo(schoolId, beginPos, num, Integer.MAX_VALUE, TimeUnit.SECONDS);

    }

    public ArrayList<CourseBriefInfo> getMyCourseBriefs(String studentId, int beginPos, int num, int time, TimeUnit timeUnit) {
        if (CourseNetService.hasMoreMyCourse(beginPos, num)) {
            return CourseNetService.getMyCourseBrief(studentId, beginPos, num, time, timeUnit);
        } else {
            return new ArrayList<CourseBriefInfo>();
        }
    }


    public ArrayList<CourseBriefInfo> getAllCourseBriefsInfo(String schoolId, int beginPos, int num, int time, TimeUnit timeUnit) {
        if(CourseNetService.hasMoreAllCourse(beginPos, num)) {
            return CourseNetService.getAllCourseBrief(schoolId, beginPos, num, time, timeUnit);
        } else {
            return new ArrayList<CourseBriefInfo>();
        }
    }

    public CourseDetailInfo getCourseDetail(String courseId) {
        return CourseNetService.getCourseDetail(courseId);
    }

    public CourseDetailInfo getCourseDetail(String courseId, int time, TimeUnit timeUnit) {
        return CourseNetService.getCourseDetail(courseId, time, timeUnit);
    }

    public NetOperateResultMessage newAnnoucement(CourseAnnoucement annoucement) {
        return CourseNetService.newAnnoucement(annoucement.getCourseId(), annoucement.getPersonId()
                , annoucement.getTitle(), annoucement.getContent());
    }

    public QuestionSet getQuestion(String courseId) {
        return CourseNetService.getQuestion(courseId);
    }

    public boolean hasMoreMyCourses(int beginPos, int num) {
        return CourseNetService.hasMoreMyCourse(beginPos, num);
    }

    public boolean hasMoreAllCourses(int beginPos, int num) {
        return CourseNetService.hasMoreAllCourse(beginPos, num);
    }

    public NetOperateResultMessage addNewQuestion(CurrentQuestion question) {
        return CourseNetService.addNewQuestion(question);
    }

    public ArrayList<String> getAllCourseTypeNamesMock() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("通识课");
        names.add("通修课");
        names.add("核心课");
        names.add("平台课");
        return names;
    }

}
