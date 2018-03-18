package com.a3imedia.ted;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;

public class LocationDatabase {
    static final String DATABASE_NAME = "tedapp.db";
    static final int DATABASE_VERSION = 1;
    static final String DATABASE__User_Reg = "create table " + "user_reg" + "( " + "ID" + " integer primary key  autoincrement ," + "Name text,Username text,Passowrd text,Mobile text);";
    static final String DATABASE__Carrer_Post = "create table " + "carrier" + "( " + "ID" + " integer primary key  autoincrement ," + "Companyname text,designation text,experience text,location text,keyskills text,Qualification text,Noticeperiod text,Enddate text,Photo text,Mobile text);";
    private SQLiteDatabase myDb;
    private Database_helper myDhhelper;
    Context myContext;

    LocationDatabase(Context aContext) {
        myContext = aContext;
        myDhhelper = new Database_helper(myContext, DATABASE_NAME, null, DATABASE_VERSION);
        open();
    }

    void open() {
        myDb = myDhhelper.getWritableDatabase();
    }

    void close() {
        myDb.close();
    }

    public SQLiteDatabase Getdatabase() {
        return myDb;
    }


    public long Insert_User_Reg(String aName, String aUsername, String aPassword, String aMobile) {
        ContentValues acontentinsert = new ContentValues();
        acontentinsert.put("Name", aName);
        acontentinsert.put("Username", aUsername);
        acontentinsert.put("Passowrd", aPassword);
        acontentinsert.put("Mobile", aMobile);
        return myDb.insert("user_reg", null, acontentinsert);
    }


    public long Insert_Carrer(String Companyname, String designation, String experience, String location, String keyskills, String Qualification, String Noticeperiod, String Enddate, String Photo, String aMobile) {
        ContentValues acontentinsert = new ContentValues();
        acontentinsert.put("Companyname", Companyname);
        acontentinsert.put("designation", designation);
        acontentinsert.put("experience", experience);
        acontentinsert.put("location", location);
        acontentinsert.put("keyskills", keyskills);
        acontentinsert.put("Qualification", Qualification);
        acontentinsert.put("Noticeperiod", Noticeperiod);
        acontentinsert.put("Enddate", Enddate);
        acontentinsert.put("Photo", Photo);
        acontentinsert.put("Mobile", aMobile);
        return myDb.insert("carrier", null, acontentinsert);
    }

    public ArrayList<HashMap<String,String>> Get_All_Carrier()
    {
        ArrayList<HashMap<String, String>> aListarray =new ArrayList<HashMap<String,String>>();
        myDb=myDhhelper.getWritableDatabase();
        String aselectquery="SELECT * From carrier";
        Cursor aCursor=myDb.rawQuery(aselectquery, null);
        if(aCursor.getCount()<0)
        {
            aCursor.close();
        }

        if(aCursor!=null)
        {
            aCursor.moveToFirst();
            while(!aCursor.isAfterLast()){
                HashMap<String, String> aMydetailHashMap=new HashMap<String, String>();
                aMydetailHashMap.put("Companyname",aCursor.getString(aCursor.getColumnIndex("Companyname")));
                aMydetailHashMap.put("designation",aCursor.getString(aCursor.getColumnIndex("designation")));
                aMydetailHashMap.put("experience",aCursor.getString(aCursor.getColumnIndex("experience")));
                aMydetailHashMap.put("location",aCursor.getString(aCursor.getColumnIndex("location")));
                aMydetailHashMap.put("keyskills",aCursor.getString(aCursor.getColumnIndex("keyskills")));
                aMydetailHashMap.put("Qualification", aCursor.getString(aCursor.getColumnIndex("Qualification")));
                aMydetailHashMap.put("Noticeperiod",aCursor.getString(aCursor.getColumnIndex("Noticeperiod")));
                aMydetailHashMap.put("Enddate",aCursor.getString(aCursor.getColumnIndex("Enddate")));
                aMydetailHashMap.put("Photo",aCursor.getString(aCursor.getColumnIndex("Photo")));
                aMydetailHashMap.put("Mobile",aCursor.getString(aCursor.getColumnIndex("Mobile")));
                aMydetailHashMap.put("id",aCursor.getString(aCursor.getColumnIndex("ID")));
                Log.d("Id - retrive",aCursor.getString(aCursor.getColumnIndex("ID")));
                aListarray.add(aMydetailHashMap);
                aCursor.moveToNext();
            }}
        myDb.close();
        return aListarray;
    }
    public String Get_User_Password(String aUsername) {
        Cursor cursor = myDb.query("user_reg", null, " Username=?", new String[]{aUsername}, null, null, null);
        if (cursor.getCount() < 1) {
            cursor.close();
            return "NOT EXIST";
        }
        cursor.moveToFirst();
        String password = cursor.getString(cursor.getColumnIndex("Passowrd"));
        cursor.close();
        return password;
    }

    public int Carrrier_Count() {
        int aCount = 0;
        myDb = myDhhelper.getWritableDatabase();
        Cursor aCursor = myDb.rawQuery("select * from carrier", null);
        aCount = aCursor.getCount();
        aCursor.close();
        return aCount;
    }

    public boolean DeleteJobById(String name)
    {
        Log.d("Delete",name);
        myDb = myDhhelper.getWritableDatabase();
        return myDb.delete("carrier", "ID" + "=" + Integer.valueOf(name), null) > 0;
    }
}
