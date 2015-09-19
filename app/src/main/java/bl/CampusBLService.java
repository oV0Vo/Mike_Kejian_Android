package bl;

import java.util.ArrayList;

import model.campus.Post;
import model.campus.Reply;
import model.helper.ResultMessage;

/**
 * Created by showjoy on 15/9/10.
 */
public class CampusBLService {


    public static ArrayList<Post> getLatestPostList() {
        ArrayList<Post> postList = new ArrayList<Post>();
        Post post = new Post();
        post.setUserId("miketest1");
        post.setAuthorName("小明");
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


    public ResultMessage publish(Post post) {
        return null;
    }

    public ArrayList<Post> searchPost(String info){
        return null;
    }
}
