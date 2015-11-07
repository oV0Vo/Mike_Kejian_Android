package com.kejian.mike.mike_kejian_android.dataType.course;

import java.util.ArrayList;

/**
 * Created by violetMoon on 2015/9/10.
 */
public class CourseDetailInfo {
    private String courseId;
    private String courseName;
    private String courseType;
    private String timeAndPlace;
    private String academyId;
    private String accademyName;
    private ArrayList<String> teacherIds;
    private ArrayList<String> teacherNames;
    private ArrayList<String> assistantIds;
    private ArrayList<String> assistantNames;
    private int currentStudents;
    private String outline;
    private String teachContent;
    private ArrayList<String> references;

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTimeAndPlace() {
        return timeAndPlace;
    }

    public void setTimeAndPlace(String timeAndPlace) {
        this.timeAndPlace = timeAndPlace;
    }

    public String getCourseType() {
        return courseType;
    }

    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    public String getAcademyId() {
        return academyId;
    }

    public void setAcademyId(String academyId) {
        this.academyId = academyId;
    }

    public ArrayList<String> getAssistantIds() {
        return assistantIds;
    }

    public void setAssistantIds(ArrayList<String> assistantIds) {
        this.assistantIds = assistantIds;
    }

    public ArrayList<String> getAssistantNames() {
        return assistantNames;
    }

    public void setAssistantNames(ArrayList<String> assistantNames) {
        this.assistantNames = assistantNames;
    }

    public String getAccademyName() {
        return accademyName;
    }

    public void setAccademyName(String accademyName) {
        this.accademyName = accademyName;
    }

    public ArrayList<String> getTeacherIds() {
        return teacherIds;
    }

    public void setTeacherIds(ArrayList<String> teacherIds) {
        this.teacherIds = teacherIds;
    }

    public ArrayList<String> getTeacherNames() {
        return teacherNames;
    }

    public void setTeacherNames(ArrayList<String> teacherNames) {
        this.teacherNames = teacherNames;
    }

    public int getCurrentStudents() {
        return currentStudents;
    }

    public void setCurrentStudents(int currentStudents) {
        this.currentStudents = currentStudents;
    }

    public String getOutline() {
        return outline;
    }

    public void setOutline(String outline) {
        this.outline = outline;
    }

    public String getTeachContent() {
        return teachContent;
    }

    public void setTeachContent(String teachContent) {
        this.teachContent = teachContent;
    }

    public ArrayList<String> getReferences() {
        return references;
    }

    public void setReferences(ArrayList<String> references) {
        this.references = references;
    }

}
