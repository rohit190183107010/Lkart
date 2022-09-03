package com.example.rohit.lkart2;

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

public class FeedbackActivity extends AppCompatActivity {


    EditText edtName ,edtMobile,edtEmail,edtfeedback;
    Button btnfeedback;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        edtName = (EditText)findViewById(R.id.edtName);
        edtMobile = (EditText)findViewById(R.id.edtMobile);
        edtEmail = (EditText)findViewById(R.id.edtEmail);
        edtfeedback = (EditText)findViewById(R.id.edtfeedback);
        btnfeedback=(Button)findViewById(R.id.btnfeedback);

        btnfeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtName.getText().toString().length()==0)
                {
                    Toast.makeText(FeedbackActivity.this, "Enter name", Toast.LENGTH_SHORT).show();
                }
                else if(edtMobile.getText().toString().length()==0)
                {
                    Toast.makeText(FeedbackActivity.this, "enter mobile number", Toast.LENGTH_SHORT).show();
                }
                else if(edtMobile.getText().toString().length()<10)
                {
                    Toast.makeText(FeedbackActivity.this, "enter valid number", Toast.LENGTH_SHORT).show();
                }
                else if(edtEmail.getText().toString().length()==0)
                {
                    Toast.makeText(FeedbackActivity.this, "Enter email", Toast.LENGTH_SHORT).show();
                }
                else if(edtfeedback.getText().toString().length()==0)
                {
                    Toast.makeText(FeedbackActivity.this, "enter feedback", Toast.LENGTH_SHORT).show();
                }
                else
                {


                    StringRequest str=new StringRequest(Request.Method.POST, "http://192.168.1.146:8089/webservice/feedback.php", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Toast.makeText(FeedbackActivity.this, ""+response, Toast.LENGTH_SHORT).show();

                            if(response.equals("yes"))
                            {
                                Toast.makeText(FeedbackActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(FeedbackActivity.this, "Error", Toast.LENGTH_SHORT).show();
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
                            params.put("feedback",edtfeedback.getText().toString());
                            return params;

                        }
                    };
                    RequestQueue q= Volley.newRequestQueue(FeedbackActivity.this);
                    q.add(str);
                }
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
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
