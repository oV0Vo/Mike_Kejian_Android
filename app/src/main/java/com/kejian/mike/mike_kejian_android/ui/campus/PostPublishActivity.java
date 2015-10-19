package com.kejian.mike.mike_kejian_android.ui.campus;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.kejian.mike.mike_kejian_android.R;

import bl.CampusBLService;

/**
 * Created by showjoy on 15/9/24.
 */
public class PostPublishActivity extends AppCompatActivity implements View.OnClickListener{
    ActionBar actionBar;
    TextView title;
    TextView content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_publish);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        title = (TextView) findViewById(R.id.publish_title);
        content = (TextView) findViewById(R.id.publish_content);

        TextView confirm = (TextView) findViewById(R.id.publish_confirm);
        confirm.setOnClickListener(this);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // TODO Auto-generated method stub
        if(item.getItemId() == android.R.id.home)
        {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void iniData() {

    }

    @Override
    public void onClick(View v) {
        final String titleString = title.getText().toString();
        final String contentString = content.getText().toString();
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                String result = CampusBLService.publish("0",titleString, contentString);
                return result;
            }

            @Override
            public void onPostExecute(String result) {
                if(!(result.equals("false") || result==null))
                    Toast.makeText(PostPublishActivity.this, "帖子已创建", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(PostPublishActivity.this, "帖子创建失败", Toast.LENGTH_SHORT).show();
                PostPublishActivity.this.finish();

            }
        }.execute();
    }
}
