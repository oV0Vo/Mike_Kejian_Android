package bl;

/**
 * Created by kisstheraik on 15/9/10.
 */

import net.UserNetService;

import model.user.UserPost;
import model.user.UserToken;
import model.user.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserBLService {

    private static UserBLService service;
    private static List<Object> observeList;

    private UserBLService(){

    }

    public HashMap<String,ArrayList<String>> getCityAndSchool(){

        return UserNetService.getCityAndSchool();


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
    public user login(UserToken userToken){

        System.out.println("build user ");



        return UserNetService.getUser(userToken);


    }

    public UserBLResult register(UserToken userToken){

        if(userToken==null){

            return UserBLResult.REGISTER_FAILED;

        }

        else{

            UserNetService.addNewUser(userToken);

            System.out.println("注册成功!");
            userToken.setIsGetCode(true);
            return UserBLResult.REGISTER_SUCCEED;

        }

    }

    public static UserBLResult  CheckAccount(String name,String code){



        return UserBLResult.ACCOUNT_RIGHT;

    }

    public static void post(UserPost userPost){

    }


}
