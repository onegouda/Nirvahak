package com.example.nirvahak;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyTruckAdapter extends RecyclerView.Adapter<MyTruckAdapter.MyViewHolder> {

    Context context;
    ArrayList<MyTruck1>myTruck1ArrayList;

    public MyTruckAdapter(Context context, ArrayList<MyTruck1> myTruck1ArrayList) {
        this.context = context;
        this.myTruck1ArrayList = myTruck1ArrayList;
    }

    @NonNull
    @Override
    public MyTruckAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.mytruck_recycler_view,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyTruckAdapter.MyViewHolder holder, int position) {
        MyTruck1 myTruck1=myTruck1ArrayList.get(position);
        holder.TruckCategory2.setText(myTruck1.TruckCategory);
        holder.TruckRegisterNo2.setText(myTruck1.TruckRegisterNo);
        holder.TruckTonnage2.setText(myTruck1.TruckTonnage);
        holder.Truck2.setText(myTruck1.Truck);
    }

    @Override
    public int getItemCount() {
        return myTruck1ArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView TruckCategory2,TruckRegisterNo2,TruckTonnage2,Truck2;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            TruckCategory2=itemView.findViewById(R.id.TruckCategory2);
            TruckRegisterNo2=itemView.findViewById(R.id.TruckRegisterNo2);
            TruckTonnage2=itemView.findViewById(R.id.TruckTonnage2);
            Truck2=itemView.findViewById(R.id.Truck2);






        }
    }
}
