package com.chalmers.customnotifiction;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class NotificationActivity extends AppCompatActivity {

    private TextView tv_notification_msg = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        initView();
        initData();
    }

    private void initView(){
        tv_notification_msg = (TextView) findViewById(R.id.tv_notification_msg);
    }

    private void initData(){
        int what = getIntent().getIntExtra("what",-1);

        tv_notification_msg.setText("what = " + what);
    }
}
