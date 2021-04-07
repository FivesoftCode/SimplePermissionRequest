package com.fivesoft.simplepermissionrequest;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.os.Bundle;
import android.widget.Toast;

import com.fivesoft.library.PermissionRequest;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PermissionRequest.from(this)
                .setPermission(Manifest.permission.CAMERA)
                .setListener(isGranted -> {
                    Toast.makeText(this, isGranted ? "Granted!" : "Denied", Toast.LENGTH_LONG).show();
                })
                .request();
    }
}