package com.a3imedia.ted;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.HashMap;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class Carrier_List extends AppCompatActivity {

    ArrayList<HashMap<String, String>> mList_Hash = new ArrayList<>();
    LocationDatabase mLocation_Db;
    RecyclerView mCarrier_View;
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
        setContentView(R.layout.activity_carrier__list);

        ((ImageView) findViewById(R.id.backbutton)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Carrier_List.this.finish();
            }
        });

        mCarrier_View = (RecyclerView) findViewById(R.id.RecyclerView);
        mCarrier_View.setHasFixedSize(true);
        mCarrier_View.setItemViewCacheSize(20);
        mCarrier_View.setDrawingCacheEnabled(true);
        mCarrier_View.setDrawingCacheQuality(View.DRAWING_CACHE_QUALITY_HIGH);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(Carrier_List.this);
        mCarrier_View.setLayoutManager(mLayoutManager);

        mList_Hash.clear();
        mLocation_Db = new LocationDatabase(Carrier_List.this);
        mLocation_Db.open();
        mList_Hash = mLocation_Db.Get_All_Carrier();
        mCarrier_View.setAdapter(new Carrier_Adapter(Carrier_List.this, mList_Hash));
    }
}
