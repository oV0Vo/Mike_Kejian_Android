package model.message;

import java.sql.Timestamp;

/**
 * Created by I322233 on 9/15/2015.
 */
public class MentionMe extends Reply {
    public MentionMe(int id, String mentioner, String post, String replyTime){
        super(id, mentioner,post,replyTime);
    }

    @Override
    protected void resetPost() {
        if(this.post.length() >= 14){
            this.post = "\""+this.post.substring(0,13) + "...\"";
        }
    }
}
