package bl;

/**
 * Created by kisstheraik on 15/9/23.
 *
 * design used the obverse model
 *
 */

import bl.User.Observer;
import bl.User.Subject;
import model.user.AttentionList;
import model.user.Friend;
import model.user.user;

public class UserInfoManager extends Subject{

    private user user;
    private AttentionList<Friend> friendAttentionList=new AttentionList<Friend>();

    public UserInfoManager(){

    }

    public boolean addObserver(Observer observer){
        return false;
    }

    public boolean deleteObserver(Observer observer){
        return false;
    }

    public boolean notifyObserver(){

        return false;
    }




}
