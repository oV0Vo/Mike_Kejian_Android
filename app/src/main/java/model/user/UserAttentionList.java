package model.user;

import java.io.Serializable;

/**
 * Created by kisstheraik on 15/9/23.
 */
public class UserAttentionList implements Serializable{

    private user user;
    private AttentionList<Friend> attentionPeopleList=new AttentionList<Friend>();
    private AttentionList<PostBrief> attentionPostList=new AttentionList<PostBrief>();
    private AttentionList<CourseBrief> attentionCourseList=new AttentionList<CourseBrief>();

    public UserAttentionList(){

    }

    public boolean addFriend(){
        return false;
    }

    public boolean addCourse(){
        return false;
    }

    public boolean addPost(){
        return false;
    }
}
