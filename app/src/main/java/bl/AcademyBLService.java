package bl;

import java.util.ArrayList;

/**
 * Created by violetMoon on 2015/9/17.
 */
public class AcademyBLService {

    private static AcademyBLService instance;

    private AcademyBLService() {

    }

    public static AcademyBLService getInstance() {
        if(instance == null)
            instance = new AcademyBLService();
        return instance;
    }

    public void init() {
        //net work init
    }

    public ArrayList<String> getAllAcademyNamesMock() {
        ArrayList<String> names = new ArrayList<String>();
        names.add("所有院系");
        names.add("软件学院");
        names.add("商学院");
        names.add("物理学院");
        names.add("匡亚明学院");
        names.add("医学院");
        names.add("计算机科学与技术学院");
        names.add("现代工程与管理学院");
        return names;
    }


}
