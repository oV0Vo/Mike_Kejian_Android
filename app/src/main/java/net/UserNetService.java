package net;

import android.os.AsyncTask;

import net.UserDataBase.UserDataBase;
import net.httpRequest.HttpRequest;
import net.picture.MessagePrint;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLDecoder;
import java.net.URLEncoder;
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


    public static boolean bindSchoolAccount(String userId,String schoolAccount,String schoolAccountPsd){

        HashMap<String,String> para=new HashMap<>();

        para.put("userId",userId);
        para.put("schoolAccount",schoolAccount);
        para.put("schoolAccountPsd", schoolAccountPsd);

        boolean state=Boolean.parseBoolean(httpRequest.sentGetRequest(baseUrl+"bindSchoolAccount",para));

        return state;

    }
    public  static user register(UserToken userToken){





        HashMap<String,String> para=new HashMap<String, String>();

        para.put(null, null);

        JSONObject jsonObject=new JSONObject(para);

        HashMap h=new HashMap();

        h.put("userToken", jsonObject.toString());




        String result=HttpRequest.getInstance().sentGetRequest(baseUrl+"register/",h);

        System.out.println("注册成功:"+result);


        try {

            JSONObject netResult = new JSONObject(result);

            result=netResult.getString("result");

        }catch(Exception e){

            e.printStackTrace();

        }






        return new user();
    }

    public static user getUser(UserToken userToken){



        /*
        用于登录的数据结构
         */
        HashMap<String,String> par = new HashMap();
        par.put("userId",userToken.getName());
        par.put("password", userToken.getPassword());



        String userData = httpRequest.sentGetRequest(baseUrl+"login/",par);




        if(userData.equals("")){

            System.out.println("get no user");

            return null;

        }else{
            try{
                JSONObject userDataJson = new JSONObject(userData);
                /*
                {"id":"1","school_identify":"1","name":"as","icon_url":"as",
                "school_id":"1","grade":"1","nick_name":"as","gender":"0",
                "major_id":"1","department_id":"1","identify":"1",
                "password":"1",
                "signal":"1","school_account_psd":"1"}
                 */
                HashMap<String,Object> userInfo = new HashMap();
                userInfo.put("id",userDataJson.getString("id"));
                userInfo.put("name",userDataJson.getString("name"));
                userInfo.put("gender",userDataJson.getString("gender"));
                userInfo.put("grade",userDataJson.getString("grade"));

                String icon=userDataJson.getString("icon_url").replaceAll("#","/");
                userInfo.put("icon",icon);
                userInfo.put("signal",userDataJson.getString("signal"));
                userInfo.put("identify",userDataJson.getString("identify"));
                userInfo.put("id",userDataJson.getInt("id"));
                userInfo.put("password",userDataJson.getString("password"));
                userInfo.put("nick_name",userDataJson.getString("nick_name"));
                userInfo.put("school_identify",userDataJson.getString("school_identify"));
                userInfo.put("school_id",userDataJson.getString("school_id"));
                userInfo.put("department_id",userDataJson.getString("department_id"));


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

//    /*
//     * description:修改用户信息
//     * return:bool
//     * $userInfoType:PASSWORD|SIGN_TEXT|ICON|NICKNAME
//     */
//    public function resetUserInfo($userId,$userInfoType,$newUserInfo){

    public static void setUserInfo(int userId,String type,String content){

        HashMap<String,String> map=new HashMap<>();


        map.put("userId",userId+"");
        map.put("userInfoType",type);


        try {


            MessagePrint.print("before replace:" + content);
            content=content.replaceAll("/", "#");
            MessagePrint.print("after replace:" + content);

            content = URLEncoder.encode(URLEncoder.encode(content, "utf-8"));
            MessagePrint.print("after encode:" + content);
        }catch (Exception e){
            e.printStackTrace();
        }
        map.put("newUserInfo",content);

        httpRequest.sentGetRequest(baseUrl+"resetUserInfo/",map);

    }
    public void getAttentionPost(String userId){

        HashMap<String,String> map=new HashMap<>();

        map.put("userid",userId);

        httpRequest.sentGetRequest(baseUrl+"",map);




    }
    public void getAttentionPeople(String userId){

        HashMap<String,String> map=new HashMap<>();

        map.put("userid",userId);

        httpRequest.sentGetRequest(baseUrl+"",map);



    }
    public void getAttentionCourse(String userId){

        HashMap<String,String> map=new HashMap<>();

        map.put("userid",userId);

        httpRequest.sentGetRequest(baseUrl+"",map);

    }

    public void deleteAttention(String type,String id){

        HashMap<String,String> map=new HashMap<>();

        map.put(type,id);

        httpRequest.sentGetRequest(baseUrl+"",map);

    }

    public void attention(String type,String id){

        HashMap<String,String> map=new HashMap<>();

        map.put(type,id);

        httpRequest.sentGetRequest(baseUrl+"",map);

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
