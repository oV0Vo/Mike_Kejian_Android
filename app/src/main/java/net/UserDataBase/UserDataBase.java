package net.UserDataBase;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


/**
 * Created by kisstheraik on 15/9/25.
 */
public class UserDataBase {

    private HashMap<String ,HashMap> userInfoCollection;
    private HashMap<String,ArrayList> userAttentionUser=new HashMap<String, ArrayList>();
    private HashMap<String,ArrayList> userAttentionCourse=new HashMap<String, ArrayList>();
    private HashMap<String,ArrayList> userAttentionPost=new HashMap<String, ArrayList>();
    private HashMap<String,HashMap<String,String>> postList=new HashMap<String, HashMap<String, String>>();

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

    public boolean addPost(String userName,String postName,String reply,Date publishTime,String courseName){

        int maxId=postList.size()+1;

        return true;
    }
    public UserDataBase(){
        userInfoCollection=new HashMap<String, HashMap>();
        buildMock();
    }

    public boolean addAttentionUser(String user,String name){

        userAttentionUser.get(user).add(name);

        return true;

    }
    public boolean addAttentionCourse(String user,String courseName){

        userAttentionCourse.get(user).add(courseName);

        return true;

    }

    public boolean addAttentionPost(String user,String postId){

        userAttentionPost.get(user).add(postId);

        return true;

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
        userAttentionUser.put((String)userInfoSet1.get("name"), new ArrayList());
        userAttentionCourse.put((String)userInfoSet1.get("name"), new ArrayList());
        userAttentionPost.put((String)userInfoSet1.get("name"), new ArrayList());

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
        userAttentionUser.put((String)userInfoSet2.get("name"), new ArrayList());
        userAttentionCourse.put((String)userInfoSet2.get("name"), new ArrayList());
        userAttentionPost.put((String)userInfoSet2.get("name"), new ArrayList());

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
        userAttentionUser.put((String)userInfoSet3.get("name"), new ArrayList());
        userAttentionCourse.put((String)userInfoSet3.get("name"), new ArrayList());
        userAttentionPost.put((String)userInfoSet3.get("name"), new ArrayList());


        HashMap<String ,Object> userInfoSet4=new HashMap<String, Object>();
        userInfoSet4.put("name","test");
        userInfoSet4.put("gender","菇凉");
        userInfoSet4.put("grade","1096");
        userInfoSet4.put("icon","http");
        userInfoSet4.put("sign","先王万代");
        userInfoSet4.put("identify","路易十六");
        userInfoSet4.put("id",1);
        userInfoSet4.put("nickname","");
        userInfoSet4.put("department","法兰西共和国");
        userInfoSet4.put("major","国王");
        userInfoSet4.put("background","hello");
        userInfoSet4.put("password","test");


        userInfoCollection.put("test",userInfoSet4);
        userAttentionUser.put((String)userInfoSet4.get("name"), new ArrayList());
        userAttentionCourse.put((String)userInfoSet4.get("name"), new ArrayList());
        userAttentionPost.put((String)userInfoSet4.get("name"), new ArrayList());

    }
}
