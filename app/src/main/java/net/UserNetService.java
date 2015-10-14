package net;

import android.os.AsyncTask;

import net.UserDataBase.UserDataBase;
import net.httpRequest.HttpRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import bl.UserBLResult;
import model.user.AttentionList;
import model.user.Friend;
import model.user.Invitee;
import model.user.UserToken;
import model.user.user;


/**
 * Created by kisstheraik on 15/9/23.
 */
public class UserNetService {
    private static String baseUrl = "http://112.124.101.41/mike_server_v02/index.php/Home/User/";
    private static HttpRequest httpRequest = HttpRequest.getInstance();

    private UserNetService instance=new UserNetService();

    private static UserDataBase userDataBase=new UserDataBase();

    private UserNetService(){


    }


    private user register(UserToken userToken){





        HashMap<String,String> para=new HashMap<String, String>();

        para.put(null, null);

        JSONObject jsonObject=new JSONObject(para);

        HashMap h=new HashMap();

        h.put("userToken", jsonObject.toString());


        String result=HttpRequest.getInstance().sentGetRequest(baseUrl+"register/",h);


        try {

            JSONObject netResult = new JSONObject(result);

            result=netResult.getString("result");

        }catch(Exception e){

            e.printStackTrace();

        }






        return new user();
    }

    public static user getUser(UserToken userToken){

        System.out.println("search in database " + userToken.getName());

        HashMap<String,String> par = new HashMap();
        par.put("userId",userToken.getName());
        par.put("password",userToken.getPassword());


        System.out.println("Start http request!");


        String userData = httpRequest.sentGetRequest(baseUrl+"login/",par);

        System.out.println("user data:"+userData);


        if(userData.equals("")){
            return null;
        }else{
            try{
                JSONObject userDataJson = new JSONObject(userData);
                HashMap<String,Object> userInfo = new HashMap();
                userInfo.put("name",userDataJson.getString("name"));
                userInfo.put("gender",userDataJson.getString("gender"));
                userInfo.put("grade",userDataJson.getString("grade"));
                userInfo.put("icon",userDataJson.getString("icon_url"));
                userInfo.put("sign",userDataJson.getString("signal"));
                userInfo.put("identify",userDataJson.getString("identify"));
                userInfo.put("id",userDataJson.getInt("id"));
                userInfo.put("password",userDataJson.getString("password"));
                return new user(userInfo);
            }catch (JSONException e){
                e.printStackTrace();
                return null;
            }
        }
//        HashMap userInfo=userDataBase.getUser(userToken.getName(),userToken.getPassword());
//
//
//        if(userInfo!=null){
//
//            return new user(userInfo);
//
//        }
//        else{
//
//            return null;
//
//        }

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
