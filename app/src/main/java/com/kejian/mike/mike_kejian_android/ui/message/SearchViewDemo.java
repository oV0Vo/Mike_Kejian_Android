package com.kejian.mike.mike_kejian_android.ui.message;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.TypedValue;
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
import com.kejian.mike.mike_kejian_android.ui.campus.PostDetailActivity;
import com.kejian.mike.mike_kejian_android.ui.course.detail.CourseActivity;

import net.picture.DownloadPicture;
import net.picture.MessagePrint;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import bl.MessageBLService;
import bl.SearchBLService;
import model.course.CourseModel;
import model.message.SearchResult;

public class SearchViewDemo extends AppCompatActivity implements AdapterView.OnItemClickListener {
    private LayoutInflater myInflater;
//    private SearchView srv1;
    private ListView courseContainer;
    private ListView postContainer;
    private ArrayAdapter<SearchResult> courseAdapter;
    private ArrayAdapter<SearchResult> postAdapter;
    private TextView courseText;
    private TextView postText;
//    private SearchTask searchTask = null;
    private SearchTaskManager searchTaskManager = null;
//    private SearchManagerTask searchManagerTask = null;
//    private boolean textChange = false;
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
        courseText = (TextView)findViewById(R.id.course_tag);
        postText = (TextView)findViewById(R.id.post_tag);

        courseContainer=(ListView)findViewById(R.id.course_container);
        postContainer = (ListView)findViewById(R.id.post_container);
        this.myInflater = getLayoutInflater();
        courseAdapter=new SearchResultAdapter(this, android.R.layout.simple_list_item_1, SearchBLService.courses);
        postAdapter = new SearchResultAdapter(this,android.R.layout.simple_list_item_1,SearchBLService.posts);

        courseContainer.setAdapter(courseAdapter);
        postContainer.setAdapter(postAdapter);

        this.searchTaskManager = new SearchTaskManager();
        this.courseContainer.setOnItemClickListener(this);
        this.postContainer.setOnItemClickListener(this);
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

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SearchResult searchResult = (SearchResult)parent.getItemAtPosition(position);
        if(!searchResult.isCourse()){
            Intent intent = new Intent();
            intent.setClass(this, PostDetailActivity.class);
            intent.putExtra("postId",searchResult.getId()+"");
            startActivity(intent);
        }else{
            CourseModel.getInstance().setCurrentCourseId(searchResult.getId()+"");
            Intent intent = new Intent(this, CourseActivity.class);
            startActivity(intent);
        }
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
            DownloadPicture downloadPicture=new DownloadPicture(getContext(),viewHolder.imageView,searchResult.getIconUrl(),searchResult.getIconUrl());
//            DownloadPicture d=new DownloadPicture(getContext()){
//
//                @Override
//                public void updateView(Bitmap bitmap) {
//
//                    viewHolder.imageView.setImageBitmap(bitmap);
//
//                }
//            };
//
//            d.getBitMapFromNet(searchResult.getIconUrl(), searchResult.getLocalIconPath());
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
        searchview.setQueryHint("请输入查询内容");
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
                SearchBLService.clearCourses();
                SearchBLService.clearPosts();
                courseText.setVisibility(View.GONE);
                postText.setVisibility(View.GONE);
                courseAdapter.notifyDataSetChanged();
                postAdapter.notifyDataSetChanged();
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
                Thread.sleep(600);
            }catch (Exception e){
                e.printStackTrace();
            }
            if(isCancelled){
                return "";
            }
            String key = params[0];
            SearchBLService.search(key);
            return "";
        }

        @Override
        public void onPostExecute(String result) {
            MessagePrint.print("------------------------------------on Post Execute-----------------------------------------");
            if(isCancelled){
                return;
            }
            if(SearchBLService.courses.size() > 0){
                courseText.setVisibility(View.VISIBLE);
            }else{
                courseText.setVisibility(View.GONE);
            }
            if(SearchBLService.posts.size()>0){
                postText.setVisibility(View.VISIBLE);
            }else{
                postText.setVisibility(View.GONE);
            }
            courseAdapter.notifyDataSetChanged();
            postAdapter.notifyDataSetChanged();
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
            SearchBLService.clearPosts();;
            SearchBLService.clearCourses();
            this.finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
