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

    public String getMajorName() {
        return majorName;
    }

    public void setMajorName(String majorName) {
        this.majorName = majorName;
    }

    private String majorName;

    private String icon;//头像
    private school schoolInfo;//学校的信息

    public String getSchoolAccount() {
        return schoolAccount;
    }

    public void setSchoolAccount(String schoolAccount) {
        this.schoolAccount = schoolAccount;
    }

    private String schoolAccount;

    public department getDepartmentInfo() {
        return departmentInfo;
    }

    public void setDepartmentInfo(department departmentInfo) {
        this.departmentInfo = departmentInfo;
    }

    public school getSchoolInfo() {
        return schoolInfo;
    }

    public void setSchoolInfo(school schoolInfo) {
        this.schoolInfo = schoolInfo;
    }

    private department departmentInfo;//院系的信息
    private String nick_name;//昵称
    private String identify;//身份
    private String gender;//性别
    private String grade;//年级
    private String sign;//个性签名
    private String background_icon_path;//背景图片
    private String id;

    private boolean ifBind;

    public void setIfBind(boolean ifBind){

        this.ifBind=ifBind;

    }

    public boolean getIfBind(){

        ifBind=(schoolAccount!=null);

        return ifBind;
    }


    public String getId(){

        return id;
    }

    private UserAttentionList userAttentionList=new UserAttentionList();

    private UserToken token;




    public user(){

    }


    public user(HashMap<String,Object> infoSet){

        if(infoSet==null){

            return;

        }



//        userInfo.put("id",userDataJson.getString("id"));
//        userInfo.put("name",userDataJson.getString("name"));
//        userInfo.put("gender",userDataJson.getString("gender"));
//        userInfo.put("grade",userDataJson.getString("grade"));
//        userInfo.put("icon",userDataJson.getString("icon_url"));
//        userInfo.put("signal",userDataJson.getString("signal"));
//        userInfo.put("identify",userDataJson.getString("identify"));
//        userInfo.put("id",userDataJson.getInt("id"));
//        userInfo.put("password",userDataJson.getString("password"));
//        userInfo.put("nick_name",userDataJson.getString("nick_name"));
//        userInfo.put("school_identify",userDataJson.getString("school_identify"));





        name=(String)(infoSet.get("name"));
        gender=(String)(infoSet.get("gender"));
        grade=(String)(infoSet.get("grade"));
        icon=(String)(infoSet.get("icon"));
        sign=(String)(infoSet.get("signal"));
        identify=(String)(infoSet.get("identify"));
        id=infoSet.get("id").toString();
        nick_name=(String)infoSet.get("nick_name");
        background_icon_path=(String)infoSet.get("background");
        schoolAccount=(String)infoSet.get("school_identify");

        schoolInfo=new school();
        schoolInfo.setId((String)infoSet.get("school_id"));
        schoolInfo.setName((String)infoSet.get("school_name"));
//        schoolInfo.setNumber();
//
        majorName=(String)infoSet.get("major_name");

        departmentInfo=new department();
//        departmentInfo.setNumber();
        departmentInfo.setName((String)infoSet.get("department_name"));
        departmentInfo.setId((String) infoSet.get("department_id"));
        departmentInfo.setSchoolId(schoolInfo.getId());





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

    public UserBLResult setSign(String sign){

        this.sign=sign;

        return null;
    }

    public UserBLResult setNickName(String nick_name){

        this.nick_name=nick_name;

        return null;

    }

    public UserBLResult setIcon(String url){

        this.icon=url;

        return null;

    }








}
