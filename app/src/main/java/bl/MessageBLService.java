package bl;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import net.MessageNetService;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import model.message.CourseNotice;
import model.message.MentionMe;
import model.message.MessageType;
import model.message.Praise;
import model.message.Reply;
import model.message.UnReadMessageType;

/**
 * Created by I322233 on 9/8/2015.
 */
public class MessageBLService {
    public static int unreadCourseNoticeNum = 0;
    public static int unreadReplyNum = 0;
    public static int unreadPraiseNum = 0;
    public static int unreadMentionNum = 0;

    public static void persistentUnreadMessageNum(Context context){
        //实例化SharedPreferences对象（第一步）
        SharedPreferences mySharedPreferences= context.getSharedPreferences(UnReadMessageType.unRead,
                Activity.MODE_PRIVATE);
        //实例化SharedPreferences.Editor对象（第二步）
        SharedPreferences.Editor editor = mySharedPreferences.edit();
        //用putString的方法保存数据
        editor.putInt(UnReadMessageType.courseNotice, unreadCourseNoticeNum);
        editor.putInt(UnReadMessageType.reply,unreadReplyNum);
        editor.putInt(UnReadMessageType.praise,unreadPraiseNum);
        editor.putInt(UnReadMessageType.mentionMe,unreadMentionNum);
        //提交当前数据
        editor.commit();
    }

    public static void initUnreadMessageNum(Context context){
        SharedPreferences sharedPreferences= context.getSharedPreferences(UnReadMessageType.unRead,
                Activity.MODE_PRIVATE);
        // 使用getString方法获得value，注意第2个参数是value的默认值
        unreadCourseNoticeNum = sharedPreferences.getInt(UnReadMessageType.courseNotice,0);
        unreadReplyNum = sharedPreferences.getInt(UnReadMessageType.reply,0);
        unreadPraiseNum = sharedPreferences.getInt(UnReadMessageType.praise,0);
        unreadMentionNum = sharedPreferences.getInt(UnReadMessageType.mentionMe,0);

    }

    public static void incrementUnReadMessageNum(MessageType type){
        switch (type){
            case courseNotice:
                unreadCourseNoticeNum++;
                break;
            case reply:
                unreadReplyNum++;
                break;
            case praise:
                unreadPraiseNum++;
                break;
            case mentionMe:
                unreadMentionNum++;
                break;
        }
    }

    public static int totalCourseNotice = -1;
    public static ArrayList<CourseNotice> courseNotices = null;
    private static int course_notice_last_id = Integer.MAX_VALUE;
    private static long courseNoticeLatestRefreshTime = 0;

    public static int totalReply = -1;
    public static ArrayList<Reply> replies = null;
    private static int reply_last_id = Integer.MAX_VALUE;
    private static long replyLatestRefreshTime = 0;

    public static int totalPraise = -1;
    public static ArrayList<Praise> praises = null;
    private static int praise_last_id = Integer.MAX_VALUE;
    private static long praiseLatestRefreshTime = 0;

    public static int totalMentionMe = -1;
    public static ArrayList<MentionMe> mentionMes = null;
    private static int mentionme_last_id = Integer.MAX_VALUE;
    private static long mentionLatestRefreshTime = 0;

    public static void refreshTotalCourseNoticeNum(String userId){
        totalCourseNotice = MessageNetService.getTotalCourseNoticeNum();
    }

    private static void refreshLastId(int type){
        switch (type){
            case 0:
                CourseNotice tmp = courseNotices.get(courseNotices.size()-1);
                course_notice_last_id = tmp.getId();
                break;
            case 1:
                Reply reply = replies.get(replies.size()-1);
                reply_last_id = reply.getId();
                break;
            case 2:
                Praise praise = praises.get(praises.size()-1);
                praise_last_id = praise.getId();
                break;
            case 3:
                MentionMe mentionMe = mentionMes.get(mentionMes.size()-1);
                mentionme_last_id = mentionMe.getId();
                break;
        }
    }

    //初始化课程公告，如果已经有数据相当于刷新
    public static void initCourseNotices(String userId){

        if(courseNotices == null){
            courseNotices = new ArrayList<CourseNotice>();
            courseNoticeLatestRefreshTime = System.currentTimeMillis();
            ArrayList<CourseNotice> newCourseNotices = MessageNetService.getNextCourseNotices(userId,course_notice_last_id,5);
            for(int i = 0;i<newCourseNotices.size();i++){
                courseNotices.add(newCourseNotices.get(i));
            }
            refreshLastId(0);
        }else{
            refreshCourseNotices(userId);
        }
    }

    public static void addCourseNotices(String userId){
            ArrayList<CourseNotice> newCourseNotices = MessageNetService.getNextCourseNotices(userId, course_notice_last_id, 5);
            for(int i = 0;i < newCourseNotices.size();i++){
                courseNotices.add(newCourseNotices.get(i));
            }
            refreshLastId(0);

    }
    //刷新，相当于获取前一次刷新时间到当前时间之间的课程公告
    public static void refreshCourseNotices(String userId){
        long now = System.currentTimeMillis();
        ArrayList<CourseNotice> newCourseNotices = MessageNetService.getLatestCourseNotices(userId, courseNoticeLatestRefreshTime, now);
        courseNoticeLatestRefreshTime = now;
        for(int i =0;i<newCourseNotices.size();i++){
            courseNotices.add(i,newCourseNotices.get(i));
        }
        totalCourseNotice+=newCourseNotices.size();
    }

    public static void refreshTotalReplyNum(String userId){
        totalReply = MessageNetService.getTotalReplyNum();

    }
    public static void initReplies(String userId){
        if(replies == null){
            replies = new ArrayList<Reply>();
            replyLatestRefreshTime = System.currentTimeMillis();
            ArrayList<Reply> newReplies = MessageNetService.getNextReplies(userId,reply_last_id,5);
            for(int i = 0;i<newReplies.size();i++){
                replies.add(newReplies.get(i));
            }
            refreshLastId(1);
        }else{
            refreshReplies(userId);
        }

    }
    public static void addReplies(String userId){
        ArrayList<Reply> newReplies = MessageNetService.getNextReplies(userId,reply_last_id,5);
        for(int i = 0;i < newReplies.size();i++){
            replies.add(newReplies.get(i));
        }
        refreshLastId(1);
    }
    public static void refreshReplies(String userId){
        long now = System.currentTimeMillis();
        ArrayList<Reply> newReplies = MessageNetService.getLatestReplies(userId, replyLatestRefreshTime, now);
        replyLatestRefreshTime = now;
        for(int i =0;i<newReplies.size();i++){
            replies.add(i,newReplies.get(i));
        }
        totalReply+=newReplies.size();
    }

    public static void refreshTotalPraiseNum(String userId){
        totalPraise = MessageNetService.getTotalPraiseNum();
    }
    public static void initPraises(String userId){
        if(praises == null){
            praises = new ArrayList();
            praiseLatestRefreshTime = System.currentTimeMillis();
            ArrayList<Praise> newPraises = MessageNetService.getNextPraises(userId, praise_last_id, 5);
            for(int i = 0;i<newPraises.size();i++){
                praises.add(newPraises.get(i));
            }
            refreshLastId(2);
        }else{
            refreshPraises(userId);
        }
    }
    public static void addPraises(String userId){
        ArrayList<Praise> newPraises = MessageNetService.getNextPraises(userId, praise_last_id, 5);
        for(int i = 0;i < newPraises.size();i++){
            praises.add(newPraises.get(i));
        }
        refreshLastId(2);
    }
    public static void refreshPraises(String userId){
        long now = System.currentTimeMillis();
        ArrayList<Praise> newPraises = MessageNetService.getLatestPraises(userId, praiseLatestRefreshTime, now);
        praiseLatestRefreshTime = now;
        for(int i =0;i<newPraises.size();i++){
            praises.add(i,newPraises.get(i));
        }
        totalPraise+=newPraises.size();
    }

    public static void refreshTotalMentionMeNum(String userId){
        totalMentionMe = MessageNetService.getTotalMentionMeNum();

    }
    public static void initMentionMes(String userId){
        if(mentionMes == null){
            mentionMes = new ArrayList();
            mentionLatestRefreshTime = System.currentTimeMillis();
            ArrayList<MentionMe> newMentions= MessageNetService.getNextMentionMes(userId, mentionme_last_id, 5);
            for(int i = 0;i<newMentions.size();i++){
                mentionMes.add(newMentions.get(i));
            }
            refreshLastId(3);
        }else{
            refreshMentionMes(userId);
        }

    }
    public static void addMentionMes(String userId){
        ArrayList<MentionMe> newMentionmes = MessageNetService.getNextMentionMes(userId,mentionme_last_id, 5);
        for(int i = 0;i < newMentionmes.size();i++){
            mentionMes.add(newMentionmes.get(i));
        }
        refreshLastId(3);
    }
    public static void refreshMentionMes(String userId){
        long now = System.currentTimeMillis();
        ArrayList<MentionMe> newMentionmes = MessageNetService.getLatestMentionMes(userId,mentionLatestRefreshTime,now);
        mentionLatestRefreshTime = now;
        for(int i =0;i<newMentionmes.size();i++){
            mentionMes.add(i,newMentionmes.get(i));
        }
        totalMentionMe+=newMentionmes.size();

    }

    public static int getUnreadMessgeNum() {
        return unreadMentionNum + unreadCourseNoticeNum + unreadPraiseNum + unreadReplyNum;
    }
}
