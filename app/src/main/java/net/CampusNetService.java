package net;

import net.httpRequest.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import model.campus.Post;

/**
 * Created by ShowJoy on 2015/10/18.
 */
public class CampusNetService {
    private static HttpRequest httpRequest = HttpRequest.getInstance();
    private static String baseUrl = "http://112.124.101.41:80/mike_server_v02/index.php/Home/Post/";

    private static ArrayList<Post> handlePostJson(String json) {
        ArrayList<Post> posts = new ArrayList();
        try {
            JSONArray postsJsonArray = new JSONArray(json);
            for(int  i=0; i<postsJsonArray.length(); i++) {
                Post tempPost = new Post();
                JSONObject postJson = postsJsonArray.getJSONObject(i);
                tempPost.setPostId(postJson.getString("id"));
                tempPost.setUserId(postJson.getString("user_id"));
                tempPost.setTitle(postJson.getString("title"));
                tempPost.setContent(postJson.getString("content"));
                tempPost.setDate(postJson.getString("date"));
                tempPost.setViewNum(postJson.getInt("watch_count"));
                tempPost.setReplyNum(postJson.getInt("reply_to"));
                tempPost.setPraise(postJson.getInt("praise"));
                tempPost.setUserIconUrl(postJson.getString("icon_url"));
                posts.add(tempPost);
            }
        } catch(JSONException e) {
            e.printStackTrace();
        }

        return posts;
    }

    public static ArrayList<Post> getLatestPosts(String startId, int number) {
        HashMap<String, String> params = new HashMap<>();
        params.put("courseId", "0");
        params.put("startId", startId);
        params.put("number", number+"");
        String result = httpRequest.sentGetRequest(baseUrl+"getNewestPost/", params);
        return handlePostJson(result);
    }

    public static ArrayList<Post> getHottestPosts(String startId, int number) {
        HashMap<String, String> params = new HashMap<>();
        params.put("courseId", "0");
        params.put("startId", startId);
        params.put("number", number+"");
        String result = httpRequest.sentGetRequest(baseUrl+"getHotestPost/", params);
        return handlePostJson(result);
    }
}
