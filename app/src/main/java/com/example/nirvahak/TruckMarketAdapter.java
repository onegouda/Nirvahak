package com.example.nirvahak;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TruckMarketAdapter extends RecyclerView.Adapter<TruckMarketAdapter.MyViewHolder> {

    Context context;
    ArrayList<TruckMarket1>truckMarket1ArrayList;

    public TruckMarketAdapter(Context context, ArrayList<TruckMarket1> truckMarket1ArrayList ) {
        this.context = context;
        this.truckMarket1ArrayList = truckMarket1ArrayList;

    }

    @NonNull
    @Override
    public TruckMarketAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.my_truck_rview,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull TruckMarketAdapter.MyViewHolder holder, int position) {
        TruckMarket1 truckMarket1=truckMarket1ArrayList.get(position);
        holder.TruckCategory.setText(truckMarket1.TruckCategory1);
        holder.TruckRegisterNo.setText(truckMarket1.TruckRegisterNo1);
        holder.TruckTonnage.setText(truckMarket1.TruckTonnage1);
        holder.Truck.setText(truckMarket1.Truck1);

        holder.btnAuct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile=truckMarket1.TruckOwnerNo1;
                String call="tel:"+mobile.trim();
                Intent intent=new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(call));
                context.startActivity(intent);
            }
        });
    }




    @Override
    public int getItemCount() {
        return truckMarket1ArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        TextView TruckCategory,TruckRegisterNo,TruckTonnage,Truck;
        Button btnAuct;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            TruckCategory=itemView.findViewById(R.id.TruckCategory);
            TruckRegisterNo=itemView.findViewById(R.id.TruckRegisterNo);
            TruckTonnage=itemView.findViewById(R.id.TruckTonnage);
            Truck=itemView.findViewById(R.id.Truck);
            btnAuct=itemView.findViewById(R.id.btnAuct);

        }
    }
}
