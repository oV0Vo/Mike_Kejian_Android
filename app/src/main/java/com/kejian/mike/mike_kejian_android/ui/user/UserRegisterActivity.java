package com.kejian.mike.mike_kejian_android.ui.user;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.kejian.mike.mike_kejian_android.R;

/**
 * Created by kisstheraik on 15/9/20.
 */
public class UserRegisterActivity extends Activity{

    private Button confirm;
    private Context context;

    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);
        context=this;
        confirm=(Button)findViewById(R.id.register_confirm);
        confirm.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent=new Intent();
                        intent.setClass(context,UserLoginActivity.class);
                        startActivity(intent);
                        close();
                    }
                }
        );

    }

    public void close(){
        this.finish();
    }
}
