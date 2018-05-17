package com.example.duy26.app1;

import android.app.Application;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import static android.content.Context.*;
import static android.widget.Toast.LENGTH_LONG;
import static android.widget.Toast.LENGTH_SHORT;

public class LoginActivity extends AppCompatActivity{
    Button login,signup;
    EditText phone, password,id;
    private boolean success = false;
    Connectionclass connectionclass;
    private ArrayList<Data_User> datalist;

    private SessionManager session;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        login = (Button) findViewById(R.id.btn_login);
        phone = (EditText) findViewById(R.id.idphone);
        password = (EditText) findViewById(R.id.idpass);

        session = new SessionManager(getApplicationContext());
        if (session.isLoggedIn()) {
            // User is already logged in. Take him to main activity
            Intent intent = new Intent(LoginActivity.this, Home_Eployess.class);
            startActivityForResult(intent,1);
            finish();
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Checklogin checklogin = new Checklogin();
                checklogin.execute("");
            }
        });
    }

    private class Checklogin extends AsyncTask<String, String, String>{
        ProgressDialog dialog;

        String msg = "Loading....!";

        @Override
        protected void onPreExecute() //Starts the progress dailog
        {
            dialog = new ProgressDialog(LoginActivity.this);
            dialog.setMessage(msg);
            dialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String usernam = phone.getText().toString();
            String passwordd = password.getText().toString();


            datalist = new ArrayList<Data_User>();
            try {
                if (usernam.trim().equals("") || passwordd.trim().equals("")) {
                    success = false;
                    msg = "nhap day du";
                } else {
                    try {
                        connectionclass = new Connectionclass();
                        Connection connection = connectionclass.CONN();
                        if (connection == null) {
                            success = false;
                        } else {
                            Log.e("QQQQQQQQQQQQQQQQQ", "connect");
                            String query = "SELECT * FROM [Employess] WHERE Phone_Employess= '" + usernam.toString() + "' " +
                                    "AND Password_employess = '" + passwordd.toString() + "' ";
                            Statement statement = connection.createStatement();
                            ResultSet resultSet = statement.executeQuery(query);
                            if (resultSet.next()) {
                                try {
                                    session.setLogin(true);
                                    datalist.add(new Data_User(resultSet.getString("idemployess"),
                                            resultSet.getString("Name_employess")));

                                    success = true;
                                    msg = "Login success";


                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            } else {
                                msg = "found";
                                success = false;
                            }

                        }

                    } catch (Exception e) { }
                }
            }catch (Exception e) {
                e.printStackTrace();
                Writer write = new StringWriter();
                e.printStackTrace(new PrintWriter(write));
                msg = write.toString();
                success = false;
            }
            return msg;
            }

        @Override
        protected void onPostExecute(String msg) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            if(success ==false) {}
            else
            {
                try {

                    Intent intent = new Intent(getApplicationContext(),Home_Eployess.class);
                    startActivity(intent);

                    SharedPreferences preferences = getSharedPreferences("ID_EMPLOYESS",MODE_PRIVATE);
                    SharedPreferences.Editor editor =  preferences.edit();
                    editor.putString("ID",datalist.get(0).getId());
                    editor.commit();

                } catch (Exception e) {

                }
            }
        }
    }

}
