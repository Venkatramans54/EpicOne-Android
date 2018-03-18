package com.a3imedia.ted;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class MainActivity extends AppCompatActivity {

    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/font_qarmic_sans.ttf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        setContentView(R.layout.activity_main);

        ImageView aSplash = (ImageView) findViewById(R.id.splash);
        Glide.with(MainActivity.this).load(R.drawable.carrier_splash).into(aSplash);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            permissonChecker();
        } else {
            LoadAppdata();
        }
    }

    private void permissonChecker() {
        List<String> permissionsNeeded = new ArrayList<>();
        final List<String> permissionsList = new ArrayList<>();
        if (!addPermission(permissionsList, android.Manifest.permission.CAMERA))
            permissionsNeeded.add("Camera");
        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                String message = "You need to grant access to " + permissionsNeeded.get(0);
                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message = message + ", " + permissionsNeeded.get(i);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(permissionsList.toArray(new String[permissionsList.size()]), REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
                }
                return;
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                        REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            }
        } else {
            LoadAppdata();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    private boolean addPermission(List<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            if (!shouldShowRequestPermissionRationale(permission))
                return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        try {
            switch (requestCode) {
                case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
                    if (grantResults[0] == 0) {
                        Map<String, Integer> perms = new HashMap<>();
                        perms.put(Manifest.permission.CAMERA, PackageManager.PERMISSION_GRANTED);
                        for (int i = 0; i < permissions.length; i++)
                            perms.put(permissions[i], grantResults[i]);
                        if (perms.get(android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                            LoadAppdata();
                        }
                    } else {
                        LoadAppdata();
                    }
                }
                break;
                default:
                    super.onRequestPermissionsResult(requestCode, permissions, grantResults);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void LoadAppdata() {

        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                Intent i = new Intent(getApplicationContext(),
                        AboutCompanyScreen.class);
                startActivity(i);
                finish();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 3000);
    }
}
