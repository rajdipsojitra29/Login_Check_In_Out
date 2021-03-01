package com.login_check_in_out.NativeClasses.a2Utility.UtilityJava;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

public class ProgressBarHandler {
    private ProgressBar progressBar;
    private Activity activity;

    public ProgressBarHandler(Context context, Activity activityTemp) {
        activity = activityTemp;
        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        wait(1);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                ViewGroup viewGroup = (ViewGroup) ((Activity) context).findViewById(android.R.id.content).getRootView();
                                progressBar = new ProgressBar(context, null, android.R.attr.progressBarStyleLarge);
                                progressBar.setIndeterminate(true);
                        //        progressBar.setBackgroundColor(Color.parseColor("#FF0000"));
                        //        progressBar.setOutlineSpotShadowColor(Color.parseColor("#FF0000"));
                        //        progressBar.setOutlineAmbientShadowColor(Color.parseColor("#FF0000"));
                        //        progressBar.getProgressDrawable().setColorFilter(Color.RED, android.graphics.PorterDuff.Mode.SRC_IN);
                                progressBar.getIndeterminateDrawable().setColorFilter(Color.parseColor("#DA435E"),android.graphics.PorterDuff.Mode.MULTIPLY);

                                RelativeLayout.LayoutParams relativeLayoutLayoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);

                                RelativeLayout relativeLayout = new RelativeLayout(context);
                                relativeLayout.setGravity(Gravity.CENTER);
                                relativeLayout.addView(progressBar);
                                viewGroup.addView(relativeLayout, relativeLayoutLayoutParams);
                                hide();
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
        };
        thread.start();
    }


    public void show() {
        /*
        if (progressBar != null) {
            progressBar.setVisibility(View.VISIBLE);
            activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
*/

        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        wait(5);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (progressBar != null) {
                                    progressBar.setVisibility(View.VISIBLE);
                                    activity.getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                }
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
        };
        thread.start();


    }


    public void hide() {
        /*
        if (progressBar != null) {
            progressBar.setVisibility(View.INVISIBLE);
            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
        }
*/

        Thread thread = new Thread(){
            @Override
            public void run() {
                try {
                    synchronized (this) {
                        wait(1);
                        activity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (progressBar != null) {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                }
                            }
                        });
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            };
        };
        thread.start();

    }
}

