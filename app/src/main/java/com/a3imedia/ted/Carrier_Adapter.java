package com.a3imedia.ted;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by Elango on 3/8/2018.
 */

public class Carrier_Adapter extends RecyclerView.Adapter<Carrier_Adapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<HashMap<String, String>> mList_Hash = new ArrayList<>();
    LocationDatabase mLocation_Db;
    SharedPreferences pref;
    public class MyViewHolder extends RecyclerView.ViewHolder {

        CardView mParent;
        ImageView aCarrier_Logo;
        TextView aCompany_Name, aMobile_no, aDesignation, aExperience, aLocation_det, mEndates, mQualifacition, mKetskills, mNotice , aDelete;

        public MyViewHolder(View view) {
            super(view);
            mParent = view.findViewById(R.id.cardparent);
            aCarrier_Logo = view.findViewById(R.id.carrierphoto);
            aCompany_Name = view.findViewById(R.id.Companyname);
            aMobile_no = view.findViewById(R.id.mobileno);
            aDesignation = view.findViewById(R.id.designation);
            aExperience = view.findViewById(R.id.experience);
            aLocation_det = view.findViewById(R.id.locations);
            mEndates = view.findViewById(R.id.endates);
            mQualifacition = view.findViewById(R.id.qualifications);
            mKetskills = view.findViewById(R.id.keyskills);
            mNotice = view.findViewById(R.id.noticeperiod);
            aDelete = view.findViewById(R.id.delete_text_view);

        }

    }

    public Carrier_Adapter(Context aContext, ArrayList<HashMap<String, String>> aList_Hash) {
        this.mContext = aContext;
        this.mList_Hash = aList_Hash;
        mLocation_Db = new LocationDatabase(aContext);
        mLocation_Db.open();
         pref = mContext.getSharedPreferences("MyPref", 0); // 0 - for private mode
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.carriers_adapter, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {


        if( pref.getString("loginType","user").equalsIgnoreCase("user"))
        {
            holder.aDelete.setVisibility(View.GONE);
        }

        final HashMap<String, String> aHashmap = mList_Hash.get(position);
        if (!TextUtils.isEmpty(aHashmap.get("Companyname"))) {
            holder.aCompany_Name.setText(aHashmap.get("Companyname"));
        } else {
            holder.aCompany_Name.setText("");
        }
        if (!TextUtils.isEmpty(aHashmap.get("designation"))) {
            holder.aDesignation.setText(aHashmap.get("designation"));
        } else {
            holder.aDesignation.setText("");
        }
        if (!TextUtils.isEmpty(aHashmap.get("experience"))) {
            holder.aExperience.setText(aHashmap.get("experience"));
        } else {
            holder.aExperience.setText("");
        }
        if (!TextUtils.isEmpty(aHashmap.get("location"))) {
            holder.aLocation_det.setText(aHashmap.get("location"));
        } else {
            holder.aLocation_det.setText("");
        }
        if (!TextUtils.isEmpty(aHashmap.get("keyskills"))) {
            holder.mKetskills.setText(aHashmap.get("keyskills"));
        } else {
            holder.mKetskills.setText("");
        }
        if (!TextUtils.isEmpty(aHashmap.get("Qualification"))) {
            holder.mQualifacition.setText(aHashmap.get("Qualification"));
        } else {
            holder.mQualifacition.setText("");
        }
        if (!TextUtils.isEmpty(aHashmap.get("Noticeperiod"))) {
            holder.mNotice.setText("Notice Period: " + aHashmap.get("Noticeperiod"));
        } else {
            holder.mNotice.setText("");
        }
        if (!TextUtils.isEmpty(aHashmap.get("Enddate"))) {
            holder.mEndates.setText(aHashmap.get("Enddate"));
        } else {
            holder.mEndates.setText("");
        }
        if (!TextUtils.isEmpty(aHashmap.get("Mobile"))) {
            holder.aMobile_no.setText(aHashmap.get("Mobile"));
        } else {
            holder.aMobile_no.setText("");
        }
        if (!TextUtils.isEmpty(aHashmap.get("Photo"))) {
            String aImage_Enc = aHashmap.get("Photo").trim();
            byte[] decodedString = Base64.decode(aImage_Enc, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            decodedByte.compress(Bitmap.CompressFormat.PNG, 100, stream);
            if (decodedByte != null) {
                holder.aCarrier_Logo.setImageBitmap(decodedByte);
            }
        } else {
            holder.aMobile_no.setText("");
        }
        holder.aDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mLocation_Db.DeleteJobById(aHashmap.get("id"));
                mList_Hash.remove(position);
                notifyItemRemoved(position);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mList_Hash.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }


}

