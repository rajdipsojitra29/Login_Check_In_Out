package com.login_check_in_out.NativeClasses.a2Utility;


import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.os.Handler;
import android.util.Log;

import com.login_check_in_out.MainActivity;
import com.login_check_in_out.NativeClasses.a4VideoRecord.VideoRecordActivity;
import com.login_check_in_out.NativeClasses.a2Utility.UtilityJava.UtilityJava;

import java.io.File;


public class HardcodedData_Java {




















    public static void hardcodeDataFor(final MainActivity mainActivity) {

        UtilityJava.setContext(mainActivity);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
/*
                Intent intent = new Intent(mainActivity, TestActivity.class);
                mainActivity.startActivityForResult(intent, 0);
*/

//                ActivityStarterModule activityStarterModule = new ActivityStarterModule(null);
//                activityStarterModule.onClickButtonNativeAndroidTest1("",null);

/*
                String stringFilePath = UtilityJava.getStringFilePathInEmulatorFromDownloadFolder("testVideo.mp4");
                Log.d("",stringFilePath);
                String stringBase64 = UtilityJava.getBase64FromVideoFilePath(stringFilePath);
                Log.d("", "stringBase64");
*/
            }
        },5000);
    }


    public static void hardcodeDataFor(final VideoRecordActivity videoRecordActivity) {
/*
        UtilityJava.setContext(videoRecordActivity);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                videoRecordActivity.tempMethod();
            }
        },5000);
        */
    }


    public static void hardcodeDataFor_ActivityStarterModule_onClickButtonHitUserRegisterDeviceAPIFromReactNativeToJava() {
//        SharedPreferencesHawkManager.setIsRegisterDevice(false);
    }

}
