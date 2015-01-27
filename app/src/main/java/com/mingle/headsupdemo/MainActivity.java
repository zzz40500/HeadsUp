package com.mingle.headsupdemo;

import android.app.Notification;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.mingle.headsUp.HeadsUp;
import com.mingle.headsUp.HeadsUpManager;


public class MainActivity extends ActionBarActivity {


    private int code=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.l1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                NotificationCompat.Builder builder = new NotificationCompat.Builder(MainActivity.this);
                builder.setSmallIcon(R.drawable.ic_launcher);
                builder.setContentTitle("标题");
                builder.setContentText("通知内容: message");
                Notification notification = builder
                        .build();
                HeadsUpManager manage = HeadsUpManager.getInstant(getApplication());


                HeadsUp headsUp = new HeadsUp(MainActivity.this);
                headsUp.setNotification(notification);
                headsUp.setTitle("标题");
                headsUp.setMessage("通知内容: message");
                headsUp.setIcon(R.drawable.ic_launcher);
                manage.notify(code++, headsUp);


            }
        });

        findViewById(R.id.l2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotificationCompat.Builder builder1 = new NotificationCompat.Builder(MainActivity.this);
                builder1.setSmallIcon(R.drawable.ic_launcher);
                builder1.setContentTitle("标题");
                builder1.setContentText("这个是自定义通知");
                Notification notification1 = builder1
                        .build();

                final HeadsUpManager manage1 = HeadsUpManager.getInstant(getApplication());
                HeadsUp headsUp1 = new HeadsUp(MainActivity.this);
                headsUp1.setNotification(notification1);

                headsUp1.setIcon(R.drawable.ic_launcher);

                View view=getLayoutInflater().inflate(R.layout.custom_notification, null);

                view.findViewById(R.id.openSource).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/zzz40500/HeadsUp"));
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        MainActivity.this.startActivity(intent);
                        manage1.animDismiss();
                    }
                });
                headsUp1.setCustomView(view);
                manage1.notify(code++, headsUp1);

            }
        });

        findViewById(R.id.l3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                HeadsUpManager.getInstant(MainActivity.this).cleanAll();

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
