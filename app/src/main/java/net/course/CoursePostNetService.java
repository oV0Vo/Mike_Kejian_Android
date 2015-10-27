package net.course;

import android.util.Log;

import net.NetConfig;
import net.httpRequest.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import model.campus.Post;
import model.campus.Reply;
import util.DateUtil;

/**
 * Created by violetMoon on 2015/10/14.
 */
public class CoursePostNetService {

    private static final String TAG = "CourseInfoNetService";

    private static final String s = "/";
    private static final String BASE_URL = NetConfig.BASE_URL + "CoursePost" + s;

    private static HttpRequest http = HttpRequest.getInstance();

    public static ArrayList<Post> getPosts(String courseId) {
        String url = BASE_URL + "getCoursePosts/";
        HashMap<String, String> paraMap = new HashMap<String, String>();
        paraMap.put("courseId", courseId);
        paraMap.put("lastId", Integer.toString(Integer.MAX_VALUE));
        paraMap.put("num", Integer.toString(Integer.MAX_VALUE));

        String responseContent = http.sentGetRequest(url, paraMap);
        Log.i(TAG, "getPosts: " + responseContent);
        try {
            JSONArray jPosts = new JSONArray(responseContent);
            ArrayList<Post> posts = new ArrayList<Post>();
            for(int i=0; i<jPosts.length(); ++i) {
                JSONObject jPost = jPosts.getJSONObject(i);
                Post post = parsePost(jPost);
                posts.add(post);
            }

            return posts;

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "getMyCourseBrief json error");
            return null;
        }
    }

    private static Post parsePost(JSONObject jPost) {
        try {
            Post post = new Post();

            String postId = jPost.getString("id");
            post.setPostId(postId);

            String title = jPost.getString("title");
            post.setTitle(title);

            String content = jPost.getString("content");
            post.setContent(content);

            String authorId = jPost.getString("user_id");
            post.setUserId(authorId);

            String timeStamp = jPost.getString("timestamp");
            Date date = DateUtil.convertPhpTimeStamp(timeStamp);
            post.setDate(date);

            int viewNum = jPost.getInt("watch_count");
            post.setViewNum(viewNum);

            int praiseNum = jPost.getInt("like_count");
            post.setPraise(praiseNum);

            String authorName = jPost.getString("authorName");
            post.setAuthorName(authorName);

            int replyCount = jPost.getInt("reply_count");
            post.setReplyNum(replyCount);

            post.setReplyList(null);

            return post;

        } catch (JSONException e) {
            e.printStackTrace();
            Log.e(TAG, "parseAnnouc json error");
            return null;
        }
    }
}
