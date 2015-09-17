package bl;

import net.CourseNetService;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import model.course.CourseAnnoucement;
import model.course.CourseBriefInfo;
import model.course.CourseDetailInfo;
import model.helper.ResultMessage;
import util.NeedAsyncAnnotation;
import util.NetOperateResultMessage;

/**
 * Created by violetMoon on 2015/9/8.
 */
public class CourseBLService {

    private static CourseBLService instance;

    public static CourseBLService getInstance() {
        return instance;
    }

    /**
     * 初始化缓存数据
     */
    @NeedAsyncAnnotation
    public static void initInstance() {
        if(instance == null) {
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
        return CourseNetService.getMyCourseBrief(studentId, beginPos, num);
    }

    public ArrayList<CourseBriefInfo> getAllCourseBriefs(String schoolId, int beginPos, int num) {
        return CourseNetService.getAllCourseBrief(schoolId, beginPos, num);
    }

    public ArrayList<CourseBriefInfo> getMyCourseBriefs(String studentId, int beginPos, int num, int time, TimeUnit timeUnit) {
        return CourseNetService.getMyCourseBrief(studentId, beginPos, num, time, timeUnit);
    }

    public ArrayList<CourseBriefInfo> getAllCourseBriefsInfo(String schoolId, int beginPos, int num, int time, TimeUnit timeUnit) {
        return CourseNetService.getAllCourseBrief(schoolId, beginPos, num, time, timeUnit);
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

}
