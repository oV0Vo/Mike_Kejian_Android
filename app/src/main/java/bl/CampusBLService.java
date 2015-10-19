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
        String startId = (latestPosts.size()-1)>0?latestPosts.get(latestPosts.size()-1).getPostId():"0";
        ArrayList<Post> nextPosts = CampusNetService.getLatestPosts(startId, 7);
        latestPosts.addAll(nextPosts);
    }

    public static void refreshHottestPosts() {
        hottestPosts = CampusNetService.getHottestPosts("0",7);
    }

    public static void getNextHottestPosts() {
        String startId = (hottestPosts.size()-1)>0?hottestPosts.get(hottestPosts.size()-1).getPostId():"0";
        ArrayList<Post> nextPosts = CampusNetService.getHottestPosts(startId, 7);
        hottestPosts.addAll(nextPosts);
    }

    public static Post getPostDetail(String postId) {
        return CampusNetService.getPostInfo(postId);
    }

    public ResultMessage publish(Post post) {
        return null;
    }

    public ArrayList<Post> searchPost(String info){
        return null;
    }


}
