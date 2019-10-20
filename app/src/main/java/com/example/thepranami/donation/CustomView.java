package com.example.thepranami.donation;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class CustomView extends RecyclerView.Adapter<CustomView.MyViewHolder> {
    ArrayList<DonorViewModel> donorViewModels;
    Context context;

    public CustomView(ArrayList<DonorViewModel> donorViewModels, Context context) {
        this.donorViewModels = donorViewModels;
        this.context = context;
    }
    class MyViewHolder extends RecyclerView.ViewHolder implements RecyclerView.RecyclerListener{

        TextView idTextView, amountTextView, nameTextView, otherTextView, addressTextView, mobileTextView;

        public MyViewHolder(View itemView){
            super(itemView);
            idTextView=(TextView)itemView.findViewById(R.id.idTextView_id);
            amountTextView=(TextView)itemView.findViewById(R.id.amountTextView_id);
            nameTextView=(TextView)itemView.findViewById(R.id.nameTextView_id);
            otherTextView=(TextView)itemView.findViewById(R.id.otherTextView_id);
            addressTextView=(TextView)itemView.findViewById(R.id.addressTextView_id);
            mobileTextView=(TextView)itemView.findViewById(R.id.mobileTextView_id);
        }

        @Override
        public void onViewRecycled(RecyclerView.ViewHolder holder) {
            //
        }
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_view, parent, false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String ID = donorViewModels.get(position).getId();
        String AMOUNT = donorViewModels.get(position).getDonorAmount();
        String NAME = donorViewModels.get(position).getDonorName();
        String OTHER = donorViewModels.get(position).getDonorOther();
        String ADDRESS = donorViewModels.get(position).getDonorAddress();
        String MOBILE = donorViewModels.get(position).getDonorMobile();
        holder.idTextView.setText(ID);
        holder.amountTextView.setText(AMOUNT);
        holder.nameTextView.setText(NAME);
        holder.otherTextView.setText(OTHER);
        holder.addressTextView.setText(ADDRESS);
        holder.mobileTextView.setText(MOBILE);
    }

    @Override
    public int getItemCount() {
        return donorViewModels.size();
    }
}
