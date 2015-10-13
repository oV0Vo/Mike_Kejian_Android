package net;

import net.httpRequest.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import model.message.CourseNotice;
import model.message.MentionMe;
import model.message.Praise;
import model.message.Reply;

/**
 * Created by I322233 on 9/20/2015.
 */
public class MessageNetService {
    private static HttpRequest httpRequest = HttpRequest.getInstance();
    private static String baseUrl = "http://112.124.101.41:80/mike_server_v02/index.php/Home/Message/";
    private static int handleNumData(String result){
        int num = -1;
        try {
            JSONObject jsonObject = new JSONObject(result);
            num = jsonObject.getInt("num");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return num;
    }
    public static int getTotalCourseNoticeNum(){
        String result = httpRequest.sentGetRequest(baseUrl+"getTotalCourseNoticeNum/",null);
        return handleNumData(result);
    }
    public static ArrayList<CourseNotice> getNextCourseNotices(String userId, int lastId,int max_num){
        ArrayList<CourseNotice> courseNotices = new ArrayList();
        HashMap<String,String> params = new HashMap<>();
        params.put("lastId",lastId+"");
        params.put("num",max_num+"");
        String result = httpRequest.sentGetRequest(baseUrl+"getNextCourseNotices/",params);
        try {
            JSONArray courseNoticesArray = new JSONArray(result);
            for(int i = 0;i<courseNoticesArray.length();i++){
                JSONObject courseNoticeJson = courseNoticesArray.getJSONObject(i);
                int id = courseNoticeJson.getInt("id");
                String courseName = courseNoticeJson.getString("course_name");
                String content = courseNoticeJson.getString("content");
                String publisher = courseNoticeJson.getString("user_name");
                String publish_time = courseNoticeJson.getString("publish_time");
                CourseNotice courseNotice = new CourseNotice(id,courseName,content,publisher,publish_time);
                courseNotices.add(courseNotice);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        for(int i = 0;i<max_num;i++){
//            courseNotices.add(new CourseNotice("数据结构与算法分析","微信公众平台,给个人、企业和组织提供业务服务与用户管理能力的全新服务平台。卡账单、额度及积分企业和组织提供业务服务与用户管理能力的全新服务平台...","管登荣","2015-09-09 12:00:00"));
//        }
//        try {
//            Thread.sleep(500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        return courseNotices;

    }
    public static ArrayList<CourseNotice> getLatestCourseNotices(String userId, long startTime,long endTime){
        ArrayList<CourseNotice> courseNotices = new ArrayList();
        for(int i = 0;i<2;i++){
//            courseNotices.add(new CourseNotice("数据结构","微信公众平台,给个人、企业和组织提供业务服务与用户管理能力的全新服务平台。卡账单、额度及积分企业和组织提供业务服务与用户管理能力的全新服务平台...","管登荣","2015-09-09 12:00:00"));
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return courseNotices;

    }
    public static int getTotalReplyNum(){
        String result = httpRequest.sentGetRequest(baseUrl+"getTotalReplyNum/",null);
        return handleNumData(result);
    }
    public static ArrayList<Reply> getNextReplies(String userId,int index, int max_num){
        ArrayList<Reply> replies = new ArrayList();
        java.util.Date date = new java.util.Date();
        for(int i = 0;i<max_num;i++){
//            replies.add(new Reply("我不是小明","啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦",new Timestamp(date.getTime())));
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return replies;
    }
    public static ArrayList<Reply> getLatestReplies(String userId,long startTime, long endTime){
        ArrayList<Reply> replies = new ArrayList();
        java.util.Date date = new java.util.Date();
        for(int i = 0;i<2;i++){
//            replies.add(new Reply("我不是小明","啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦",new Timestamp(date.getTime())));
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return replies;
    }
    public static int getTotalPraiseNum(){
        String result = httpRequest.sentGetRequest(baseUrl+"getTotalPraiseNum/",null);
        return handleNumData(result);
    }
    public static ArrayList<Praise> getNextPraises(String userId, int index, int max_num){
        ArrayList<Praise> praises = new ArrayList();
        java.util.Date date = new java.util.Date();
        for(int i = 0;i<max_num;i++){
//            praises.add(new Praise("我不是小明","啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦",new Timestamp(date.getTime())));
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return praises;
    }
    public static ArrayList<Praise> getLatestPraises(String userId, long startTime, long endTime){
        ArrayList<Praise> praises = new ArrayList();
        java.util.Date date = new java.util.Date();
        for(int i = 0;i<2;i++){
//            praises.add(new Praise("我不是小明","啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦",new Timestamp(date.getTime())));
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return praises;
    }
    public static int getTotalMentionMeNum(){
        String result = httpRequest.sentGetRequest(baseUrl+"getTotalMentionMeNum/",null);
        return handleNumData(result);
    }
    public static ArrayList<MentionMe> getNextMentionMes(String userId, int index, int max_num){
        ArrayList<MentionMe> mentionMes = new ArrayList();
        java.util.Date date = new java.util.Date();
        for(int i = 0;i<max_num;i++){
//            mentionMes.add(new MentionMe("我不是小明","啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦",new Timestamp(date.getTime())));
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mentionMes;
    }
    public static ArrayList<MentionMe> getLatestMentionMes(String userId, long startTime, long endTime){
        ArrayList<MentionMe> mentionMes = new ArrayList();
        java.util.Date date = new java.util.Date();
        for(int i = 0;i<2;i++){
//            mentionMes.add(new MentionMe("我不是小明","啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦",new Timestamp(date.getTime())));
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mentionMes;
    }
}
