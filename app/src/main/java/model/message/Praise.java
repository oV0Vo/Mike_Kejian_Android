package model.message;

import java.sql.Timestamp;

/**
 * Created by I322233 on 9/15/2015.
 */
public class Praise extends Reply{
    public Praise(int id,String praiser, String post, String replyTime,String iconUrl,int postId,int replyerId){
        super(id,praiser,post,replyTime,iconUrl,postId,replyerId);

    }
    protected void resetPost(){
        if(this.post.length() >= 12){
            this.post = "\""+this.post.substring(0,10) + "...\"";
        }
    }

}
