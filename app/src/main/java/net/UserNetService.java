package net;

import net.UserDataBase.UserDataBase;

import java.util.ArrayList;
import java.util.HashMap;

import bl.UserBLResult;
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

        System.out.println("search in database "+userToken.getName());


        HashMap userInfo=userDataBase.getUser(userToken.getName(),userToken.getPassword());


        if(userInfo!=null){

            return new user(userInfo);

        }
        else{

            return null;

        }





    }

    public AttentionList<Friend> getAttentionFriendList(UserToken userToken){

        return null;

    }
    public AttentionList[] getAttentionLists(UserToken user){

        return null;

    }

    public static UserBLResult addNewUser(UserToken userToken){

        HashMap userInfo=new HashMap();
        userInfo.put("name", userToken.getName());
        userInfo.put("phoneNumber", userToken.getName());
        userInfo.put("password",userToken.getPassword());

        int id=userDataBase.addNewUser(userToken.getName(),userInfo);

        return UserBLResult.REGISTER_SUCCEED;


    }



}
