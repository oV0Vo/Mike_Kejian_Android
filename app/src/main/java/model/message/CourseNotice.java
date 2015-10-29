package model.message;

/**
 * Created by I322233 on 9/12/2015.
 */
public class CourseNotice {
    private int id;
    private String courseName;
    private String content;
    private String publisher;
    private int courseId;

    public String getCourseName() {
        return courseName;
    }

    public String getContent() {
        return content;
    }

    public String getPublisher() {
        return publisher;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public int getId(){
        return this.id;
    }

    private String publishTime;
    public CourseNotice(int id, String courseName,String content,String publisher,String publishTime,int courseId){
        this.id = id;
        this.courseName = courseName;
        this.content = content;
        this.publisher = publisher;
        this.publishTime = publishTime;
        this.courseId = courseId;

    }
    public int getCourseId(){
        return this.courseId;
    }
    private void resetContent(){
        if(this.content.length() >= 71){
            this.content = this.content.substring(0,69)+"...";
        }
    }

}
