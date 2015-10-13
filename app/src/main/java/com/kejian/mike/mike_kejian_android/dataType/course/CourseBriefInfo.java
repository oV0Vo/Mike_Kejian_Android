package com.kejian.mike.mike_kejian_android.dataType.course;

/**
 * Created by violetMoon on 2015/9/10.
 */
public class CourseBriefInfo {
    private String courseId;
    private String courseName;
    private String academyName;
    private CourseType courseType;
    private int progressWeek;
    private byte[] courseImage;

    public java.lang.String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public java.lang.String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public java.lang.String getAcademyName() {
        return academyName;
    }

    public CourseType getCourseType() {
        return courseType;
    }

    public int getProgressWeek() {
        return progressWeek;
    }

    public void setProgressWeek(int progressWeek) {
        this.progressWeek = progressWeek;
    }

    public void setAcademyName(String academyName) {
        this.academyName = academyName;
    }

    public byte[] getCourseImage() {
        return courseImage;
    }

    public void setCourseImage(byte[] courseImage) {
        this.courseImage = courseImage;
    }

    public void setCourseType(CourseType courseType) {
        this.courseType = courseType;
    }
}
