package com.example.duy26.app1;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Home_Eployess extends AppCompatActivity {

    private ArrayList<Data> datalist;
    private RecyclerView recyclerView;
    private boolean success = false;
    private Connectionclass connectionclass;
    private Button signin_user;
    private ArrayList<Data_oderdetails> data_oderdetails;
    SessionManager sessionManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hom_employess);

        recyclerView = (RecyclerView) findViewById(R.id.idrecyle);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        connectionclass = new Connectionclass();
        datalist = new ArrayList<Data>();

        signin_user = (Button)findViewById(R.id.btn_uer);
        signin_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home_Eployess.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
        SyncData orderData = new SyncData();
        orderData.execute("");
        Log.e("ahihihi", String.valueOf(datalist));


    }

    private class SyncData extends AsyncTask<String, String, String>{


        ProgressDialog dialog;
        String msg = "Internet/DB_Credentials/Windows_FireWall_TurnOn Error, See Android Monitor in the bottom For details!";

        @Override
        protected void onPreExecute() //Starts the progress dailog
        {
            dialog = ProgressDialog.show(Home_Eployess.this,"Loading","Loading! Please Wait...",true);
        }

        @Override
        protected String doInBackground(String... strings) {

            try
            {
                Connection connection = connectionclass.CONN();
                if (connection == null) {
                    success = false;
                } else {
                    String query = "SELECT iduser,Name,PhoneNumber,Address,Email FROM [User]";
                    Statement statement = connection.createStatement();
                    ResultSet resultSet = statement.executeQuery(query);
                    if (resultSet != null) {
                        while (resultSet.next()) {
                            try {
                                datalist.add(new Data(resultSet.getString("iduser"),resultSet.getString("Name"),
                                        resultSet.getString("PhoneNumber"),resultSet.
                                        getString("Address"),resultSet.getString("Email")));

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        msg = "found";
                        success = true;
                    } else {
                        msg = "No data found";
                        success = false;
                    }
                }

            }catch(Exception e)

            {
                e.printStackTrace();
                Writer write = new StringWriter();
                e.printStackTrace(new PrintWriter(write));
                msg = write.toString();
                success = false;
            }
            return  msg;
        }

        @Override
        protected void onPostExecute(String msg) {
            dialog.dismiss();
            if(success ==false)

            {

            }
            else

                try {
                    final MyAppdater myAppdater = new MyAppdater(datalist, Home_Eployess.this, new CustomItemClickListener() {
                        @Override
                        public void onItemClick(Data user, int position) {
                            Toast.makeText(getApplicationContext(),""+user.getName(),Toast.LENGTH_SHORT).show();
                            Intent intent =new Intent(getApplicationContext(),Oder.class);
                            Bundle bundle = new Bundle();
                            bundle.putString("ID_USER",user.getId());
                            bundle.putString("NAME_KEY",user.getName());
                            bundle.putString("PHONE_KEY",user.getPhonenumber());
                            bundle.putString("ADDRESS_KEY",user.Address);
                            intent.putExtra("DATA",bundle);
                            startActivity(intent);                        }

                        @Override
                        public void onClick(Data_Food data_food, int position) {

                        }

                    });
                    recyclerView.setAdapter(myAppdater);

//                    Log.e("HOME____EMPLOYEES_BEFORE DELETE", String.valueOf(data_oderdetails));
//                    SQLiteHander sqLiteHander = new SQLiteHander(getBaseContext());
//                    sqLiteHander.delete(data_oderdetails);
//                    Log.e("HOME____EMPLOYEES", String.valueOf(data_oderdetails));
//
                    EditText editText = (EditText)findViewById(R.id.hi);
                    editText.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            myAppdater.getFilter().filter(s);
                        }
                    });
//                    searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//                        @Override
//                        public boolean onQueryTextSubmit(String query) {
//                            return false;
//                        }
//
//                        @Override
//                        public boolean onQueryTextChange(String newText) {
//                            myAppdater.getFilter().filter(newText);
//                            return true;
//                        }
//                    });

                } catch (Exception e) {

                }
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.navigation_home:
                about();
                return true;
            case R.id.navigation_dashboard:
                // Làm cái gì đó
                return true;
            case R.id.navigation_notifications:
                Logout();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void about() {
        Fragment_about fragment_about = new Fragment_about();
        FragmentManager fragmentManager = getFragmentManager();
        fragment_about.show(fragmentManager,null);
    }
    private void Logout(){
        SQLiteHander sqLiteHander = new SQLiteHander(getApplicationContext());
        sqLiteHander.deleteall();
        sessionManager = new SessionManager(getApplicationContext());
        sessionManager.setLogin(false);
        SharedPreferences preferences = getSharedPreferences("ID_EMPLOYESS", 0);
        preferences.edit().remove("ID").commit();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);

    }

}
