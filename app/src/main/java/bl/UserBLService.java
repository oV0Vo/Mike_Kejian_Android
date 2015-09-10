package bl;

/**
 * Created by kisstheraik on 15/9/10.
 */

import model.user.user;
public class UserBLService {

    private static UserBLService service;

    private UserBLService(){

    }
    public static UserBLService getInstance(){
        return (service==null)?(new UserBLService()):service;
    }

    public user getUser(){

        return null;

    }

}
