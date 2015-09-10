package bl;

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
}
