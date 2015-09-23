package bl.User;

import bl.UserBLResult;
import model.user.UserToken;

/**
 * Created by kisstheraik on 15/9/23.
 */
public class Register {

    private String code;
    private UserToken userToken;
    private String phoneNumber;
    private String password;


    public Register(UserToken userToken){

        this.userToken=userToken;

    }

    //检查输入的验证码是否正确
    public boolean checkCode(String inputCode){

        return inputCode==code?true:false;

    }

    public UserBLResult setCode(){

        code="123456";



        return UserBLResult.SUCCEED;

    }

    public UserToken getToken(String code){

        userToken.setIsGetCode(checkCode(code));
        userToken.setPassword(password);
        userToken.setPhoneNumber(phoneNumber);

        return userToken;

    }

}
