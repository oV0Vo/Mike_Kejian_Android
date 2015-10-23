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
        int start = this.title.toLowerCase().indexOf(key.toLowerCase());
        ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.RED);
        int len = this.title.length();
        if(key.length() >= 16){
            this.title = this.title.substring(0,15)+"...";
            this.builder = new SpannableStringBuilder(this.title);
            this.builder.setSpan(redSpan,0,15, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }else{
            if(len >= 16){
                if(start+key.length() > 15){
                    //居中显示
                    int offset = (16-key.length())/2;
                    this.title = ".."+this.title.substring(start-offset,start+key.length()+offset);
                    start = 2+offset;
                    if(start+key.length()+offset < len-1){
                        this.title = this.title+"..";
                    }
                }else{
                    this.title = this.title.substring(0,15)+"...";
                }
            }
            this.builder = new SpannableStringBuilder(this.title);
            this.builder.setSpan(redSpan,start,start+key.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        }
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
