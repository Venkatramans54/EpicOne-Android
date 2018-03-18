package com.a3imedia.ted;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import java.util.Locale;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class GeneralScreen extends AppCompatActivity {

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
        setContentView(R.layout.activity_general_screen);
    }

    public void porfolio(View view) {
        startActivity(new Intent(GeneralScreen.this, portfolioScreen.class));
    }

    public void aboutus(View view) {
        startActivity(new Intent(GeneralScreen.this, AboutUsUserView.class));
    }

    public void countactUs(View view) {
        startActivity(new Intent(GeneralScreen.this, ContactusScreen.class));
    }

    public void googleMap(View view) {
        String uri = String.format(Locale.ENGLISH, "geo:%f,%f", Double.valueOf("11.0168"), Double.valueOf("76.9558"));
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        this.startActivity(intent);
    }

    public void careerJob(View view) {
        startActivity(new Intent(GeneralScreen.this, User_Dashboard.class));
    }
}
