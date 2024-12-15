package com.function_list.vargani.database;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.FileProvider;

import com.function_list.vargani.R;
import com.function_list.vargani.activitis.MainActivity;
import com.function_list.vargani.model.Birthday_Party_List;
import com.function_list.vargani.model.Ganesha_SubscriptionModel;
import com.function_list.vargani.model.Marriage_List_Model;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

public class DataBaseHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "vargani_db";
    private static final int DATABASE_VERSION = 1;
    private static final String GANESHA_SUBSCRIPTION = "ganesha_subscription";

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
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String amount = cursor.getString(2);
                String address = cursor.getString(3);

                Ganesha_SubscriptionModel subscription = new Ganesha_SubscriptionModel(id, name, amount, address);
                subscriptionList.add(subscription);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return subscriptionList;
    }

    public List<Marriage_List_Model> getAllMarriageList() {
        List<Marriage_List_Model> marriageList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM marriage_list", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String mlName = cursor.getString(1);
                String mlAmount = cursor.getString(2);
                String mlSarees = cursor.getString(3);
                String mlAddress = cursor.getString(4);

                Marriage_List_Model marriageListModel = new Marriage_List_Model(id, mlName, mlAmount, mlSarees, mlAddress);
                marriageList.add(marriageListModel);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return marriageList;
    }

    public boolean added_Marriage_List(String mlName, String mlAmount, String mlSarees, String mlAddress) {

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("ml_name", mlName);
        contentValues.put("ml_amount", mlAmount);
        contentValues.put("ml_saree", mlSarees);
        contentValues.put("ml_address", mlAddress);

        long insertResult = db.insert("marriage_list", null, contentValues);
        db.close();

        if (insertResult > 0) {
            return true;
        } else {
            return false;
        }
    }

    public boolean added_Birthday_Party(String bName, String bAmount, String bGift, String bAddress) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("b_name", bName);
        contentValues.put("b_amount", bAmount);
        contentValues.put("b_gift", bGift);
        contentValues.put("b_address", bAddress);

        long insertResult = db.insert("birthday_party", null, contentValues);
        db.close();

        if (insertResult > 0) {
            return true;
        } else {
            return false;
        }
    }

    public List<Birthday_Party_List> getAllBirthdayPartyList() {
        List<Birthday_Party_List> birthdayPartyLists = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM birthday_party", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String b_name = cursor.getString(1);
                String b_amount = cursor.getString(2);
                String b_gift = cursor.getString(3);
                String b_address = cursor.getString(4);

                Birthday_Party_List birthdayPartyListModel = new Birthday_Party_List(id, b_name, b_amount, b_gift, b_address);
                birthdayPartyLists.add(birthdayPartyListModel);

            } while (cursor.moveToNext());
        }
        cursor.close();
        return birthdayPartyLists;
    }

    public void savePdfGaneshaSubscriptions(Context context) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM ganesha_subscription", null);

//        String filePath = Environment.getExternalStorageDirectory().getPath() + "/GaneshaSubscriptions.pdf";
        String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/Ganesha_Subscription.pdf";
        PdfWriter writer = null;
        try {
            writer = new PdfWriter(filePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        supportMultiLanguage(document,context);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("name"));
                @SuppressLint("Range") String amount = cursor.getString(cursor.getColumnIndex("amount"));
                @SuppressLint("Range") String address = cursor.getString(cursor.getColumnIndex("address"));
                document.add(new Paragraph("Name: " + name));
                document.add(new Paragraph("Amount: " + amount));
                document.add(new Paragraph("Address: " + address));
                document.add(new Paragraph(" "));
            } while (cursor.moveToNext());
        }
        cursor.close();
        document.close();

        Toast.makeText(context, "Data Saved in " + filePath, Toast.LENGTH_LONG).show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            showNotification(context, filePath, "PDF Saved", "Your PDF has been saved to " + filePath);
        }

    }

    public void savePdfMarriage(Context context) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM marriage_list", null);

        String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/Marriage.pdf";
        PdfWriter writer = null;
        try {
            writer = new PdfWriter(filePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        supportMultiLanguage(document,context);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("ml_name"));
                @SuppressLint("Range") String amount = cursor.getString(cursor.getColumnIndex("ml_amount"));
                @SuppressLint("Range") String address = cursor.getString(cursor.getColumnIndex("ml_address"));
                @SuppressLint("Range") String sarees = cursor.getString(cursor.getColumnIndex("ml_saree"));
                document.add(new Paragraph("Name: " + name));
                document.add(new Paragraph("Amount: " + amount));
                document.add(new Paragraph("Address: " + address));
                document.add(new Paragraph("Sarees: " + sarees));
                document.add(new Paragraph(" "));
            } while (cursor.moveToNext());
        }
        cursor.close();
        document.close();

        Toast.makeText(context, "Data Saved in " + filePath, Toast.LENGTH_LONG).show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            showNotification(context, filePath, "PDF Saved", "Your PDF has been saved to " + filePath);
        }

    }

    public void savePdfBirthdayParty(Context context) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM birthday_party", null);

        String filePath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/Birthdays_Party.pdf";
        PdfWriter writer = null;
        try {
            writer = new PdfWriter(filePath);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        PdfDocument pdfDoc = new PdfDocument(writer);
        Document document = new Document(pdfDoc);

        supportMultiLanguage(document,context);

        if (cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") String name = cursor.getString(cursor.getColumnIndex("b_name"));
                @SuppressLint("Range") String amount = cursor.getString(cursor.getColumnIndex("b_amount"));
                @SuppressLint("Range") String gift = cursor.getString(cursor.getColumnIndex("b_gift"));
                @SuppressLint("Range") String address = cursor.getString(cursor.getColumnIndex("b_address"));
                document.add(new Paragraph( context.getResources().getString(R.string.name)+" "+ name));
                document.add(new Paragraph(context.getResources().getString(R.string.amount)+" " + amount));
                document.add(new Paragraph(context.getResources().getString(R.string.gift)+" " + gift));
                document.add(new Paragraph(context.getResources().getString(R.string.address)+" " + address));
                document.add(new Paragraph(" "));
            } while (cursor.moveToNext());
        }
        cursor.close();
        document.close();

        Toast.makeText(context, "Data Saved in " + filePath, Toast.LENGTH_LONG).show();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            showNotification(context, filePath, "PDF Saved", "Your PDF has been saved to " + filePath);
        }

    }


    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    private void showNotification(Context context, String filePath, String title, String
            content) {
        String channelId = "pdf_channel_id";
        String channelName = "PDF Notifications"; // Create Intent to open the PDF file
        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri fileUri = FileProvider.getUriForFile(context, "com.function_list.vargani.provider", new File(filePath));
        intent.setDataAndType(fileUri, "application/pdf");
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY | Intent.FLAG_GRANT_READ_URI_PERMISSION);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            manager.createNotificationChannel(channel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title)
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent) // Set the PendingIntent here
                .setAutoCancel(true); // Remove the notification when clicked
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((Activity) context.getApplicationContext(), new String[]{Manifest.permission.POST_NOTIFICATIONS}, 1111);
            return;
        }

        notificationManager.notify(1, builder.build());

    }

    public void supportMultiLanguage(Document document, Context context){
        try { // Copy font from res/raw to an accessible location
            File fontFile = new File(context.getFilesDir(), "nota_sence_devgiri.ttf");
            if (!fontFile.exists()) {
                try (InputStream inputStream = context.getResources().openRawResource(R.raw.nota_sence_devgiri); OutputStream outputStream = new FileOutputStream(fontFile)) {
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = inputStream.read(buffer)) > 0) {
                        outputStream.write(buffer, 0, length);
                    }
                }
            } // Load the font file from the accessible location
            PdfFont font = PdfFontFactory.createFont(fontFile.getAbsolutePath(), PdfEncodings.IDENTITY_H, true);
            document.setFont(font);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}