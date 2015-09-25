package bl.User;

import android.os.Parcel;
import android.os.Parcelable;

import bl.UserBLResult;
import model.user.UserToken;

/**
 * Created by kisstheraik on 15/9/23.
 */
public class Register implements Parcelable{

    private String code;
    private UserToken userToken;
    private String phoneNumber;
    private String password;
    private String inputCode;


    public Register(UserToken userToken){

        this.userToken=userToken;

    }

    public int describeContents(){

        return 0;

    }
    //写入接口函数，打包
    public void writeToParcel(Parcel dest, int flags){

    }

    public void setPhoneNumber(String phoneNumber){

        this.phoneNumber=phoneNumber;

    }

    public void setPassword(String password){

        this.password=password;

    }

    public void setInputCode(String inputCode){

        this.inputCode=inputCode;

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

    public UserToken getToken(){

        userToken.setIsGetCode(checkCode(code));
        userToken.setPassword(password);
        userToken.setPhoneNumber(phoneNumber);

        return userToken;

    }

}
