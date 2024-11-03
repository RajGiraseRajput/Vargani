package com.function_list.vargani.activitis;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.function_list.vargani.R;
import com.function_list.vargani.adapter.Birthday_Party_Adapter;
import com.function_list.vargani.database.DataBaseHelper;
import com.function_list.vargani.databinding.ActivityBirthdayPartyBinding;
import com.function_list.vargani.model.Birthday_Party_List;
import com.function_list.vargani.model.MyApp;
import com.function_list.vargani.utils.MyUtils;

import java.util.List;

public class Birthday_Party_Activity extends AppCompatActivity {

    private MyUtils myUtils;
    ActivityBirthdayPartyBinding binding;
    DataBaseHelper dataBaseHelper;
    List<Birthday_Party_List> birthdayPartyLists;
    Birthday_Party_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityBirthdayPartyBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        myUtils = new MyUtils(this);

//        setContentView(R.layout.activity_birthday_party);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dataBaseHelper = new DataBaseHelper(this);

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name = binding.edNames.getText().toString();
                String amount = binding.edAmounts.getText().toString();
                String gift = binding.edGift.getText().toString();
                String address = binding.edAddress.getText().toString();

                if(!name.equals("") && !amount.equals("") && !gift.equals("") && !address.equals("")){

                    if (gift.equalsIgnoreCase("yes") || gift.equalsIgnoreCase("no")){
                        boolean isInserted = dataBaseHelper.added_Birthday_Party(name,amount,gift,address);

                        if (isInserted){
                            Toast.makeText(Birthday_Party_Activity.this, getString(R.string.marriage_add_success), Toast.LENGTH_SHORT).show();

                            updateRecyclerView();

                            binding.edNames.setText("");
                            binding.edAmounts.setText("");
                            binding.edGift.setText("");
                            binding.edAddress.setText("");

                        }else{
                            Toast.makeText(Birthday_Party_Activity.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(Birthday_Party_Activity.this, getString(R.string.gift_yes_no), Toast.LENGTH_SHORT).show();
                    }


                }else{
                    Toast.makeText(Birthday_Party_Activity.this, getString(R.string.provide_all_details), Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.btnAllDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                birthdayPartyLists = dataBaseHelper.getAllBirthdayPartyList();

                if (birthdayPartyLists.size() > 0){
                    binding.recyclerView.setVisibility(View.VISIBLE);
                    adapter = new Birthday_Party_Adapter(Birthday_Party_Activity.this,birthdayPartyLists);
                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(Birthday_Party_Activity.this));
                    binding.recyclerView.setAdapter(adapter);

                    Animation slindeIn = AnimationUtils.loadAnimation(Birthday_Party_Activity.this,R.anim.slide_in);
                    binding.recyclerView.startAnimation(slindeIn);

                    binding.btnHide.setVisibility(View.VISIBLE);
                }else{
                    Toast.makeText(Birthday_Party_Activity.this, getString(R.string.no_data), Toast.LENGTH_SHORT).show();
                }

            }
        });

        binding.nameVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myUtils.setupVoiceInput(Birthday_Party_Activity.this,binding.edNames,getString(R.string.name_voice));
            }
        });

        binding.amountVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myUtils.setupVoiceInput(Birthday_Party_Activity.this,binding.edAmounts,getString(R.string.amount_voice));
            }
        });

        binding.giftVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myUtils.setupVoiceInput(Birthday_Party_Activity.this,binding.edGift,getString(R.string.gift_voice));
            }
        });

        binding.addressVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myUtils.setupVoiceInput(Birthday_Party_Activity.this,binding.edAddress,getString(R.string.address_voice));
            }
        });

        binding.btnHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.btnHide.setVisibility(View.GONE);
                binding.recyclerView.setVisibility(View.GONE);

            }
        });
    }

    private void updateRecyclerView() {
        if (adapter == null) {
            birthdayPartyLists = dataBaseHelper.getAllBirthdayPartyList();
            adapter = new Birthday_Party_Adapter(Birthday_Party_Activity.this, birthdayPartyLists);
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(Birthday_Party_Activity.this));
            binding.recyclerView.setAdapter(adapter);
        } else {
            birthdayPartyLists = dataBaseHelper.getAllBirthdayPartyList();
            adapter.updateBirthdayPartyList(birthdayPartyLists);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(MyApp.applyLanguageContext(newBase));
    }

}