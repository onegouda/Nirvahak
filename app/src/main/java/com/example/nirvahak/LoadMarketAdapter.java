package com.example.nirvahak;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class LoadMarketAdapter extends RecyclerView.Adapter<LoadMarketAdapter.MyViewHolder> {

    Context context;
    ArrayList<LoadMarket1>loadMarket1ArrayList;

    public LoadMarketAdapter(Context context, ArrayList<LoadMarket1> loadMarket1ArrayList) {
        this.context = context;
        this.loadMarket1ArrayList = loadMarket1ArrayList;
    }

    @NonNull
    @Override
    public LoadMarketAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.loadmarketrecyclerview,parent,false);

        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LoadMarketAdapter.MyViewHolder holder, int position) {
        LoadMarket1 loadMarket1=loadMarket1ArrayList.get(position);

        holder.LoadCategory2.setText(loadMarket1.LoadCategory1);
        holder.LoadSource2.setText(loadMarket1.LoadSource1);
        holder.LoadDestination2.setText(loadMarket1.LoadDestination1);
        holder.LoadTonnage2.setText(loadMarket1.LoadTonnage1);
        holder.LoadRent2.setText(loadMarket1.LoadRent1);


        holder.btnBid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mobile1=loadMarket1.TransporterNo1;
                String call1="tel:"+mobile1.trim();
                Intent intent=new Intent(Intent.ACTION_CALL);
                intent.setData(Uri.parse(call1));
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return loadMarket1ArrayList.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{
            TextView LoadSource2,LoadDestination2,LoadCategory2,LoadTonnage2,LoadRent2;
            Button btnBid;



        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            LoadSource2=itemView.findViewById(R.id.LoadSource2);
            LoadDestination2=itemView.findViewById(R.id.LoadDestination2);
            LoadCategory2=itemView.findViewById(R.id.LoadCategory2);
            LoadTonnage2=itemView.findViewById(R.id.LoadTonnage2);
            LoadRent2=itemView.findViewById(R.id.LoadRent2);
            btnBid=itemView.findViewById(R.id.btnBid);


        }
    }

}
