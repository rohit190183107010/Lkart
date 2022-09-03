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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class Forgot_Password_Activity extends AppCompatActivity {
    Button btnforgot;
    EditText edtforgettext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot__password);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnforgot=(Button)findViewById(R.id.btnforgot);
        edtforgettext=(EditText)findViewById(R.id.edtforgettext);

        btnforgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              if(edtforgettext.getText().toString().length()==0)
              {
                  Toast.makeText(Forgot_Password_Activity.this, "please Enter mobile number", Toast.LENGTH_SHORT).show();
              }
                else if(edtforgettext.getText().toString().length()<10)
              {
                  Toast.makeText(Forgot_Password_Activity.this, "please enter valid number", Toast.LENGTH_SHORT).show();
              }
                else
              {

                  Intent i = new Intent(Forgot_Password_Activity.this,OtpActivity.class);
                  startActivity(i);
                  //server request
                  StringRequest str=new StringRequest(Request.Method.POST, "", new Response.Listener<String>() {
                      @Override
                      public void onResponse(String response) {

                      }
                  }, new Response.ErrorListener() {
                      @Override
                      public void onErrorResponse(VolleyError error) {

                      }
                  });

                  RequestQueue q= Volley.newRequestQueue(Forgot_Password_Activity.this);
                  q.add(str);


              }
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
