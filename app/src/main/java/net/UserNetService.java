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

    public static HashMap<String,ArrayList<String>> getCityAndSchool(){


        HashMap<String,ArrayList<String>> list=new HashMap<String, ArrayList<String>>();
        ArrayList schoolList1=new ArrayList();
        schoolList1.add("南京大学");
        schoolList1.add("东南大学");
        schoolList1.add("南京航空航天大学");
        schoolList1.add("南京邮电大学");
        schoolList1.add("南京林业大学");
        schoolList1.add("河海大学");
        schoolList1.add("南京财经大学");

        ArrayList schoolList2=new ArrayList();
        schoolList2.add("上海交通大学");
        schoolList2.add("复旦大学");
        schoolList2.add("同济大学");
        schoolList2.add("上海财经大学");
        schoolList2.add("上海外国语大学");
        schoolList2.add("上海大学");
        schoolList2.add("华东理工大学");

        ArrayList schoolListInBeijing=new ArrayList();
        schoolListInBeijing.add("北京大学");
        schoolListInBeijing.add("清华大学");
        schoolListInBeijing.add("北京航空航天大学");
        schoolListInBeijing.add("北京邮电大学");
        schoolListInBeijing.add("中央财经大学");
        schoolListInBeijing.add("中国人民大学");
        schoolListInBeijing.add("中央民族大学");


        list.put("南京", schoolList1);
        list.put("上海",schoolList2);
        list.put("北京",schoolListInBeijing);

        return list;

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
