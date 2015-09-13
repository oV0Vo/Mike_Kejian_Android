package model.message;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by I322233 on 9/13/2015.
 */
public class Reply {
    private String replyer;
    private String post;
    private String replyTime;
    private Timestamp timestamp;
    public Reply(String replyer,String post,Timestamp timestamp){
        this.replyer = replyer;
        this.post = post;
        this.timestamp = timestamp;
        this.setTime();
        this.resetPost();
    }
    private void setTime(){
        Date nowTime = new Date();
        java.sql.Date nowDate = new java.sql.Date(nowTime.getTime());
        java.sql.Date date = new java.sql.Date(this.timestamp.getTime());
        DateFormat todayFormat = new SimpleDateFormat("HH:mm:ss");
        DateFormat otherDayFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(date.compareTo(nowDate) == 0){
            this.replyTime = "今天 "+todayFormat.format(this.timestamp);
        }else{
            this.replyTime = otherDayFormat.format(this.timestamp);
        }
    }
    private void resetPost(){
        if(this.post.length() >= 17){
            this.post = this.post.substring(0,17) + "...";
        }

    }
    public String getReplyer(){
        return this.replyer;
    }
    public String getPost(){
        return this.post;
    }
    public String getReplyTime(){
        return this.replyTime;
    }
}
