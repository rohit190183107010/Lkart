package com.example.rohit.lkart2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rohit.lkart2.Adapter.CartAdapter;
import com.example.rohit.lkart2.Adapter.DetailAdapter;
import com.example.rohit.lkart2.ClassFiles.Cart;
import com.example.rohit.lkart2.ClassFiles.Detail;
import com.example.rohit.lkart2.Utility.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderDetailActivity extends AppCompatActivity {
    ListView listorderdetail;
    DetailAdapter adapter;
    List<Detail> orderdetaillist=new ArrayList<>();
    ProgressDialog dialog;
    SessionManager session;
    String order_id;
    String price,qty,size;
    Button btndelete;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        session=new SessionManager(this);
        dialog=new ProgressDialog(this);
        dialog.setMessage("Please Wait..");
        dialog.setCancelable(false);
        listorderdetail=(ListView)findViewById(R.id.listorderdetail);
        btndelete=(Button)findViewById(R.id.btndelete);
        adapter=new DetailAdapter(this,orderdetaillist);
        listorderdetail.setAdapter(adapter);
        price=getIntent().getStringExtra("price");
        qty=getIntent().getStringExtra("qty");
        size=getIntent().getStringExtra("size");
        order_id=getIntent().getStringExtra("order_id");


        dialog.show();



        StringRequest str=new StringRequest(Request.Method.POST,WebServices.LOAD_ORDERDETAIL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                try {
                    JSONArray array=new JSONArray(response);
                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject obj=array.getJSONObject(i);
                        Detail item=new Detail();
                        item.setOrder_id(obj.getString("order_id"));
                        item.setQty(obj.getString("qty"));
                        item.setProduct_name(obj.getString("product_name"));
                        item.setProduct_img(obj.getString("product_img"));
                        item.setPrice(obj.getString("price"));
                        item.setSize(obj.getString("size"));
                        orderdetaillist.add(item);
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    Toast.makeText(OrderDetailActivity.this, "Exception " + e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Toast.makeText(OrderDetailActivity.this, "server "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("order_id",order_id);
                return params;
            }
        };
        RequestQueue q= Volley.newRequestQueue(OrderDetailActivity.this);
        q.add(str);


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
