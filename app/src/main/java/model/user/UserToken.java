package model.user;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by kisstheraik on 15/9/23.
 */
public class UserToken implements Serializable{

    private boolean state;//当前状态
    private boolean isGetCode=false;
    private String ipAddress;
    private String name;
    private String tokenId;
    private String userId;
    private String password;
    private String phoneNumber;
    private String schoolAccount;
    private String schoolAccountPassword;
    private boolean ifBindSchoolAccount;

    public boolean ifBindSchoolAccount(){

        return ifBindSchoolAccount;

    }

    public void bindSchoolAccount(){

        this.ifBindSchoolAccount=true;

    }


    public void setIsGetCode(boolean b){

        isGetCode=true;

    }

    public String getSchoolAccount(){

        return schoolAccount;

    }

    public String getSchoolAccountPassword(){

        return schoolAccountPassword;

    }

    public void setSchoolAccount(String schoolAccount){

        this.schoolAccount=schoolAccount;

    }

    public void setSchoolAccountPassword(String schoolAccountPassword){

        this.schoolAccountPassword=schoolAccountPassword;

    }

    public void writeToParcel(Parcel dest, int flags){

    }

    public void setPassword(String password){

        this.password=password;

    }

    public String getName(){

        return name;
    }

    public String getPassword(){

        return password;

    }

    public void setPhoneNumber(String phoneNumber){

        this.phoneNumber=phoneNumber;

    }

    public int describeContents(){

        return 0;

    }

    public void setName(String name){

        this.name=name;

    }

}
