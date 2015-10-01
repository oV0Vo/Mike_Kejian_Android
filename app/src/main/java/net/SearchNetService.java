package net;

import java.util.ArrayList;

import model.campus.Post;
import model.message.SearchResult;
import model.user.CourseBrief;

/**
 * Created by I322233 on 10/1/2015.
 */
public class SearchNetService {
    private static ArrayList<Post> posts = new ArrayList<>();
    private static ArrayList<CourseBrief> courseBriefs = new ArrayList<>();
    static {
        String[] words = {"数","我","是","蘑","故","事","会","大","街","上","人","无","为","可","你","个","扣","品","未"};
        for(int i = 0;i<5;i++){
            Post post = new Post();
            int ran1 = (int)(Math.random()*19);
            int ran2 = (int)(Math.random()*19);
            int ran3 = (int)(Math.random()*19);
            post.setTitle("数学"+words[ran1]+words[ran2]+words[ran3]);
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
            courseBrief.setCourseName("数学" + words[ran1] + words[ran2]);
        }
        for(int i =0;i<5;i++){
            CourseBrief courseBrief = new CourseBrief();
            int ran1 = (int)(Math.random()*19);
            int ran2 = (int)(Math.random()*19);
            courseBrief.setCourseName(words[ran1]+words[ran2]+"学");
        }

    }
    public static ArrayList<SearchResult> search(String key){
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ArrayList<SearchResult> searchResults = new ArrayList<>();
        for(int i = 0;i<courseBriefs.size();i++){
            String courseName = courseBriefs.get(i).getCourseName();
            if(courseName.contains(key)){
                SearchResult searchResult = new SearchResult();
                searchResult.setTitle(courseName);
                searchResult.setStringBuilder(key);
                searchResult.setIsCourse(true);
                searchResults.add(searchResult);
            }
        }
        for(int i = 0;i<posts.size();i++){
            String title = posts.get(i).getTitle();
            if(title.contains(key)){
                SearchResult searchResult = new SearchResult();
                searchResult.setTitle(title);
                searchResult.setStringBuilder(key);
                searchResult.setIsCourse(false);
                searchResults.add(searchResult);
            }
        }
        return searchResults;
    }
}
