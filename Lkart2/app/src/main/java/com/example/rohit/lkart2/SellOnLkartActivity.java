package com.example.rohit.lkart2;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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

public class SellOnLkartActivity extends AppCompatActivity {
    Button btncreatesubmit;

    EditText edtName,edtMobile,edtEmail,edtPassword,edtstorename,edtaddress,edtpincode,edtaboutstore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sell_on_lkart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btncreatesubmit=(Button)findViewById(R.id.btncreatesubmit);

        edtName=(EditText)findViewById(R.id.edtName);
        edtMobile=(EditText)findViewById(R.id.edtMobile);
        edtEmail=(EditText)findViewById(R.id.edtEmail);
        edtPassword=(EditText)findViewById(R.id.edtPassword);

        edtstorename=(EditText)findViewById(R.id.edtstorename);
        edtaddress=(EditText)findViewById(R.id.edtaddress);
        edtpincode=(EditText)findViewById(R.id.edtpincode);
        edtaboutstore=(EditText)findViewById(R.id.edtaboutstore);

        btncreatesubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //server request

                if (edtName.getText().toString().length() == 0) {
                    Toast.makeText(SellOnLkartActivity.this, "Please Enter Name", Toast.LENGTH_SHORT).show();
                } else if (edtMobile.getText().toString().length() == 0) {
                    Toast.makeText(SellOnLkartActivity.this, "Please Enter Mobile Number", Toast.LENGTH_SHORT).show();
                } else if (edtMobile.getText().toString().length() != 10) {
                    Toast.makeText(SellOnLkartActivity.this, "Please Enter Valid Mobile Number", Toast.LENGTH_SHORT).show();
                } else if (edtEmail.getText().toString().length() == 0) {
                    Toast.makeText(SellOnLkartActivity.this, "Please Enter Email Address", Toast.LENGTH_SHORT).show();
                } else if (edtPassword.getText().toString().length() == 0) {
                    Toast.makeText(SellOnLkartActivity.this, "Please Enter PAssword", Toast.LENGTH_SHORT).show();
                } else if (edtPassword.getText().toString().length() < 4) {
                    Toast.makeText(SellOnLkartActivity.this, "Password Length Must be Greter than 4", Toast.LENGTH_SHORT).show();
                }
                else if (edtstorename.getText().toString().length() ==0) {
                    Toast.makeText(SellOnLkartActivity.this, "Enter Store Name ", Toast.LENGTH_SHORT).show();
                }
                else if (edtaddress.getText().toString().length() ==0) {
                    Toast.makeText(SellOnLkartActivity.this, "Enter Address", Toast.LENGTH_SHORT).show();
                }
                else if (edtpincode.getText().toString().length() <= 6  ) {
                    Toast.makeText(SellOnLkartActivity.this, "Enter Pincode", Toast.LENGTH_SHORT).show();
                }
                else if (edtaboutstore.getText().toString().length() < 4) {
                    Toast.makeText(SellOnLkartActivity.this, "Enter About Store", Toast.LENGTH_SHORT).show();
                }



                else {

                    //server request
                    StringRequest str = new StringRequest(Request.Method.POST,WebServices.ADD_SELLER, new Response.Listener<String>() {
                        @Override

                        public void onResponse(String response) {

                            if (response.equals("yes")) {
                                Toast.makeText(SellOnLkartActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(SellOnLkartActivity.this, "Error", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {

                            Map<String, String> params = new HashMap<String, String>();
                            params.put("username", edtName.getText().toString());
                            params.put("contact_number", edtMobile.getText().toString());
                            params.put("email_id", edtEmail.getText().toString());
                            params.put("password", edtPassword.getText().toString());

                            params.put("store_name", edtstorename.getText().toString());
                            params.put("address", edtaddress.getText().toString());
                            params.put("about_store", edtaboutstore.getText().toString());
                            params.put("pincode", edtPassword.getText().toString());
                            return params;

                        }
                    };

                    RequestQueue q = Volley.newRequestQueue(SellOnLkartActivity.this);
                    q.add(str);
                }



            }
        });
               getSupportActionBar().setDisplayHomeAsUpEnabled(true);
           }

       }
