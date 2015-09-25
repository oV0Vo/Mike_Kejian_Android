package net.UserDataBase;

import java.util.HashMap;

/**
 * Created by kisstheraik on 15/9/25.
 */
public class UserDataBase {

    private HashMap<String ,HashMap> userInfoCollection;

    public HashMap<String ,Object> getUser(String name){

        return userInfoCollection.get(name);

    }

    public UserDataBase(){
        userInfoCollection=new HashMap<String, HashMap>();
        buildMock();
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

        userInfoCollection.put("十里长亭",userInfoSet2);

        HashMap<String ,Object> userInfoSet3=new HashMap<String, Object>();
        userInfoSet3.put("name","Viva La");
        userInfoSet3.put("gender","菇凉");
        userInfoSet3.put("grade","1096");
        userInfoSet3.put("icon","http");
        userInfoSet3.put("sign","先王万代");
        userInfoSet3.put("identify","路易十六");
        userInfoSet3.put("id",1);

        userInfoCollection.put("Viva La",userInfoSet3);

    }
}
