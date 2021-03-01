package com.login_check_in_out.NativeClasses.a1JavaFunctionsToCallInReactNative;

import com.facebook.react.uimanager.ViewManager;

import androidx.annotation.NonNull;

import com.facebook.react.ReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class JavaFunctionsToCallInReactNativeReactPackage implements ReactPackage {










    @NonNull
    @Override
    public List<NativeModule> createNativeModules(@NonNull ReactApplicationContext reactContext) {
        List<NativeModule> modules = new ArrayList<>();
        //this is where you register the module
        modules.add(new JavaFunctionsToCallInReactNativeModule(reactContext));
//        modules.add((NativeModule) new EventEmitterModule(reactContext));
        return modules;
    }


    @NonNull
    @Override
    public List<ViewManager> createViewManagers(@NonNull ReactApplicationContext reactContext) {
        return Collections.emptyList();
    }
}
