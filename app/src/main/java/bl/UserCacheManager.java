package bl;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

/**
 * Created by kisstheraik on 15/9/23.
 */
public class UserCacheManager {

    private static Context context;


    private static HashMap<String,Object> userCache;
    private static SharedPreferences sharedPreferences;
    private static int maxSize=100;
    private static int currentSize=0;


    private UserCacheManager(Context context){

        this.context=context;


        sharedPreferences=context.getSharedPreferences("userCache",0);
    }

    public static void initData(){

        SharedPreferences.Editor editor=sharedPreferences.edit();



    }

    public static boolean writeObject(String name,Object item){

        if(!(currentSize<100)){

            return false;


        }

        return true;


    }

    public static Object readObject(String name){


        return userCache.get(name);



    }

    public static void cleanCache(){

    }
}
