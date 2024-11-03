package com.function_list.vargani.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.function_list.vargani.R;
import com.function_list.vargani.model.Marriage_List_Model;

import java.util.List;

public class Marriage_List_Adapter extends RecyclerView.Adapter<Marriage_List_Adapter.MyViewHolder> {

    Context context;
    List<Marriage_List_Model> marriageList;
    String gender;

    public Marriage_List_Adapter(Context context, List<Marriage_List_Model> marriageList, String gender) {
        this.context = context;
        this.marriageList = marriageList;
        this.gender = gender;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.ganesha_subscription_single_details, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Marriage_List_Model marriageListModel = marriageList.get(position);
        holder.tv_name.setText(marriageListModel.getName());
        holder.tv_amount.setText(marriageListModel.getAmount());
        holder.tv_address.setText(marriageListModel.getAddress());
        if (gender.equals("Female")) {
            holder.ll_sarees.setVisibility(View.VISIBLE);
            holder.tv_sarees.setText(marriageListModel.getSarees());
        }
    }

    @Override
    public int getItemCount() {
        return marriageList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_name, tv_amount, tv_address, tv_sarees;
        LinearLayout ll_sarees;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_name = itemView.findViewById(R.id.tv_name);
            tv_amount = itemView.findViewById(R.id.tv_amount);
            tv_address = itemView.findViewById(R.id.tv_address);
            if (gender.equals("Female")) {
                ll_sarees = itemView.findViewById(R.id.ll_sarees);
                tv_sarees = itemView.findViewById(R.id.tv_saree);
            }
        }
    }

    public void updateMarriageList(List<Marriage_List_Model> newList){
        marriageList = newList;
        notifyDataSetChanged();
    }

}
