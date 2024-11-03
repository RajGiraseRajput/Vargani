package com.function_list.vargani.model;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;

import java.util.Locale;

public class MyApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        loadLanguage(); // Apply saved language
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(applyLanguageContext(base));
    }

    private void loadLanguage() {
        SharedPreferences preferences = getSharedPreferences("Settings", MODE_PRIVATE);
        String language = preferences.getString("My_Lang", "en"); // Default to English if not set
        setLocale(language);
    }

    private void setLocale(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }

    public static Context applyLanguageContext(Context context) {
        SharedPreferences preferences = context.getSharedPreferences("Settings", MODE_PRIVATE);
        String language = preferences.getString("My_Lang", "en"); // Default to English if not set
        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        return context.createConfigurationContext(config);
    }
}

