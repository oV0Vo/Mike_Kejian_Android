package net;

import java.util.ArrayList;

import model.user.AttentionList;
import model.user.Friend;
import model.user.UserToken;
import model.user.user;


/**
 * Created by kisstheraik on 15/9/23.
 */
public class UserNetService {

    private UserNetService instance=new UserNetService();

    private UserNetService(){


    }

    public user getUserInfo(UserToken userToken){

        user u=new user(null);


        return u;

    }

    public AttentionList<Friend> getAttentionList(UserToken userToken){

        return null;

    }


}
