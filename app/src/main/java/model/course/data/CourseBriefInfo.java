package model.course.data;

/**
 * Created by violetMoon on 2015/9/10.
 */
public class CourseBriefInfo {
    private String courseId;
    private String courseName;
    private String academyName;
    private int progressWeek;
    private byte[] courseImage;

    public byte[] getCourseImage() {
        return courseImage;
    }

    public void setCourseImage(byte[] courseImage) {
        this.courseImage = courseImage;
    }

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

}
