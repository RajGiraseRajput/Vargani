package com.function_list.vargani.model;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import com.function_list.vargani.utils.LocalHelper;

import java.util.Locale;

public class MyApp extends Application {


    @Override
    public void onCreate() {
        super.onCreate();
        LocalHelper.onCreate(this);
    }

}

