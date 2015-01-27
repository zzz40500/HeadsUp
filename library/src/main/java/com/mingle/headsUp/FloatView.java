package com.mingle.headsUp;


import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.ll.R;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;

import java.text.SimpleDateFormat;
import java.util.Date;

public class FloatView extends LinearLayout {
    public static int i = 0;
    private View defaultView;
    private float rawX = 0;
    private float rawY=0;
    private float touchX = 0;
    private float startY = 0;

    private WindowManager wm = (WindowManager) getContext().getApplicationContext()
            .getSystemService(getContext().WINDOW_SERVICE);
    public LinearLayout rootView;
    public int originalLeft;
    public int viewWidth;
    private CountDownTimer countDownTimer;

    private ScrollOrientationEnum scrollOrientationEnum=ScrollOrientationEnum.NONE;




    public static WindowManager.LayoutParams winParams = new WindowManager.LayoutParams();

    public FloatView(final Context context, int i) {
        super(context);
        LinearLayout view = (LinearLayout) LayoutInflater.from(getContext()).inflate(R.layout.notification_bg, null);

        rootView = (LinearLayout) view.findViewById(R.id.rootView);
        addView(view);
        viewWidth = context.getResources().getDisplayMetrics().widthPixels;

        originalLeft = 0;


    }

    public void setCustomView(View view) {
        rootView.addView(view);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }


    private HeadsUp headsUp;

    private long cutDown;


    private   Handler mHandle=null;

    private CutDownTime cutDownTime;

    private class CutDownTime extends Thread{

        @Override
        public void run() {
            super.run();


            while (cutDown>0){
                try {
                    Thread.sleep(1000);
                    cutDown--;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if(cutDown==0) {
                mHandle.sendEmptyMessage(0);
            }


        }
    };

    public void setNotification(final HeadsUp headsUp) {

        this.headsUp = headsUp;




        mHandle= new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);


                HeadsUpManager.getInstant(getContext()).animDismiss(headsUp);
            }
        };


        cutDownTime=  new CutDownTime();
        cutDownTime.start();

    }

    public HeadsUp getHeadsUp() {
        return headsUp;
    }

    private long startTime;

    public boolean onTouchEvent(MotionEvent event) {
        rawX = event.getRawX();
        rawY=event.getRawY();
        cutDown= headsUp.getDuration();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touchX = event.getX();
                startY = event.getRawY();
                startTime = System.currentTimeMillis();
                break;
            case MotionEvent.ACTION_MOVE:

                switch (scrollOrientationEnum){
                    case NONE:

                        if(Math.abs((rawX - touchX))>20) {
                            scrollOrientationEnum=ScrollOrientationEnum.HORIZONTAL;

                        }else if(startY-rawY>20){
                            scrollOrientationEnum=ScrollOrientationEnum.VERTICAL;

                        }



                        break;

                    case HORIZONTAL:

                        updatePosition((int) (rawX - touchX));
                        break;

                    case VERTICAL:

                        if(startY-rawY>20) {
                            HeadsUpManager.getInstant(getContext()).animDismiss();
                            cutDownTime.interrupt();
                            cutDown = -1;
                        }

                        break;

                }







                break;
            case MotionEvent.ACTION_UP:


                if (preLeft < -viewWidth / 4.0) {
                    //右滑消失
                    ObjectAnimator a = ObjectAnimator.ofFloat(rootView, "translationX", preLeft, -viewWidth);
                    a.start();
                    a.addListener(new Animator.AnimatorListener() {
                        @Override
                        public void onAnimationStart(Animator animator) {
                        }

                        @Override
                        public void onAnimationEnd(Animator animator) {
                            HeadsUpManager.getInstant(getContext()).dismiss();
                            cutDown=-1;
                        }

                        @Override
                        public void onAnimationCancel(Animator animator) {
                        }

                        @Override
                        public void onAnimationRepeat(Animator animator) {
                        }
                    });


                } else if (preLeft > originalLeft) {

                    double d = (preLeft - originalLeft) * Math.max(3 - (System.currentTimeMillis() - startTime) / 1000.0, 1);
                    if (d >= viewWidth / 2) {
                        ObjectAnimator a = ObjectAnimator.ofFloat(rootView, "translationX", preLeft, viewWidth);
                        a.addListener(new Animator.AnimatorListener() {
                            @Override
                            public void onAnimationStart(Animator animator) {

                            }

                            @Override
                            public void onAnimationEnd(Animator animator) {
                                HeadsUpManager.getInstant(getContext()).dismiss();
                                cutDown=-1;
                            }

                            @Override
                            public void onAnimationCancel(Animator animator) {

                            }

                            @Override
                            public void onAnimationRepeat(Animator animator) {

                            }
                        });

                        a.start();
                    } else {
                        updatePosition(originalLeft);
                        ObjectAnimator a = ObjectAnimator.ofFloat(rootView, "translationX", preLeft, 0);
                        a.start();
                        scrollOrientationEnum=ScrollOrientationEnum.NONE;

                    }

                } else if (preLeft < originalLeft) {
                    updatePosition(originalLeft);
                    ObjectAnimator a = ObjectAnimator.ofFloat(rootView, "translationX", preLeft, 0);
                    a.start();
                    scrollOrientationEnum=ScrollOrientationEnum.NONE;

                }
                preLeft = 0;

                break;

        }

        return super.onTouchEvent(event);

    }


    private int preLeft;

    public void updatePosition(int left) {
        winParams.x = left;
        winParams.y = 0;
        if (left < 0) {
            ObjectAnimator a = ObjectAnimator.ofFloat(rootView, "translationX", preLeft, left);
            a.start();
        } else if (left > originalLeft) {
            ObjectAnimator a = ObjectAnimator.ofFloat(rootView, "translationX", preLeft - originalLeft, left - originalLeft);
            a.start();
        }
        preLeft = left;
        wm.updateViewLayout(this, winParams);
    }




    public void setEntity(HeadsUp headsUp) {


        cutDown= headsUp.getDuration();

        if (headsUp.getCustomView() == null) {

            defaultView = LayoutInflater.from(getContext()).inflate(R.layout.notification, null);
            rootView.addView(defaultView);
            ImageView imageView = (ImageView) defaultView.findViewById(R.id.iconIM);
            TextView titleTV = (TextView) defaultView.findViewById(R.id.titleTV);
            TextView timeTV = (TextView) defaultView.findViewById(R.id.timeTV);
            TextView messageTV = (TextView) defaultView.findViewById(R.id.messageTV);
            imageView.setImageDrawable(headsUp.getIcon());
            titleTV.setText(headsUp.getTitleStr());
            messageTV.setText(headsUp.getMsgStr());
            SimpleDateFormat simpleDateFormat=new SimpleDateFormat("HH:mm");
            timeTV.setText( simpleDateFormat.format(new Date()));
        } else {
            setCustomView(headsUp.getCustomView());
        }
    }


    enum ScrollOrientationEnum {
        VERTICAL,HORIZONTAL,NONE
    }
}
