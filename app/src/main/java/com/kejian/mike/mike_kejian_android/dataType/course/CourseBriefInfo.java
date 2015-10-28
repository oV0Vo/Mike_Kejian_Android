package com.kejian.mike.mike_kejian_android.dataType.course;

import java.util.ArrayList;

/**
 * Created by violetMoon on 2015/9/10.
 */
public class CourseBriefInfo {
    private String courseId;
    private String courseName;
    private String academyName;
    private String courseType;
    private ArrayList<String> teacherNames;
    private String imageUrl;

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

    public String getCourseType() {
        return courseType;
    }

    public ArrayList<String> getTeacherNames() {
        return teacherNames;
    }

    public void setTeacherNames(ArrayList<String> teacherNames) {
        this.teacherNames = teacherNames;
    }

    public void setAcademyName(String academyName) {
        this.academyName = academyName;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }
}
