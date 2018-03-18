package com.a3imedia.ted;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;


public class portfolioScreen extends AppCompatActivity {


    ImageView img1,img2,img3,img4,img5,img6,img7,img8;
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
        setContentView(R.layout.activity_portfolio_screen);
        img1 =(ImageView) findViewById(R.id.image1);
        img2 =(ImageView) findViewById(R.id.image2);
        img3 =(ImageView) findViewById(R.id.image3);
        img4 =(ImageView) findViewById(R.id.image4);
        img5 =(ImageView) findViewById(R.id.image5);
        img6 =(ImageView) findViewById(R.id.image6);
        img7 =(ImageView) findViewById(R.id.image7);
        img8 =(ImageView) findViewById(R.id.image8);

        Glide.with(this).load(R.drawable.img1)
                .thumbnail(0.5f)
                .into(img1);
        Glide.with(this).load(R.drawable.img2)
                .thumbnail(0.5f)
                .into(img2);
        Glide.with(this).load(R.drawable.img3)
                .thumbnail(0.5f)
                .into(img3);
        Glide.with(this).load(R.drawable.img4)
                .thumbnail(0.5f)
                .into(img4);
        Glide.with(this).load(R.drawable.img5)
                .thumbnail(0.5f)
                .into(img5);
        Glide.with(this).load(R.drawable.img6)
                .thumbnail(0.5f)
                .into(img6);
        Glide.with(this).load(R.drawable.img7)
                .thumbnail(0.5f)
                .into(img7);
        Glide.with(this).load(R.drawable.img8)
                .thumbnail(0.5f)
                .into(img8);

    }
}
