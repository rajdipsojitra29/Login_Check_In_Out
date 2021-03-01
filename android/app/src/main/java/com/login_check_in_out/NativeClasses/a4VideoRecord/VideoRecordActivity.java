package com.login_check_in_out.NativeClasses.a4VideoRecord;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.login_check_in_out.NativeClasses.a2Utility.Constants_Java;
import com.login_check_in_out.NativeClasses.a2Utility.HardcodedData_Java;
import com.login_check_in_out.NativeClasses.a2Utility.UtilityJava.ProgressBarHandler;
import com.login_check_in_out.NativeClasses.a2Utility.UtilityJava.UtilityJava;
import com.login_check_in_out.R;

public class VideoRecordActivity extends AppCompatActivity {

    ProgressBarHandler progressBarHandler;
    ConstraintLayout constraintLayoutMain;
    Button buttonEnroll;












    @Override
    protected void onCreate(Bundle savedInstanceState) {
        customizeActionBar();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_record_activity);

        UtilityJava.setContext(this);

//        Intent intent = getIntent();
//        stringData = intent.getStringExtra("dataFromChooseFileFromStorageActivity") == null ? "" : intent.getStringExtra("dataFromChooseFileFromStorageActivity");

        progressBarHandler = new ProgressBarHandler(UtilityJava.getContext(),this);
        progressBarHandler.show();

        allFindViewByIdInitialization();

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.container, new CameraFragment()).commit();
        }

        buttonEnroll.setVisibility(View.INVISIBLE);
        buttonEnroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickButtonEnroll();
            }
        });
    }


    @Override
    protected final void onStart() {
        super.onStart();
        constraintLayoutMain.setBackgroundColor(getResources().getColor(R.color.blackTransparentOpacity00Parcent));
        HardcodedData_Java.hardcodeDataFor(this);

//        onClickButtonEnroll();
        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                onClickButtonEnroll();
            }
        }, 1000);
    }


    @Override
    protected final void onResume() {
        super.onResume();
    }


    @Override
    protected final void onPause() {
        super.onPause();
    }


    @Override
    protected final void onStop() {
        super.onStop();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private void customizeActionBar() {
        /*
//        UtilityJava.setStatusBarBackgroundColor(this, R.color.colorNavyblue);
//        getSupportActionBar().hide();
//        getSupportActionBar().setTitle("");
//        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000080")));
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
         */
    }


    @Override
    public void onBackPressed() {
        finish();
        progressBarHandler.hide();
        super.onBackPressed();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }


    public void allFindViewByIdInitialization() {
        constraintLayoutMain = (ConstraintLayout) findViewById(R.id.constraintLayoutIdMain);
        buttonEnroll = findViewById(R.id.buttonIdEnroll);
    }


    private void onClickButtonEnroll() {
        UtilityJava.showCustomToast("Video recording started. Video length 13 sec",true);
        CameraFragment fragment = (CameraFragment) getSupportFragmentManager().findFragmentById(R.id.container);
        fragment.startRecording();

        final Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                fragment.stopRecording(new CameraFragment_SR_Interface() {
                    @Override
                    public void callBackStopRecording(String stringBase64) {
//                                Log.d("stringBase64 = ",stringBase64);
                        Bundle bundle = new Bundle();
                        bundle.putString("stringFileInBase64",stringBase64);
                        Intent intentTemp = new Intent(Constants_Java.LOCAL_BROADCAST_FROM_JFTCRNM_TO_VRA).putExtra("DataFromVideoRecordActivity",bundle);
                        LocalBroadcastManager.getInstance(VideoRecordActivity.this).sendBroadcast(intentTemp);
                        onBackPressed();
                    }
                });
                UtilityJava.showCustomToast("Video captured",true);
            }
        }, 13000); //13000
    }


/*
    public void tempMethod() {
        Bundle bundle = new Bundle();
        bundle.putString("stringFileInBase64","stringBase64");
        Intent intentTemp = new Intent(Constants_Java.LOCAL_BROADCAST_FROM_JFTCRNM_TO_VRA).putExtra("DataFromVideoRecordActivity",bundle);
        LocalBroadcastManager.getInstance(VideoRecordActivity.this).sendBroadcast(intentTemp);
        onBackPressed();
    }
    */

}
