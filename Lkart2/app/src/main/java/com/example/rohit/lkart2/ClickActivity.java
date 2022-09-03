package com.example.rohit.lkart2;

import android.app.VoiceInteractor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
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

public class ClickActivity extends AppCompatActivity {

    Button btnclicksubmit;
    TextView edtpassword,edtname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        btnclicksubmit=(Button)findViewById(R.id.btnclicksubmit);
        edtname=(TextView)findViewById(R.id.edtname);
        edtpassword=(TextView)findViewById(R.id.edtpassword);

        btnclicksubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(edtname.getText().toString().length()==0)
                {
                    Toast.makeText(ClickActivity.this, "Enter Name", Toast.LENGTH_SHORT).show();
                }
                else if(edtpassword.getText().toString().length()==0)
                {
                    Toast.makeText(ClickActivity.this, "Enter Password", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    StringRequest str=new StringRequest(Request.Method.POST, "http://192.168.1.146:8089/webservice/seller.php", new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {

                            Toast.makeText(ClickActivity.this, response, Toast.LENGTH_SHORT).show();

                            if(response.equals("yes"))
                            {
                                Toast.makeText(ClickActivity.this, "Successfully Registered", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                Toast.makeText(ClickActivity.this, "Error", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String,String> paramas=new HashMap<String, String>();
                            paramas.put("name",edtname.getText().toString());
                            paramas.put("password",edtpassword.getText().toString());
                            return  paramas;
                        }
                    };
                    RequestQueue q= Volley.newRequestQueue(ClickActivity.this);
                    q.add(str);
                }
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
