package model.user;

import java.util.Date;

/**
 * Created by kisstheraik on 15/9/23.
 */
public class CourseBrief {


    private CourseInfoType type;
    private String courseName;
    private String content;
    private String user;
    private String name;
    private Date time;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public CourseInfoType getType() {
        return type;
    }

    public void setType(CourseInfoType type) {
        this.type = type;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }









}
