package com.function_list.vargani.activitis;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
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
import com.function_list.vargani.utils.LocalHelper;
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
            v.setPadding(40, systemBars.top, 40, systemBars.bottom);
            return insets;
        });

        binding.cdLanguage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                languageDialog();
            }
        });

        SharedPreferences sharedPreferences = getSharedPreferences("checked", MODE_PRIVATE);
        boolean checked = sharedPreferences.getBoolean("checked", false);
        Log.e("checked", "" + checked);
        binding.sbDarkMood.setChecked(checked);

        binding.sbDarkMood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int mode = SharedPrefs.getAppNightDayMode(getApplicationContext());
                if (mode == AppCompatDelegate.MODE_NIGHT_NO) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    SharedPrefs.setInt(getApplicationContext(), SharedPrefs.PREF_NIGHT_MODE, AppCompatDelegate.MODE_NIGHT_YES);
                    SharedPreferences sharedPreferences = getSharedPreferences("checked", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("checked", true);
                    editor.apply();
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    SharedPrefs.setInt(getApplicationContext(), SharedPrefs.PREF_NIGHT_MODE, AppCompatDelegate.MODE_NIGHT_NO);
                    SharedPreferences sharedPreferences = getSharedPreferences("checked", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("checked", false);
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

                LocalHelper.setLocale(Settings_Activity.this, "en");
                recreate();
                dialog.dismiss();
            }
        });

        marathi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LocalHelper.setLocale(Settings_Activity.this, "mr");
                recreate();
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(LocalHelper.wrap(newBase, LocalHelper.getCurrentLanguage(newBase)));
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        LocalHelper.setLocale(this, LocalHelper.getCurrentLanguage(this));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        restartApp();
    }

    public void restartApp() {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }
}
