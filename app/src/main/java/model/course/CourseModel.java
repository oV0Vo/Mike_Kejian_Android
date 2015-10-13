package model.course;

import net.course.CourseInfoNetService;
import net.course.CourseQuestionNetService;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.kejian.mike.mike_kejian_android.dataType.course.CourseAnnoucement;
import com.kejian.mike.mike_kejian_android.dataType.course.CourseBriefInfo;
import com.kejian.mike.mike_kejian_android.dataType.course.CourseDetailInfo;
import com.kejian.mike.mike_kejian_android.dataType.course.UserTypeInCourse;
import com.kejian.mike.mike_kejian_android.dataType.course.question.BasicQuestion;
import com.kejian.mike.mike_kejian_android.dataType.course.question.CurrentQuestion;
import model.campus.Post;
import util.NeedAsyncAnnotation;
import util.NetOperateResultMessage;

/**
 * Created by violetMoon on 2015/9/8.
 */
public class CourseModel {
    public static final String ARG_COURSE_ID = "courseId";

    private static CourseModel instance;
    private ArrayList<CourseBriefInfo> myCourseBriefs;
    private ArrayList<CourseBriefInfo> allCourseBriefs;

    private static final int MY_COURSE_BRIEF_UPDATE_NUM = 20;
    private static final int ALL_COURSE_BRIEF_UPDATE_NUM = 20;

    private CurrentCourseModel currentCourse;
    private UserTypeInCourse currentUserType;

    private String sidMock = "131250012";
    private String schoolIdMock = "南京大学";

    private CourseModel() {

    }

    @NeedAsyncAnnotation
    public static boolean createInstance() {
        instance = new CourseModel();
        boolean initSuccess = instance.init();
        return initSuccess;
    }

    public static CourseModel getInstance() {
        return instance;
    }

    /*
    以后做课程缓存的时候从文件读取课程数据可以在这个方法里面进行，不过这个类目前之考虑单线程的修改，
    如果是网络和文件都要修改这个类，CourseListFragment也要修改
     */
    public boolean init() {
        myCourseBriefs = new ArrayList<CourseBriefInfo>();
        allCourseBriefs = new ArrayList<CourseBriefInfo>();
        return true;
    }

    public String getCurrentCourseId() {
        if(currentCourse != null) {
            return currentCourse.getCourseId();
        } else {
            return null;
        }
    }

    public ArrayList<CourseBriefInfo> getMyCourseBriefs() {
        return myCourseBriefs;
    }

    public ArrayList<CourseBriefInfo> getAllCourseBriefs() {
        return allCourseBriefs;
    }

/*
    public ArrayList<CourseBriefInfo> getMyCourseBriefs(int beginPos, int num) {
        return getSubList(beginPos, num, myCourseBriefs);
    }

    public ArrayList<CourseBriefInfo> getAllCourseBriefs(int beginPos, int num) {
        return getSubList(beginPos, num, allCourseBriefs);
    }*/

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

    @NeedAsyncAnnotation
    public ArrayList<CourseBriefInfo> updateMyCourseBriefs() {
        return updateMyCourseBriefs(Integer.MAX_VALUE, TimeUnit.SECONDS);
    }

    @NeedAsyncAnnotation
    public ArrayList<CourseBriefInfo> updateMyCourseBriefs(int time, TimeUnit timeUnit) {
        int beginPos = myCourseBriefs.size();
        int updateNum = MY_COURSE_BRIEF_UPDATE_NUM;
        ArrayList<CourseBriefInfo> updateInfos = CourseInfoNetService.getMyCourseBrief(sidMock, beginPos,
                updateNum, time, timeUnit);
        this.myCourseBriefs.addAll(updateInfos);
        return updateInfos;
    }

    @NeedAsyncAnnotation
    public ArrayList<CourseBriefInfo> updateAllCourseBriefs(){
        return updateAllCourseBriefs(Integer.MAX_VALUE, TimeUnit.SECONDS);
    }

    @NeedAsyncAnnotation
    public ArrayList<CourseBriefInfo> updateAllCourseBriefs(int time, TimeUnit timeUnit) {
        int beginPos = allCourseBriefs.size();
        int updateNum = ALL_COURSE_BRIEF_UPDATE_NUM;
        ArrayList<CourseBriefInfo> updateInfos = CourseInfoNetService.getAllCourseBrief(schoolIdMock,
                beginPos, updateNum, time, timeUnit);
        this.allCourseBriefs.addAll(updateInfos);
        return updateInfos;
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

    public CourseBriefInfo getCurrentCourseBrief() {
        return currentCourse.courseBrief;
    }
    public CourseDetailInfo getCurrentCourseDetail() {
        return currentCourse.courseDetail;
    }

    public void setCurrentCourseBrief(CourseBriefInfo courseBrief) {
        currentCourse = new CurrentCourseModel(courseBrief);
    }

    @NeedAsyncAnnotation
    public boolean updateCurrentCourseDetail() {
        return updateCurrentCourseDetail(Integer.MAX_VALUE, TimeUnit.SECONDS);
    }

    @NeedAsyncAnnotation
    public boolean updateCurrentCourseDetail(int time, TimeUnit timeUnit) {
        return currentCourse.updateCourseDetail(time, timeUnit);
    }

    public ArrayList<BasicQuestion> getHistoryQuestions() {
        return currentCourse.historyQuestions;
    }

    @NeedAsyncAnnotation
    public ArrayList<BasicQuestion> updateHistoryQuestions() {
        return updateHistoryQuestion(Integer.MAX_VALUE, TimeUnit.SECONDS);
    }

    @NeedAsyncAnnotation
    public ArrayList<BasicQuestion> updateHistoryQuestion(int time, TimeUnit timeUnit) {
        return currentCourse.updateHistoryQuestions(time, timeUnit);
    }

    public ArrayList<CurrentQuestion> getCurrentQuestions() {
        return currentCourse.currentQuestions;
    }

    @NeedAsyncAnnotation
    public ArrayList<CurrentQuestion> updateCurrentQuestions() {
        return updateCurrentQuestions(Integer.MAX_VALUE, TimeUnit.SECONDS);
    }

    @NeedAsyncAnnotation
    public ArrayList<CurrentQuestion> updateCurrentQuestions(int time, TimeUnit timeUnit) {
        return currentCourse.updateCurrentQuestions(time, timeUnit);
    }

    @NeedAsyncAnnotation
    public boolean addNewQuestion(CurrentQuestion question) {
        return CourseQuestionNetService.addNewQuestion(question) != null;
    }

    public ArrayList<Post> getCurrentCoursePosts() {
        return currentCourse.posts;
    }

    @NeedAsyncAnnotation
    public ArrayList<Post> updatePosts() {
        return updatePosts(Integer.MAX_VALUE, TimeUnit.SECONDS);
    }

    @NeedAsyncAnnotation
    public ArrayList<Post> updatePosts(int time, TimeUnit timeUnit) {
        return currentCourse.updatePosts(time, timeUnit);
    }

    public Post getCurrentPost() {
        return currentCourse.currentPost;
    }

    @NeedAsyncAnnotation
    @Deprecated
    public void setCurrentPost(Post currentPost) {
        this.currentCourse.currentPost = currentPost;
    }

    @NeedAsyncAnnotation
    public boolean updateCourseDetail() {
        return updateCourseDetail(Integer.MAX_VALUE, TimeUnit.SECONDS);
    }

    @NeedAsyncAnnotation
    public boolean updateUserTypeInCurrentCourse() {
        currentUserType = CourseInfoNetService.getUserTypeInCourse(currentCourse.courseId,
                sidMock);
        return true;
    }

    public UserTypeInCourse getUserTypeInCurrentCourse() {
        return currentUserType;
    }

    @NeedAsyncAnnotation
    public boolean updateCourseDetail(int time, TimeUnit timeUnit) {
        return currentCourse.updateCourseDetail(time, timeUnit);
    }

    @NeedAsyncAnnotation
    public NetOperateResultMessage newAnnoucement(CourseAnnoucement annoucement) {
        return CourseInfoNetService.newAnnoucement(annoucement.getCourseId(), annoucement.getPersonId()
                , annoucement.getTitle(), annoucement.getContent());
    }

    public ArrayList<CourseAnnoucement> getAnnoucs() {
        return currentCourse.annoucs;
    }

    public CourseAnnoucement getOnTopAnnouc() {
        ArrayList<CourseAnnoucement> annoucs = currentCourse.annoucs;
        for(CourseAnnoucement annouc: annoucs)
            if(annouc.isOnTop())
                return annouc;
        return null;
    }

    @NeedAsyncAnnotation
    public ArrayList<CourseAnnoucement> updateAnnoucs() {
        return updateAnnoucs(Integer.MAX_VALUE, TimeUnit.SECONDS);
    }

    @NeedAsyncAnnotation
    public ArrayList<CourseAnnoucement> updateAnnoucs(int time, TimeUnit timeUnit) {
        return currentCourse.updateAnnouc(time, timeUnit);
    }

    public void setStatsFocusQuestion(BasicQuestion question) {
        currentCourse.statsFocusQuesiton = question;
    }

    public void setAnswerFocusQuestion(BasicQuestion question) {
        currentCourse.answerFocusQuestion = question;
    }

    public BasicQuestion getStatsFocusQuestion() {
        return currentCourse.statsFocusQuesiton;
    }

    public BasicQuestion getAnswerFocusQuestion() {
        return currentCourse.answerFocusQuestion;
    }

    public ArrayList<String> getAllCourseTypeNamesMock() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("通识课");
        names.add("通修课");
        names.add("核心课");
        names.add("平台课");
        return names;
    }

    private class CurrentCourseModel {

        private String courseId;

        private CourseBriefInfo courseBrief;
        private CourseDetailInfo courseDetail;

        private ArrayList<BasicQuestion> historyQuestions;
        private ArrayList<CurrentQuestion> currentQuestions;

        private BasicQuestion statsFocusQuesiton;
        private BasicQuestion answerFocusQuestion;

        private ArrayList<Post> posts;
        private Post currentPost;

        private ArrayList<CourseAnnoucement> annoucs;

        private static final int HISTORY_QUESTION_UPDATE_NUM = 20;
        private static final int CURRENT_QUESTION_UPDATE_NUM = 5;
        private static final int ANNOUC_UPDATE_NUM = 5;

        public CurrentCourseModel(CourseBriefInfo courseBrief) {
            historyQuestions = new ArrayList<BasicQuestion>();
            currentQuestions = new ArrayList<CurrentQuestion>();
            annoucs = new ArrayList<CourseAnnoucement>();
            posts = new ArrayList<Post>();
            this.courseBrief = courseBrief;
            this.courseId = courseBrief.getCourseId();
        }

        public String getCourseId() {
            return courseId;
        }

        public boolean updateCourseDetail(int time, TimeUnit timeUnit) {
            CourseDetailInfo newCourseDetail = CourseInfoNetService.getCourseDetail(courseId, time, timeUnit);
            if (newCourseDetail != null) {
                this.courseDetail = newCourseDetail;
                return true;
            } else {
                return false;
            }
        }

        public ArrayList<BasicQuestion> updateHistoryQuestions(int time, TimeUnit timeUnit) {
            int beginPos = historyQuestions.size();
            int updateNum = HISTORY_QUESTION_UPDATE_NUM;
            ArrayList<BasicQuestion> updateInfos = CourseQuestionNetService.getHistroryQuestions(courseId,
                    beginPos, updateNum, time, timeUnit);
            if(updateInfos != null) {
                historyQuestions.addAll(updateInfos);
            }
            return updateInfos;
        }

        public ArrayList<CurrentQuestion> updateCurrentQuestions(int time, TimeUnit timeUnit) {
            int beginPos = currentQuestions.size();
            int updateNum = HISTORY_QUESTION_UPDATE_NUM;
            ArrayList<CurrentQuestion> updateInfos = CourseQuestionNetService.getCurrentQuestions(courseId,
                    beginPos, updateNum, time, timeUnit);
            if(updateInfos != null) {
                currentQuestions.addAll(updateInfos);
            }
            return updateInfos;
        }

        public ArrayList<Post> updatePosts(int time, TimeUnit timeUnit) {
            int beginPos = currentQuestions.size();
            int updateNum = HISTORY_QUESTION_UPDATE_NUM;
            ArrayList<Post> updateInfos = CourseInfoNetService.getCoursePosts(courseId,
                    beginPos, updateNum, time, timeUnit);
            if(updateInfos != null) {
                posts.addAll(updateInfos);
            }
            return updateInfos;

        }

        public ArrayList<CourseAnnoucement> updateAnnouc(int time, TimeUnit timeUnit) {
            int beginPos = annoucs.size();
            int updateNum = ANNOUC_UPDATE_NUM;
            ArrayList<CourseAnnoucement> updateInfos = CourseInfoNetService.getAnnouc(courseId,
                    beginPos, updateNum, time, timeUnit);
            if(updateInfos != null) {
                annoucs.addAll(updateInfos);
            }
            return updateInfos;
        }

    }

}
