package com.example.duy26.app1;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter_dinary extends RecyclerView.Adapter<Adapter_dinary.ViewHolder> {
    private ArrayList<Data_Dinary> data_dinaries;
    private Context context;

    public Adapter_dinary(ArrayList<Data_Dinary> data_dinaries, Context context) {
        this.data_dinaries = data_dinaries;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dinary, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bindData(data_dinaries.get(position));
        final String id_bill = data_dinaries.get(position).getId_bill();
        holder.setItemclick(new Onitemclick() {
            @Override
            public void onItem(View v, int post) {
                Bundle bundle = new Bundle();
                bundle.putString("ID_BILL",id_bill);

                Fragment_dinary fragment_dinary = new Fragment_dinary();
                fragment_dinary.setArguments(bundle);

                fragment_dinary.show(((AppCompatActivity)context).getFragmentManager(),null);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data_dinaries.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView date;
        private Onitemclick itemclick;
        public ViewHolder(View itemView) {
            super(itemView);
            date = (TextView) itemView.findViewById(R.id.dinary_date);

        }
        void bindData(Data_Dinary data_tt) {
            date.setText(data_tt.getDate());
            itemView.setOnClickListener(this);
        }

        public void setItemclick(Onitemclick itemclick) {
            this.itemclick = itemclick;
        }


        @Override
        public void onClick(View v) {
            itemclick.onItem(v,getAdapterPosition());
        }
    }
}
