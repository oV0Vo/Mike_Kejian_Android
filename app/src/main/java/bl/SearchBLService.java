package bl;

import net.SearchNetService;

import java.util.ArrayList;

import model.helper.SearchType;
import model.message.SearchResult;

/**
 * Created by I322233 on 9/30/2015.
 */
public class SearchBLService {
    public static ArrayList<SearchResult> courses = new ArrayList();
    public static ArrayList<SearchResult> posts = new ArrayList();
    public static ArrayList<SearchResult> people = new ArrayList<>();
    public static void search(String key){
        ArrayList<SearchResult> searchResults = SearchNetService.search(key);
        addResults(searchResults);

    }
    public synchronized static void clearPeople(){
        people.clear();
    }
    public static void searchPeople(String key,int searchType){
        people = SearchNetService.searchPeople(key,searchType);

    }

    private synchronized static void addResults(ArrayList<SearchResult> searchResults){
        for(int i = 0;i<searchResults.size();i++){
            SearchResult searchResult = searchResults.get(i);
            if(searchResult.isCourse()){
                courses.add(searchResult);
            }else{
                posts.add(searchResult);
            }
        }
    }
    public synchronized static void clearCourses(){
        courses.clear();
    }
    public synchronized static void clearPosts(){
        posts.clear();
    }
}
