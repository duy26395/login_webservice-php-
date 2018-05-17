package com.example.duy26.app1;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class Adapter_bill extends RecyclerView.Adapter<Adapter_bill.ViewHolder>{

    public List<Data_oderdetails> oderdetails;
    public Context context;
    SQLiteHander sqLiteHander;

    public Adapter_bill(List<Data_oderdetails> oderdetails, Context context) {
        if (oderdetails == null) {
            this.oderdetails = new ArrayList<>();
        }
        this.oderdetails = (oderdetails);
        this.context = context;
        this.sqLiteHander = new SQLiteHander(context.getApplicationContext());
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inlater = LayoutInflater.from(parent.getContext());
        View view = inlater.inflate(R.layout.item_bill_recycle,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.bindData(oderdetails.get(position));

    }



    @Override
    public int getItemCount() {
        return oderdetails == null ? 0 : oderdetails.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private TextView mTvName,mIntro,dongia;



        ViewHolder(View view) {
            super(view);

            mTvName = (TextView) view.findViewById(R.id.bill_food);
            mIntro = (TextView)view.findViewById(R.id.bill_sl);
            dongia = (TextView)view.findViewById(R.id.bill_gia);



        }
        void bindData(Data_oderdetails data_tt) {


            mTvName.setText(data_tt.getFood());
            mIntro.setText(data_tt.getNumber_details());
            dongia.setText(data_tt.getToatal()+",000");

        }

    }

}
