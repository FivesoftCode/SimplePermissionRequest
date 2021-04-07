package com.fivesoft.library;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

/**
 * The simplest way to request app permission.
 */

public class PermissionRequest {

    private static OnPermissionRequestResultListener listener;
    private static String permission1 = Manifest.permission.READ_EXTERNAL_STORAGE;
    private static String text = "We need this permission to continue.";
    private final Activity activity;

    private PermissionRequest(Activity activity){
        this.activity = activity;
    }

    /**
     * Creates new PermissionManager instance.
     * @param activity Running activity from which the dialog
     *                 will be created.
     * @return new PermissionManager instance.
     */

    public static PermissionRequest from(Activity activity){
        return new PermissionRequest(activity);
    }

    /**
     * Sets the permission you want to request.
     * @param permission The name of the permission from {@link Manifest.permission}
     * @return current PermissionManager instance.
     */

    public PermissionRequest setPermission(String permission){
        PermissionRequest.permission1 = permission;
        return this;
    }

    /**
     * Sets the listener called when permission request results are ready.
     * @param listener the listener.
     * @return current PermissionManager instance.
     */

    public PermissionRequest setListener(OnPermissionRequestResultListener listener){
        PermissionRequest.listener = listener;
        return this;
    }

    /**
     * Sets the text displayed when settings tab is opened, because user has
     * not granted the permission when dialog was displayed.
     * @param text The text described above
     * @return current PermissionManager instance.
     */

    public PermissionRequest setInSettingsTabText(String text){
        PermissionRequest.text = text;
        return this;
    }

    /**
     * Asks user about the permission.
     * @see #from(Activity)
     * @see #setPermission(String)
     * @see #setListener(OnPermissionRequestResultListener)
     */

    public void request(){
        if(isPermissionGranted(permission1, activity)){
            if(listener != null)
                listener.onResult(true);
            return;
        }
        activity.startActivity(new Intent().setClass(activity, PermissionRequestActivity.class));
    }

    /**
     * Checks if permission is already granted.
     * @param permission Permission name from {@link Manifest.permission}
     * @param context Context necessary to check permission states.
     * @return true if permission is granted.
     */

    public static boolean isPermissionGranted(String permission, Context context){
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public interface OnPermissionRequestResultListener{
        void onResult(boolean isGranted);
    }

    public static class PermissionRequestActivity extends Activity {

        private boolean returnsFromSettingsPage = false;
        private int resumeCount = 0;

        @Override
        protected void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            if(ActivityCompat.shouldShowRequestPermissionRationale(this, permission1) || !wasUserAlreadyAskedAboutThisPermission(permission1, this)){
                ActivityCompat.requestPermissions(this, new String[]{permission1}, 1000);
            } else {
                returnsFromSettingsPage = true;
                openAppSettings();
            }
            notifyAskedAboutPermission(permission1, this);
        }

        @Override
        protected void onResume() {
            super.onResume();
            if(returnsFromSettingsPage && resumeCount > 0){
                listener.onResult(isPermissionGranted(permission1, this));
                finish();
            }
            resumeCount++;
        }

        @Override
        public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            listener.onResult(isPermissionGranted(permission1, this));
            finish();
        }

        private void openAppSettings(){
            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            Uri uri = Uri.fromParts("package", getPackageName(), null);
            intent.setData(uri);
            startActivity(intent);
            Toast.makeText(this, text, Toast.LENGTH_LONG).show();
        }

        private boolean wasUserAlreadyAskedAboutThisPermission(String permission, Context context){
            return context.getSharedPreferences("PERMISSION_MANAGER", MODE_PRIVATE).getBoolean(permission, false);
        }

        private void notifyAskedAboutPermission(String permission, Context context){
            context.getSharedPreferences("PERMISSION_MANAGER", MODE_PRIVATE)
                    .edit().putBoolean(permission, true).apply();
        }

    }

}
