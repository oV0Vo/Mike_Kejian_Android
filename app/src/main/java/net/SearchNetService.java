package net;

import net.httpRequest.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import model.campus.Post;
import model.message.CourseNotice;
import model.message.SearchResult;
import model.user.CourseBrief;

/**
 * Created by I322233 on 10/1/2015.
 *
 */
public class SearchNetService {
    private static HttpRequest httpRequest = HttpRequest.getInstance();
    private static String courseSearchUrl = "http://112.124.101.41:80/mike_server_v02/index.php/Home/Course/searchCourse";
    private static String postSearchUrl = "http://112.124.101.41/mike_server_v02/index.php/Home/Post/searchPost";
    private static ArrayList<Post> posts = new ArrayList();
    private static ArrayList<CourseBrief> courseBriefs = new ArrayList();
    static {
        String[] words = {"数","我","是","蘑","故","事","会","大","街","上","人","无","为","可","你","个","扣","品","未"};
        for(int i = 0;i<5;i++){
            Post post = new Post();
            int ran1 = (int)(Math.random()*19);
            int ran2 = (int)(Math.random()*19);
            int ran3 = (int)(Math.random()*19);
            post.setTitle("ab"+words[ran1]+words[ran2]+words[ran3]);
            posts.add(post);
        }
        for(int i = 0;i<5;i++){
            Post post = new Post();
            int ran1 = (int)(Math.random()*19);
            int ran2 = (int)(Math.random()*19);
            int ran3 = (int)(Math.random()*19);
            post.setTitle(words[ran1]+words[ran2]+words[ran3]+"学");
            posts.add(post);
        }
        for(int i =0;i<5;i++){
            CourseBrief courseBrief = new CourseBrief();
            int ran1 = (int)(Math.random()*19);
            int ran2 = (int)(Math.random()*19);
            courseBrief.setCourseName("a" + words[ran1] + words[ran2]+"bc");
            courseBriefs.add(courseBrief);
        }
        for(int i =0;i<5;i++){
            CourseBrief courseBrief = new CourseBrief();
            int ran1 = (int)(Math.random()*19);
            int ran2 = (int)(Math.random()*19);
            courseBrief.setCourseName(words[ran1]+words[ran2]+"学");
            courseBriefs.add(courseBrief);
        }

    }
    private static void handleCourseResults(ArrayList<SearchResult> searchResults,String key, String jsonString){
        try {
            JSONArray courseResultsArray = new JSONArray(jsonString);
            for(int i = 0;i<courseResultsArray.length();i++){
                JSONObject courseResultJson = courseResultsArray.getJSONObject(i);
                int id = courseResultJson.getInt("id");
                String courseName = courseResultJson.getString("course_name");
                String iconUrl = courseResultJson.getString("icon_url");
                SearchResult searchResult = new SearchResult();
                searchResult.setTitle(courseName);
                searchResult.setStringBuilder(key);
                searchResult.setIsCourse(true);
                searchResult.setId(id);
                searchResult.setIconUrl(iconUrl);
                searchResult.setLocalIconPath();
                searchResults.add(searchResult);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private static void handlePostResults(ArrayList<SearchResult> searchResults,String key, String jsonString){
        try {
            JSONArray postResultsArray = new JSONArray(jsonString);
            for(int i = 0;i<postResultsArray.length();i++){
                JSONObject postResultJson = postResultsArray.getJSONObject(i);
                int id = postResultJson.getInt("post_id");
                String title = postResultJson.getString("title");
                String iconUrl = postResultJson.getString("icon_url");
                int iconId = postResultJson.getInt("user_id");
                SearchResult searchResult = new SearchResult();
                searchResult.setTitle(title);
                searchResult.setStringBuilder(key);
                searchResult.setIsCourse(false);
                searchResult.setId(id);
                searchResult.setIconUrl(iconUrl);
                searchResult.setIconId(iconId);
                searchResult.setLocalIconPath();
                searchResults.add(searchResult);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public static ArrayList<SearchResult> search(String key){
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        ArrayList<SearchResult> searchResults = new ArrayList();
        HashMap<String,String> params = new HashMap<>();
        params.put("info",key);
        String courseResult = httpRequest.sentGetRequest(courseSearchUrl,params);
        handleCourseResults(searchResults,key,courseResult);

        String postResult = httpRequest.sentGetRequest(postSearchUrl,params);
        handlePostResults(searchResults,key,postResult);
//        for(int i = 0;i<courseBriefs.size();i++){
//            String courseName = courseBriefs.get(i).getCourseName();
//            if(courseName.contains(key)){
//                SearchResult searchResult = new SearchResult();
//                searchResult.setTitle(courseName);
//                searchResult.setStringBuilder(key);
//                searchResult.setIsCourse(true);
//                searchResults.add(searchResult);
//            }
//        }
//        for(int i = 0;i<posts.size();i++){
//            String title = posts.get(i).getTitle();
//            if(title.contains(key)){
//                SearchResult searchResult = new SearchResult();
//                searchResult.setTitle(title);
//                searchResult.setStringBuilder(key);
//                searchResult.setIsCourse(false);
//                searchResults.add(searchResult);
//            }
//        }

        return searchResults;
    }
}
