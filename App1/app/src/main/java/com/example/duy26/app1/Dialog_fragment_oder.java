package com.example.duy26.app1;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Dialog_fragment_oder extends DialogFragment {
    TextView name1,gia1;
    EditText soluong,ghichu;
    SQLiteHander sqLiteHander;
    private String id_bill,id_employess,id_food,dongia,ten;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_oderrbill, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ten = getArguments().getString("NAME_KEY");
        id_food= getArguments().getString("ID_KEY");
        dongia = getArguments().getString("PRINCE");
        id_bill = getArguments().getString("ID_BILL");

        name1 = (TextView)view.findViewById(R.id.id_fragmentenoder);
        gia1 = (TextView)view.findViewById(R.id.id_fragmentprince);
        soluong = (EditText)view.findViewById(R.id.id_fragmentnumber);
        ghichu = (EditText)view.findViewById(R.id.id_fragmentghichu);

        name1.setText(ten);
        gia1.setText(dongia);

        Button btn_confirm = (Button)view.findViewById(R.id.id_fragbtn_ok);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        Button btn_cancle = (Button)view.findViewById(R.id.id_fragbtn_cancle);
        btn_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sendata();
                getDialog().dismiss();
            }
        });

    }

    private void sendata() {
        String sl;
        sl = soluong.getText().toString().trim();
        Log.e("SLLLLLLLLLLLL",sl);
        String note = ghichu.getText().toString().trim();

        Log.e("SSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSSS",id_bill);

        sqLiteHander = new SQLiteHander(getContext());
        Data_oderdetails data_oderdetails = new Data_oderdetails();

                data_oderdetails.setFood(ten);
                data_oderdetails.setId_food(id_food);
                data_oderdetails.setNumber_details(sl);
                data_oderdetails.setPrince(dongia);
                data_oderdetails.setId_bill(id_bill);

                sqLiteHander.addTEAM(data_oderdetails);

                Log.e("ADD BILL_________________", String.valueOf(data_oderdetails));
        Toast.makeText(getContext(),"Thành Công",Toast.LENGTH_SHORT).show();
                soluong.setText("1");
                ghichu.setText("");
    }
}
