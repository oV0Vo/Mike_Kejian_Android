package model.course;

import java.util.ArrayList;

/**
 * Created by violetMoon on 2015/9/8.
 */
public class Course {

    private int courseId;
    private String name;
    private String academyName;
    private int progressWeek;
    private String teacherName;
    private String annoucement;
    private String briefIntro;
    private String teachContent;
    private ArrayList<String> references;

    private static int id;

    public Course() {
        courseId = id++;
    }

    public int getCourseId() {
        return courseId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAcademyName() {
        return academyName;
    }

    public void setAcademyName(String academyName) {
        this.academyName = academyName;
    }

    public int getProgressWeek() {
        return progressWeek;
    }

    public void setProgressWeek(int progressWeek) {
        this.progressWeek = progressWeek;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getAnnoucement() {
        return annoucement;
    }

    public void setAnnoucement(String annoucement) {
        this.annoucement = annoucement;
    }

    public String getBriefIntro() {
        return briefIntro;
    }

    public void setBriefIntro(String briefIntro) {
        this.briefIntro = briefIntro;
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
