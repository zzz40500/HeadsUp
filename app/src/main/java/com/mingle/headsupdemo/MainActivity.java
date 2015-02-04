package com.mingle.headsupdemo;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RemoteViews;
import android.widget.ScrollView;

import com.mingle.headsUp.HeadsUp;
import com.mingle.headsUp.HeadsUpManager;


public class MainActivity extends ActionBarActivity {


    private int code=1;


    private Context context;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context=this;

        findViewById(R.id.l0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                PendingIntent pendingIntent=PendingIntent.getActivity(MainActivity.this,11,new Intent(MainActivity.this,MainActivity.class),PendingIntent.FLAG_UPDATE_CURRENT);
                HeadsUpManager manage = HeadsUpManager.getInstant(getApplication());
                HeadsUp.Builder builder = new HeadsUp.Builder(MainActivity.this);
                builder.setContentTitle("提醒").setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS)
                        //要显示通知栏通知,这个一定要设置
                        .setSmallIcon(R.drawable.icon)
                        //2.3 一定要设置这个参数,负责会报错
                        .setContentIntent(pendingIntent)
                .setFullScreenIntent(pendingIntent,false)
                        .setContentText("你有新的消息");

                HeadsUp headsUp = builder.buildHeadUp();
                headsUp.setSticky(true);
                manage.notify(code++, headsUp);


            }
        });

        findViewById(R.id.l1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PendingIntent pendingIntent=PendingIntent.getActivity(MainActivity.this,11,new Intent(MainActivity.this,MainActivity.class),PendingIntent.FLAG_UPDATE_CURRENT);
                HeadsUpManager manage = HeadsUpManager.getInstant(getApplication());
                HeadsUp.Builder builder = new HeadsUp.Builder(MainActivity.this);
                builder.setContentTitle("提醒").setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS)
                       //要显示通知栏通知,这个一定要设置
                        .setSmallIcon(R.drawable.icon)
                        .setContentText("你有新的消息")
                        //2.3 一定要设置这个参数,负责会报错
                        .setContentIntent(pendingIntent)
                        .setFullScreenIntent(pendingIntent, false)
                        //设置是否显示 action 按键
                        .setUsesChronometer(true)
                        .addAction(R.drawable.ic_cloud_queue_black_24dp, "查看", pendingIntent);

                HeadsUp headsUp = builder.buildHeadUp();
                manage.notify(1, headsUp);


            }
        });

        findViewById(R.id.l2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PendingIntent pendingIntent=PendingIntent.getActivity(MainActivity.this,11,new Intent(MainActivity.this,MainActivity.class),PendingIntent.FLAG_UPDATE_CURRENT);

                final HeadsUpManager manage1 = HeadsUpManager.getInstant(getApplication());

                View view=getLayoutInflater().inflate(R.layout.custom_notification, null);

                view.findViewById(R.id.openSource).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/zzz40500/HeadsUp"));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        MainActivity.this.startActivity(intent);
                        manage1.cancel();
                    }
                });

                HeadsUp headsUp1 = new HeadsUp.Builder(MainActivity.this)
                        .setContentTitle("标题")
                        //要显示通知栏通知,这个一定要设置
                        .setSmallIcon(R.drawable.icon)
                        //2.3 一定要设置这个参数,负责会报错
                        .setContentIntent(pendingIntent)
                        .setContentText("这个是自定义通知")
                        .buildHeadUp();
                headsUp1.setCustomView(view);
                manage1.notify(code++, headsUp1);

            }
        });

        findViewById(R.id.l3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HeadsUpManager.getInstant(MainActivity.this).cancelAll();





            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
