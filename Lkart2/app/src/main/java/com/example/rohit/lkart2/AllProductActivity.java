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
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Spinner;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AllProductActivity extends AppCompatActivity {
    GridView listproduct;
    ProductAdapter adapter;
    List<Product> productList;
    List<Product> allproductlist= new ArrayList<Product>();
    EditText edtsearch;
    String subcatid,subcatname;
    Spinner spnsort;
    ArrayList<String> sorting=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        spnsort=(Spinner)findViewById(R.id.spnsort);

        subcatid=getIntent().getStringExtra("subcatid");
        subcatname=getIntent().getStringExtra("subcatname");
        edtsearch=(EditText)findViewById(R.id.edtsearch);
        listproduct=(GridView)findViewById(R.id.listproduct);
        productList=new ArrayList<>();
        adapter=new ProductAdapter(AllProductActivity.this,productList);
        listproduct.setAdapter(adapter);


        sorting.add("Price : Low to High");
        sorting.add("Price : High to Low");
        sorting.add("Name : A to Z");
        sorting.add("Name : Z to A");

        ArrayAdapter adapter1=new ArrayAdapter(this,android.R.layout.simple_dropdown_item_1line,sorting);
        spnsort.setAdapter(adapter1);

        spnsort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadproduct(sorting.get(position));
                //  Toast.makeText(AllProductActivity.this, ""+sorting.get(position), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


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
                Intent intent = new Intent(AllProductActivity.this, ProductDetailActivity.class);

                Product item = productList.get(position);
                String productid = item.getProduct_id();
                String productname = item.getProduct_name();
                String description = item.getProduct_description();
                String price = item.getNew_price();
                String image = item.getProduct_img();
                String qty = item.getQty();
                String size=item.getSize();
                intent.putExtra("productid", productid);
                intent.putExtra("productname", productname);
                intent.putExtra("description", description);
                intent.putExtra("price", price);
                intent.putExtra("image", image);
                intent.putExtra("qty", qty);
                intent.putExtra("size",size);
                startActivity(intent);
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



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
    public void loadproduct(final String type)
    {
        productList.clear();
        allproductlist.clear();
        StringRequest str=new StringRequest(Request.Method.POST,WebServices.LOAD_ALLPRODUCT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray array=new JSONArray(response);

                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject obj=array.getJSONObject(i);
                        Product item=new Product();
                        item.setProduct_id(obj.getString("product_id"));
                        item.setProduct_description(obj.getString("product_description"));
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

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("type",type);
                return params;
            }
        };
        // get our folding cell

        // attach click listener to toast btn
        RequestQueue q = Volley.newRequestQueue(AllProductActivity.this);
        q.add(str);
    }

}
