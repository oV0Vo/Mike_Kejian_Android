package bl;

import java.util.ArrayList;

import model.message.CourseNotice;

/**
 * Created by I322233 on 9/8/2015.
 */
public class MessageBLService {
    public static int unreadCourseNoticeNum = 1;
    public static int unreadReplyNum = 2;
    public static int unreadPraiseNum = 3;
    public static int unreadMentionNum = 4;
    public static int getUnreadMessgeNum(){
        return unreadMentionNum+unreadCourseNoticeNum+unreadPraiseNum+unreadReplyNum;
    }
    public static ArrayList<CourseNotice> getCourseNoticeList(){
        ArrayList<CourseNotice> courseNotices = new ArrayList<CourseNotice>();
        courseNotices.add(new CourseNotice("数据结构与算法分析","微信公众平台,给个人、企业和组织提供业务服务与用户管理能力的全新服务平台。卡账单、额度及积分企业和组织提供业务服务与用户管理能力的全新服务平台...","管登荣","2015-09-09 12:00:00"));
        courseNotices.add(new CourseNotice("数据结构与算法分析","微信公众平台,给个人、企业和组织提供业务服务与用户管理能力的全新服务平台。卡账单、额度及积分企业和组织提供业务服务与用户管理能力的全新服务平台...","管登荣","2015-09-09 12:00:00"));
        courseNotices.add(new CourseNotice("数据结构与算法分析","微信公众平台,给个人、企业和组织提供业务服务与用户管理能力的全新服务平台。卡账单、额度及积分企业和组织提供业务服务与用户管理能力的全新服务平台...","管登荣","2015-09-09 12:00:00"));
        courseNotices.add(new CourseNotice("数据结构与算法分析","微信公众平台,给个人、企业和组织提供业务服务与用户管理能力的全新服务平台。卡账单、额度及积分企业和组织提供业务服务与用户管理能力的全新服务平台...","管登荣","2015-09-09 12:00:00"));
        courseNotices.add(new CourseNotice("数据结构与算法分析","微信公众平台,给个人、企业和组织提供业务服务与用户管理能力的全新服务平台。卡账单、额度及积分企业和组织提供业务服务与用户管理能力的全新服务平台...","管登荣","2015-09-09 12:00:00"));
        courseNotices.add(new CourseNotice("数据结构与算法分析","微信公众平台,给个人、企业和组织提供业务服务与用户管理能力的全新服务平台。卡账单、额度及积分企业和组织提供业务服务与用户管理能力的全新服务平台...","管登荣","2015-09-09 12:00:00"));
        courseNotices.add(new CourseNotice("数据结构与算法分析","微信公众平台,给个人、企业和组织提供业务服务与用户管理能力的全新服务平台。卡账单、额度及积分企业和组织提供业务服务与用户管理能力的全新服务平台...","管登荣","2015-09-09 12:00:00"));
        courseNotices.add(new CourseNotice("数据结构与算法分析","微信公众平台,给个人、企业和组织提供业务服务与用户管理能力的全新服务平台。卡账单、额度及积分企业和组织提供业务服务与用户管理能力的全新服务平台...","管登荣","2015-09-09 12:00:00"));
        courseNotices.add(new CourseNotice("数据结构与算法分析","微信公众平台,给个人、企业和组织提供业务服务与用户管理能力的全新服务平台。卡账单、额度及积分企业和组织提供业务服务与用户管理能力的全新服务平台...","管登荣","2015-09-09 12:00:00"));

        return courseNotices;
    }
}
