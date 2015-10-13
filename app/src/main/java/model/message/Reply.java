package model.message;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by I322233 on 9/13/2015.
 */
public class Reply {
    protected int id;
    protected String replyer;
    protected String post;
    protected String replyTime;
    protected Timestamp timestamp;
    public Reply(int id,String replyer,String post,Timestamp timestamp){
        this.id = id;
        this.replyer = replyer;
        this.post = post;
        this.timestamp = timestamp;
        this.setTime();
        this.resetPost();
    }
    protected void setTime(){
        Date nowTime = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        DateFormat todayFormat = new SimpleDateFormat("HH:mm:ss");
        DateFormat otherDayFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(dateFormat.format(nowTime).equals(dateFormat.format(this.timestamp))){
            this.replyTime = "今天  "+todayFormat.format(this.timestamp);
        }else{
            this.replyTime = otherDayFormat.format(this.timestamp);
        }
    }
    protected void resetPost(){
        if(this.post.length() >= 17){
            this.post = "\""+this.post.substring(0,16) + "...\"";
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
    public int getId(){
        return this.id;
    }
}
