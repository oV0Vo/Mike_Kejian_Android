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
        String tmp_title = this.title;
        int start = tmp_title.toLowerCase().indexOf(key.toLowerCase());
        int len = tmp_title.length();
        if(start == -1){

            if(len >= 17){
                tmp_title = tmp_title.substring(0,16)+"..";
            }
            this.builder = new SpannableStringBuilder(tmp_title);
        }else{
            ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.RED);
            if(key.length() >= 17){
                tmp_title = ".."+key.substring(0,16)+"..";
                key = key.substring(0,16);
                start = 2;
            }else{
                if(len >= 17){
                    int offset = (16-key.length())/2;
                    if(start+key.length()-1+offset >= len-1 ){
                        start = start - len +18;
                        tmp_title = ".."+tmp_title.substring(len-16,len);
                    }else if(start - offset <=0){
                        tmp_title = tmp_title.substring(0,16)+"..";
                    }else{
                        tmp_title = ".."+tmp_title.substring(start-offset,start+offset+key.length())+"..";
                        start = offset+2;
                    }
                }
            }
            this.builder = new SpannableStringBuilder(tmp_title);
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
