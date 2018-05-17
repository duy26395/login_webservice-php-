package com.example.duy26.app1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import static java.sql.Types.NULL;

public class Bill_Details extends AppCompatActivity {
    int id_bill;
    String id_employess;
    RecyclerView recyclerView;
    ArrayList<Data_oderdetails> data_oderdetails;
    Adapter_bill adapter_bill;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill__details);


        final Intent intent = getIntent();
        String ten = intent.getStringExtra("KEY_NAME");
        String diachi = intent.getStringExtra("KEY_ADDRESS");

        TextView ten1 = (TextView)findViewById(R.id.bill_name);
        TextView diachi1 = (TextView)findViewById(R.id.bill_address);
        TextView tong = (TextView)findViewById(R.id.toatalbill);

        final SQLiteHander sqLiteHander = new SQLiteHander(this.getApplicationContext());
        Log.e("IIIIIIIIIIIIIIIIII", String.valueOf(sqLiteHander.addAllValues()));
        final int sum = sqLiteHander.addAllValues();

        tong.setText(Integer.toString(sum)+",000");
        ten1.setText(ten);
        diachi1.setText(diachi);


        recyclerView = (RecyclerView)findViewById(R.id.id_recyclebill);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(linearLayoutManager);

        adapter_bill = new Adapter_bill(sqLiteHander.getallteam(),getApplicationContext());
        recyclerView.setAdapter(adapter_bill);

        Button oder_confirm = (Button)findViewById(R.id.oder_bill);
        oder_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data_oderdetails = (ArrayList<Data_oderdetails>) sqLiteHander.getallteam();

                if(data_oderdetails.size() == 0)
                {
                    getApplicationContext().getClassLoader();
                    Toast.makeText(getApplicationContext(),"Không có Dữ Liệu",Toast.LENGTH_LONG).show();
                }
                else {
                send_data_sever();
                sqLiteHander.deleteall();
                adapter_bill.notifyDataSetChanged();

                finish();
                onBackPressed();
                }

            }
        });


    }

    private void send_data_sever() {

        SQLiteHander sqLiteHander = new SQLiteHander(this);
       sqLiteHander.getallteam();
       data_oderdetails = (ArrayList<Data_oderdetails>) sqLiteHander.getallteam();

        Connectionclass connectionclass = new Connectionclass();

        Connection connection = connectionclass.CONN();
        try {
            String query_bill = "INSERT INTO [Bill_details] "
                    + "(idfood,number,Note,idbill,Total) VALUES"
                    + "(?,?,?,?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query_bill);

            for(int i=0;i< data_oderdetails.size();i++)
            {
                preparedStatement.setString(1,data_oderdetails.get(i).getId_food());
                preparedStatement.setString(2,data_oderdetails.get(i).number_details);
                preparedStatement.setString(3,data_oderdetails.get(i).getPrince());
                preparedStatement.setString(4,data_oderdetails.get(i).getId_bill());
                preparedStatement.setString(5,data_oderdetails.get(i).toatal);

                Log.e("DELETE_________ODERDETAILS", data_oderdetails.get(0).getId_bill());

                preparedStatement.execute();
            }
            preparedStatement.executeUpdate();
            Toast.makeText(getApplicationContext(),"Oder Thành Công",Toast.LENGTH_SHORT).show();

            } catch (Exception  e) {}

    }


}
