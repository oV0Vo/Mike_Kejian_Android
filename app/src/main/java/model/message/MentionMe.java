package model.message;

import java.sql.Timestamp;

/**
 * Created by I322233 on 9/15/2015.
 */
public class MentionMe extends Reply {
    public MentionMe(String mentioner, String post, Timestamp timestamp){
        super(mentioner,post,timestamp);
    }

    @Override
    protected void resetPost() {
        if(this.post.length() >= 14){
            this.post = "\""+this.post.substring(0,13) + "...\"";
        }
    }
}
