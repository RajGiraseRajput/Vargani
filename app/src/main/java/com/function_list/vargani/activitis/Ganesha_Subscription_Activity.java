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

import com.function_list.vargani.model.Ganesha_SubscriptionModel;
import com.function_list.vargani.R;
import com.function_list.vargani.adapter.Ganesha_Subscription_Adapter;
import com.function_list.vargani.database.DataBaseHelper;
import com.function_list.vargani.databinding.ActivityGaneshaSubscriptionBinding;
import com.function_list.vargani.model.MyApp;
import com.function_list.vargani.utils.MyUtils;

import java.util.List;

public class Ganesha_Subscription_Activity extends AppCompatActivity {

    private MyUtils myUtils;
    ActivityGaneshaSubscriptionBinding binding;

    DataBaseHelper dataBaseHelper;

    List<Ganesha_SubscriptionModel> subscriptionList;

    Ganesha_Subscription_Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);

        binding = ActivityGaneshaSubscriptionBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        myUtils = new MyUtils(this);

//        setContentView(R.layout.activity_ganesha_subscription);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        dataBaseHelper = new DataBaseHelper(this);


//        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String name = binding.edNames.getText().toString();
//                String amount = binding.edAmounts.getText().toString();
//                String address = binding.edAddress.getText().toString();
//
//
//                if(!name.equals("") && !amount.equals("") && !address.equals("")){
//
//                    boolean isInserted = dataBaseHelper.added_Ganesha_Subscription(name,amount,address);
//
//                    if (isInserted){
//
//                        Toast.makeText(Ganesha_Subscription_Activity.this, getString(R.string.ganesha_subscription_success), Toast.LENGTH_SHORT).show();
//
//                        // Fetch updated list from the database
//                        subscriptionList = dataBaseHelper.getAllGaneshaSubscription();
//                        adapter.updateSubscriptionList(subscriptionList); // Update the adapter
//
//                        binding.edNames.setText("");
//                        binding.edAmounts.setText("");
//                        binding.edAddress.setText("");
//
//                    } else {
//                        Toast.makeText(Ganesha_Subscription_Activity.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
//                    }
//
//                }else{
//                    Toast.makeText(Ganesha_Subscription_Activity.this, getString(R.string.toast_ent_all_details), Toast.LENGTH_SHORT).show();
//                }
//
////                Toast.makeText(Ganesha_Subscription_Activity.this, name+", "+amount+", "+address, Toast.LENGTH_SHORT).show();
//            }
//        });

        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = binding.edNames.getText().toString();
                String amount = binding.edAmounts.getText().toString();
                String address = binding.edAddress.getText().toString();

                if (!name.equals("") && !amount.equals("") && !address.equals("")) {

                    boolean isInserted = dataBaseHelper.added_Ganesha_Subscription(name, amount, address);

                    if (isInserted) {

                        Toast.makeText(Ganesha_Subscription_Activity.this, getString(R.string.ganesha_subscription_success), Toast.LENGTH_SHORT).show();

                        // Initialize the adapter if it's null
                        updateRecyclerView();

                        binding.edNames.setText("");
                        binding.edAmounts.setText("");
                        binding.edAddress.setText("");

                    } else {
                        Toast.makeText(Ganesha_Subscription_Activity.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(Ganesha_Subscription_Activity.this, getString(R.string.toast_ent_all_details), Toast.LENGTH_SHORT).show();
                }
            }
        });


        binding.btnAllDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                subscriptionList = dataBaseHelper.getAllGaneshaSubscription();

                if (subscriptionList.size() > 0) {
                    binding.recyclerView.setVisibility(View.VISIBLE);
                    adapter = new Ganesha_Subscription_Adapter(Ganesha_Subscription_Activity.this, subscriptionList);
                    binding.recyclerView.setLayoutManager(new LinearLayoutManager(Ganesha_Subscription_Activity.this));
                    binding.recyclerView.setAdapter(adapter);

                    // Load and start the animation
                    Animation slideIn = AnimationUtils.loadAnimation(Ganesha_Subscription_Activity.this, R.anim.slide_in);
                    binding.recyclerView.startAnimation(slideIn);

                    binding.btnHide.setVisibility(View.VISIBLE);
                } else {
                    Toast.makeText(Ganesha_Subscription_Activity.this, getString(R.string.no_data), Toast.LENGTH_SHORT).show();
                }
            }
        });

        binding.nameVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myUtils.setupVoiceInput(Ganesha_Subscription_Activity.this,binding.edNames,getString(R.string.name_voice));
            }
        });

        binding.amountVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myUtils.setupVoiceInput(Ganesha_Subscription_Activity.this,binding.edAmounts,getString(R.string.amount_voice));

            }
        });

        binding.addressVoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myUtils.setupVoiceInput(Ganesha_Subscription_Activity.this,binding.edAddress,getString(R.string.address_voice));

            }
        });

        binding.btnHide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.btnHide.setVisibility(View.GONE);
                binding.recyclerView.setVisibility(View.GONE);

                Animation slideIn = AnimationUtils.loadAnimation(Ganesha_Subscription_Activity.this, R.anim.slide_in);
                binding.recyclerView.startAnimation(slideIn);

            }
        });

    }
    private void updateRecyclerView(){
        if (adapter == null) {
            subscriptionList = dataBaseHelper.getAllGaneshaSubscription();
            adapter = new Ganesha_Subscription_Adapter(Ganesha_Subscription_Activity.this, subscriptionList);
            binding.recyclerView.setLayoutManager(new LinearLayoutManager(Ganesha_Subscription_Activity.this));
            binding.recyclerView.setAdapter(adapter);
        } else {
            // Fetch updated list from the database and update the adapter
            subscriptionList = dataBaseHelper.getAllGaneshaSubscription();
            adapter.updateSubscriptionList(subscriptionList);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(MyApp.applyLanguageContext(newBase));
    }
}