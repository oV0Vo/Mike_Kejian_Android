package model.course;

import android.util.Log;

import net.course.CourseAnnoucNetService;
import net.course.CourseInfoNetService;
import net.course.CoursePostNetService;
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
import model.user.Global;
import model.user.user;
import util.NeedAsyncAnnotation;
import util.NetOperateResultMessage;

/**
 * Created by violetMoon on 2015/9/8.
 */
public class CourseModel {

    private static final String TAG = "CourseModel";

    private static CourseModel instance;
    private ArrayList<CourseBriefInfo> myCourseBriefs;
    private ArrayList<CourseBriefInfo> allCourseBriefs;

    private ArrayList<String> allAcademyNames;
    private ArrayList<String> allCourseTypes;

    private static final int DEFAULT_ALL_COURSE_INIT_NUM = 100;
    private static final int ALL_COURSE_BRIEF_UPDATE_NUM = 20;

    private CurrentCourseModel currentCourse;
    private UserTypeInCourse currentUserType;

    private CourseAnnoucement focusAnnouc;

    private boolean noMoreAllCourseBriefs;

    private CourseModel() {
        myCourseBriefs = new ArrayList<CourseBriefInfo>();
        allCourseBriefs = new ArrayList<CourseBriefInfo>(DEFAULT_ALL_COURSE_INIT_NUM);
        allAcademyNames = new ArrayList<String>();
        allCourseTypes = new ArrayList<String>();
    }

    public static CourseModel getInstance() {
        if(instance == null)
            instance = new CourseModel();
        return instance;
    }

    public String getCurrentCourseId() {
        if(currentCourse != null) {
            return currentCourse.getCourseId();
        } else {
            return null;
        }
    }

    public ArrayList<String> getAllAcademyNames() {
        return allAcademyNames;
    }

    public ArrayList<String> getAllCourseTypes() {
        return allCourseTypes;
    }

    public ArrayList<CourseBriefInfo> getMyCourseBriefs() {
        return myCourseBriefs;
    }

    public ArrayList<CourseBriefInfo> getAllCourseBriefs() {
        return allCourseBriefs;
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
        ArrayList<CourseBriefInfo> updateInfos = CourseInfoNetService.getMyCourseBrief();
        this.myCourseBriefs.addAll(updateInfos);
        return updateInfos;
    }

    public boolean hasMoreAllCourseBriefs() {
        return !noMoreAllCourseBriefs;
    }

    @NeedAsyncAnnotation
    public ArrayList<CourseBriefInfo> updateAllCourseBriefs(){
        return updateAllCourseBriefs(Integer.MAX_VALUE, TimeUnit.SECONDS);
    }

    @NeedAsyncAnnotation
    public ArrayList<CourseBriefInfo> updateAllCourseBriefs(int time, TimeUnit timeUnit) {
        String lastCourseId = null;
        if(allCourseBriefs.size() != 0)
            lastCourseId = allCourseBriefs.get(allCourseBriefs.size() - 1).getCourseId();

        int updateNum = ALL_COURSE_BRIEF_UPDATE_NUM;

        String schoolId = getSchoolId();
        ArrayList<CourseBriefInfo> updateInfos = CourseInfoNetService.getAllCourseBrief(schoolId,
                lastCourseId, updateNum, time, timeUnit);
        if(updateInfos != null) {
            if(updateInfos.size() != 0)
                allCourseBriefs.addAll(updateInfos);
            else
                noMoreAllCourseBriefs = true;
        }
        return updateInfos;
    }

    private String getSchoolId() {
        user currentUser = (user)Global.getObjectByName("user");
        if(currentUser == null)
            return null;
        return currentUser.getSchoolInfo().getId();
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
        return currentCourse.courseDetail;
    }

    public void setCurrentCourseId(String courseId) {
        currentCourse = new CurrentCourseModel(courseId);
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
        //目前界面上涉及时钟计数的有些问题，用多个的话可能会出现界面上的bug
        ArrayList<CurrentQuestion> questions = new ArrayList<CurrentQuestion>();
        ArrayList<CurrentQuestion> currentQuestions = currentCourse.currentQuestions;
        if(currentQuestions != null && currentQuestions.size() != 0)
            questions.add(currentQuestions.get(0));
        return questions;
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
        return CourseQuestionNetService.addNewQuestion(question);
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
        currentUserType = CourseInfoNetService.getUserTypeInCourse(currentCourse.courseId);
        return currentUserType != null;
    }

    public UserTypeInCourse getUserTypeInCurrentCourse() {
        return currentUserType;
    }

    @NeedAsyncAnnotation
    public boolean updateCourseDetail(int time, TimeUnit timeUnit) {
        return currentCourse.updateCourseDetail(time, timeUnit);
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

    public CourseAnnoucement getLatestAnnouc() {
        return currentCourse.getLatestAnnouc();
    }

    @NeedAsyncAnnotation
    public boolean updateAnnoucs() {
        return updateAnnoucs(Integer.MAX_VALUE, TimeUnit.SECONDS);
    }

    @NeedAsyncAnnotation
    public boolean updateAnnoucs(int time, TimeUnit timeUnit) {
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

    public void setCurrentFocusAnnouc(CourseAnnoucement annouc) {
        focusAnnouc = annouc;
    }

    public CourseAnnoucement getCurrentFocusAnnouc() {
        return focusAnnouc;
    }

    private class CurrentCourseModel {

        private String courseId;

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

        public CurrentCourseModel(String courseId) {
            historyQuestions = new ArrayList<BasicQuestion>();
            currentQuestions = new ArrayList<CurrentQuestion>();
            annoucs = new ArrayList<CourseAnnoucement>();
            posts = new ArrayList<Post>();
            this.courseId = courseId;
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
            ArrayList<BasicQuestion> newInfos = CourseQuestionNetService.getHistroryQuestions(courseId);
            if(newInfos != null) {
                historyQuestions.clear();
                historyQuestions.addAll(newInfos);
            }
            return newInfos;
        }

        public ArrayList<CurrentQuestion> updateCurrentQuestions(int time, TimeUnit timeUnit) {
            ArrayList<CurrentQuestion> newInfos = CourseQuestionNetService.getCurrentQuestions(courseId);
            if(newInfos != null) {
                historyQuestions.clear();
                currentQuestions.addAll(newInfos);
            }
            return newInfos;
        }

        public ArrayList<Post> updatePosts(int time, TimeUnit timeUnit) {
            ArrayList<Post> updateInfos = CoursePostNetService.getPosts(courseId);
            if(updateInfos != null) {
                posts.clear();
                posts.addAll(updateInfos);
                Log.i(TAG, "posts size " + Integer.toString(posts.size()));
            }
            return updateInfos;

        }

        public boolean updateAnnouc(int time, TimeUnit timeUnit) {
            ArrayList<CourseAnnoucement> updateInfos = CourseAnnoucNetService.getAnnoucs(courseId);
            if(updateInfos != null) {
                annoucs.clear();
                annoucs.addAll(updateInfos);
            }
            return updateInfos != null;
        }

        public CourseAnnoucement getLatestAnnouc() {
            if(annoucs.size() == 0) {
                return null;
            } else {
                return annoucs.get(0);
            }
        }
    }

}
