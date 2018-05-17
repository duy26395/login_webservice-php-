package com.example.duy26.app1;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class Oder extends AppCompatActivity {
    private RecyclerView recyclerView;
    Connectionclass connectionclass;
    private LoginActivity loginActivity;
    ArrayList<Data_Food> data_food;
    SQLiteHander sqLiteHander;
    String ghichu,id_user,id_employess;
    String id_bill;
    Home_Eployess home_eployess;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oder);

        //nhan data id_employess
        SharedPreferences preferences = getSharedPreferences("ID_EMPLOYESS",MODE_PRIVATE);
        id_employess = preferences.getString("ID","");
        Log.e("++++++++++++++",id_employess);



        TextView name_user = (TextView)findViewById(R.id.id_odername);
        TextView phone_user = (TextView)findViewById(R.id.id_oderphone);
        final TextView address_user = (TextView)findViewById(R.id.id_oderaddress);

        Intent i = this.getIntent();
        id_user = i.getBundleExtra("DATA").getString("ID_USER");
        final String name = i.getBundleExtra("DATA").getString("NAME_KEY");
        String sdt = i.getBundleExtra("DATA").getString("PHONE_KEY");
        final String diachi = i.getBundleExtra("DATA").getString("ADDRESS_KEY");

        name_user.setText(name);
        phone_user.setText(sdt);
        address_user.setText(diachi);



        Button bill_details = (Button)findViewById(R.id.id_oderbill);
        bill_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),Bill_Details.class);
                intent.putExtra("KEY_NAME",name.toString().trim());
                intent.putExtra("KEY_ADDRESS",diachi.toString().trim());
                startActivity(intent);

            }
        });
        Button dinary = (Button)findViewById(R.id.id_oderdinary);
        dinary.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Oder.this, Dinary.class);
                intent.putExtra("KEY_NAME",name.toString());
                intent.putExtra("ID_USER",id_user);
                intent.putExtra("DIACHI",diachi.toString());
                startActivity(intent);
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.id_oderRecycle);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new StaggeredGridLayoutManager(3,LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        data_food = new ArrayList<Data_Food>();
        SyncData orderData = new SyncData();
        orderData.execute("");

        create_bill();
    }

    private void create_bill() {


        //      tao bil;
        Connectionclass connectionclass = new Connectionclass();
        Connection connection = connectionclass.CONN();
        try {
            String query_bill = "INSERT INTO [Bill] "
                    + "(idemployess,iduser) VALUES"
                    + "(?,?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query_bill, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, id_employess);
            preparedStatement.setString(2,id_user );

            int affectedRows = preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next())
            {
                id_bill = resultSet.getString(1);
                Log.e("ID_____________BILL", String.valueOf(id_bill));
            } }catch (Exception  e) {}
    }


    private class SyncData extends AsyncTask<String, String, String>{

        @Override
        protected String doInBackground(String... strings) {
            connectionclass = new Connectionclass();
            Connection connection = connectionclass.CONN();

            try {
                String query = "SELECT idfood,NameFood,Details,Image,Prince FROM [Food]";
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery(query);
                if (resultSet != null) {
                    while (resultSet.next()) {
                        try {
                            data_food.add(new Data_Food(resultSet.getString("NameFood"),
                                    resultSet.getString("Details"),resultSet.
                                    getString("Image"),resultSet.getString("idfood")
                                    ,resultSet.getString("Prince")));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }

            }catch (Exception e)
            {

            }
            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            final Adapter_food adapter_food = new Adapter_food(data_food, Oder.this, new CustomItemClickListener() {
                @Override
                public void onItemClick(Data user, int position) {

                }
                @Override
                public void onClick(Data_Food data_food, int position) {
                    Toast.makeText(getApplicationContext(),""+data_food.getName_food(),Toast.LENGTH_SHORT).show();

                    Bundle bundle = new Bundle();
                    bundle.putString("ID_BILL",id_bill);
                    bundle.putString("ID_KEY", data_food.getIdfood());
                    bundle.putString("NAME_KEY",data_food.getName_food());
                    bundle.putString("PHONE_KEY",data_food.getDetails());
                    bundle.putString("ADDRESS_KEY",data_food.getImage());
                    bundle.putString("PRINCE",data_food.getGia());
                    Dialog_fragment_oder fragment_oder = new Dialog_fragment_oder();
                    fragment_oder.setArguments(bundle);

                    FragmentManager fm = getSupportFragmentManager();
                    fragment_oder.show(fm,null);


                }

            });
            recyclerView.setAdapter(adapter_food);
            EditText editText = (EditText)findViewById(R.id.id_odersearch);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    adapter_food.getFilter().filter(s);

                }
            });
            
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
                return true;
            case R.id.navigation_notifications:
                Logout();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    private void about() {
        Fragment_about fragment_about = new Fragment_about();
        android.app.FragmentManager fragmentManager = getFragmentManager();
        fragment_about.show(fragmentManager,null);
    }
    private void Logout(){
        SQLiteHander sqLiteHander = new SQLiteHander(getApplicationContext());
        sqLiteHander.deleteall();
        SessionManager sessionManager;
        sessionManager = new SessionManager(getApplicationContext());
        sessionManager.setLogin(false);
        SharedPreferences preferences = getSharedPreferences("ID_EMPLOYESS", 0);
        preferences.edit().remove("ID").commit();
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);

    }
}
