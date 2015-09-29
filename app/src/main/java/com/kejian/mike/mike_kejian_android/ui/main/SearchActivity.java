package com.kejian.mike.mike_kejian_android.ui.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kejian.mike.mike_kejian_android.R;

public class SearchActivity extends AppCompatActivity {
    private LinearLayout search_title;
    private EditText searchContent;
    private ImageView search;
    private ImageView clearSearch;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        this.initView();
    }
    private void initView(){
        this.search_title = (LinearLayout)findViewById(R.id.search_title);
        this.searchContent = (EditText)this.search_title.findViewById(R.id.searchContent);
        this.search = (ImageView)this.search_title.findViewById(R.id.search_btn);
        this.clearSearch = (ImageView)this.search_title.findViewById(R.id.clearSearchContent);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
