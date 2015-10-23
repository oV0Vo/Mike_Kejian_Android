package net;

import net.httpRequest.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;

import model.campus.Post;
import model.campus.Reply;

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
                tempPost.setCourseId(postJson.getString("course_id"));
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

    private static Post handleSinglePostJson(String json) {
        Post tempPost = new Post();
        try {
            JSONObject postJson = new JSONObject(json);
            tempPost.setPostId(postJson.getString("id"));
            tempPost.setCourseId(postJson.getString("course_id"));
            tempPost.setAuthorName(postJson.getString("authorName"));
            tempPost.setUserId(postJson.getString("user_id"));
            tempPost.setTitle(postJson.getString("title"));
            tempPost.setContent(postJson.getString("content"));
            tempPost.setDate(postJson.getString("timestamp"));
            tempPost.setViewNum(postJson.getInt("watch_count"));
            tempPost.setPraise(postJson.getInt("praise"));
            tempPost.setUserIconUrl(postJson.getString("icon_url"));
            tempPost.setReplyList(handleReplies(postJson.getString("replies")));
            tempPost.setReplyNum(tempPost.getReplyList().size());
        } catch(JSONException e) {
            e.printStackTrace();
        }
        return tempPost;
    }

    public static Post getPostInfo(String postId) {
        HashMap<String, String>  params = new HashMap<>();
        params.put("postId", postId);
        String result = httpRequest.sentGetRequest(baseUrl + "getPostReplys/", params);
        return handleSinglePostJson(result);
    }

    private static ArrayList handleReplies(String json) {
        ArrayList<Reply> replies = new ArrayList<Reply> ();
        try {
            JSONArray replyJsonArray = new JSONArray(json);
            for(int i=0; i<replyJsonArray.length(); i++) {
                Reply tempReply = new Reply();
                JSONObject reply = replyJsonArray.getJSONObject(i);
                tempReply.setContent(reply.getString("content"));
                tempReply.setUserId(reply.getString("user_id"));
                tempReply.setDate(reply.getString("timestamp"));
                tempReply.setAuthorName(reply.getString("name"));
                tempReply.setViewNum(reply.getInt("watch_count"));
                tempReply.setCommentNum(reply.getInt("reply_num"));
                replies.add(tempReply);
            }
        } catch(JSONException e) {
            e.printStackTrace();
        }
        return replies;
    }

    public static String publish(String courseId, Post post) {
        HashMap<String, String> params = new HashMap<>();
        JSONObject postJson = new JSONObject();
        try {
            postJson.put("postId", post.getPostId());
            postJson.put("userId", post.getUserId());
            postJson.put("authorName",URLEncoder.encode( post.getAuthorName(),"utf8"));
            postJson.put("title", URLEncoder.encode(post.getTitle(), "utf8"));
            postJson.put("content", URLEncoder.encode(post.getContent(), "utf8"));
            postJson.put("praise", post.getPraise());
            postJson.put("viewNum", post.getViewNum());
            postJson.put("date", post.getDate());
        } catch(Exception e) {
            e.printStackTrace();
        }
        params.put("userId", post.getUserId());
        params.put("courseId", courseId);
        try {
            params.put("postInfo", URLEncoder.encode(postJson.toString(),"utf8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String result = httpRequest.sentGetRequest(baseUrl + "postNewQuestion/", params);

        return result;

    }

    public static String reply(Reply reply) {
        HashMap<String, String> params = new HashMap<>();
        JSONObject replyJson = new JSONObject();
        try {
            replyJson.put("postId", "0");
            replyJson.put("authorName", reply.getAuthorName());
            replyJson.put("title", "");
            replyJson.put("content", reply.getContent());
            replyJson.put("date", reply.getDate());
            replyJson.put("reply_to", reply.getReplyTo());
            replyJson.put("praise", reply.getPraise());
            replyJson.put("viewNum", reply.getViewNum());
            params.put("userId", reply.getUserId());
            params.put("courseId", reply.getCourseId());
            params.put("postInfo", URLEncoder.encode(replyJson.toString(), "utf8"));

        } catch(JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        String result = httpRequest.sentGetRequest(baseUrl + "postNewQuestion/", params);
        System.out.println(replyJson.toString());
        System.out.println("result: " + result);

        return result;
    }

    public static boolean isPraised(String userId, String postId) {
        HashMap<String, String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("postId", postId);
        boolean result = Boolean.parseBoolean(httpRequest.sentGetRequest(baseUrl + "isPraised/", params));
        return result;
    }

    public static boolean praiseThisPost(String userId, String postId) {
        HashMap<String,String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("postId", postId);
        params.put("postInfoType", "PRAISE");
        boolean result = Boolean.parseBoolean(httpRequest.sentGetRequest(baseUrl + "updatePostInfo/", params));
        return result;
    }

    public static boolean viewThisPost(String userId, String postId) {
        HashMap<String,String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("postId", postId);
        params.put("postInfoType", "VIEWNUM");
        boolean result = Boolean.parseBoolean(httpRequest.sentGetRequest(baseUrl + "updatePostInfo/", params));
        return result;
    }

    public static boolean isFollowed(String userId, String postId) {
        HashMap<String,String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("postId", postId);
        boolean result = Boolean.parseBoolean(httpRequest.sentGetRequest(baseUrl + "isFollowed/", params));
        return result;
    }

    public static boolean followThisPost(String userId, String postId) {
        HashMap<String,String> params = new HashMap<>();
        params.put("userId", userId);
        params.put("postId", postId);
        boolean result = Boolean.parseBoolean(httpRequest.sentGetRequest(baseUrl + "followThisPost/", params));
        return result;
    }
}
