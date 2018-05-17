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
import android.widget.TextView;

import java.util.ArrayList;


public class MyAppdater extends  RecyclerView.Adapter<MyAppdater.Viewholder> implements Filterable,CustomItemClickListener{

    private ArrayList<Data> data;
    private ArrayList<Data> filteredUserList;
    private Context context;
    private CustomItemClickListener customItemClickListener;

    public MyAppdater(ArrayList<Data> arrayList, Context context,CustomItemClickListener customItemClickListener) {
        this.data = arrayList;
        this.filteredUserList = arrayList;
        this.context = context;
        this.customItemClickListener =  customItemClickListener;
    }

    @NonNull
    @Override
    public Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemview = layoutInflater.inflate(R.layout.item,parent,false);
        final Viewholder viewholder = new Viewholder(itemview);
        itemview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customItemClickListener.onItemClick(filteredUserList.get(viewholder.getAdapterPosition()),
                        viewholder.getAdapterPosition());
            }
        });
        return viewholder;
//        return new Viewholder( itemview);
    }

    @Override
    public void onBindViewHolder(@NonNull Viewholder holder, int position) {

        holder.textname.setText(filteredUserList.get(position).getName());
        holder.textphone.setText(filteredUserList.get(position).getPhonenumber());
        holder.textaddress.setText(filteredUserList.get(position).getAddress());
        holder.textemail.setText(filteredUserList.get(position).getEmail());
    }

    @Override
    public int getItemCount() {

        Log.e("DDDDDDDDDDDDDD", String.valueOf(filteredUserList));
        return filteredUserList.size();
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String search = constraint.toString();
                if (search.isEmpty()) {
                    filteredUserList = data;
                } else {
                    ArrayList<Data> dataArrayList = new ArrayList<>();
                    for (Data user : data) {
                        if (user.getPhonenumber().toLowerCase().contains(constraint)) {

                            dataArrayList.add(user);
                        }
                    }
                    filteredUserList = dataArrayList;
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = filteredUserList;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                filteredUserList = (ArrayList<Data>) results.values;
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

    public class Viewholder extends RecyclerView.ViewHolder
    {
        TextView textname,textphone,textemail,textaddress;

        public Viewholder(View itemView) {
            super(itemView);
            textname = (TextView)itemView.findViewById(R.id.idten);
            textphone = (TextView)itemView.findViewById(R.id.idsdt);
            textemail = (TextView)itemView.findViewById(R.id.idemail);
            textaddress = (TextView)itemView.findViewById(R.id.idaddress);

        }
    }
}
