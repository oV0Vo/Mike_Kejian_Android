package bl;

import net.CampusNetService;

import java.util.ArrayList;
import java.util.Date;

import model.campus.Post;
import model.campus.Reply;
import model.helper.ResultMessage;

/**
 * Created by showjoy on 15/9/10.
 */
public class CampusBLService {
    public static ArrayList<Post> latestPosts;
    public static ArrayList<Post> hottestPosts;


    public static void refreshLatestPosts() {
        latestPosts = CampusNetService.getLatestPosts("0",7);
    }

    public static void getNextLatestPosts() {
        ArrayList<Post> nextPosts = CampusNetService.getLatestPosts(latestPosts.get(latestPosts.size()-1).getPostId(), 7);
        latestPosts.addAll(nextPosts);
    }

    public static void refreshHottestPosts() {
        hottestPosts = CampusNetService.getHottestPosts("0",7);
    }

    public static void getNextHottestPosts() {
        ArrayList<Post> nextPosts = CampusNetService.getHottestPosts(latestPosts.get(latestPosts.size()-1).getPostId(), 7);
        hottestPosts.addAll(nextPosts);
    }

    public static Post getPostDetail(String postId) {
        Post post = new Post();
        post.setUserId("miketest1");
        post.setAuthorName("小明");
        post.setTitle("最新测试标题");
        post.setContent("测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容");
        post.setPraise(10);
        post.setViewNum(20);
        post.setDate(new Date().toString());
        Reply reply = new Reply();
        reply.setUserId("00001");
        reply.setAuthorName("小明");
        reply.setContent("12312312加12见蒋介石的姐姐啊电视剧急啊神盾局就的撒飞");
        reply.setDate(new Date());
        reply.setPraise(5);
        reply.setSubReplyList(new ArrayList<Reply>());
        ArrayList<Reply> replyList = new ArrayList<Reply>();
        for(int i=0; i<11; i++)
            replyList.add(reply);
        post.setReplyList(replyList);

        return post;

    }

    public ResultMessage publish(Post post) {
        return null;
    }

    public ArrayList<Post> searchPost(String info){
        return null;
    }
}
