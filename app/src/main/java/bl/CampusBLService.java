package bl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.campus.Post;
import model.campus.Reply;
import model.helper.ResultMessage;

/**
 * Created by showjoy on 15/9/10.
 */
public class CampusBLService {


    public static List<Post> getLatestPostList() {
        ArrayList<Post> postList = new ArrayList<Post>();
        Post post = new Post();
        post.setUserId("miketest1");
        post.setUserIconUrl("http://i13.tietuku.com/817fbd644a01a1c6.jpg");
        post.setAuthorName("小明");
        post.setDate("2015-09-11 07:10:12");
        post.setTitle("测试标题测试标题测试标题");
        post.setContent("测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容");
        post.setPraise(10);
        post.setViewNum(20);
        post.setReplyList(new ArrayList<Reply>());
        for(int i=0; i<10; i++)
            postList.add(post);

        return postList;
    }

    public static ArrayList<Post> getHottestPostList() {
        ArrayList<Post> postList = new ArrayList<Post>();
        Post post = new Post();
        post.setUserId("miketest1");
        post.setAuthorName("小明");
        post.setTitle("最新测试标题");
        post.setContent("测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容测试内容");
        post.setPraise(10);
        post.setViewNum(20);
        post.setReplyList(new ArrayList<Reply>());
        for(int i=0; i<10; i++)
            postList.add(post);

        return postList;
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
