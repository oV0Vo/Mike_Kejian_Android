package net;

import java.util.ArrayList;
import java.util.Date;

import model.course.data.CourseNamingRecord;
import model.course.data.CourseSignInRecord;

/**
 * Created by violetMoon on 2015/9/29.
 */
public class CourseSignInNetService {

    public static ArrayList<CourseSignInRecord> getSignInRecords(String sid, String courseId) {
        return signInMocks();
    }

    public static CourseSignInRecord getCurrentSignInRecord(String sid, String courseId) {
        return signInMock2();
    }

    public static boolean signIn(String sid, String namingId) {
        return true;
    }

    private static ArrayList<CourseSignInRecord> signInMocks() {
        ArrayList<CourseSignInRecord> mocks = new ArrayList<CourseSignInRecord>();
        mocks.add(signInMock1());
        mocks.add(signInMock2());
        mocks.add(signInMock1());
        mocks.add(signInMock1());
        mocks.add(signInMock1());
        mocks.add(signInMock1());
        return mocks;
    }

    private static CourseSignInRecord signInMock1() {
        CourseSignInRecord mock = new CourseSignInRecord();
        mock.setBeginTime(new Date(System.currentTimeMillis() - 1000 * 60 * 48));
        mock.setEndTime(new Date(System.currentTimeMillis() - 1000 * 60 * 32));
        mock.setHasSignIn(true);
        mock.setTeacherName("伏晓");
        return mock;
    }

    private static CourseSignInRecord signInMock2() {
        CourseSignInRecord mock = new CourseSignInRecord();
        mock.setBeginTime(new Date(System.currentTimeMillis() - 1000 * 60 * 48));
        mock.setEndTime(new Date(System.currentTimeMillis() - 1000 * 60 * 32));
        mock.setHasSignIn(false);
        mock.setTeacherName("伏晓");
        return mock;
    }

}
