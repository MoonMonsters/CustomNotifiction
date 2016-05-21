package com.chalmers.customnotifiction;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.RemoteViews;

public class MainActivity extends AppCompatActivity implements OnClickListener {

    private NotificationManager notificationManager = null;
    public static final int NOTIFICATION_PRE = 0;
    public static final int NOTIFICATION_NEXT = 1;
    public static final int NOTIFICATION_ROOT = 2;

    public final int NOTIFICATION_NO = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_send_notification).setOnClickListener(this);
        findViewById(R.id.btn_cancel_notification).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_send_notification:
                sendNotification();
                break;
            case R.id.btn_cancel_notification:
                cancelNotification();
                break;
        }
    }

    private void sendNotification() {

        /**
         * 以下属性在自定义通知中是没有用处的，但为了兼容低版本，还是需要写上
         */
        //图标
        int icon = R.drawable.icon_notification;
        //通知标题
        String contentTitle = "可以的话";
        //通知内容
        String contentText = "梁静茹";
        //通知显示时间
        long whenTime = System.currentTimeMillis();
        //未知
        String ticker = "正在播放的是: 可以的话";
        //待执行动作,什么意思呢，就是指当执行某个动作时才执行的Intent
        PendingIntent contentIntent = getPendingIntent();
        //自定义通知中的核心类
        RemoteViews remoteView = getRemoteViews();

        //Builder模式
        Notification.Builder builder = new Notification.Builder(MainActivity.this);
        //设置属性，同上
        builder.setSmallIcon(icon)
                .setContentText(contentText)
                .setContentTitle(contentTitle)
                .setWhen(whenTime)
                .setTicker(ticker)
                .setContentIntent(contentIntent)
                .setContent(remoteView);
//        Notification notification = builder.build();
        //获得管理类对象
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        //设置该通知的编号，在取消时可以用上，后面一个方法在高版本中已经废弃
//        notificationManager.notify(NOTIFICATION_NO, builder.getNotification());
        //可以使用，但在低版本中会出问题
        notificationManager.notify(NOTIFICATION_NO,builder.build());
    }


    private void cancelNotification() {
        //取消通知，里面设置了int值，这个值在配置通知时传进去的
        notificationManager.cancel(NOTIFICATION_NO);
    }

    private PendingIntent getPendingIntent() {
        Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
        //标配
        int flags = PendingIntent.FLAG_UPDATE_CURRENT;
        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, NOTIFICATION_NO, intent, flags);

        return pendingIntent;
    }

    public RemoteViews getRemoteViews() {
        //固定用法，后面一个是自定义通知的布局
        RemoteViews remoteViews = new RemoteViews(getPackageName(), R.layout.notification_item);
        //设置文本类型（标题）
        remoteViews.setTextViewText(R.id.tv_notification_title, "可以的话");
        //设置文本（内容）
        remoteViews.setTextViewText(R.id.tv_notification_content, "梁静茹");

        //设置Button
        remoteViews.setOnClickPendingIntent(R.id.btn_notification_pre, getPendingIntent2Views(NOTIFICATION_PRE));
        remoteViews.setOnClickPendingIntent(R.id.btn_notification_next,getPendingIntent2Views(NOTIFICATION_NEXT));
        remoteViews.setOnClickPendingIntent(R.id.linear_notification_root,getPendingIntent2Views(NOTIFICATION_ROOT));

        return remoteViews;
    }

    private PendingIntent getPendingIntent2Views(int what){
        Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
        //传递参数
        intent.putExtra("what",what);

        PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this,
                what,   //这个地方的值需要同what值一样
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        return pendingIntent;
    }
}