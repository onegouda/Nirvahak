package com.example.nirvahak;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyLoadAdapter extends RecyclerView.Adapter<MyLoadAdapter.MyViewHolder> {

    Context context;
    ArrayList<MyLoads> myLoadsArrayList;

    public MyLoadAdapter(Context context, ArrayList<MyLoads> myLoadsArrayList) {
        this.context = context;
        this.myLoadsArrayList = myLoadsArrayList;
    }

    @NonNull
    @Override
    public MyLoadAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.myloadrv,parent,false);


        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyLoadAdapter.MyViewHolder holder, int position) {

        MyLoads myloads = myLoadsArrayList.get(position);

        holder.LoadSource1.setText(myloads.LoadSource);
        holder.LoadDestination1.setText(myloads.LoadDestination);
        holder.LoadCategory1.setText(myloads.LoadCategory);
        holder.LoadTonnage1.setText(String.valueOf(myloads.LoadTonnage));
        holder.LoadRent1.setText(String.valueOf(myloads.LoadRent));



    }

    @Override
    public int getItemCount() {
        return myLoadsArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView LoadSource1,LoadDestination1,LoadCategory1,LoadTonnage1,LoadRent1;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            LoadSource1=itemView.findViewById(R.id.LoadSource1);
            LoadDestination1=itemView.findViewById(R.id.LoadDestination1);
            LoadCategory1=itemView.findViewById(R.id.LoadCategory1);
            LoadTonnage1=itemView.findViewById(R.id.LoadTonnage1);
            LoadRent1=itemView.findViewById(R.id.LoadRent1);






        }
    }

}
