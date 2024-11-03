package com.function_list.vargani.activitis;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.function_list.vargani.R;
import com.function_list.vargani.databinding.ActivityMainBinding;
import com.function_list.vargani.model.MyApp;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        intent = new Intent(MainActivity.this,Marriage_List_Activity.class);

//        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        binding.cvGaneshaSubscription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Ganesha_Subscription_Activity.class);
                startActivity(intent);
            }
        });

        binding.cvMarriageList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // Create an AlertDialog builder
                AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this);
                dialog.setTitle(getResources().getString(R.string.choice_for));

                dialog.setPositiveButton(getResources().getString(R.string.male), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        intent.putExtra("gender",getResources().getString(R.string.male));
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });

                dialog.setNegativeButton(getResources().getString(R.string.female), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        intent.putExtra("gender",getResources().getString(R.string.female));
                        startActivity(intent);
                        dialog.dismiss();
                    }
                });

                dialog.setNeutralButton(getResources().getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                // Show the dialog
                dialog.show();

            }
        });

        binding.cvBirthdayParty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent birthdayIntent = new Intent(MainActivity.this, Birthday_Party_Activity.class);
                startActivity(birthdayIntent);
            }
        });

        binding.cvSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingIntent = new Intent(MainActivity.this, Settings_Activity.class);
                settingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(settingIntent);
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(MyApp.applyLanguageContext(newBase));
    }
}