package com.kejian.mike.mike_kejian_android.ui.main;

import android.content.Context;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.kejian.mike.mike_kejian_android.R;

import java.util.List;

import bl.MessageBLService;
import bl.SearchBLService;
import model.message.CourseNotice;
import model.message.SearchResult;

public class SearchActivity extends AppCompatActivity implements TextWatcher,View.OnClickListener{
    private LinearLayout search_title;
    private ImageView back;
    private EditText searchContent;
    private ImageView search;
    private ImageView clearSearch;
    private LayoutInflater myInflater;
    private ListView courseContainer;
    private ListView postContainer;
    private ArrayAdapter<SearchResult> courseAdapter;
    private ArrayAdapter<SearchResult> postAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        this.initView();
    }
    private void initView(){
        this.search_title = (LinearLayout)findViewById(R.id.search_title);
        this.back = (ImageView)this.search_title.findViewById(R.id.back);
        this.back.setOnClickListener(this);
        this.searchContent = (EditText)this.search_title.findViewById(R.id.searchContent);
        this.search = (ImageView)this.search_title.findViewById(R.id.search_btn);
        this.clearSearch = (ImageView)this.search_title.findViewById(R.id.clearSearchContent);
        this.clearSearch.setVisibility(View.GONE);
        this.searchContent.addTextChangedListener(this);
        this.clearSearch.setOnClickListener(this);

        this.myInflater = getLayoutInflater();
        this.courseContainer = (ListView)findViewById(R.id.course_container);
        this.postContainer = (ListView)findViewById(R.id.post_container);

        this.courseAdapter = new SearchResultAdapter(this, android.R.layout.simple_list_item_1, SearchBLService.courses);
        this.postAdapter = new SearchResultAdapter(this,android.R.layout.simple_list_item_1,SearchBLService.posts);

        this.courseContainer.setAdapter(this.courseAdapter);
        this.postContainer.setAdapter(this.postAdapter);

    }
    static class ViewHolder{
        ImageView imageView;
        TextView title;
    }
    private class SearchResultAdapter extends ArrayAdapter<SearchResult> {
        public SearchResultAdapter(Context context, int layoutId, List<SearchResult> searchResults){
            super(context,layoutId,searchResults);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder;
            if (convertView == null) {
                convertView = myInflater.inflate(R.layout.layout_search_result
                        , null);
                viewHolder = new ViewHolder();
                viewHolder.imageView = (ImageView)convertView.findViewById(R.id.pic);
                viewHolder.title = (TextView)convertView.findViewById(R.id.title);
                convertView.setTag(viewHolder);
            }else{
                viewHolder = (ViewHolder)convertView.getTag();
            }
            SearchResult searchResult = getItem(position);
            viewHolder.imageView.setImageResource(R.drawable.daoxu);
            viewHolder.title.setText(searchResult.getBuilder());
            return convertView;
        }
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

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        if(s.length() > 0){
            this.clearSearch.setVisibility(View.VISIBLE);

        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if(id == R.id.clearSearchContent){
            this.searchContent.setText("");
        }else if(id == R.id.back){
            SearchBLService.courses.clear();
            SearchBLService.posts.clear();
            this.finish();
        }
    }
}
