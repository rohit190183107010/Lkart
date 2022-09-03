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
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rohit.lkart2.Adapter.CartAdapter;
import com.example.rohit.lkart2.Adapter.OrderAdapter;
import com.example.rohit.lkart2.Adapter.ProductAdapter;
import com.example.rohit.lkart2.ClassFiles.Cart;
import com.example.rohit.lkart2.ClassFiles.Order;
import com.example.rohit.lkart2.ClassFiles.Product;
import com.example.rohit.lkart2.ClassFiles.Store;
import com.example.rohit.lkart2.Utility.SessionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderActivity extends AppCompatActivity {

    SessionManager session;
    ListView listorder;
    OrderAdapter adapter;
    List<Order> orderlist=new ArrayList<>();
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        session=new SessionManager(this);

        dialog=new ProgressDialog(this);
        dialog.setMessage("Please Wait..");
        dialog.setCancelable(false);
        listorder=(ListView)findViewById(R.id.listorder);
        adapter=new OrderAdapter(this,orderlist);
        listorder.setAdapter(adapter);

        listorder.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Order item = orderlist.get(position);
                String order_id = item.getOrder_id();
                String total_payment = item.getTotal_payment();
                String address = item.getAddress();
                String landmark = item.getLandmark();
                String contact = item.getContact();
                String ispay = item.getIspay();
                String pay_mode = item.getPay_mode();
                String transaction_number = item.getTransaction_number();
                String status = item.getStatus();
                String order_datetime = item.getOrder_datetime();
                String pincode = item.getPincode();

                Intent intent = new Intent(OrderActivity.this, OrderDetailActivity.class);
                intent.putExtra("order_id", order_id);
                intent.putExtra("total_payment", total_payment);
                intent.putExtra("address", address);
                intent.putExtra("landmark", landmark);
                intent.putExtra("pincode", pincode);
                intent.putExtra("contact", contact);
                intent.putExtra("ispay", ispay);
                intent.putExtra("pay_mode", pay_mode);
                intent.putExtra("transaction_number", transaction_number);
                intent.putExtra("status", status);
                intent.putExtra("order_datetime", order_datetime);



                startActivity(intent);


            }
        });
        dialog.show();
        StringRequest str=new StringRequest(Request.Method.POST,WebServices.LOAD_ORDER, new Response.Listener<String>() {
            @Override

            public void onResponse(String response) {
                dialog.hide();
                try {
                    JSONArray array=new JSONArray(response);

                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject obj=array.getJSONObject(i);
                        Order item=new Order();
                        item.setOrder_id(obj.getString("order_id"));
                        item.setAddress(obj.getString("address"));
                        item.setLandmark(obj.getString("landmark"));
                        item.setPincode(obj.getString("pincode"));
                        item.setTotal_payment(obj.getString("total_payment"));
                        item.setContact(obj.getString("contact"));
                        item.setIspay(obj.getString("ispay"));
                        item.setPay_mode(obj.getString("pay_mode"));
                        item.setTransaction_number(obj.getString("transaction_number"));
                        item.setStatus(obj.getString("status"));
                        item.setOrder_datetime(obj.getString("order_datetime"));
                        orderlist.add(item);
                    }
                    adapter.notifyDataSetChanged();



                } catch (JSONException e) {
                    Toast.makeText(OrderActivity.this, "Exception "+e.getMessage(), Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Toast.makeText(OrderActivity.this, "server " + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("userid", session.getId());
                return params;
            }
        };

        // get our folding cell

        // attach click listener to toast btn
        RequestQueue q = Volley.newRequestQueue(OrderActivity.this);
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
