package bl;

import net.MessageNetService;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import model.message.CourseNotice;
import model.message.MentionMe;
import model.message.Praise;
import model.message.Reply;

/**
 * Created by I322233 on 9/8/2015.
 */
public class MessageBLService {
    public static int unreadCourseNoticeNum = 1;
    public static int unreadReplyNum = 2;
    public static int unreadPraiseNum = 3;
    public static int unreadMentionNum = 4;

    public static int totalCourseNotice = -1;
    public static ArrayList<CourseNotice> courseNotices = null;
    private static int course_notice_index = 0;
    private static long courseNoticeLatestRefreshTime = 0;

    public static int totalReply = -1;
    public static ArrayList<Reply> replies = null;
    private static int reply_index = 0;
    private static long replyLatestRefreshTime = 0;

    public static int totalPraise = -1;
    public static ArrayList<Praise> praises = null;
    private static int praise_index = 0;
    private static long praiseLatestRefreshTime = 0;

    public static int totalMentionMe = -1;
    public static ArrayList<MentionMe> mentionMes = null;
    private static int mentionme_index = 0;
    private static long mentionLatestRefreshTime = 0;

    public static void refreshTotalCourseNoticeNum(String userId){
        totalCourseNotice = MessageNetService.getTotalCourseNoticeNum();
    }

    //初始化课程公告，如果已经有数据相当于刷新
    public static void initCourseNotices(String userId){

        if(courseNotices == null){
            courseNotices = new ArrayList<>();
            courseNoticeLatestRefreshTime = System.currentTimeMillis();
            ArrayList<CourseNotice> newCourseNotices = MessageNetService.getNextCourseNotices(userId,course_notice_index,2);
            for(int i = 0;i<newCourseNotices.size();i++){
                courseNotices.add(newCourseNotices.get(i));
            }
        }else{
            refreshCourseNotices(userId);
        }
    }

    //加载，这边有问题有待解决，即每次加载之前若有新的数据插入，会造成重复加载的现象
    public static void addCourseNotices(String userId){
            ArrayList<CourseNotice> newCourseNotices = MessageNetService.getNextCourseNotices(userId, course_notice_index, 10);
            for(int i = 0;i < newCourseNotices.size();i++){
                courseNotices.add(newCourseNotices.get(i));
            }

    }
    //刷新，相当于获取前一次刷新时间到当前时间之间的课程公告
    public static void refreshCourseNotices(String userId){
        long now = System.currentTimeMillis();
        ArrayList<CourseNotice> newCourseNotices = MessageNetService.getLatestCourseNotices(userId, courseNoticeLatestRefreshTime, now);
        courseNoticeLatestRefreshTime = now;
        for(int i =0;i<newCourseNotices.size();i++){
            courseNotices.add(i,newCourseNotices.get(i));
        }
    }

    public static void refreshTotalReplyNum(String userId){
        totalReply = MessageNetService.getTotalReplyNum();

    }
    public static void initReplies(String userId){
        if(replies == null){
            replies = new ArrayList<>();
            replyLatestRefreshTime = System.currentTimeMillis();
            ArrayList<Reply> newReplies = MessageNetService.getNextReplies(userId,reply_index,2);
            for(int i = 0;i<newReplies.size();i++){
                replies.add(newReplies.get(i));
            }
        }else{
            refreshReplies(userId);
        }

    }
    public static void addReplies(String userId){
        ArrayList<Reply> newReplies = MessageNetService.getNextReplies(userId,reply_index,10);
        for(int i = 0;i < newReplies.size();i++){
            replies.add(newReplies.get(i));
        }
    }
    public static void refreshReplies(String userId){
        long now = System.currentTimeMillis();
        ArrayList<Reply> newReplies = MessageNetService.getLatestReplies(userId, replyLatestRefreshTime, now);
        replyLatestRefreshTime = now;
        for(int i =0;i<newReplies.size();i++){
            replies.add(i,newReplies.get(i));
        }
    }

    public static void refreshTotalPraiseNum(String userId){
        totalPraise = MessageNetService.getTotalPraiseNum();
    }
    public static void initPraises(String userId){
        if(praises == null){
            praises = new ArrayList<>();
            praiseLatestRefreshTime = System.currentTimeMillis();
            ArrayList<Praise> newPraises = MessageNetService.getNextPraises(userId, praise_index, 2);
            for(int i = 0;i<newPraises.size();i++){
                praises.add(newPraises.get(i));
            }
        }else{
            refreshPraises(userId);
        }
    }
    public static void addPraises(String userId){
        ArrayList<Praise> newPraises = MessageNetService.getNextPraises(userId, praise_index, 10);
        for(int i = 0;i < newPraises.size();i++){
            praises.add(newPraises.get(i));
        }
    }
    public static void refreshPraises(String userId){
        long now = System.currentTimeMillis();
        ArrayList<Praise> newPraises = MessageNetService.getLatestPraises(userId, praiseLatestRefreshTime, now);
        praiseLatestRefreshTime = now;
        for(int i =0;i<newPraises.size();i++){
            praises.add(i,newPraises.get(i));
        }
    }

    public static void refreshTotalMentionMeNum(String userId){
        totalMentionMe = MessageNetService.getTotalMentionMeNum();

    }
    public static void initMentionMes(String userId){
        if(mentionMes == null){
            mentionMes = new ArrayList<>();
            mentionLatestRefreshTime = System.currentTimeMillis();
            ArrayList<MentionMe> newMentions= MessageNetService.getNextMentionMes(userId, mentionme_index, 2);
            for(int i = 0;i<newMentions.size();i++){
                mentionMes.add(newMentions.get(i));
            }
        }else{
            refreshMentionMes(userId);
        }

    }
    public static void addMentionMes(String userId){
        ArrayList<MentionMe> newMentionmes = MessageNetService.getNextMentionMes(userId, mentionme_index, 10);
        for(int i = 0;i < newMentionmes.size();i++){
            mentionMes.add(newMentionmes.get(i));
        }
    }
    public static void refreshMentionMes(String userId){
        long now = System.currentTimeMillis();
        ArrayList<MentionMe> newMentionmes = MessageNetService.getLatestMentionMes(userId,mentionLatestRefreshTime,now);
        mentionLatestRefreshTime = now;
        for(int i =0;i<newMentionmes.size();i++){
            mentionMes.add(i,newMentionmes.get(i));
        }

    }

    public static int getUnreadMessgeNum() {
        return unreadMentionNum + unreadCourseNoticeNum + unreadPraiseNum + unreadReplyNum;
    }
}
