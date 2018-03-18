package com.a3imedia.ted;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class User_View extends AppCompatActivity {

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
        setContentView(R.layout.activity_user__view);

        mLocation_Db = new LocationDatabase(User_View.this);
        mLocation_Db.open();

        final EditText aUsermame = findViewById(R.id.username);
        final EditText aPassword = findViewById(R.id.password);
        final Spinner aUserType = findViewById(R.id.usertype);
        TextView aLogin = findViewById(R.id.loginbtn);
        ArrayList<String> aSpinnerData = new ArrayList<>();
        aSpinnerData.add("Select User Role");
        aSpinnerData.add("Admin");
        aSpinnerData.add("User");
        ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>
                (this, R.layout.spinner_item, R.id.list_items,
                        aSpinnerData);
        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        aUserType.setAdapter(spinnerArrayAdapter);
        aLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
                SharedPreferences.Editor editor = pref.edit();
                String aUsermeval = aUsermame.getText().toString();
                String aPasswordval = aPassword.getText().toString();
                String aUsertypeval = aUserType.getSelectedItem().toString();
                if (TextUtils.isEmpty(aUsermeval)) {
                    Toast.makeText(User_View.this, "Enter Username", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(aPasswordval)) {
                    Toast.makeText(User_View.this, "Enter Password", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(aUsertypeval) || aUsertypeval.equalsIgnoreCase("Select User Role")) {
                    Toast.makeText(User_View.this, "Please Select User Role", Toast.LENGTH_SHORT).show();
                } else {
                    if (aUsermeval.equalsIgnoreCase("admin") && aPasswordval.equalsIgnoreCase("admin") && aUsertypeval.equalsIgnoreCase("admin")) {
                        editor.putString("loginType",aUsertypeval );
                        editor.commit();
                        Intent aIntent = new Intent(User_View.this, Admin_Dashboard.class);
                        startActivity(aIntent);
                        finish();
                    } else if (aUsertypeval.equalsIgnoreCase("user")) {
                        String aPassword_Sql = mLocation_Db.Get_User_Password(aUsermeval);
                        if (aPassword_Sql.equalsIgnoreCase(aPasswordval)) {


                            editor.putString("loginType",aUsertypeval );
                            editor.commit();
                            Toast.makeText(User_View.this, "Hello " + aUsermeval + " Access Granted..", Toast.LENGTH_SHORT).show();
                            Intent aIntent = new Intent(User_View.this, GeneralScreen.class);
                            startActivity(aIntent);
                            finish();
                        } else {
                            Toast.makeText(User_View.this, "User Access Denied..", Toast.LENGTH_SHORT).show();
                            aUsermame.setText("");
                            aPassword.setText("");
                        }
                    } else
                    {
                        Toast.makeText(User_View.this, "User Access Denied..", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

}
