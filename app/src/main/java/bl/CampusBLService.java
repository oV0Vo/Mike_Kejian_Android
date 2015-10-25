package bl;

import net.CampusNetService;

import java.util.ArrayList;
import java.util.Date;

import model.campus.Post;
import model.campus.Reply;
import model.user.Global;
import model.user.user;

/**
 * Created by showjoy on 15/9/10.
 */
public class CampusBLService {
    public static ArrayList<Post> latestPosts = new ArrayList<>();
    public static ArrayList<Post> nextLatestPosts = new ArrayList<>();
    public static ArrayList<Post> hottestPosts = new ArrayList<>();
    public static ArrayList<Post> nextHottestPosts = new ArrayList<>();
    public static Reply publishedReply;


    public static void refreshLatestPosts() {
        latestPosts.clear();
        latestPosts.addAll(CampusNetService.getLatestPosts("0", 7));
    }

    public static void getNextLatestPosts() {
        String startId = (latestPosts.size()-1)>0?latestPosts.get(latestPosts.size()-1).getPostId():"0";
        nextLatestPosts = CampusNetService.getLatestPosts(startId, 7);
    }

    public static void moveLatestPosts() {
        latestPosts.addAll(nextLatestPosts);
        nextLatestPosts.clear();
    }

    public static void refreshHottestPosts() {
        hottestPosts.clear();
        hottestPosts.addAll(CampusNetService.getHottestPosts("0",7));
    }

    public static void getNextHottestPosts() {
        String startId = (hottestPosts.size()-1)>0?hottestPosts.get(hottestPosts.size()-1).getPostId():"0";
        nextHottestPosts = CampusNetService.getHottestPosts(startId, 7);
    }

    public static void moveHottestPosts() {
        hottestPosts.addAll(nextHottestPosts);
        nextHottestPosts.clear();
    }

    public static Post getPostDetail(String postId) {
        Post post = CampusNetService.getPostInfo(postId);
        return post;
    }

    public static String publish(String courseId, String title, String content) {

        Post post = new Post();
        post.setPostId("0");
        post.setUserId(((user) Global.getObjectByName("user")).getId());
        post.setAuthorName(((user) Global.getObjectByName("user")).getNick_name());
        post.setTitle(title);
        post.setContent(content);
        post.setDate(new Date());
        post.setViewNum(0);
        post.setPraise(0);
        return CampusNetService.publish(courseId, post);
    }

    public static String reply(String courseId, String replyTo, String replyToTitle, String content) {
        Reply reply = new Reply();
        reply.setCourseId(courseId);
        reply.setTitle("回复:" + replyToTitle);
        reply.setUserIconUrl(((user)Global.getObjectByName("user")).getIcon());
        reply.setUserId(((user) Global.getObjectByName("user")).getId());
        reply.setAuthorName(((user) Global.getObjectByName("user")).getNick_name());
        reply.setContent(content);
        reply.setDate(new Date());
        reply.setCommentNum(0);
        reply.setPraise(0);
        reply.setReplyTo(replyTo);
        publishedReply = reply;

       return  CampusNetService.reply(reply);

    }

    public static boolean isFollowed(String postId) {
        String userId = ((user) Global.getObjectByName("user")).getId();
        return CampusNetService.isFollowed(userId, postId);
    }

    public static boolean isPraised(String postId) {
        String userId = ((user) Global.getObjectByName("user")).getId();
        return CampusNetService.isPraised(userId, postId);
    }

    public static void followThisPost(String postId) {
        String userId = ((user) Global.getObjectByName(("user"))).getId();
        CampusNetService.followThisPost(userId, postId);
    }

    public static void praiseThisPost(String postId) {
        String userId = ((user) Global.getObjectByName("user")).getId();
        CampusNetService.praiseThisPost(userId, postId);
    }

    public static void viewThisPost(String postId) {
        String userId = ((user) Global.getObjectByName("user")).getId();
        CampusNetService.viewThisPost(userId, postId);
    }

    public static String inviteToAnswer(String postId, ArrayList<String> userIdList) {
        String userId = ((user) Global.getObjectByName("user")).getId();
        return CampusNetService.inviteToAnswer(userId, postId, userIdList);
    }

    public static String inviteToAnswer(String postId, String userId) {
        ArrayList<String> userList = new ArrayList<>();
        userList.add(userId);
        return inviteToAnswer(postId, userList);
    }

}
