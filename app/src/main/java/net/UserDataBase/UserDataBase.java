package net.UserDataBase;

import java.util.HashMap;
import java.util.List;

import bl.UserBLResult;

/**
 * Created by kisstheraik on 15/9/25.
 */
public class UserDataBase {

    private HashMap<String ,HashMap> userInfoCollection;
    private HashMap<String,List> userAttentionUser;
    private HashMap<String,List> userAttentionCourse;
    private HashMap<String,List> userAttentionPost;

    public HashMap<String ,Object> getUser(String name,String password){

        System.out.println("database -> name:"+name+" password:"+password);

        HashMap tem= userInfoCollection.get(name);

        if(tem!=null&&tem.get("password").equals(password)){

            return tem;

        }
        else{

            return null;

        }

    }

    public UserDataBase(){
        userInfoCollection=new HashMap<String, HashMap>();
        buildMock();
    }

    public int addNewUser(String name,HashMap infoSet){

        if(infoSet!=null) {

            userInfoCollection.put(name, infoSet);

            infoSet.put("gender","菇凉");
            infoSet.put("grade","2013");
            infoSet.put("icon","http");
            infoSet.put("sign","请多关照");
            infoSet.put("identify","见习生");
            infoSet.put("id",1);
            infoSet.put("nickname","baby");
            infoSet.put("department","楼兰");
            infoSet.put("major","大漠");
            infoSet.put("background", "hello");



            return userInfoCollection.size();
        }

        else{

            return -1;

        }

    }

    public void buildMock(){

        HashMap<String ,Object> userInfoSet1=new HashMap<String, Object>();
        userInfoSet1.put("name","义薄云天");
        userInfoSet1.put("gender","菇凉");
        userInfoSet1.put("grade","2013");
        userInfoSet1.put("icon","http");
        userInfoSet1.put("sign","The truth that you leave");
        userInfoSet1.put("identify","");
        userInfoSet1.put("id",1);
        userInfoSet1.put("password","123456");

        userInfoCollection.put("义薄云天",userInfoSet1);


        HashMap<String ,Object> userInfoSet2=new HashMap<String, Object>();
        userInfoSet2.put("name","十里长亭");
        userInfoSet2.put("gender","菇凉");
        userInfoSet2.put("grade","2013");
        userInfoSet2.put("icon","http");
        userInfoSet2.put("sign","The truth that you leave");
        userInfoSet2.put("identify","");
        userInfoSet2.put("id",1);
        userInfoSet2.put("attention_courses",new HashMap());
        userInfoSet2.put("password","123456");

        userInfoCollection.put("十里长亭",userInfoSet2);

        HashMap<String ,Object> userInfoSet3=new HashMap<String, Object>();
        userInfoSet3.put("name","Viva La");
        userInfoSet3.put("gender","菇凉");
        userInfoSet3.put("grade","1096");
        userInfoSet3.put("icon","http");
        userInfoSet3.put("sign","先王万代");
        userInfoSet3.put("identify","路易十六");
        userInfoSet3.put("id",1);
        userInfoSet3.put("nickname","");
        userInfoSet3.put("department","法兰西共和国");
        userInfoSet3.put("major","国王");
        userInfoSet3.put("background","hello");
        userInfoSet3.put("password","123456");


        userInfoCollection.put("Viva La",userInfoSet3);

    }
}
