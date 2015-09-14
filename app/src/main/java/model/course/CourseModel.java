package model.course;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.campus.Post;

/**
 * Created by violetMoon on 2015/9/8.
 */
public class CourseModel {
    public static final String ARG_COURSE_ID = "courseId";

    private static CourseModel instance;
    private ArrayList<CourseBriefInfo> myCourseBriefs;
    private ArrayList<CourseBriefInfo> allCourseBriefs;

    private CourseBriefInfo currentCourseBrief;
    private CourseDetailInfo currentCourseDetail;

    private Post currentPost;

    public static CourseModel getInstance() {
        if(instance == null) {
            instance = new CourseModel();
            instance.init();
        }
        return instance;
    }
    /*
    以后做课程缓存的时候从文件读取课程数据可以在这个方法里面进行，不过这个类目前之考虑单线程的修改，
    如果是网络和文件都要修改这个类，CourseListFragment也要修改
     */
    public void init() {
        myCourseBriefs = new ArrayList<CourseBriefInfo>();
        allCourseBriefs = new ArrayList<CourseBriefInfo>();
    }

    public ArrayList<CourseBriefInfo> getMyCourseBriefs(int beginPos, int num) {
        return getSubList(beginPos, num, myCourseBriefs);
    }

    public ArrayList<CourseBriefInfo> getAllCourseBriefs(int beginPos, int num) {
        return getSubList(beginPos, num, allCourseBriefs);
    }

    /**
     *
     * @param courseId
     * @return
     */
    public CourseBriefInfo getCourseBriefInMyCourse(String courseId) {
        return getCourseBrief(courseId, myCourseBriefs);
    }

    /**
     *
     * @param courseId
     * @return
     */
    public CourseBriefInfo getCourseBriefInAllCourse(String courseId) {
        return getCourseBrief(courseId, allCourseBriefs);
    }

    private CourseBriefInfo getCourseBrief(String courseId, ArrayList<CourseBriefInfo> briefs) {
        for(CourseBriefInfo courseBrief: briefs)
            if(courseBrief.getCourseId().equals(courseId)) {
                return courseBrief;
            }
        return null;
    }

    private CourseDetailInfo getCourseDetail(String courseId, ArrayList<CourseDetailInfo> courseDetails) {
        for(CourseDetailInfo courseDetail: courseDetails)
            if(courseDetail.getCourseId().equals(courseId)) {
                return courseDetail;
            }
        return null;
    }

    private <E> ArrayList<E> getSubList(int beginPos, int num, List<E> list) {
        if(!isValidPos(beginPos, list)) {
            return new ArrayList<E>();
        }
        if(!isValidPos(beginPos + num, list)) {
            return new ArrayList<E>(list.subList(beginPos, list.size()));
        }
        return new ArrayList<E>(list.subList(beginPos, beginPos + num));
    }

    private boolean isValidPos(int beginPos, List<?> list) {
        return list.size() > beginPos && beginPos >= 0;
    }

    public CourseDetailInfo getCurrentCourseDetail() {
        return currentCourseDetail;
    }

    public void setCurrentCourseDetail(CourseDetailInfo currentCourseDetail) {
        this.currentCourseDetail = currentCourseDetail;
    }

    public CourseBriefInfo getCurrentCourseBrief() {
        return currentCourseBrief;
    }

    public void setCurrentCourseBrief(CourseBriefInfo currentCourseBrief) {
        this.currentCourseBrief = currentCourseBrief;
    }

    public void setMyCourseBriefs(ArrayList<CourseBriefInfo> myCourseBriefs) {
        this.myCourseBriefs = myCourseBriefs;
    }

    public Post getCurrentPost() {
        return currentPost;
    }

    public void setCurrentPost(Post currentPost) {
        this.currentPost = currentPost;
    }

    public void setAllCourseBriefs(ArrayList<CourseBriefInfo> allCourseBriefs) {
        this.allCourseBriefs = allCourseBriefs;
    }

    private void addCourseBrief(CourseBriefInfo courseBrief, boolean isMyCourse) {
        if(isMyCourse) {
            myCourseBriefs.add(courseBrief);
            allCourseBriefs.add(courseBrief);
        } else {
            allCourseBriefs.add(courseBrief);
        }
    }

}
