package model.user;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by kisstheraik on 15/9/17.
 */
public class UserPost {

    private String title;
    private String content;
    private ArrayList<Invitee> inviteList;
    private Date postDate;
    private user user;

    public UserPost(user user){

        this.user=user;
        inviteList=new ArrayList<Invitee>();

    }

    public UserPost(user user,String title,String content){

        this.user=user;
        this.title=title;
        this.content=content;


    }


    public void addTitle(String title){

        this.title=title;

    }

    public void addContent(String content){

        this.content=content;

    }

    public ResultMessage invite(Invitee invitee ){

        boolean result=false;
        if(inviteList!=null){
            result=inviteList.add(invitee);
        }

        return result==false?ResultMessage.OPERATION_FAILED:ResultMessage.OPERATION_SUCCEED;

    }




}
