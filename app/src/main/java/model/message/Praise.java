package model.message;

import java.sql.Timestamp;

/**
 * Created by I322233 on 9/15/2015.
 */
public class Praise extends Reply{
    public Praise(String praiser, String post, Timestamp timestamp){
        super(praiser,post,timestamp);

    }
    protected void resetPost(){
        if(this.post.length() >= 12){
            this.post = this.post.substring(0,10) + "...";
        }
    }

}
