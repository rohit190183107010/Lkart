package com.example.rohit.lkart2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rohit.lkart2.Adapter.ProductAdapter;
import com.example.rohit.lkart2.ClassFiles.Product;
import com.example.rohit.lkart2.ClassFiles.ProductDetail;
import com.ramotion.foldingcell.FoldingCell;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ProductActivity extends AppCompatActivity {

    GridView listproduct;
    ProductAdapter adapter;
    List<Product> productList;

    List<Product> allproductlist= new ArrayList<Product>();
    EditText edtsearch;


    String subcatid,subcatname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        subcatid=getIntent().getStringExtra("subcatid");
        edtsearch=(EditText)findViewById(R.id.edtsearch);
        subcatname=getIntent().getStringExtra("subcatname");

        listproduct=(GridView)findViewById(R.id.listproduct);
        productList=new ArrayList<>();
        adapter=new ProductAdapter(ProductActivity.this,productList);
        listproduct.setAdapter(adapter);

        edtsearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String text = charSequence.toString().toLowerCase(Locale.getDefault());
                productList.clear();
                if (text.length() == 0) {
                    productList.addAll(allproductlist);
                } else {
                    for (Product wp : allproductlist) {
                        if (wp.getProduct_name().toLowerCase(Locale.getDefault()).contains(text)) {
                            productList.add(wp);
                        }
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });

        listproduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(ProductActivity.this, ProductDetailActivity.class);

                Product item=productList.get(position);
                String productid=item.getProduct_id();
                String productname=item.getProduct_name();
                String description=item.getProduct_description();
                String price=item.getNew_price();
                String image=item.getProduct_img();
                String qty=item.getQty();
                String size=item.getSize();

                intent.putExtra("productid",productid);
                intent.putExtra("productname",productname);
                intent.putExtra("description",description);
                intent.putExtra("price",price);
                intent.putExtra("image",image);
                intent.putExtra("size",size);
                intent.putExtra("qty",qty);



                startActivity(intent);
            }
        });

        StringRequest str=new StringRequest(Request.Method.POST,WebServices.LOAD_PRODUCT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray array=new JSONArray(response);

                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject obj=array.getJSONObject(i);
                        Product item=new Product();
                        item.setProduct_description(obj.getString("product_description"));
                        item.setProduct_id(obj.getString("product_id"));
                        item.setProduct_name(obj.getString("product_name"));
                        item.setNew_price(obj.getString("new_price"));
                        item.setProduct_img(obj.getString("product_img"));
                        item.setQty(obj.getString("qty"));
                        item.setSize(obj.getString("size"));
                        productList.add(item);
                    }
                    allproductlist.addAll(productList);
                    adapter.notifyDataSetChanged();



                } catch (JSONException e) {
                    Toast.makeText(ProductActivity.this, "server " + e.getMessage(), Toast.LENGTH_SHORT).show();

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ProductActivity.this, "server " + error.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("subcatid",subcatid);
                return params;
            }
        };
        // get our folding cell

        // attach click listener to toast btn
        RequestQueue q = Volley.newRequestQueue(ProductActivity.this);
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
