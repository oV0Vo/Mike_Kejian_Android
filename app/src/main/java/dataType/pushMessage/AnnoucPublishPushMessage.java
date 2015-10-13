package dataType.pushMessage;

/**
 * Created by violetMoon on 2015/10/12.
 */
public class AnnoucPublishPushMessage extends PushMessage{

    private String courseId;

    private String courseName;

    private String annoucId;

    private String annoucContent;

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

    public String getAnnoucId() {
        return annoucId;
    }

    public void setAnnoucId(String annoucId) {
        this.annoucId = annoucId;
    }

    public String getAnnoucContent() {
        return annoucContent;
    }

    public void setAnnoucContent(String annoucContent) {
        this.annoucContent = annoucContent;
    }

}
