package model.course;

import java.util.ArrayList;

/**
 * Created by violetMoon on 2015/9/10.
 */
public class CourseDetailInfo {
    private String courseId;
    private CourseType courseType;
    private ArrayList<String> teacherIds;
    private ArrayList<String> teacherNames;
    private CourseAnnoucement annoucment;
    private ArrayList<Object> tiezis;
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

    public CourseType getCourseType() {
        return courseType;
    }

    public void setCourseType(CourseType courseType) {
        this.courseType = courseType;
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

    public CourseAnnoucement getAnnoucment() {
        return annoucment;
    }

    public void setAnnoucment(CourseAnnoucement annoucment) {
        this.annoucment = annoucment;
    }

    public int getCurrentStudents() {
        return currentStudents;
    }

    public void setCurrentStudents(int currentStudents) {
        this.currentStudents = currentStudents;
    }

    public ArrayList<Object> getTiezis() {
        return tiezis;
    }

    public void setTiezis(ArrayList<Object> tiezis) {
        this.tiezis = tiezis;
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
