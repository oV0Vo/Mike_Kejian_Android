package net;

import net.UserDataBase.UserDataBase;

import java.util.ArrayList;
import java.util.HashMap;

import model.user.AttentionList;
import model.user.Friend;
import model.user.UserToken;
import model.user.user;


/**
 * Created by kisstheraik on 15/9/23.
 */
public class UserNetService {



    private UserNetService instance=new UserNetService();

    private static UserDataBase userDataBase=new UserDataBase();

    private UserNetService(){


    }

    private user register(){

        user user=new user(null);

        return user;
    }

    public static user getUser(UserToken userToken){

        System.out.print("search in database "+userToken.getName());


        HashMap userInfo=userDataBase.getUser(userToken.getName());


        if(userInfo!=null){

            return new user(userInfo);
        }
        else{

            return null;

        }





    }

    public AttentionList<Friend> getAttentionList(UserToken userToken){

        return null;

    }


}
