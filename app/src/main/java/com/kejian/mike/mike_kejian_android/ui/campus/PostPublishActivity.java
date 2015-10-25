package com.kejian.mike.mike_kejian_android.ui.campus;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kejian.mike.mike_kejian_android.R;
import com.kejian.mike.mike_kejian_android.ui.main.SearchPeopleActivity;

import java.lang.reflect.Array;
import java.util.ArrayList;

import bl.CampusBLService;
import model.helper.SearchType;

/**
 * Created by showjoy on 15/9/24.
 */
public class PostPublishActivity extends AppCompatActivity{
    ActionBar actionBar;
    TextView title;
    TextView content;
    TextView invite_view;
    String courseId;
    ArrayList<String> userList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_publish);
        actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        title = (TextView) findViewById(R.id.publish_title);
        content = (TextView) findViewById(R.id.publish_content);
        invite_view = (TextView) findViewById(R.id.publish_invite_view);
        courseId = getIntent().getStringExtra("courseId");

        TextView confirm = (TextView) findViewById(R.id.publish_confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String titleString = title.getText().toString();
                final String contentString = content.getText().toString();
                new AsyncTask<Void, Void, String>() {
                    @Override
                    protected String doInBackground(Void... params) {
                        String result = CampusBLService.publish(courseId,titleString, contentString);
                        return result;
                    }

                    @Override
                    public void onPostExecute(String result) {
                        if(!(result.equals("false") || result==null)) {
                            Toast.makeText(PostPublishActivity.this, "帖子已创建", Toast.LENGTH_SHORT).show();
                            System.out.println(CampusBLService.inviteToAnswer(result, userList));
                        }
                        else
                            Toast.makeText(PostPublishActivity.this, "帖子创建失败", Toast.LENGTH_SHORT).show();
                        PostPublishActivity.this.finish();

                    }
                }.execute();
            }
        });

        TextView invite = (TextView) findViewById(R.id.publish_invite);
        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                invite_view.setVisibility(View.VISIBLE);
                Intent intent = new Intent();
                intent.setClass(PostPublishActivity.this, SearchPeopleActivity.class);
                intent.putExtra("searchType", SearchType.addAssistant);
                startActivityForResult(intent,1000);
            }
        });

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
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) { //resultCode为回传的标记，我在B中回传的是RESULT_OK
            case RESULT_OK:
                break;
            default:
                invite_view.setText(invite_view.getText() + data.getStringExtra("nick_name") + ", ");
                userList.add(data.getStringExtra("user_id"));
                break;
        }

    }
}
