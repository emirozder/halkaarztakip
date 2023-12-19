package com.example.halkaarztakip;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private Context context;
    private List<DataClass> dataList;

    public MyAdapter(Context context, List<DataClass> dataList) {
        this.context = context;
        this.dataList = dataList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.recTitle.setText("#" + dataList.get(position).getHisseTitle());
        holder.recPrice.setText(dataList.get(position).getHissePrice() + " ₺");
        holder.recLot.setText(dataList.get(position).getHisseLot() + "\nLOT");

        holder.recCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("Title", dataList.get(holder.getAdapterPosition()).getHisseTitle());
                intent.putExtra("Price", dataList.get(holder.getAdapterPosition()).getHissePrice());
                intent.putExtra("Lot", dataList.get(holder.getAdapterPosition()).getHisseLot());
                intent.putExtra("Note", dataList.get(holder.getAdapterPosition()).getHisseNot());
                intent.putExtra("Key", dataList.get(holder.getAdapterPosition()).getKey());

                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    public void searchDataList(ArrayList<DataClass> searchList){
        dataList = searchList;
        notifyDataSetChanged();
    }
}

class MyViewHolder extends RecyclerView.ViewHolder{

    TextView recTitle, recPrice, recLot;
    CardView recCard;


    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        recTitle = itemView.findViewById(R.id.recTitle);
        recPrice = itemView.findViewById(R.id.recPrice);
        recLot = itemView.findViewById(R.id.recLot);
        recCard = itemView.findViewById(R.id.RV_item_card1);
    }
}
