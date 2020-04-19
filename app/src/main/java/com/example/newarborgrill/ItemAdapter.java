package com.example.newarborgrill;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.ItemViewHolder> {

    private List<Item>items;
    private int rowLayout;
    private Context context;

    public ItemAdapter(List<Item>items, int rowLayout, Context context){
        this.items = items;
        this.rowLayout  = rowLayout;
        this.context = context;
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder{
        LinearLayout itemLayout;
        TextView itemName;
        TextView itemDescription;
        TextView itemPrice;
        public ItemViewHolder(View V){
            super(V);
            itemLayout = (LinearLayout)V.findViewById(R.id.itemData);
            itemName = (TextView)V.findViewById(R.id.name);
            itemDescription = (TextView)V.findViewById(R.id.description);
            itemPrice = (TextView)V.findViewById(R.id.price);

        }
    }

    @Override
    public ItemAdapter.ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        View view = LayoutInflater.from(context).inflate(R.layout.list_item_data,parent,false);
        return  new ItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, final int position){
        holder.itemName.setText(items.get(position).getName());
        holder.itemDescription.setText(items.get(position).getDescription());
        holder.itemPrice.setText(items.get(position).getPrice());
    }

    @Override
    public  int getItemCount(){
        return  items.size();
    }
}
