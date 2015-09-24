package bl;

/**
 * Created by kisstheraik on 15/9/23.
 */
public class UserAccountService {

    private static UserAccountService instance=new UserAccountService();

    private UserAccountService(){



    }

    public static UserAccountService getInstance(){

        return instance;

    }

    public UserBLResult login(){


        return UserBLResult.ERROR_IN_NETWORK;

    }
}
