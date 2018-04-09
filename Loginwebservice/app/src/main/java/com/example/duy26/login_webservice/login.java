package com.example.duy26.login_webservice;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.duy26.login_webservice.app.AppConfig;
import com.example.duy26.login_webservice.app.AppController;
import com.example.duy26.login_webservice.helper.SQLiteHandler;
import com.example.duy26.login_webservice.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.android.volley.Request.Method;

public class login extends AppCompatActivity {
    private static  final String TAG = register.class.getSimpleName();
    private Button btnLogin;
    private Button btnRegister;
    private EditText inputemail;
    private EditText inputpassword;
    private ProgressDialog progressDialog;
    private SessionManager sessionManager;
    private SQLiteHandler db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputemail = (EditText)findViewById(R.id.email);
        inputpassword = (EditText)findViewById(R.id.password);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnRegister = (Button)findViewById(R.id.btnLinkToRegisterScreen);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);

        db = new SQLiteHandler(getApplicationContext());
        sessionManager = new SessionManager(getApplicationContext());

        if(sessionManager.isLoginIN()){
            Intent intent = new Intent(login.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = inputemail.getText().toString().trim();
                String password =  inputpassword.getText().toString().trim();

                if (!email.isEmpty() && !password.isEmpty())
                {
                    checklogin(email,password);
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"please try again",Toast.LENGTH_LONG).show();
                }
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),register.class);
                startActivity(intent);
                finish();
            }
        });
    }

    private void checklogin(final String email, final String password) {
        String tag_req = "req_login";
        progressDialog.setMessage("Login....");
        showDialog();
        StringRequest request = new StringRequest(Method.POST, AppConfig.URL_LOGIN, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                hidedialog();
                try {
                    JSONObject object = new JSONObject(response);
                    boolean error = object.getBoolean("error");

                    if (!error) {
                        sessionManager.setLogin(true);
                        JSONObject user = object.getJSONObject("user");

                        String uid = object.getString("uid");
                        String name = user.getString("name");
                        String email = user.getString("email");
                        String created_at = user
                                .getString("created_at");

                        db.adduser(name, email, uid, created_at);

                        Intent intent = new Intent(login.this, MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        String errorMsg = object.getString("Please try agin");
                        Toast.makeText(getApplicationContext(),
                                errorMsg, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        }, new ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "Login Error: " + error.getMessage());
                Toast.makeText(getApplicationContext(),
                        "Email or password wrong,again", Toast.LENGTH_LONG).show();
                progressDialog.dismiss();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String>  paran = new HashMap<String,String>();
                paran.put("email",email);
                paran.put("password",password);

                return paran;
            }
        };
        AppController.getmController().addToRequetsQueue(request,tag_req);

    }

    private void hidedialog() {
        if (!progressDialog.isShowing())
            progressDialog.dismiss();
    }

    private void showDialog() {
        if(!progressDialog.isShowing())progressDialog.show();
    }

}
