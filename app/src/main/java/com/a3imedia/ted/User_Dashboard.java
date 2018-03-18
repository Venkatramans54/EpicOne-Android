package com.a3imedia.ted;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class User_Dashboard extends AppCompatActivity {
    int mCount;
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
        setContentView(R.layout.activity_user__dashboard);


        ((ImageView) findViewById(R.id.backbutton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        mLocation_Db = new LocationDatabase(User_Dashboard.this);
         mCount = mLocation_Db.Carrrier_Count();
        mLocation_Db.close();

        CardView mCarrier = (CardView) findViewById(R.id.cardparent);
        TextView aCount_Jobs = findViewById(R.id.countofjobs);
        aCount_Jobs.setText(mCount + " Jobs Available");
        mCarrier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(mCount>0)
                startActivity(new Intent(User_Dashboard.this, Carrier_List.class));
                else
                    Toast.makeText(User_Dashboard.this,"No Job Available Please try later",Toast.LENGTH_SHORT).show();
            }
        });

    }
}
