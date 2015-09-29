package net;

import java.util.ArrayList;
import java.util.Date;

import model.course.data.CourseNamingRecord;
import util.NetOperateResultMessage;

/**
 * Created by violetMoon on 2015/9/29.
 */
public class CourseNamingNetService {

    public static ArrayList<CourseNamingRecord> getNamingRecords(String courseId) {
        return historyNamingMocks();
    }

    public static CourseNamingRecord getCurrentNamingRecord(String courseId) {
        return historyNamingMock();
    }

    public static CourseNamingRecord beginNaming(String courseId, String teacherId) {
        return historyNamingMock();
    }

    private static ArrayList<CourseNamingRecord> historyNamingMocks() {
        ArrayList<CourseNamingRecord> mocks = new ArrayList<CourseNamingRecord>();
        mocks.add(historyNamingMock());
        mocks.add(historyNamingMock());
        return mocks;
    }

    private static CourseNamingRecord historyNamingMock() {
        CourseNamingRecord mock = new CourseNamingRecord();
        ArrayList<String> sids = new ArrayList<String>();
        sids.add("131250666");
        sids.add("131250777");
        sids.add("131250888");
        sids.add("131250999");
        mock.setAbsentIds(sids);
        ArrayList<String> names = new ArrayList<String>();
        names.add("黄崇和");
        names.add("洪传旺");
        names.add("朱方圆");
        names.add("高阳一桥");
        mock.setAbsentNames(names);
        mock.setBeginTime(new Date(System.currentTimeMillis() - 1000 * 3600 * 32));
        mock.setEndTime(new Date(System.currentTimeMillis() -  - 1000 * 3600 * 31));
        mock.setNamingId("dsfsdfsdfds");
        mock.setSignInNum(92);
        mock.setTeacherId("tvbccvbcvbcv");
        mock.setTotalNum(122);
        return mock;
    }
}
