package model.course.data;

import java.util.Date;

/**
 * Created by violetMoon on 2015/9/28.
 */
public class CourseSignInRecord {

    private Date beginTime;
    private Date endTime;
    private String teacherName;
    private boolean hasSignIn;

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public java.lang.String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(java.lang.String teacherName) {
        this.teacherName = teacherName;
    }

    public boolean isHasSignIn() {
        return hasSignIn;
    }

    public void setHasSignIn(boolean hasSignIn) {
        this.hasSignIn = hasSignIn;
    }

}
