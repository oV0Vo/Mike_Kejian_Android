package bl;

import net.SearchNetService;

import java.util.ArrayList;

import model.message.SearchResult;

/**
 * Created by I322233 on 9/30/2015.
 */
public class SearchBLService {
    public static ArrayList<SearchResult> courses = new ArrayList();
    public static ArrayList<SearchResult> posts = new ArrayList();
    public static void search(String key){
        ArrayList<SearchResult> searchResults = SearchNetService.search(key);
        for(int i = 0;i<searchResults.size();i++){
            SearchResult searchResult = searchResults.get(i);
            if(searchResult.isCourse()){
                courses.add(searchResult);
            }else{
                posts.add(searchResult);
            }
        }

    }
}
