package com.kejian.mike.mike_kejian_android.ui.course.detail.naming;

import android.content.Context;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kejian.mike.mike_kejian_android.R;

import model.course.data.CourseNamingRecord;

public class CourseNamingActivity extends AppCompatActivity {

    private TextView timeActionTitle;
    private TextView courseTimeText;
    private TextView clockText;
    private TextView namingButton;
    private ProgressBar progressBar;
    private ListView historyListView;
    private ArrayAdapter d;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_naming);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_course_naming, menu);
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

    private class GetHistoryNamingTask extends AsyncTask<Void, Void, Boolean> {

        @Override
        protected Boolean doInBackground(Void... params) {
            return null;
        }
    }

    private class HistoryNamingAdapter extends ArrayAdapter<CourseNamingRecord> {

        public HistoryNamingAdapter(Context context, int resource, CourseNamingRecord[] objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup viewGroup) {
            if(convertView == null) {
                convertView = getLayoutInflater().inflate(R.layout.layout_history_naming, null);
            }
            return convertView;
        }
    }

}
