package net;

import net.httpRequest.HttpRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URLEncoder;
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
    private static String peopleSearchUrl = "http://112.124.101.41/mike_server_v02/index.php/Home/User/searchUser";

    private static void handleCourseResults(ArrayList<SearchResult> searchResults,String key, String jsonString){
        if(jsonString != null){
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
    }
    private static void handlePostResults(ArrayList<SearchResult> searchResults,String key, String jsonString){
        if(jsonString != null){
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
    }
    private static void handlePeopleResults(ArrayList<SearchResult> searchResults,String key,String jsonString){
        if(jsonString != null){
            try {
                JSONArray peopleResultsArray = new JSONArray(jsonString);
                for(int i = 0;i<peopleResultsArray.length();i++){
                    JSONObject peolpeResultJson = peopleResultsArray.getJSONObject(i);
                    int id = peolpeResultJson.getInt("id");
                    String title = peolpeResultJson.getString("nick_name");
                    String iconUrl = peolpeResultJson.getString("icon_url");

                    SearchResult searchResult = new SearchResult();
                    searchResult.setTitle(title);
                    searchResult.setStringBuilder(key);
                    searchResult.setIsCourse(false);
                    searchResult.setId(id);
                    searchResult.setIconUrl(iconUrl);
                    searchResult.setIconId(id);
                    searchResult.setLocalIconPath();
                    searchResults.add(searchResult);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    public static ArrayList<SearchResult> searchPeople(String key,int searchType){
        ArrayList<SearchResult> searchResults = new ArrayList<>();
        HashMap<String,String> params = new HashMap<>();
        String tmp_key = URLEncoder.encode(key);
        params.put("key", tmp_key);
        params.put("type",searchType+"");
        String peopleResult = httpRequest.sentGetRequest(peopleSearchUrl,params);
        handlePeopleResults(searchResults,key,peopleResult);
        return searchResults;
    }
    public static ArrayList<SearchResult> search(String key){
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        ArrayList<SearchResult> searchResults = new ArrayList();
        HashMap<String,String> params = new HashMap<>();
        String tmp_key = URLEncoder.encode(key);
        params.put("info",tmp_key);
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
