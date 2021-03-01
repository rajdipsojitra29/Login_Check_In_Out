package com.login_check_in_out.NativeClasses.a1JavaFunctionsToCallInReactNative;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReactContextBaseJavaModule;
import com.facebook.react.bridge.Callback;
import com.facebook.react.bridge.ReactMethod;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.DexterError;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.PermissionRequestErrorListener;
import com.karumi.dexter.listener.single.PermissionListener;
import com.login_check_in_out.NativeClasses.a2Utility.Constants_Java;
import com.login_check_in_out.NativeClasses.a2Utility.UtilityJava.UtilityJava;
import com.login_check_in_out.NativeClasses.a4VideoRecord.VideoRecordActivity;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;


public class JavaFunctionsToCallInReactNativeModule extends ReactContextBaseJavaModule {

    public JavaFunctionsToCallInReactNativeModule(ReactApplicationContext reactContext) {
        super(reactContext); //required by React Native
    }


    //getName is required to define the name of the module represented in JavaScript
    @NonNull
    @Override
    public String getName() {
        return "JavaFunctionsToCallInReactNativeModule";
    }

    public Activity activityObj = null;
    Context contextObj = null;
    boolean isHittingAPI = false;

    Callback callbackTest1;
    Callback callbackTest2;
    Callback callbackEnroll;






















    //Start implement Android method to call in React-native
    private void initializeActivity_Context() {
        if (activityObj == null) {
            try {
                activityObj = getCurrentActivity();
                if (activityObj != null) {
                    Context contextTemp = activityObj;
                    contextObj = contextTemp;
                    UtilityJava.setContext(contextObj);
                }
            } catch (Exception e) {
                Log.d("", String.valueOf(e));
                if (contextObj == null) {
                    contextObj = UtilityJava.getContext();
                }
            }
        }

        if ((contextObj != null) && (activityObj == null)) {
            activityObj = (Activity) contextObj;
        }
    }
    

    private BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String stringLocalBroadcastFrom = intent.getAction();
            if (stringLocalBroadcastFrom.equals(Constants_Java.LOCAL_BROADCAST_FROM_JFTCRNM_TO_VRA) && (callbackEnroll != null)) {
                Bundle bundle = intent.getBundleExtra("DataFromVideoRecordActivity");
                String string1 = bundle.getString("stringFileInBase64");
/*
                String stringObj = intent.getStringExtra("DataFromChooseFileFromStorageActivity");
//                callbackPickFileFromFolder.invokeAndKeepAlive(stringImageInBase64);
                callbackEnroll.invoke(stringObj);
                */
                callbackEnroll.invoke(new String[]{string1,""});
                callbackEnroll = null;
            }
        }
    };


    // @ReactMethod(isBlockingSynchronousMethod = true)
    @ReactMethod()
    public void onClickButtonTest1(String stringFromReactNative, Callback callbackSuccess, Callback callbackError) {
        initializeActivity_Context();
        callbackTest1 = callbackSuccess;
        Log.d("", String.valueOf(stringFromReactNative));
//        UtilityJava.showCustomToast(UtilityJava.getContext(),stringFromReactNative,true);
/*
        if (callbackTest1 != null) {
//            At a time only one Callback can invoke
            callbackTest1.invoke("callbackFromJava");
//            callbackError.invoke("callbackFromJava");
            callbackTest1 = null;
        }
        */

    }


    // @ReactMethod(isBlockingSynchronousMethod = true)
    @ReactMethod()
    public void onClickButtonTest2(String stringFromReactNative, Callback callbackSuccess, Callback callbackError) {
        initializeActivity_Context();
        callbackTest2 = callbackSuccess;
        Log.d("",String.valueOf(stringFromReactNative));
//        UtilityJava.showCustomToast(UtilityJava.getContext(),stringFromReactNative,true);
/*
        if (callbackTest2 != null) {
//            At a time only one Callback can invoke
            callbackTest2.invoke("callbackFromJava");
//            callbackError.invoke("callbackFromJava");
            callbackTest2 = null;
        }
 */
    }


    // @ReactMethod(isBlockingSynchronousMethod = true)
    @ReactMethod()
    public void onClickButtonEnroll(String stringFromReactNative, Callback callbackSuccess, Callback callbackError) {
        initializeActivity_Context();
        callbackEnroll = callbackSuccess;

//        UtilityJava.showCustomToast(UtilityJava.getContext(),stringFromReactNative,true);

        if (UtilityJava.checkIsPermissionGiven(contextObj, Manifest.permission.CAMERA)) {
            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    if (UtilityJava.checkIsPermissionGiven(contextObj, Manifest.permission.RECORD_AUDIO)) {
                        final Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                if (UtilityJava.checkIsPermissionGiven(contextObj, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                                    LocalBroadcastManager.getInstance(contextObj).registerReceiver(broadcastReceiver, new IntentFilter(Constants_Java.LOCAL_BROADCAST_FROM_JFTCRNM_TO_VRA));
                                    Intent intent = new Intent(contextObj, VideoRecordActivity.class);
                                    //        intent.putExtra("DataFromJavaFunctionsToCallInReactNativeModule_string", stringFromReactNative);
                                    activityObj.startActivity(intent);
                                }
                                else {
                                    getPermissionFor_WRITE_EXTERNAL_STORAGE(stringFromReactNative);
                                }

                            }
                        },1000);
                    }
                    else {
                        getPermissionFor_RECORD_AUDIO(stringFromReactNative);
                    }

                }
            },1000);
        }
        else {
            getPermissionFor_CAMERA(stringFromReactNative);
        }


/*
        //Hardcoded data start
        String stringFilePath = UtilityJava.getStringFilePathInEmulatorFromDownloadFolder("testVideo_frant.mp4");
        Log.d("",stringFilePath);
        String stringBase64 = UtilityJava.getBase64FromVideoFilePath(stringFilePath);
        callbackEnroll.invoke(new String[]{stringBase64,""});
        //Hardcoded data end
*/
        /*
        if (callbackEnroll != null) {
//            At a time only one Callback can invoke
            callbackEnroll.invoke(""); //callbackLogin.invoke(new String[]{"", ""});
//            callbackError.invoke(""); //callbackError.invoke(new String[]{"", ""});
            callbackEnroll = null;
        }
        */
    }


    private void getPermissionFor_CAMERA(final String stringDataForCallAgainLastMethod1) {
        initializeActivity_Context();

        Dexter.withActivity(activityObj).withPermission(Manifest.permission.CAMERA).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                Log.d("", "onPermissionGranted");
                onClickButtonEnroll(stringDataForCallAgainLastMethod1, callbackEnroll, null);
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {
                UtilityJava.showCustomToast(contextObj, "Permission denied for camera", true);
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).withErrorListener(new PermissionRequestErrorListener() {
            @Override
            public void onError(DexterError error) {
                Log.d("", String.valueOf(error));
            }
        }).check();
    }


    private void getPermissionFor_RECORD_AUDIO(final String stringDataForCallAgainLastMethod1) {
        initializeActivity_Context();

        Dexter.withActivity(activityObj).withPermission(Manifest.permission.RECORD_AUDIO).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                Log.d("", "onPermissionGranted");
                onClickButtonEnroll(stringDataForCallAgainLastMethod1, callbackEnroll, null);
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {
                UtilityJava.showCustomToast(contextObj, "Permission denied for record audio", true);
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).withErrorListener(new PermissionRequestErrorListener() {
            @Override
            public void onError(DexterError error) {
                Log.d("", String.valueOf(error));
            }
        }).check();
    }


    private void getPermissionFor_WRITE_EXTERNAL_STORAGE(final String stringDataForCallAgainLastMethod1) {
        initializeActivity_Context();

        Dexter.withActivity(activityObj).withPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse response) {
                Log.d("", "onPermissionGranted");
//                onClickButtonPickFileFromFolderCamera(stringDataForCallAgainLastMethod1, stringDataForCallAgainLastMethod2, callbackPickFileFromFolderCamera, null);
                onClickButtonEnroll(stringDataForCallAgainLastMethod1, callbackEnroll, null);
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse response) {
                UtilityJava.showCustomToast(contextObj, "Permission denied for write external storage", true);
            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                token.continuePermissionRequest();
            }
        }).withErrorListener(new PermissionRequestErrorListener() {
            @Override
            public void onError(DexterError error) {
                Log.d("", String.valueOf(error));
            }
        }).check();
    }

}
