package bl;

/**
 * Created by kisstheraik on 15/9/10.
 */

import net.UserNetService;

import model.user.UserPost;
import model.user.UserToken;
import model.user.user;

import java.util.HashMap;
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
    public user login(UserToken userToken){

        System.out.println("build user ");



        return UserNetService.getUser(userToken);


    }

    public UserBLResult register(UserToken userToken){

        if(userToken==null){

            return UserBLResult.REGISTER_FAILED;

        }

        else{

            System.out.println("注册成功!");
            userToken.setIsGetCode(true);
            return UserBLResult.REGISTER_SUCCEED;

        }

    }

    public static void post(UserPost userPost){

    }







}
