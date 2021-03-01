package com.login_check_in_out;

import android.os.Bundle;
import android.view.WindowManager;

import com.facebook.react.ReactActivity;
import com.login_check_in_out.NativeClasses.a2Utility.HardcodedData_Java;
import com.login_check_in_out.NativeClasses.a2Utility.UtilityJava.UtilityJava;

public class MainActivity extends ReactActivity {

  /**
   * Returns the name of the main component registered from JavaScript. This is used to schedule
   * rendering of the component.
   */
  @Override
  protected String getMainComponentName() {
    return "Login_Check_In_Out";
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    UtilityJava.setContext(this);
//    SplashScreen.show(this);
    super.onCreate(savedInstanceState);

//        getWindow().getDecorView().setBackgroundColor(Color.WHITE);

    //        UtilityJava.setContext(this);
    HardcodedData_Java.hardcodeDataFor(this);
//        UtilityJava.showCustomToast(UtilityJava.getContext(),"MainActivity onCreate",true);
  }

}
