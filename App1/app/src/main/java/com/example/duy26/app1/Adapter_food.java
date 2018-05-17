package com.example.duy26.app1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class Adapter_food extends RecyclerView.Adapter<Adapter_food.viewholder>implements Filterable,CustomItemClickListener
{
    private ArrayList<Data_Food> data_foods;
    private ArrayList<Data_Food> fUserList;
    private Context context;
    private CustomItemClickListener customItemClickListener;

    public Adapter_food(ArrayList<Data_Food> arrayList, Context context,CustomItemClickListener customItemClickListener) {
        this.data_foods = arrayList;
        this.fUserList = arrayList;
        this.context = context;
        this.customItemClickListener =  customItemClickListener;
    }
    @NonNull
    @Override
    public viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemview = layoutInflater.inflate(R.layout.item_food,parent,false);
        final Adapter_food.viewholder holder1 = new Adapter_food.viewholder(itemview);
        itemview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customItemClickListener.onClick(fUserList.get(holder1.getAdapterPosition()),
                        holder1.getAdapterPosition());
            }
        });
        return holder1;
    }

    @Override
    public void onBindViewHolder(@NonNull viewholder holder, int position) {
        holder.txtname.setText(fUserList.get(position).getName_food());
        Glide.with(context)
                .load(fUserList.get(position).getImage())
                .into(holder.imageView);

    }

    @Override
    public int getItemCount() {
        Log.e("DDDDDDDDDDDDDDiiiiiiiiiiiiiiii", String.valueOf(fUserList));
        return fUserList.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {

                String search = constraint.toString();
                if (search.isEmpty()) {
                    fUserList = data_foods;
                } else {
                    ArrayList<Data_Food> dataArrayList = new ArrayList<>();
                    for (Data_Food data_food : data_foods) {
                        if (data_food.getName_food().toLowerCase().contains(constraint)) {

                            dataArrayList.add(data_food);
                        }
                    }
                    fUserList = dataArrayList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = fUserList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                fUserList = (ArrayList<Data_Food> )results.values;
                notifyDataSetChanged();

            }
        };
    }

    @Override
    public void onItemClick(Data user, int position) {

    }

    @Override
    public void onClick(Data_Food data_food, int position) {

    }


    public class viewholder extends RecyclerView.ViewHolder {
        TextView txtname;
        ImageView imageView;
        public viewholder(View itemView) {
            super(itemView);

            txtname =  (TextView)itemView.findViewById(R.id.id_itemmon);
            imageView = (ImageView)itemView.findViewById(R.id.id_imgitem);

        }
    }

}
