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
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    private String title;

    public void setStringBuilder(String key){
        this.builder = new SpannableStringBuilder(this.title);
        ForegroundColorSpan redSpan = new ForegroundColorSpan(Color.RED);
        int start = this.title.indexOf(key);
        this.builder.setSpan(redSpan,start,start+key.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
    }
    public SpannableStringBuilder getBuilder(){
        return this.builder;
    }

}
