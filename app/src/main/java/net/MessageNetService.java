package net;

import java.sql.Timestamp;
import java.util.ArrayList;

import model.message.CourseNotice;
import model.message.MentionMe;
import model.message.Praise;
import model.message.Reply;

/**
 * Created by I322233 on 9/20/2015.
 */
public class MessageNetService {
    public static int getTotalCourseNoticeNum(){
        return 100;
    }
    public static ArrayList<CourseNotice> getNextCourseNotices(String userId, int index,int max_num){
        ArrayList<CourseNotice> courseNotices = new ArrayList();
        for(int i = 0;i<max_num;i++){
            courseNotices.add(new CourseNotice("数据结构与算法分析","微信公众平台,给个人、企业和组织提供业务服务与用户管理能力的全新服务平台。卡账单、额度及积分企业和组织提供业务服务与用户管理能力的全新服务平台...","管登荣","2015-09-09 12:00:00"));
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return courseNotices;

    }
    public static ArrayList<CourseNotice> getLatestCourseNotices(String userId, long startTime,long endTime){
        ArrayList<CourseNotice> courseNotices = new ArrayList();
        for(int i = 0;i<2;i++){
            courseNotices.add(new CourseNotice("数据结构","微信公众平台,给个人、企业和组织提供业务服务与用户管理能力的全新服务平台。卡账单、额度及积分企业和组织提供业务服务与用户管理能力的全新服务平台...","管登荣","2015-09-09 12:00:00"));
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return courseNotices;

    }
    public static int getTotalReplyNum(){
        return 30;
    }
    public static ArrayList<Reply> getNextReplies(String userId,int index, int max_num){
        ArrayList<Reply> replies = new ArrayList();
        java.util.Date date = new java.util.Date();
        for(int i = 0;i<max_num;i++){
            replies.add(new Reply("我不是小明","啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦",new Timestamp(date.getTime())));
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
            replies.add(new Reply("我不是小明","啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦",new Timestamp(date.getTime())));
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return replies;
    }
    public static int getTotalPraiseNum(){
        return 40;
    }
    public static ArrayList<Praise> getNextPraises(String userId, int index, int max_num){
        ArrayList<Praise> praises = new ArrayList();
        java.util.Date date = new java.util.Date();
        for(int i = 0;i<max_num;i++){
            praises.add(new Praise("我不是小明","啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦",new Timestamp(date.getTime())));
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
            praises.add(new Praise("我不是小明","啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦",new Timestamp(date.getTime())));
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return praises;
    }
    public static int getTotalMentionMeNum(){
        return 10;
    }
    public static ArrayList<MentionMe> getNextMentionMes(String userId, int index, int max_num){
        ArrayList<MentionMe> mentionMes = new ArrayList();
        java.util.Date date = new java.util.Date();
        for(int i = 0;i<max_num;i++){
            mentionMes.add(new MentionMe("我不是小明","啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦",new Timestamp(date.getTime())));
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
            mentionMes.add(new MentionMe("我不是小明","啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦啦",new Timestamp(date.getTime())));
        }
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return mentionMes;
    }
}
