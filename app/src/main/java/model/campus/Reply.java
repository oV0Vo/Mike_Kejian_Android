package model.campus;

import java.util.ArrayList;
import java.util.Date;

/**
 * Created by showjoy on 15/9/10.
 */
public class Reply {
    private String userId;
    private String authorName;
    private String content;
    private Date date;
    private int praise;
    private ArrayList<Reply> subReplyList;
    private String userIconUrl;


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getPraise() {
        return praise;
    }

    public void setPraise(int praise) {
        this.praise = praise;
    }

    public ArrayList<Reply> getSubReplyList() {
        return subReplyList;
    }

    public void setSubReplyList(ArrayList<Reply> subReplyList) {
        this.subReplyList = subReplyList;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getUserIconUrl() {
        return userIconUrl;
    }

    public void setUserIconUrl(String userIconUrl) {
        this.userIconUrl = userIconUrl;
    }
}
