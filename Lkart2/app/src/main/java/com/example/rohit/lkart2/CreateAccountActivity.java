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
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class CreateAccountActivity extends AppCompatActivity {

    Button btncreatesubmit;

    EditText edtName,edtMobile,edtEmail,edtPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btncreatesubmit=(Button)findViewById(R.id.btncreatesubmit);

        edtName=(EditText)findViewById(R.id.edtName);
        edtMobile=(EditText)findViewById(R.id.edtMobile);
        edtEmail=(EditText)findViewById(R.id.edtEmail);
        edtPassword=(EditText)findViewById(R.id.edtPassword);



        btncreatesubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //server request

                if(edtName.getText().toString().length()==0)
                {
                    Toast.makeText(CreateAccountActivity.this, "Please Enter Name", Toast.LENGTH_SHORT).show();
                }
                else  if(edtMobile.getText().toString().length()==0)
                {
                    Toast.makeText(CreateAccountActivity.this, "Please Enter Mobile Number", Toast.LENGTH_SHORT).show();
                }
                else  if(edtMobile.getText().toString().length()!=10)
                {
                    Toast.makeText(CreateAccountActivity.this, "Please Enter Valid Mobile Number", Toast.LENGTH_SHORT).show();
                }
                else  if(edtEmail.getText().toString().length()==0)
                {
                    Toast.makeText(CreateAccountActivity.this, "Please Enter Email Address", Toast.LENGTH_SHORT).show();
                }
                else  if(edtPassword.getText().toString().length()==0)
                {
                    Toast.makeText(CreateAccountActivity.this, "Please Enter Password", Toast.LENGTH_SHORT).show();
                }
                else  if(edtPassword.getText().toString().length()<8)
                {
                    Toast.makeText(CreateAccountActivity.this, "Password Length Must Be 8 Character", Toast.LENGTH_SHORT).show();
                }
                else
                {

                    //server request
                    StringRequest str=new StringRequest(Request.Method.POST,WebServices.ADD_USER, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            if(response.equals("yes"))
                            {
                                Toast.makeText(CreateAccountActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                                Intent i= new Intent(CreateAccountActivity.this,LoginSignupActivity.class);
                                startActivity(i);


                            }
                            else
                            {
                                Toast.makeText(CreateAccountActivity.this, "Account Already Exist", Toast.LENGTH_SHORT).show();
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
                            params.put("name",edtName.getText().toString());
                            params.put("mobile",edtMobile.getText().toString());
                            params.put("email",edtEmail.getText().toString());
                            params.put("password",edtPassword.getText().toString());
                            return params;

                        }
                    };

                    RequestQueue q=Volley.newRequestQueue(CreateAccountActivity.this);
                    q.add(str);


                }


                //
            }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
