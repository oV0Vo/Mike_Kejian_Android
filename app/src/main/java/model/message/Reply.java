package model.message;

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
    protected String adjustTime;
    protected String iconUrl;
    protected int postId;
    protected String localIconPath;
    protected int replyerId;
    public Reply(int id,String replyer,String post,String replyTime, String iconUrl, int post_id, int replyerId){
        this.id = id;
        this.replyer = replyer;
        this.post = post;
        this.replyTime = replyTime;
        this.iconUrl = iconUrl;
        this.postId = post_id;
        this.replyerId = replyerId;
        this.setLocalIconPath();
        this.setAdjustTime();
        this.resetPost();
    }
    protected void setLocalIconPath(){
        this.localIconPath = this.iconUrl.replace(".","").replace(":","").replace("/","") + "#"+"user_id"+"#"+this.replyerId;
    }
    public String getLocalIconPath(){
        return this.localIconPath;
    }
    protected void setAdjustTime(){
        Date nowTime = new Date();
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String[] replyTimes = this.replyTime.split(" ");
        String replyDate = replyTimes[0];
        String replyTime = replyTimes[1];
        if(dateFormat.format(nowTime).equals(replyDate)){
            this.adjustTime = "今天  "+replyTime;
        }else{
            this.adjustTime = this.replyTime;
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
    public int getId(){
        return this.id;
    }
    public String getAdjustTime(){
        return this.adjustTime;
    }
    public String getIconUrl(){
        return this.iconUrl;
    }
    public int getPostId(){
        return this.postId;
    }
}
