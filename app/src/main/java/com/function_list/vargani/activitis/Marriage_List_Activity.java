package com.function_list.vargani.activitis;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
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
import com.function_list.vargani.adapter.Marriage_List_Adapter;
import com.function_list.vargani.database.DataBaseHelper;
import com.function_list.vargani.databinding.ActivityMarriageListBinding;
import com.function_list.vargani.model.Marriage_List_Model;
import com.function_list.vargani.model.MyApp;
import com.function_list.vargani.utils.MyUtils;

import java.util.List;

public class Marriage_List_Activity extends AppCompatActivity {

    ActivityMarriageListBinding binding;
    DataBaseHelper dataBaseHelper;
    List<Marriage_List_Model> marriageList;
    Marriage_List_Adapter adapter;
    String gender;

    private MyUtils myUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityMarriageListBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        myUtils = new MyUtils(this);

//        setContentView(R.layout.activity_marriage_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dataBaseHelper = new DataBaseHelper(this);

        gender = getIntent().getStringExtra("gender");
//        Log.e("receivedIntent",maleIntent);

        if (gender.equals("Male")){
            binding.cvSarees.setVisibility(View.GONE);

        }

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String names = binding.edNames.getText().toString();
                String amounts = binding.edAmounts.getText().toString();
                String sarees = binding.edSaree.getText().toString();
                String addresses = binding.edAddress.getText().toString();


                if(!names.equals("") && !amounts.equals("") && !addresses.equals("")){
                    boolean isInserted = dataBaseHelper.added_Marriage_List(names,amounts,sarees,addresses);

                    if(isInserted){
                        Toast.makeText(Marriage_List_Activity.this, getString(R.string.marriage_add_success), Toast.LENGTH_SHORT).show();

                        updateRecyclerView();

                        binding.edNames.setText("");
                        binding.edAmounts.setText("");
                        if(!gender.equals("Male")) {
                            binding.edSaree.setText("");
                        }
                        binding.edAddress.setText("");

                    }else{
                        Toast.makeText(Marriage_List_Activity.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(Marriage_List_Activity.this, getString(R.string.provide_all_details), Toast.LENGTH_SHORT).show();
                }

            }
        });

        binding.btnAllDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                marriageList = dataBaseHelper.getAllMarriageList();

                if(marriageList.size() > 0){
                    binding.recyclerView.setVisibility(View.VISIBLE);
                    adapter = new Marriage_List_Adapter(Marriage_List_Activity.this,marriageList,gender);
                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(Marriage_List_Activity.this));

                    binding.recyclerView.setAdapter(adapter);

                    Animation slideIn = AnimationUtils.loadAnimation(Marriage_List_Activity.this,R.anim.slide_in);
                    binding.recyclerView.startAnimation(slideIn);

                    binding.btnHide.setVisibility(View.VISIBLE);
                }else{
                    Toast.makeText(Marriage_List_Activity.this, getString(R.string.no_data), Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.nameVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myUtils.setupVoiceInput(Marriage_List_Activity.this,binding.edNames,getString(R.string.name_voice));
            }
        });

        binding.amountVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myUtils.setupVoiceInput(Marriage_List_Activity.this,binding.edAmounts,getString(R.string.amount_voice));
            }
        });

        binding.sareeVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myUtils.setupVoiceInput(Marriage_List_Activity.this,binding.edSaree,getString(R.string.saree_voice));
            }
        });

        binding.addressVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myUtils.setupVoiceInput(Marriage_List_Activity.this,binding.edAddress,getString(R.string.address_voice));
            }
        });

        binding.btnHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.btnHide.setVisibility(View.GONE);
                binding.recyclerView.setVisibility(View.GONE);

                Animation slideIn = AnimationUtils.loadAnimation(Marriage_List_Activity.this,R.anim.slide_in);
                binding.recyclerView.startAnimation(slideIn);
            }
        });

    }
    private void updateRecyclerView(){
        if(adapter == null){
            marriageList = dataBaseHelper.getAllMarriageList();
            adapter = new Marriage_List_Adapter(Marriage_List_Activity.this,marriageList,gender);
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(Marriage_List_Activity.this));
            binding.recyclerView.setAdapter(adapter);
        }else{
            // Fetch updated list from the database
            marriageList = dataBaseHelper.getAllMarriageList();
            adapter.updateMarriageList(marriageList); // update the adapter

        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(MyApp.applyLanguageContext(newBase));
    }
}