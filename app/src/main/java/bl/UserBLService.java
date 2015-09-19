package bl;

/**
 * Created by kisstheraik on 15/9/10.
 */

import model.user.UserPost;
import model.user.user;
import java.util.List;

public class UserBLService {

    private static UserBLService service;
    private static List<Object> observeList;

    private UserBLService(){

    }

    public static UserBLService getInstance(){
        return (service==null)?(new UserBLService()):service;
    }

    public user getUser(){

        return null;

    }
    public List<Object> getUserAttention(){

        return null;

    }
    public void update(){

    }
    public boolean login(){
        return false;
    }

    public static void post(UserPost userPost){

    }



}
