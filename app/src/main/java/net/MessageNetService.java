package net;

import java.util.ArrayList;

import model.message.CourseNotice;

/**
 * Created by I322233 on 9/20/2015.
 */
public class MessageNetService {
    public static int getTotalCourseNoticeNum(){
        return 100;
    }
    public static ArrayList<CourseNotice> getNextCourseNotices(String userId, int index,int max_num){
        ArrayList<CourseNotice> courseNotices = new ArrayList<>();
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
}
