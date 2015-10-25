package net;

import android.os.AsyncTask;

import net.UserDataBase.UserDataBase;
import net.httpRequest.HttpRequest;
import net.picture.MessagePrint;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import bl.UserBLResult;
import model.user.AttentionList;
import model.user.CourseBrief;
import model.user.Friend;
import model.user.Invitee;
import model.user.PostBrief;
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


    //http://112.124.101.41/mike_server_v02/index.php/Home/User/bind/schoolAccount/131250114/schoolAccountPsd/132491/userId/1

    public static boolean bindSchoolAccount(String userId,String schoolAccount,String schoolAccountPsd){

        HashMap<String,String> para = new HashMap<>();

        para.put("userId",userId);
        para.put("schoolAccount",schoolAccount);
        para.put("schoolAccountPsd", schoolAccountPsd);
        String result=httpRequest.sentGetRequest(baseUrl+"bind/",para);

        System.out.println("绑定结果:"+result);


        boolean state=Boolean.parseBoolean(result);

        return state;

    }
    public  static UserBLResult register(UserToken userToken){


        /*
        'phone_num'=>$userToken['phoneNumber'],
            'name'=>$userToken['name'],
            'password'=>$userToken['password'],
            'school_id'=>$userToken['schoolNumber'],
         */



        HashMap<String,String> para=new HashMap<String, String>();

        para.put("phoneNumber", userToken.getPhoneNumber());
        para.put("name",userToken.getName());
        para.put("password",userToken.getPassword());
        para.put("schoolNumber","10284");

        JSONObject jsonObject = new JSONObject(para);

        HashMap h=new HashMap();

        String r=jsonObject.toString();

        System.out.println("register "+r);

        r=URLEncoder.encode(r);


        h.put("userToken", r);






        String result=HttpRequest.getInstance().sentGetRequest(baseUrl + "register/", h);

        System.out.println("注册成功:"+result);


        try {

            JSONObject netResult = new JSONObject(result);

            result=netResult.getString("state");

        }catch(Exception e){

            e.printStackTrace();

        }





        if(Boolean.parseBoolean(result)==true) {

            return UserBLResult.REGISTER_SUCCEED;
        }
        else{

            return UserBLResult.REGISTER_FAILED;
        }
    }
    public static user getUserInfo(String id){

        //http://112.124.101.41/mike_server_v02/index.php/Home/User/getUser/id/1
         /*
        用于登录的数据结构
         */
        HashMap<String,String> par = new HashMap();
        par.put("id",id);






        String userData = httpRequest.sentGetRequest(baseUrl+"getUser/",par);




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

    public static user getUser(UserToken userToken){


        /*
        用于登录的数据结构
         */

        HashMap<String,String> par = new HashMap();


        par.put("phoneNumber",userToken.getPhoneNumber());


        par.put("password", userToken.getPassword());

        System.out.println(userToken.getName() + "  " + userToken.getPassword());





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
//        list.put("上海",schoolList2);
//        list.put("北京",schoolListInBeijing);

        return list;

    }

//    /*
//     * description:修改用户信息
//     * return:bool
//     * $userInfoType:PASSWORD|SIGN_TEXT|ICON|NICKNAME
//     */
//    public function resetUserInfo($userId,$userInfoType,$newUserInfo){

    public static boolean setUserInfo(int userId,String type,String content){

        HashMap<String,String> map=new HashMap<>();


        map.put("userId",userId+"");
        map.put("userInfoType", type);


        try {


            MessagePrint.print("before replace:" + content);
            content=content.replaceAll("/", "\\/");
            MessagePrint.print("after replace:" + content);

            content = URLEncoder.encode(URLEncoder.encode(content, "utf-8"));
            MessagePrint.print("after encode:" + content);
        }catch (Exception e){
            e.printStackTrace();
        }
        map.put("newUserInfo", content);

        return Boolean.parseBoolean(httpRequest.sentGetRequest(baseUrl+"resetUserInfo/",map));

    }
    public static ArrayList<PostBrief> getAttentionPost(String userId){

        ArrayList<PostBrief> postList=new ArrayList<>();


//http://112.124.101.41/mike_server_v02/index.php/Home/UserAttentionList/getAttentionPost/userId/1
        HashMap<String,String> map=new HashMap<>();

        map.put("userId", userId);

       String result= httpRequest.sentGetRequest("http://112.124.101.41/mike_server_v02/index.php/Home/UserAttentionList/getAttentionPost/", map);

        try{


        JSONArray jsonArray=new JSONArray(result);

            int length=jsonArray.length();

            for(int i=0;i<length;i++){

                JSONObject jsonObject=(JSONObject)jsonArray.get(i);



                PostBrief postBrief=new PostBrief();

                postBrief.setUserName(jsonObject.getString("userName"));
                postBrief.setPostName(jsonObject.getString("title"));
                //postBrief.setPublishTime(null);
                postBrief.setId(jsonObject.getString("id"));
                postBrief.setUserIcon(jsonObject.getString("userIcon"));

                postList.add(postBrief);
            }

        }catch (Exception e){
            e.printStackTrace();
        }

        return postList;




    }
    public static ArrayList<Friend> getAttentionPeople(String userId){

        ArrayList<Friend> friendList=new ArrayList<>();

        HashMap<String,String> map=new HashMap<>();

        map.put("userId", userId);
//http://112.124.101.41/mike_server_v02/index.php/Home/UserAttentionList/getAttentionPeople/userId/1
        String result=httpRequest.sentGetRequest("http://112.124.101.41/mike_server_v02/index.php/Home/UserAttentionList/"+"getAttentionPeople/",map);

        System.out.println("get friend " + result);

        try {

            JSONArray jsonArray=new JSONArray(result);

            int length=jsonArray.length();

            for(int i=0;i<length;i++) {

                //todo

                JSONObject jsonObject = (JSONObject)jsonArray.get(i);

                String id = jsonObject.getString("userId");
                String icon = jsonObject.getString("userIcon");
                String name = jsonObject.getString("userName");
                String signal=jsonObject.getString("userSignal");

                Friend friend=new Friend();

                friend.setUserName(name);
                friend.setIcon(icon);
                friend.setId(id);
                friend.setSign(signal);

                MessagePrint.print("name :" + friend.getUserName());

                friendList.add(friend);

            }


        }catch (Exception e){

            e.printStackTrace();

        }

        return friendList;



    }
    public static ArrayList<CourseBrief> getAttentionCourse(String userId){

        ArrayList<CourseBrief> courseList=new ArrayList<>();

        HashMap<String,String> map=new HashMap<>();

        map.put("userId",userId);
//http://112.124.101.41/mike_server_v02/index.php/Home/UserAttentionList/getAttentionCourse/userId/1
        String result=httpRequest.sentGetRequest("http://112.124.101.41/mike_server_v02/index.php/Home/UserAttentionList/getAttentionCourse/",map);

        try {

            JSONArray jsonArray=new JSONArray(result);

            int length=jsonArray.length();

            for(int i=0;i<length;i++) {

                //todo

                JSONObject jsonObject = (JSONObject)jsonArray.get(i);

                String id = jsonObject.getString("id");
                String icon = jsonObject.getString("courseIcon");
                String name = jsonObject.getString("course_name");

                CourseBrief courseBrief=new CourseBrief();

                courseBrief.setIcon(icon);
                courseBrief.setName(name);
                courseBrief.setId(id);


                MessagePrint.print("name :" + courseBrief.getName());

                courseList.add(courseBrief);

            }


        }catch (Exception e){

            e.printStackTrace();

        }

        return courseList;


    }

    public void deleteAttention(String type,String id){

        HashMap<String,String> map=new HashMap<>();

        map.put(type,id);

        httpRequest.sentGetRequest(baseUrl + "", map);

    }

    public static boolean attention(String userId,String type,String id){

        HashMap<String,String> map=new HashMap<>();


        map.put("itemId",id);
        map.put("type", type);
        map.put("userId",userId);

        //http://112.124.101.41/mike_server_v02/index.php/Home/UserAttentionList/addAttentionItem/type/PEOPLE/userId/1/itemId/3

        String result=httpRequest.sentGetRequest("http://112.124.101.41/mike_server_v02/index.php/Home/UserAttentionList/addAttentionItem/",map);

     return Boolean.parseBoolean(result);

    }

    public static boolean unattention(String userId,String type,String id){

        HashMap<String,String> map=new HashMap<>();


        map.put("itemId",id);
        map.put("type",type);
        map.put("userId",userId);

        //http://112.124.101.41/mike_server_v02/index.php/Home/UserAttentionList/addAttentionItem/type/PEOPLE/userId/1/itemId/3

        String result=httpRequest.sentGetRequest("http://112.124.101.41/mike_server_v02/index.php/Home/UserAttentionList/deleteAttentionItem/",map);

        return Boolean.parseBoolean(result);

    }

    public static UserBLResult addNewUser(UserToken userToken){

        HashMap userInfo=new HashMap();
        userInfo.put("name", userToken.getName());
        userInfo.put("phoneNumber", userToken.getName());
        userInfo.put("password",userToken.getPassword());

        int id=userDataBase.addNewUser(userToken.getName(), userInfo);

        return UserBLResult.REGISTER_SUCCEED;


    }

    public static boolean resetPassword(String phoneNumber,String newPassword){

        HashMap<String,String> para=new HashMap<>();

        para.put("phoneNumber",phoneNumber);
        para.put("newPassword", newPassword);

        String result=httpRequest.sentGetRequest(baseUrl+"resetPassword/",para);



        return Boolean.parseBoolean(result);
    }






}
