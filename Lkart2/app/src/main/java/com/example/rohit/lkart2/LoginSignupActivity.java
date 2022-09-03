package com.example.rohit.lkart2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rohit.lkart2.Utility.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginSignupActivity extends AppCompatActivity {


    TextView textforgot;
    TextView textcreate;
    Button btnlogin;
    EditText edtmobile;
    EditText edtpassword;
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_signup);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        session=new SessionManager(this);

        btnlogin=(Button)findViewById(R.id.btnlogin);
        edtmobile=(EditText)findViewById(R.id.edtmobile);
        edtpassword=(EditText)findViewById(R.id.edtpassword);


        btnlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtmobile.getText().toString().length() == 0) {

                    Toast.makeText(LoginSignupActivity.this, "Please enter mobile number", Toast.LENGTH_SHORT).show();
                } else if (edtmobile.getText().toString().length() < 10) {
                    Toast.makeText(LoginSignupActivity.this, "Enter valid mobile number", Toast.LENGTH_SHORT).show();
                } else if (edtpassword.getText().toString().length() == 0) {
                    Toast.makeText(LoginSignupActivity.this, "Enter password", Toast.LENGTH_SHORT).show();
                } else if (edtpassword.getText().toString().length() < 8) {
                    Toast.makeText(LoginSignupActivity.this, "Enter valid password", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    StringRequest str=new StringRequest(Request.Method.POST, WebServices.LOAD_LOGIN, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            try {

                                JSONObject mainobj=new JSONObject(response);

                                String status=mainobj.getString("status");

                                if(status.equals("yes"))
                                {
                                    JSONObject data=mainobj.getJSONObject("data");
                                    String user_id=data.getString("user_id");
                                    String name=data.getString("name");

                                    session.setLogin(true);
                                    session.setId(user_id);
                                    session.setName(name);

                                   // Toast.makeText(LoginSignupActivity.this, "Welcome : "+user_id+name, Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(LoginSignupActivity.this, HomePageActivity.class);
                                    startActivity(i);
                                }
                                else
                                {
                                    Toast.makeText(LoginSignupActivity.this, "Mobile Number Or Password Not Found", Toast.LENGTH_SHORT).show();
                                }

                            } catch (JSONException e) {

                            }


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            Map<String,String> params=new HashMap<String, String>();
                            params.put("mobile",edtmobile.getText().toString());
                            params.put("password",edtpassword.getText().toString());
                            return params;

                        }
                    };
                    RequestQueue q= Volley.newRequestQueue(LoginSignupActivity.this);
                    q.add(str);
                }
            }
        });


        textcreate=(TextView)findViewById(R.id.textcreate);
        textcreate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginSignupActivity.this, CreateAccountActivity.class);
                startActivity(i);
            }
        });



        textforgot=(TextView) findViewById(R.id.textforgot);
        textforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginSignupActivity.this, Forgot_Password_Activity.class);
                startActivity(i);
            }
        });

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //finish();
                onBackPressed();
                break;
        }
        return true;
    }
    @Override
    public void onBackPressed() {
        //Execute your code here
        finish();

    }
}
