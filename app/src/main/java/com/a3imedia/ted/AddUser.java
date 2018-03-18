package com.a3imedia.ted;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AddUser extends AppCompatActivity {


    LocationDatabase mLocation_Db;
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
        setContentView(R.layout.activity_add_user);
        mLocation_Db = new LocationDatabase(AddUser.this);
        mLocation_Db.open();
        ((ImageView) findViewById(R.id.backbutton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddUser.this.finish();
            }
        });

        final EditText aName = findViewById(R.id.name);
        final EditText aUserName = findViewById(R.id.username);
        final EditText aPassword = findViewById(R.id.password);
        final EditText aMobile = findViewById(R.id.mobile);
        final TextView aSignup = (TextView) findViewById(R.id.signup);
        aSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String aNameval = aName.getText().toString();
                String aUsermeval = aUserName.getText().toString();
                String aPasswordval = aPassword.getText().toString();
                String aMobileval = aMobile.getText().toString();
                if (TextUtils.isEmpty(aNameval)) {
                    Toast.makeText(AddUser.this, "Enter Name", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(aUsermeval)) {
                    Toast.makeText(AddUser.this, "Enter Username", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(aPasswordval)) {
                    Toast.makeText(AddUser.this, "Enter Password", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(aMobileval)) {
                    Toast.makeText(AddUser.this, "Enter Mobile Number", Toast.LENGTH_SHORT).show();
                } else {
                    long aSuccess = mLocation_Db.Insert_User_Reg(aNameval, aUsermeval, aPasswordval, aMobileval);
                    if (aSuccess != -1) {
                        Toast.makeText(AddUser.this, "User Registered SuccessFully", Toast.LENGTH_SHORT).show();
                        mLocation_Db.close();
                        finish();
                    } else {
                        Toast.makeText(AddUser.this, "User Failed to Registered", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

    }
}
