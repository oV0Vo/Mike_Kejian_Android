package bl;

import java.util.ArrayList;

import util.NameAndValuePair;
import util.NeedAsyncAnnotation;
import util.PropertiesFileIO;

/**
 * Created by violetMoon on 2015/9/10.
 */
public class UserAccountBLService {

    private static final String ARG_SID = "学号";

    private static final String ARG_PASSWORD = "密码";

    private static final String ACCOUNT_FILE = "user.info";

    private static UserAccountBLService instance;

    private String sid = "131250012";

    private String password = "1213";

    public static UserAccountBLService getInstance() {
        return instance;
    }

    @NeedAsyncAnnotation
    public static void createInstance() {
        if(instance == null) {
            instance = new UserAccountBLService();
            //instance.init();
        }
    }

    private void init() {
        ArrayList<NameAndValuePair<String, String>> accountInfos = PropertiesFileIO.getProperties(ACCOUNT_FILE);
        for(NameAndValuePair<String, String> info: accountInfos) {
            if(info.getName().equals(ARG_SID)) {
                sid = info.getValue();
            } else if(info.getName().equals(ARG_PASSWORD)) {
                password = info.getValue();
            } else {
                continue;
            }
        }
    }

    public String getSid() {
        return sid;
    }

    public String getPassword() {
        return password;
    }

    public boolean saveAccount(String newSid, String newPassword) {
        NameAndValuePair sidInfo = new NameAndValuePair(ARG_SID, newSid);
        NameAndValuePair passwordInfo  =new NameAndValuePair(ARG_PASSWORD, newPassword);
        ArrayList<NameAndValuePair<String, String>> accountInfos = new ArrayList<NameAndValuePair<String, String>>();
        accountInfos.add(sidInfo);
        accountInfos.add(passwordInfo);
        boolean savedSuccess = PropertiesFileIO.savedProperties(accountInfos, ACCOUNT_FILE);

        if(savedSuccess) {
            sid = newSid;
            password = newPassword;
            return true;
        } else {
            return false;
        }
    }

}
