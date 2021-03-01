package com.login_check_in_out.NativeClasses.a2Utility.UtilityJava;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import androidx.core.content.ContextCompat;
import android.util.Base64;
import android.view.Gravity;
import com.muddzdev.styleabletoast.StyleableToast;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import static android.widget.Toast.LENGTH_SHORT;

public class UtilityJava {

    private static Context context;




















    //set Initialize Contect in first activity
    public static void setContext(Context context) {
        UtilityJava.context = context;
    }


    //check if Context is null or not before use it
    public static Context getContext() {
        return context;
    }


    public static boolean isInternetConnected(Context context, boolean isShowToast) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = connectivityManager.getActiveNetworkInfo();
        if (activeNetwork != null && activeNetwork.isConnected()) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                // connected to wifi
                return true;
            }
            else if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                // connected to the mobile provider's data plan
                return true;
            }
        }
        else {
            showCustomToast(context, "Please check your internet connection",isShowToast);
            return false;
        }
        return false;
    }


    public static void showCustomToast(Context context, String stringMessage, boolean isShow) {
        if (isShow) {
            new StyleableToast
                    .Builder(context)
                    .text(stringMessage)
                    .textColor(Color.WHITE)
                    .backgroundColor(Color.BLACK)
                    .gravity(Gravity.CENTER)
                    .length(LENGTH_SHORT)
                    .show();
        }
    }


    public static void showCustomToast(String stringMessage, boolean isShow) {
        if (isShow) {
            new StyleableToast
                    .Builder(context)
                    .text(stringMessage)
                    .textColor(Color.WHITE)
                    .backgroundColor(Color.BLACK)
                    .gravity(Gravity.CENTER)
                    .length(LENGTH_SHORT)
                    .show();
        }
    }

    public static boolean checkIsPermissionGiven(Context context, String permission) {
//        permission should (Manifest.permission.ACCESS_FINE_LOCATION)
        boolean isPermissionGiven = false;
        // If android sdk version is bigger than 23 the need to check run time permission.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            // return phone read contacts permission grant status.
            int hasPermission = ContextCompat.checkSelfPermission(context, permission);
            // If permission is granted then return true.
            if (hasPermission == PackageManager.PERMISSION_GRANTED) {
                isPermissionGiven = true;
            }
        }
        else {
            isPermissionGiven = true;
        }
        return isPermissionGiven;
    }


    public static boolean createNewFileDirs(String stringFileDirsName) {
        boolean isCreatedNewFileDirs = false;
        File fileDirs = new File(Environment.getExternalStorageDirectory() + File.separator + stringFileDirsName);
        if (fileDirs.exists()) {
            isCreatedNewFileDirs = true;
        }
        else if (!fileDirs.exists()) {
            isCreatedNewFileDirs = fileDirs.mkdirs();
        }
        return isCreatedNewFileDirs;
    }


    public static void showApplicationSettingsAlert(final Context context, String stringTitle, String stringMessage, String stringSettingButtonName) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(context);
        alertDialog.setTitle(stringTitle);
        alertDialog.setMessage(stringMessage);
        alertDialog.setPositiveButton(stringSettingButtonName, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                intent.setData(uri);
                context.startActivity(intent);
            }
        });

        //On pressing cancel button
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        alertDialog.show();
    }


    public static String getStringFilePathInEmulatorFromDownloadFolder(String stringFileName) {
        String pathEnvironment = Environment.getExternalStorageDirectory().getPath();
        String stringFilePath = pathEnvironment + "/Download/" + stringFileName;
        return stringFilePath;
    }


    public static String getBase64FromVideoFilePath(String stringFilePath) {
        String stringBase64 = "";
        try {
            File file = new File(stringFilePath);
            byte[] buffer = new byte[(int) file.length() + 100];
            int length = new FileInputStream(file).read(buffer);
            stringBase64 = Base64.encodeToString(buffer, 0, length, Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            return stringBase64;
        }
    }

}