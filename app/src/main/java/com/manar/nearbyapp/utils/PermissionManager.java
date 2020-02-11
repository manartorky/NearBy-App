package com.manar.nearbyapp.utils;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Build;

import androidx.annotation.IntDef;
import androidx.annotation.NonNull;
import androidx.annotation.StringDef;
import androidx.core.app.ActivityCompat;

import java.lang.annotation.Retention;
import java.util.ArrayList;

import static java.lang.annotation.RetentionPolicy.SOURCE;

public class PermissionManager {
    public static final int LOCATION_PERMISSION_REQUEST_CODE = 110;
    public static final int MULTIPLE_PERMISSION_REQUEST_CODE = 100;

    private PermissionManager() {
    }

    public static void checkForPermission(@NonNull Activity activity, @PermissionName String permission, @RequestCode int requestCode) {
        ArrayList<String> permissionsNeeded = new ArrayList<>();
        if (ActivityCompat.checkSelfPermission(activity, permission)
                != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(permission);
        }
        if (!permissionsNeeded.isEmpty()) {
            requestPermission(activity, permissionsNeeded.toArray(new String[0]), requestCode);
        }
    }

    public static boolean isPermissionGranted(@NonNull Activity activity, @PermissionName String permission) {
        return ActivityCompat.checkSelfPermission(activity, permission)
                == PackageManager.PERMISSION_GRANTED;
    }

    public static boolean isAllPermissionGranted(@NonNull Activity activity, String[] permissions) {
        boolean isAllPermissionsGranted = true;
        for (String permission : permissions) {
            if (!isPermissionGranted(activity, permission)) {
                isAllPermissionsGranted = false;
                break;
            }
        }
        return isAllPermissionsGranted;
    }

    public static void checkForPermissions(@NonNull Activity activity, String[] permissions) {
        ArrayList<String> permissionsNeeded = new ArrayList<>();
        for (String permission : permissions) {
            if (ActivityCompat.checkSelfPermission(activity, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                permissionsNeeded.add(permission);
            }
        }
        if (!permissionsNeeded.isEmpty()) {
            requestPermission(activity, permissionsNeeded.toArray(new String[permissionsNeeded.size()]), MULTIPLE_PERMISSION_REQUEST_CODE);
        }
    }

    private static void requestPermission(Activity activity, String[] permissions, @RequestCode int requestCode) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.requestPermissions(permissions, requestCode);

        }
    }

    @Retention(SOURCE)
    @IntDef({
            LOCATION_PERMISSION_REQUEST_CODE,
            MULTIPLE_PERMISSION_REQUEST_CODE
    })
    @interface RequestCode {
    }

    @Retention(SOURCE)
    @StringDef({
            Manifest.permission.ACCESS_FINE_LOCATION,
    })
    @interface PermissionName {
    }

}