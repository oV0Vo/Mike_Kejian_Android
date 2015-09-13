package model.message;

/**
 * Created by I322233 on 9/12/2015.
 */
public class CourseNotice {
    private String courseName;
    private String content;
    private String publisher;

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

    private String publishTime;
    public CourseNotice(String courseName,String content,String publisher,String publishTime){
        this.courseName = courseName;
        this.content = content;
        this.publisher = publisher;
        this.publishTime = publishTime;

    }
    private void resetContent(){
        if(this.content.length() >= 71){
            this.content = this.content.substring(0,69)+"...";
        }
    }

}
