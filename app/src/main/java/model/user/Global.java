package model.user;

import java.util.HashMap;

/**
 * Created by kisstheraik on 15/10/8.
 */
 public  class Global {


    private static HashMap<String,Object> itemList=new HashMap<String, Object>();

    private Global(){

    }

    public static Object getObjectByName(String name){

        return itemList.get(name);

    }

    public static void addGlobalItem(String name,Object item){

        itemList.put(name,item);

        user userInfo=(user)Global.getObjectByName("user");


    }

    public static void deleteGlobalItemByName(String name){

        itemList.remove(name);

    }
    public static int localVersion = 0;
    public static int serverVersion = 0;
    public static String downloadDir = "app/download/";
    public static String downloadUrl = "";
}
