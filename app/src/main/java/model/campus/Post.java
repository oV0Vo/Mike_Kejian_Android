package model.campus;

import java.util.ArrayList;

/**
 * Created by showjoy on 15/9/10.
 */
public class Post {
    private int userId;
    private String title;
    private String content;
    private int praise;
    private ArrayList<Reply> replyList;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getPraise() {
        return praise;
    }

    public void setPraise(int praise) {
        this.praise = praise;
    }

    public ArrayList<Reply> getReplyList() {
        return replyList;
    }

    public void setReplyList(ArrayList<Reply> replyList) {
        this.replyList = replyList;
    }

}
