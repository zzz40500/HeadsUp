#2.0 使用库实现类似的方法(向下兼容2.3)#
下面介绍一个自己写的,类似 Heads-up 组件的库.
[github 源码地址](https://github.com/zzz40500/HeadsUp)
simple heads-up (no expand)
使用方法:
~~~
    HeadsUpManager manage = HeadsUpManager.getInstant(getApplication());
                HeadsUp.Builder builder = new HeadsUp.Builder(MainActivity.this);
                builder.setContentTitle("提醒").setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS)
                        //要显示通知栏通知,这个一定要设置
                        .setSmallIcon(R.drawable.icon)
                        //2.3 一定要设置这个参数,否则会报错
                        .setContentIntent(pendingIntent)
                        .setContentText("你有新的消息");

                HeadsUp headsUp = builder.buildHeadUp();
                manage.notify(code++, headsUp);
~~~
simple heads-up 
使用方法:
~~~
    HeadsUpManager manage = HeadsUpManager.getInstant(getApplication());
                HeadsUp.Builder builder = new HeadsUp.Builder(MainActivity.this);
                builder.setContentTitle("提醒").setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS)
                       //要显示通知栏通知,这个一定要设置
                        .setSmallIcon(R.drawable.icon)
                        .setContentText("你有新的消息")
                       //2.3 一定要设置这个参数,否则会报错
                        .setContentIntent(pendingIntent)
                        //设置是否显示 action 按键
                        .setUsesChronometer(true)
                        .addAction(R.drawable.ic_cloud_queue_black_24dp, "查看", pendingIntent);

                HeadsUp headsUp = builder.buildHeadUp();
                manage.notify(code++, headsUp);
~~~

使用自定义布局,支持 button 的点击事件.
使用方法:
~~~
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
                        //2.3 一定要设置这个参数,否则会报错
                        .setContentIntent(pendingIntent)
                        .setContentText("这个是自定义通知")
                        .buildHeadUp();
                headsUp1.setCustomView(view);
                manage1.notify(code++, headsUp1);
~~~

![heads-up.gif](http://upload-images.jianshu.io/upload_images/166866-543a5d26ab71d0f6.gif)

局限性:
>1. ~~heads-up 没有加入阴影效果:~~
* 没有对消息进行优先级排序,现在的排序算法是先进先出.
* miui 系统开启悬浮窗才可以正常使用. (**miui 真讨厌!!!!**)

1.0.1更新内容;
 >增加了背景阴影部分.
  1. 增加了消息横向滑动时候的 alpha 值的改变
  * 增加了常驻消息:调用 HeadsUp.setSticky(true),用 HeadsUpManager.cancel(code)取消.
  * 修改了滑动动画.
  * 向下兼容2.3

1.1.0更新内容;
>* 增加了圆形头像,基于[CircleImageView](https://github.com/hdodenhof/CircleImageView)上做的修改
* 增加在 android 5.0 上使用原生的 headsUp.
增加了方法 `setActivateStatusBar(boolean )` 默认 true  ,设置 false 表示只显示 heads-up ,而不会发送到通知栏上.

#####1.1.0注意:#####
>*使用原生的 headsUp 逻辑是: 
    系统是大于21,且headsUp 没有使用`setCustomView(View )`也没有设置`setIsActivateStatusBar(true)`则使用原生的 heads-up
如果要调用了原生的 heads-up,所以你得设置`setFullScreenIntent(pendding,false)`pendding不能为空,否则没有效果









