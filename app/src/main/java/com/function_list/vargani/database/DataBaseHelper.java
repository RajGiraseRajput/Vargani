package com.function_list.vargani.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.function_list.vargani.model.Birthday_Party_List;
import com.function_list.vargani.model.Ganesha_SubscriptionModel;
import com.function_list.vargani.model.Marriage_List_Model;

import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "vargani_db";
    private static final int DATABASE_VERSION = 1;
    private static String GANESHA_SUBSCRIPTION = "ganesha_subscription";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {

        String CREATE_TABLE_QUERY = " CREATE TABLE ganesha_subscription (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT,amount TEXT, address TEXT)";
        db.execSQL(CREATE_TABLE_QUERY);

        String CREATE_MARRIAGE_TABLE_QUERY = "CREATE TABLE marriage_list (id INTEGER PRIMARY KEY AUTOINCREMENT,ml_name TEXT,ml_amount TEXT,ml_saree TEXT,ml_address TEXT)";
        db.execSQL(CREATE_MARRIAGE_TABLE_QUERY);

        String CREATE_BIRTHDAY_PARTY = "CREATE TABLE birthday_party (id INTEGER PRIMARY KEY AUTOINCREMENT, b_name TEXT,b_amount TEXT, b_gift TEXT,b_address TEXT)";
        db.execSQL(CREATE_BIRTHDAY_PARTY);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS ganesha_subscription");
        db.execSQL("DROP TABLE IF EXISTS marriage_list");
        db.execSQL("DROP TABLE IF EXISTS birthday_party");

        onCreate(db);
    }

    public boolean added_Ganesha_Subscription(String name1, String amount1, String address1) {

        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        // in this key provide the column names
        contentValues.put("name", name1);
        contentValues.put("amount", amount1);
        contentValues.put("address", address1);

        Long insertValues = sqLiteDatabase.insert("ganesha_subscription", null, contentValues);
        sqLiteDatabase.close();

        if (insertValues > 0) {
            return true;
        } else {
            return false;
        }
    }

    public List<Ganesha_SubscriptionModel> getAllGaneshaSubscription() {

        List<Ganesha_SubscriptionModel> subscriptionList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ganesha_subscription", null);

        if (cursor.moveToFirst()) {
            do{
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String amount = cursor.getString(2);
                String address = cursor.getString(3);

                Ganesha_SubscriptionModel subscription = new Ganesha_SubscriptionModel(id,name,amount,address);
                subscriptionList.add(subscription);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return subscriptionList;
    }

    public List<Marriage_List_Model> getAllMarriageList(){
        List<Marriage_List_Model> marriageList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM marriage_list",null);

        if(cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String mlName = cursor.getString(1);
                String mlAmount = cursor.getString(2);
                String mlSarees = cursor.getString(3);
                String mlAddress = cursor.getString(4);

                Marriage_List_Model marriageListModel = new Marriage_List_Model(id,mlName,mlAmount,mlSarees,mlAddress);
                marriageList.add(marriageListModel);
            }while (cursor.moveToNext());
        }
        cursor.close();
        return marriageList;
    }

    public boolean added_Marriage_List(String mlName,String mlAmount,String mlSarees,String mlAddress){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("ml_name",mlName);
        contentValues.put("ml_amount",mlAmount);
        contentValues.put("ml_saree",mlSarees);
        contentValues.put("ml_address",mlAddress);

        long insertResult = db.insert("marriage_list",null,contentValues);
        db.close();

        if(insertResult > 0){
            return true;
        }else{
            return false;
        }
    }

    public boolean added_Birthday_Party(String bName,String bAmount,String bGift,String bAddress){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("b_name",bName);
        contentValues.put("b_amount",bAmount);
        contentValues.put("b_gift",bGift);
        contentValues.put("b_address",bAddress);

        long insertResult = db.insert("birthday_party",null,contentValues);
        db.close();

        if (insertResult > 0){
            return true;
        }else {
            return false;
        }
    }

    public List<Birthday_Party_List> getAllBirthdayPartyList(){
        List<Birthday_Party_List> birthdayPartyLists = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM birthday_party",null);

        if (cursor.moveToFirst()){
            do{
                int id = cursor.getInt(0);
                String b_name = cursor.getString(1);
                String b_amount = cursor.getString(2);
                String b_gift = cursor.getString(3);
                String b_address = cursor.getString(4);

                Birthday_Party_List birthdayPartyListModel = new Birthday_Party_List(id,b_name,b_amount,b_gift,b_address);
                birthdayPartyLists.add(birthdayPartyListModel);

            }while (cursor.moveToNext());
        }
        cursor.close();
        return birthdayPartyLists;
    }
}

