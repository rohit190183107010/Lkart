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
import android.widget.GridView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rohit.lkart2.Adapter.ProductAdapter;
import com.example.rohit.lkart2.Adapter.SubcategoryAdapter;
import com.example.rohit.lkart2.ClassFiles.Product;
import com.example.rohit.lkart2.ClassFiles.Subcategory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class storeproductActivity extends AppCompatActivity {

    GridView listproduct;
    ProductAdapter adapter;
    List<Product> productList;

    String store_id,store_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_product);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        store_id=getIntent().getStringExtra("store_id");

        store_name=getIntent().getStringExtra("store_name");

        listproduct=(GridView)findViewById(R.id.listproduct);
        productList=new ArrayList<>();
        adapter=new ProductAdapter(storeproductActivity.this,productList);
        listproduct.setAdapter(adapter);

        listproduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(storeproductActivity.this, ProductDetailActivity.class);

                Product item = productList.get(position);
                String productid = item.getProduct_id();
                String productname = item.getProduct_name();
                String description = item.getProduct_description();
                String price = item.getNew_price();
                String image = item.getProduct_img();

                intent.putExtra("productid", productid);
                intent.putExtra("productname", productname);
                intent.putExtra("description", description);
                intent.putExtra("price", price);
                intent.putExtra("image", image);

                startActivity(intent);
            }
        });






        StringRequest str=new StringRequest(Request.Method.POST,WebServices.LOAD_STOREPRODUCT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray array=new JSONArray(response);

                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject obj=array.getJSONObject(i);
                        Product item=new Product();
                        item.setProduct_id(obj.getString("product_id"));
                        item.setProduct_name(obj.getString("product_name"));
                        item.setNew_price(obj.getString("new_price"));
                        item.setProduct_img(obj.getString("product_img"));
                        productList.add(item);
                    }
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
                params.put("store_id",store_id);
                return params;
            }
        };
        // get our folding cell

        // attach click listener to toast btn
        RequestQueue q = Volley.newRequestQueue(storeproductActivity.this);
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



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

}
