package model.user;

import android.os.Parcel;
import android.os.Parcelable;

import junit.framework.Assert;

import java.io.Serializable;
import java.util.HashMap;

import bl.UserBLResult;

/**
 * Created by kisstheraik on 15/9/10.
 */
public class user implements Serializable{

    private String name;//
    private String icon;//头像
    private school schoolInfo;//学校的信息
    private department departmentInfo;//院系的信息
    private String nick_name;//昵称
    private String identify;//身份
    private String gender;//性别
    private String grade;//年级
    private String sign;//个性签名
    private String background_icon_path;//背景图片
    private String id;

    private UserAttentionList userAttentionList=new UserAttentionList();

    private UserToken token;




    public user(){

    }


    public user(HashMap<String,Object> infoSet){

        if(infoSet==null){

            return;

        }







        name=(String)(infoSet.get("name"));
        gender=(String)(infoSet.get("gender"));
        grade=(String)(infoSet.get("grade"));
        icon=(String)(infoSet.get("icon"));
        sign=(String)(infoSet.get("sign"));
        identify=(String)(infoSet.get("identify"));
        id=infoSet.get("id").toString();
        nick_name=(String)infoSet.get("nickname");
        background_icon_path=(String)infoSet.get("background");





    }

    public String getName(){
        return name;
    }

    public String getIcon(){
        return icon;
    }

    public String getNick_name(){

        return nick_name;

    }

    public String getIdentify(){

        return identify;

    }

    public String getGender(){

        return gender;

    }

    public String getGrade(){

        return grade;

    }

    public String getSign(){

        return sign;

    }

    public String getBackground_icon_path(){

        return background_icon_path;

    }

    public UserBLResult setSign(){

        return null;
    }

    public UserBLResult setNickName(){

        return null;

    }

    public UserBLResult setIcon(){

        return null;

    }








}
