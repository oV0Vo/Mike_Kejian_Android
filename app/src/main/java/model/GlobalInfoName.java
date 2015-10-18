package model;

/**
 * Created by kisstheraik on 15/10/18.
 */
public interface GlobalInfoName {

    /*
     * description:用来定义可以全局访问的数据的种类和名称
     *
     * 成员变量的属性 必需有 static final
     *
     */

    public String USER="user";
    public String USER_TOKEN="userToken";
    public enum  BIND_SCHOOL_ACCOUNT_TIME{BIND_SCHOOL_ACCOUNT_TIME,AFTER_REGISTER,FROM_SETTING};
    public String COOKIE="cookie";


}
