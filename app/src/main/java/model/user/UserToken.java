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



    public void setIsGetCode(boolean b){

        isGetCode=true;

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
