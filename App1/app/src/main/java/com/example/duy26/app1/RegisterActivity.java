package com.example.duy26.app1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

public class RegisterActivity extends AppCompatActivity {

    private EditText name,phone,password,address,email;
    ProgressDialog dialog;
    Connectionclass connectionclass;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        name = (EditText)findViewById(R.id.rgname);
        address = (EditText)findViewById(R.id.rgadress);
        email = (EditText)findViewById(R.id.rgemail);
        password = (EditText)findViewById(R.id.rgpass);
        phone = (EditText)findViewById(R.id.rgphone);
        Button btn_regist = (Button) findViewById(R.id.btn_register);

        connectionclass = new Connectionclass();
        btn_regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Checksigup checksigup =  new Checksigup();
                checksigup.execute("");
            }
        });
        TextView txtlogin = (TextView)findViewById(R.id.idtext_btnlogin);
        txtlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });
    }

     class Checksigup extends AsyncTask<String, String, String> {
        boolean success = false;
        String msg = "";
        String name1 = name.getText().toString();
        String address1 = address.getText().toString();
        String email1 = email.getText().toString();
        String phone1 = phone.getText().toString();
        String pass = password.getText().toString();

        @Override
        protected void onPreExecute() {
            dialog = ProgressDialog.show(RegisterActivity.this, "Loangding", "Please wait....", true);
        }

        @Override
        protected String doInBackground(String... strings) {
            try
            {
                Connection connection = connectionclass.CONN();
                if (connection == null) {
                    success = false;
                    msg = "Kết nối thất bại, kiểm tra kết nối";
                }
                else if (name1.trim().equals("") || pass.trim().equals("") || phone1.trim().equals("")) {
                    success = false;
                    msg = "không bỏ trống các trường";

                }else {
                    String query = "SELECT * FROM [User] WHERE PhoneNumber= '" + phone1.toString() + "' " +
                            "OR Name = '" + name1.toString() + "' ";
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(query);
                    if (resultSet.next()) {
                        try {
                            success = false;
                            msg = " số điện thoại và tài khoản đã tồn tại";
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        String insertTableSQL = "INSERT INTO [User] "
                                + "(Name,Password,PhoneNumber,Address,Email) VALUES"
                                + "(?,?,?,?,?)";
                        try {
                            PreparedStatement preparedStatement = connection.prepareStatement(insertTableSQL);
                            preparedStatement.setString(1, name1);
                            preparedStatement.setString(3, phone1);
                            preparedStatement.setString(2, pass);
                            preparedStatement.setString(4, address1);
                            preparedStatement.setString(5, email1);

                            preparedStatement.executeUpdate();

                            success = true;
                            Log.e("AAAAAAAAAAAAA", String.valueOf(success));
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
//                        success = true;
                }

            }catch(Exception e){
                e.printStackTrace();
                Writer write = new StringWriter();
                e.printStackTrace(new PrintWriter(write));
                msg = write.toString();
                success = false;
            }
            return  msg;
        }

        @Override
        protected void onPostExecute(String s) {
            if (dialog.isShowing()) {
                dialog.dismiss();
                Log.e("AAAAAAAAA","DIALOG ON POST");
            }
            Log.e("cccccccccccccccccccc", String.valueOf(success));
            if(success ==false) {
                Toast.makeText(RegisterActivity.this,msg,Toast.LENGTH_LONG).show();

            }
            else
            {
                Toast.makeText(RegisterActivity.this, "successfully",Toast.LENGTH_SHORT).show();
                    Log.e("AAAAAAAAAAAAAAAAAAAAAAAAA", String.valueOf(success));
                    Intent intent = new Intent(RegisterActivity.this, Home_Eployess.class);
                    startActivityForResult(intent,1);
                    }

            }
        }

    }
