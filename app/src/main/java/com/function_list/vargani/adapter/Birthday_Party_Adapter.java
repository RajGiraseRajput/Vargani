package com.function_list.vargani.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.function_list.vargani.R;
import com.function_list.vargani.model.Birthday_Party_List;

import java.util.List;

public class Birthday_Party_Adapter extends RecyclerView.Adapter<Birthday_Party_Adapter.MyViewHolde> {

    Context context;
    List<Birthday_Party_List> birthdayPartyLists;

    public Birthday_Party_Adapter(Context context, List<Birthday_Party_List> birthdayPartyLists){
        this.context = context;
        this.birthdayPartyLists = birthdayPartyLists;
    }

    @NonNull
    @Override
    public MyViewHolde onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.birthday_party_items,parent,false);
        return new MyViewHolde(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolde holder, int position) {

        Birthday_Party_List birthdayParty = birthdayPartyLists.get(position);
        holder.tv_name.setText(birthdayParty.getName());
        holder.tv_amount.setText(birthdayParty.getAmount());
        holder.tv_gift.setText(birthdayParty.getGift());
        holder.tv_address.setText(birthdayParty.getAddress());
    }

    @Override
    public int getItemCount() {
        return birthdayPartyLists.size();
    }

    public class MyViewHolde extends RecyclerView.ViewHolder {
        TextView tv_name,tv_amount, tv_gift, tv_address;

        public MyViewHolde(@NonNull View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.tv_name);
            tv_amount = itemView.findViewById(R.id.tv_amount);
            tv_gift = itemView.findViewById(R.id.tv_gift);
            tv_address = itemView.findViewById(R.id.tv_address);
        }
    }

    public void updateBirthdayPartyList(List<Birthday_Party_List> newList){
        birthdayPartyLists = newList;
        notifyDataSetChanged();
    }
}
