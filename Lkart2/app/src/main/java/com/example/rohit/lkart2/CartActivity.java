package com.example.rohit.lkart2;

import android.app.AlertDialog;
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
import com.example.rohit.lkart2.ClassFiles.Cart;
import com.example.rohit.lkart2.ClassFiles.Category;
import com.example.rohit.lkart2.ClassFiles.Store;
import com.example.rohit.lkart2.Utility.SessionManager;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CartActivity extends AppCompatActivity {

    ListView listcart;
    CartAdapter adapter;
    List<Cart> cartlist=new ArrayList<>();
    ProgressDialog dialog;
    SessionManager session;
    Button btnconfirmorder;
    float total=0;
    Button btndelete;

    int q=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        btndelete=(Button)findViewById(R.id.btndelete);

        session=new SessionManager(this);
        btnconfirmorder=(Button)findViewById(R.id.btnconfirmorder);
        dialog=new ProgressDialog(this);
        dialog.setMessage("Please Wait..");
        dialog.setCancelable(false);
        listcart=(ListView)findViewById(R.id.listcart);
        adapter=new CartAdapter(this,cartlist);
        listcart.setAdapter(adapter);


        btnconfirmorder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toast.makeText(CartActivity.this, ""+total, Toast.LENGTH_SHORT).show();


                if(total==0){
                    Toast.makeText(CartActivity.this, "Cart is Empty", Toast.LENGTH_SHORT).show();
                }
                else {
                    Intent i = new Intent(CartActivity.this, Fillorder_Activity.class);
                    i.putExtra("total", String.valueOf(total));
                    startActivity(i);
                }

            }
        });

        loadcartdata();

        listcart.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                final Cart item = cartlist.get(position);
                long viewid=view.getId();
                if (viewid == R.id.btnadd)
                {
                    q = Integer.parseInt(item.getQty());
                    q++;
                    dialog.show();
                    StringRequest str = new StringRequest(Request.Method.POST,WebServices.LOAD_UPDATE_CART, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            dialog.hide();
                            if(response.equals("no"))
                            {
                                Toast.makeText(CartActivity.this, "Quantity Not Available More Than This", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                loadcartdata();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            dialog.hide();

                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("cartid", item.getCart_id());
                            params.put("qty", String.valueOf(q));
                            params.put("product_id", item.getProduct_id());
                            return params;
                        }
                    };

                    RequestQueue qr = Volley.newRequestQueue(CartActivity.this);
                    qr.add(str);

                }else if (viewid == R.id.btnminus)
                {
                    q = Integer.parseInt(item.getQty());
                    q--;
                    dialog.show();
                    StringRequest str = new StringRequest(Request.Method.POST, WebServices.LOAD_UPDATE_CART, new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            dialog.hide();

                                loadcartdata();


                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                            dialog.hide();
                        }
                    }) {
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("cartid", item.getCart_id());
                            params.put("qty", String.valueOf(q));
                            params.put("product_id", item.getProduct_id());
                            return params;
                        }
                    };

                    RequestQueue qr = Volley.newRequestQueue(CartActivity.this);
                    qr.add(str);
                }
            }
        });

//        listcart.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                Cart item = cartlist.get(position);
//                String cart_id = item.getCart_id();
//                String cart_added = item.getCart_added();
//                String price=item.getPrice();
//                String txtqty=item.getQty();
//                String image=item.getProduct_img();
//
//
//                Intent i = new Intent(CartActivity.this, OrderActivity.class);
//                i.putExtra("cart_id", cart_id);
//                i.putExtra("cart_added", cart_added);
//
//
//                startActivity(i);
//
//
//            }
//        });
    }

    public void loadcartdata()
    {
        dialog.show();

        cartlist.clear();

        StringRequest str=new StringRequest(Request.Method.POST,WebServices.LOAD_CART, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.dismiss();
                total=0;
                try {
                    JSONArray array=new JSONArray(response);
                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject obj=array.getJSONObject(i);
                        Cart item=new Cart();
                        item.setCart_id(obj.getString("cart_id"));
                        item.setCart_added(obj.getString("cart_added"));
                        item.setQty(obj.getString("qty"));
                        item.setProduct_id(obj.getString("product_id"));
                        item.setProduct_name(obj.getString("product_name"));
                        item.setProduct_img(obj.getString("product_img"));
                        item.setPrice(obj.getString("new_price"));
                        item.setSize(obj.getString("size"));

                        total=total+(Float.parseFloat(obj.getString("qty"))*Float.parseFloat(obj.getString("new_price")));

                        //
                        cartlist.add(item);
                    }
                    //   Toast.makeText(CartActivity.this, ""+total, Toast.LENGTH_SHORT).show();
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    Toast.makeText(CartActivity.this, "Exception "+e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                dialog.dismiss();
                Toast.makeText(CartActivity.this, "server "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("userid", session.getId());
                return params;
            }
        };
        RequestQueue q= Volley.newRequestQueue(CartActivity.this);
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
