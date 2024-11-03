package com.function_list.vargani.activitis;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.function_list.vargani.R;
import com.function_list.vargani.databinding.ActivitySettingsBinding;
import com.function_list.vargani.model.MyApp;
import com.function_list.vargani.utils.MyUtils;
import com.function_list.vargani.utils.SharedPrefs;

import java.util.Locale;
import java.util.Objects;

public class Settings_Activity extends AppCompatActivity {

    ActivitySettingsBinding binding;
    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivitySettingsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.cdLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                languageDialog();
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("checked",MODE_PRIVATE);
        boolean checked = sharedPreferences.getBoolean("checked",false);
        Log.e("checked",""+checked);
        binding.sbDarkMood.setChecked(checked);

        binding.sbDarkMood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mode = SharedPrefs.getAppNightDayMode(getApplicationContext());
                if (mode == AppCompatDelegate.MODE_NIGHT_NO) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    SharedPrefs.setInt(getApplicationContext(), SharedPrefs.PREF_NIGHT_MODE,AppCompatDelegate.MODE_NIGHT_YES);
                    SharedPreferences sharedPreferences = getSharedPreferences("checked",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("checked",true);
                    editor.apply();
                }else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    SharedPrefs.setInt(getApplicationContext(), SharedPrefs.PREF_NIGHT_MODE,AppCompatDelegate.MODE_NIGHT_NO);
                    SharedPreferences sharedPreferences = getSharedPreferences("checked",Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("checked",false);
                    editor.apply();
                }
            }
        });
    }

    public void languageDialog() {
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.language_popup);

        Objects.requireNonNull(dialog.getWindow()).setBackgroundDrawable(
                new ColorDrawable(Color.TRANSPARENT)
        );

        RelativeLayout english = dialog.findViewById(R.id.btn_english);
        RelativeLayout marathi = dialog.findViewById(R.id.btn_marathi);

        english.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyUtils.saveLanguage("en",Settings_Activity.this);
                MyUtils.setLocale("en",Settings_Activity.this);
                recreate(); // Restart the activity to apply the new language
                dialog.dismiss();
            }
        });

        marathi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyUtils.saveLanguage("mr",Settings_Activity.this);
                MyUtils.setLocale("mr",Settings_Activity.this);
                recreate(); // Restart the activity to apply the new language
                dialog.dismiss();
            }
        });

        dialog.show();
    }




    private void saveLanguage(String languageCode) {
        SharedPreferences preferences = getSharedPreferences("Settings", MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("My_Lang", languageCode);
        editor.apply();
    }

    private void setLocale(String languageCode) {
        Locale locale = new Locale(languageCode);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(MyApp.applyLanguageContext(newBase));
    }
}
