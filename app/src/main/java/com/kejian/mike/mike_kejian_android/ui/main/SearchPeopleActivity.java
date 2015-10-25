package com.kejian.mike.mike_kejian_android.ui.main;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.kejian.mike.mike_kejian_android.R;

import net.picture.DownloadPicture;
import net.picture.MessagePrint;

import java.util.ArrayList;
import java.util.List;

import bl.SearchBLService;
import model.message.SearchResult;

public class SearchPeopleActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private LayoutInflater myInflater;
    private ListView container;
    private ArrayAdapter<SearchResult> peopleAdapter;
    private SearchTaskManager searchTaskManager = null;
    private int searchType = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_people);
        Intent intent = getIntent();
        this.searchType = intent.getIntExtra("searchType",0);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        this.container = (ListView)findViewById(R.id.container);
        this.myInflater = getLayoutInflater();
        this.peopleAdapter = new SearchResultAdapter(this,android.R.layout.simple_list_item_1,SearchBLService.people);
        this.container.setAdapter(this.peopleAdapter);
        this.searchTaskManager = new SearchTaskManager();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SearchResult searchResult = (SearchResult)parent.getItemAtPosition(position);
        int user_id = searchResult.getId();
        String nick_name = searchResult.getTitle();
        Intent intent = new Intent();
        intent.putExtra("user_id",user_id);
        intent.putExtra("nick_name",nick_name);
        setResult(1000,intent);
        finish();

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
            final ViewHolder viewHolder;
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
            DownloadPicture d=new DownloadPicture(getContext()){

                @Override
                public void updateView(Bitmap bitmap) {

                    viewHolder.imageView.setImageBitmap(bitmap);

                }
            };

            d.getBitMapFromNet(searchResult.getIconUrl(), searchResult.getLocalIconPath());
//            viewHolder.imageView.setImageResource(R.drawable.daoxu);
            viewHolder.title.setText(searchResult.getBuilder());
            return convertView;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_search_view_demo, menu);
        final float density = getResources().getDisplayMetrics().density;
        MenuItem searchItem = menu.findItem(R.id.search_bar);
//        searchItem.collapseActionView();
        SearchView searchview=(SearchView) searchItem.getActionView();
        searchview.setIconified(false);
        searchview.setIconifiedByDefault(false);
        final int closeImgId = getResources().getIdentifier("search_close_btn", "id", getPackageName());
        ImageView closeImg = (ImageView)searchview.findViewById(closeImgId);
        if (closeImg != null) {
            LinearLayout.LayoutParams paramsImg = (LinearLayout.LayoutParams) closeImg.getLayoutParams();
            paramsImg.topMargin = (int) (-2 * density);
            closeImg.setImageResource(R.drawable.close);
            closeImg.setLayoutParams(paramsImg);
        }

        final int editViewId = getResources().getIdentifier("search_src_text", "id", getPackageName());
        TextView mEdit = (SearchView.SearchAutoComplete) searchview.findViewById(editViewId);
        if (mEdit != null) {
            mEdit.setHintTextColor(Color.WHITE);
            mEdit.setTextColor(Color.WHITE);
//            mEdit.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
//            mEdit.getPaint().setAntiAlias(true);
//            mEdit.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
//            mEdit.setHint(String.format(getResources().getString(R.string.search_hint_tip), MemoryData.departmentList.get(mPosition).getMembers().size()));
        }
//        final int rootViewId = getResources().getIdentifier("search_bar", "id", getPackageName());
//        LinearLayout rootView = (LinearLayout) searchview.findViewById(rootViewId);
//        rootView.setBackgroundResource(R.drawable.edit_bg);
//        searchview.setClickable(true);
//        searchview.setBackgroundColor(Color.GREEN);
//        searchview.setBottom(Color.GREEN);
//        searchview.setBackgroundResource(R.drawable.base_line);
        final int searchPlateId = getResources().getIdentifier("search_plate", "id", getPackageName());
        LinearLayout editLayout = (LinearLayout) searchview.findViewById(searchPlateId);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) editLayout.getLayoutParams();

        final int searchEditFrameId = getResources().getIdentifier("search_edit_frame", "id", getPackageName());
        LinearLayout tipLayout = (LinearLayout) searchview.findViewById(searchEditFrameId);
        LinearLayout.LayoutParams tipParams = (LinearLayout.LayoutParams) tipLayout.getLayoutParams();
        tipParams.leftMargin = 0;
        tipParams.rightMargin = 0;
        tipLayout.setLayoutParams(tipParams);
        final int searchImageViewId = getResources().getIdentifier("search_mag_icon", "id", getPackageName());
        ImageView icTip = (ImageView) searchview.findViewById(searchImageViewId);
        icTip.setImageResource(R.drawable.search);
        params.topMargin = (int) (4 * density);
        editLayout.setLayoutParams(params);
        searchview.setSubmitButtonEnabled(false);

//        searchview.setQueryHint("输入查询内容");
//        int id = searchview.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
//        TextView textView = (TextView) searchview.findViewById(id);
//        textView.setTextColor(Color.WHITE);
//        SearchView searchView = (SearchView) searchItem.getActionView();
//        searchview.setInputType();
//        searchView.setIconifiedByDefault(false);
        searchview.setQueryHint("请输入用户id或昵称");
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
                SearchBLService.clearPeople();
                peopleAdapter.notifyDataSetChanged();
                if(newText.length() > 0){
                    SearchTask searchTask = new SearchTask();
                    searchTaskManager.clearSearchTasks();
                    searchTaskManager.addSearchTask(searchTask);
                    searchTask.execute(newText);
                }

                return false;
            }
        });
        return true;
    }

    private class SearchTaskManager{
        private ArrayList<SearchTask> searchTasks = new ArrayList<>();
        public synchronized void clearSearchTasks(){
            MessagePrint.print("------------------------------------clear-----------------------------------------");
            for (SearchTask searchTask: searchTasks
                    ) {
                searchTask.isCancelled = true;

            }
            searchTasks.clear();
        }
        public synchronized void addSearchTask(SearchTask searchTask){
            MessagePrint.print("------------------------------------add-----------------------------------------");
            searchTasks.add(searchTask);
        }

    }
    private class SearchTask extends AsyncTask<String, Integer, String> {
        public boolean isCancelled = false;
        @Override
        public String doInBackground(String... params) {
            MessagePrint.print("------------------------------------do in background-----------------------------------------");
            //延迟执行
            try{
                Thread.sleep(800);
            }catch (Exception e){
                e.printStackTrace();
            }
            if(isCancelled){
                return "";
            }
            String key = params[0];
            SearchBLService.searchPeople(key,searchType);
            return "";
        }

        @Override
        public void onPostExecute(String result) {
            MessagePrint.print("------------------------------------on Post Execute-----------------------------------------");
            if(isCancelled){
                return;
            }
            peopleAdapter.notifyDataSetChanged();
        }
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
