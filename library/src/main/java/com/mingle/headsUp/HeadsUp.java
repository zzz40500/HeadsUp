package com.mingle.headsUp;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.app.NotificationCompat;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zzz40500 on 2014/10/9.
 */
public class HeadsUp   {

    private Context context;
    /**
     * 出现时间  单位是 second
     */
    private long duration= 7;
    /**
     *
     */
    private Notification notification;




    /**
     *
     */

    private Notification silencerNotification;
    /**
     * 间隔时间
     */
    private long interval=600 ;


    private int code;

    private List<Action> actions;

    private CharSequence titleStr;
    private CharSequence msgStr;
    private int icon;
    private View customView;
    private boolean isExpand;
    private HeadsUp.Builder  builder;
    private HeadsUp(Context context) {
        this.context=context;
    }





    public static class Builder  extends  NotificationCompat.Builder {

        private Context context;
        private List<Action> actions=new ArrayList<Action>();
        private     HeadsUp headsUp;

        public Builder(Context context) {
            super(context);
            headsUp=new HeadsUp(context);
        }
        /**
         * 显示全部界面
         * @param isExpand
         */
        public Builder setUsesChronometer(boolean isExpand){
            headsUp.setExpand(isExpand);
            return this;
        }
        /**
         * Set the first line of text in the platform notification template.
         */
        public Builder setContentTitle(CharSequence title) {
            headsUp.setTitle(title);
            super.setContentTitle(title);
            return this;
        }

        /**
         * Set the second line of text in the platform notification template.
         */
        public Builder setContentText(CharSequence text) {
            headsUp.setMessage(text);
            super.setContentText(text);
            return this;
        }
        public Builder setSmallIcon(int icon) {
            headsUp.setIcon(icon);
//            super.setSmallIcon(icon);
            return this;
        }



        @Override
        public Builder addAction(int icon, CharSequence title, PendingIntent intent) {
            Action  action=new HeadsUp.Action();
            action.setIcon(icon);
            action.setTitle(title);
            action.setIntent(intent);
            actions.add(action);
            super.addAction(icon, title, intent);
            return this;
        }




        public HeadsUp buildHeadUp(){
            headsUp.setNotification(this.build());
            headsUp.setActions(actions);
            headsUp.setSilencerNotification(silencerNotifcation());
            return  headsUp;
        }

        private   Notification silencerNotifcation(){
            super.setSmallIcon(headsUp.getIcon());
            setDefaults(0);
            return this.build();
        }

        @Override
        public Builder setAutoCancel(boolean autoCancel) {
             super.setAutoCancel(autoCancel);
            return this;

        }

        @Override
        public Builder setColor(int argb) {
             super.setColor(argb);
            return this;
        }

        @Override
        public Builder setDefaults(int defaults) {
             super.setDefaults(defaults);
            return this;
        }

        @Override
        public Builder setFullScreenIntent(PendingIntent intent, boolean highPriority) {
             super.setFullScreenIntent(intent, highPriority);
            return this;
        }

        @Override
        public Builder setOngoing(boolean ongoing) {
             super.setOngoing(ongoing);
            return this;
        }


        @Override
        public Builder setVibrate(long[] pattern) {
             super.setVibrate(pattern);
            return  this;
        }


        @Override
        public Builder setLargeIcon(Bitmap icon) {
             super.setLargeIcon(icon);
            return this;
        }

        @Override
        public Builder setLights(int argb, int onMs, int offMs) {
             super.setLights(argb, onMs, offMs);
            return this;
        }

    }




    protected  static class  Action{
        private  int icon;
        private CharSequence title;
        private  PendingIntent intent;

        public int getIcon() {
            return icon;
        }

        public void setIcon(int icon) {
            this.icon = icon;
        }

        public CharSequence getTitle() {
            return title;
        }

        public void setTitle(CharSequence title) {
            this.title = title;
        }

        public PendingIntent getIntent() {
            return intent;
        }

        public void setIntent(PendingIntent intent) {
            this.intent = intent;
        }
    }


    public void setIcon(int dRes) {
        icon = dRes;
    }


    /**
     * 设置消息标题
     *
     * @param titleStr
     */
    public void setTitle(CharSequence titleStr) {
        this.titleStr = titleStr;
    }

    /**
     * 设置消息内容
     *
     * @param msgStr
     */
    public void setMessage(CharSequence msgStr) {
        this.msgStr = msgStr;
    }


    public void dismiss(){
        HeadsUpManager.getInstant(context).dismiss();
    }

    public Context getContext() {
        return context;
    }

    public long getDuration() {
        return duration;
    }


    public long getInterval() {
        return interval;
    }




    public CharSequence getTitleStr() {
        return titleStr;
    }

    public CharSequence getMsgStr() {
        return msgStr;
    }

    public int getIcon() {
        return icon;
    }

    public void setInterval(long interval) {
        this.interval = interval;
    }


    public enum Priority {
        PRIORITY_NORMAL(2) , PRIORITY_LOW(3), PRIORITY_HEIGHT(1);
        int priority;
         private  Priority(int i){
          priority=i;
      }

        public int  getPriority(){
            return priority;
        }

    }


    public Notification getNotification() {
        return notification;
    }

    public void setNotification(Notification notification) {
        this.notification = notification;
    }


    public View getCustomView() {
        return customView;
    }

    public void setCustomView(View customView) {
        this.customView = customView;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public boolean isExpand() {
        return isExpand;
    }

    public void setExpand(boolean isExpand) {
        this.isExpand = isExpand;
    }

    protected Notification getSilencerNotification() {
        return silencerNotification;
    }

    private void setSilencerNotification(Notification silencerNotification) {
        this.silencerNotification = silencerNotification;
    }

}
