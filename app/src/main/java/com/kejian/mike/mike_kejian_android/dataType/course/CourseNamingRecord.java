package com.kejian.mike.mike_kejian_android.dataType.course;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by violetMoon on 2015/9/27.
 */
public class CourseNamingRecord {

    private String namingId;
    private String teacherId;
    private Date beginTime;
    private Date endTime;
    private int signInNum;
    private int totalNum;
    private ArrayList<String> absentNames;
    private ArrayList<String> absentIds;

    public int getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(int totalNum) {
        this.totalNum = totalNum;
    }

    public String getNamingId() {
        return namingId;
    }

    public void setNamingId(String namingId) {
        this.namingId = namingId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public int getSignInNum() {
        return signInNum;
    }

    public void setSignInNum(int signInNum) {
        this.signInNum = signInNum;
    }

    public ArrayList<String> getAbsentNames() {
        return absentNames;
    }

    public void setAbsentNames(ArrayList<String> absentNames) {
        this.absentNames = absentNames;
    }

    public ArrayList<String> getAbsentIds() {
        return absentIds;
    }

    public void setAbsentIds(ArrayList<String> absentIds) {
        this.absentIds = absentIds;
    }
}
