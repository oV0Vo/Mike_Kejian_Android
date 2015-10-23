package model.message;

import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;

/**
 * Created by I322233 on 9/30/2015.
 */
public class SearchResult {
    SpannableStringBuilder builder;
    private int id;
    private boolean isCourse = true;
    public void setIsCourse(boolean isCourse){
        this.isCourse = isCourse;
    }
    public boolean isCourse(){
        return this.isCourse;
    }
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String title;
    private String iconUrl;
    private String localIconPath;

    public void setStringBuilder(String key){
        this.builder = new SpannableStringBuilder(this.title);
        ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.RED);
        int start = this.title.indexOf(key);
        this.builder.setSpan(redSpan,start,start+key.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
    public SpannableStringBuilder getBuilder(){
        return this.builder;
    }
    public void setId(int id){
        this.id = id;
    }
    public int getId(){
        return this.id;
    }
    private int iconId;
    public void setIconId(int iconId){
        this.iconId = iconId;
    }

    public void setIconUrl(String iconUrl){
        this.iconUrl = iconUrl;
    }
    public String getIconUrl(){
        return this.iconUrl;
    }
    public String getLocalIconPath(){
        return this.localIconPath;
    }
    public void setLocalIconPath(){
        String mark = "course_id";
        String tmp_id = this.id+"";
        if(!this.isCourse()){
           mark = "user_id";
            tmp_id = this.iconId+"";
        }
        this.localIconPath = this.iconUrl.replace(".","").replace(":","").replace("/","") + "#"+mark+"#"+tmp_id;
    }

}
