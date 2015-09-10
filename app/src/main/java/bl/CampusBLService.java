package bl;

import java.util.ArrayList;

import model.campus.Post;
import model.helper.ResultMessage;

/**
 * Created by showjoy on 15/9/10.
 */
public class CampusBLService {
    private static CampusBLService campusBLService ;

    public static CampusBLService getCampusBLService() {
        return (campusBLService==null)?new CampusBLService():campusBLService;
    }

    public ArrayList<Post> getPosts() {
        return null;
    }

    public ArrayList<Post> sortPostsByDate(ArrayList<Post> posts) {
        return null;
    }

    public ArrayList<Post> sortPostsByPopularity(ArrayList<Post> posts) {
        return null;
    }

    public ResultMessage publish(Post post) {
        return null;
    }

    public ArrayList<Post> searchPost(String info){
        return null;
    }
}
