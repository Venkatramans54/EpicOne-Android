package com.a3imedia.ted;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;


public class LanguageLocale extends BaseAdapter {

    Context mContext;
    ArrayList<String> mList;
    LayoutInflater mLayout_Infalter;

    public LanguageLocale(Context aContext, ArrayList<String> aList) {
        this.mList = aList;
        this.mContext = aContext;
        mLayout_Infalter = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mList.size();
    }

    @Override
    public Object getItem(int i) {
        return mList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        View aView = mLayout_Infalter.inflate(R.layout.language_locale, null);
        AppCompatTextView aLanguage_Title_One = (AppCompatTextView) aView.findViewById(R.id.uschecone);
        aLanguage_Title_One.setText(mList.get(i));
        return aView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        return getView(position, convertView, parent);
    }
}
