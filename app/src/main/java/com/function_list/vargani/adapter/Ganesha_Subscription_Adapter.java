package com.function_list.vargani.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.function_list.vargani.model.Ganesha_SubscriptionModel;
import com.function_list.vargani.R;

import java.util.List;

public class Ganesha_Subscription_Adapter extends RecyclerView.Adapter<Ganesha_Subscription_Adapter.MyViewHolder> {

    Context context;
    List<Ganesha_SubscriptionModel> subscriptionList;

    public Ganesha_Subscription_Adapter(Context context, List<Ganesha_SubscriptionModel> subscriptionList) {
        this.context = context;
        this.subscriptionList = subscriptionList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.ganesha_subscription_single_details,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Ganesha_SubscriptionModel subscription = subscriptionList.get(position);
        holder.tv_name.setText(subscription.getName());
        holder.tv_amount.setText(subscription.getAmount());
        holder.tv_address.setText(subscription.getAddress());

    }

    @Override
    public int getItemCount() {
        return subscriptionList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name, tv_amount,tv_address;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.tv_name);
            tv_amount = itemView.findViewById(R.id.tv_amount);
            tv_address = itemView.findViewById(R.id.tv_address);

        }
    }

    public void updateSubscriptionList(List<Ganesha_SubscriptionModel> newList) {
        subscriptionList = newList;
        notifyDataSetChanged(); // Notify the adapter that the data has changed
    }
}
