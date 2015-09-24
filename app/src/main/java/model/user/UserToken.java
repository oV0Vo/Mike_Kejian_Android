package model.user;

/**
 * Created by kisstheraik on 15/9/23.
 */
public class UserToken {

    private boolean state;//当前状态
    private boolean isGetCode=false;
    private String ipAddress;
    private String name;
    private String tokenId;
    private String userId;
    private String password;
    private String phoneNumber;


    public void setIsGetCode(boolean b){

        isGetCode=true;

    }

    public void setPassword(String password){

        this.password=password;

    }

    public void setPhoneNumber(String phoneNumber){

        this.phoneNumber=phoneNumber;

    }

}
