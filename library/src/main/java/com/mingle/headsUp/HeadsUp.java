package com.mingle.headsUp;

import android.app.Notification;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

/**
 * Created by zzz40500 on 2014/10/9.
 */
public class HeadsUp {

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
     * 间隔时间
     */
    private long interval=600 ;
    /**
     * 优先级
     */
    private Priority priority = Priority.PRIORITY_NORMAL;
    private int code ;
    private CharSequence titleStr;
    private CharSequence msgStr;
    private Drawable icon;
    private View customView;
    public HeadsUp(Context context) {
        this.context=context;
    }
    public HeadsUp(Context context, CharSequence msgStr, CharSequence titleStr, Drawable icon) {
        this.context=context;
        this.msgStr = msgStr;
        this.titleStr = titleStr;
        this.icon = icon;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    protected int getCode() {
        return code;
    }

    protected void setCode(int code) {
        this.code = code;
    }

    public void setIcon(Drawable drawable) {
        icon = drawable;
    }

    public void setIcon(int dRes) {
        icon = context.getResources().getDrawable(dRes);
    }

    public void setIcon(Bitmap bitmap) {
        icon = new BitmapDrawable(bitmap);
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


    public Priority getPriority() {
        return priority;
    }


    public CharSequence getTitleStr() {
        return titleStr;
    }

    public CharSequence getMsgStr() {
        return msgStr;
    }

    public Drawable getIcon() {
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
}
