package com.kejian.mike.mike_kejian_android.ui.message;

import android.app.ActionBar;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import com.kejian.mike.mike_kejian_android.R;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import bl.MessageBLService;
import bl.SearchBLService;
import model.message.SearchResult;

public class SearchViewDemo extends AppCompatActivity {
    private LayoutInflater myInflater;
//    private SearchView srv1;
    private ListView lv1;
    private ArrayAdapter<SearchResult> adapter;
//    private String[] names;
//    private ArrayList<String> alist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view_demo);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        srv1=(SearchView)findViewById(R.id.searchView);
//        names=new String[]{"ad","dffa","uyiu","rqer","qwgt","afrgb","rtyr"};
        lv1=(ListView)findViewById(R.id.resultContainer);
        this.myInflater = getLayoutInflater();
        adapter=new SearchResultAdapter(this, android.R.layout.simple_list_item_1, SearchBLService.courses);

        lv1.setAdapter(adapter);
//        lv1.setTextFilterEnabled(true);

//        srv1.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//// TODO Auto-generated method stub
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//// TODO Auto-generated method stub
//// Toast.makeText(MainActivity.this, "1111", Toast.LENGTH_LONG).show();
//                SearchBLService.courses.clear();
//                if(newText.length()!=0){
////                    lv1.setFilterText(newText);
//                    SearchBLService.search(newText);
//                }else{
////                    lv1.clearTextFilter();
//                }
//                adapter.notifyDataSetChanged();
//                return false;
//            }
//        });
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
        getMenuInflater().inflate(R.menu.menu_search_view_demo, menu);
        MenuItem searchItem = menu.findItem(R.id.search_bar);
        searchItem.collapseActionView();
        SearchView searchview=(SearchView) searchItem.getActionView();
        searchview.setIconifiedByDefault(false);
        searchview.setQueryHint("输入查询内容");
//        SearchView searchView = (SearchView) searchItem.getActionView();
////        searchView.setInputType();
//        searchView.setIconifiedByDefault(false);
//        searchView.setQueryHint("请输入查询内容");
        searchview.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String query) {
// TODO Auto-generated method stub
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
// TODO Auto-generated method stub
// Toast.makeText(MainActivity.this, "1111", Toast.LENGTH_LONG).show();
                SearchBLService.courses.clear();
                if(newText.length()!=0){
//                    lv1.setFilterText(newText);
                    SearchBLService.search(newText);
                }else{
//                    lv1.clearTextFilter();
                }
                adapter.notifyDataSetChanged();
                return false;
            }
        });
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
        }else if(id == android.R.id.home){
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}