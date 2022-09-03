package com.example.rohit.lkart2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rohit.lkart2.ClassFiles.Product;
import com.example.rohit.lkart2.Utility.SessionManager;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductDetailActivity extends AppCompatActivity {

    ImageView productdetailimg;
    TextView txtproductdetailname,txtproductdetailprice,txtproductdetaildes,txtproductqty;
    Button btnbuynow,btnaddtocart;
    EditText edtqty;
    String productid;
    String productname;
    String description;
    String price,qty,size;
    String image;
    Activity activity;
    Spinner spnsize;
    SessionManager session;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        spnsize=(Spinner)findViewById(R.id.spnsize);
        productdetailimg=(ImageView)findViewById(R.id.productdetailimg);
        txtproductdetailprice=(TextView)findViewById(R.id.txtproductdetailprice);
        txtproductdetailname=(TextView)findViewById(R.id.txtproductdetailname);
        txtproductdetaildes=(TextView)findViewById(R.id.txtproductdetaildes);
        txtproductqty=(TextView)findViewById(R.id.txtproductqty);

        edtqty=(EditText)findViewById(R.id.edtqty);

        btnbuynow=(Button)findViewById(R.id.btnbuynow);
        btnaddtocart=(Button)findViewById(R.id.btnaddtocart);

        session=new SessionManager(this);

        productid=getIntent().getStringExtra("productid");
        productname=getIntent().getStringExtra("productname");
        description=getIntent().getStringExtra("description");
        price=getIntent().getStringExtra("price");
        image=getIntent().getStringExtra("image");
        qty=getIntent().getStringExtra("qty");
        size=getIntent().getStringExtra("size");

        txtproductdetailname.setText(productname);
        txtproductdetailprice.setText(price);
        txtproductdetaildes.setText(description);
        txtproductqty.setText(qty);
        edtqty.setText("1");

        String[] array = size.toString().split(",");
        ArrayAdapter adapter=new ArrayAdapter(ProductDetailActivity.this,android.R.layout.simple_spinner_dropdown_item,array);
        spnsize.setAdapter(adapter);

        Picasso.with(activity).load(WebServices.BASE_URL_IMAGE+"admin/upload/product/"+image).into(productdetailimg);

        int p=Integer.parseInt(edtqty.getText().toString());

        int q=Integer.parseInt(txtproductqty.getText().toString());


        if(q==0)
        {
            btnbuynow.setClickable(false);
            btnaddtocart.setClickable(false);
            edtqty.setEnabled(false);
            edtqty.setText("Out of Stock");
            txtproductqty.setText("Out of Stock");

        }
        else {
            btnbuynow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (edtqty.getText().toString().trim().length() == 0) {
                        edtqty.setError("Enter Valid Quantity");
                    } else {
                        int p = Integer.parseInt(edtqty.getText().toString());
                        int q = Integer.parseInt(txtproductqty.getText().toString());


                        if (p <= q) {
                            if (p <= 0) {
                                edtqty.setError("Enter Valid Quantity");
                            } else {
                                Intent i = new Intent(ProductDetailActivity.this, BuyNowActivity.class);
                                i.putExtra("qty", edtqty.getText().toString());
                                i.putExtra("total", price);
                                i.putExtra("productid", productid);
                                startActivity(i);
                            }
                        } else {
                            edtqty.setError("Enter Valid Quantity");
                        }
                    }
                }
            });


            btnaddtocart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (edtqty.getText().toString().trim().length() == 0) {
                        edtqty.setError("Enter Valid Quantity");
                    } else {
                        int p = Integer.parseInt(edtqty.getText().toString());
                        int r = Integer.parseInt(txtproductqty.getText().toString());
                        if (p <= r) {
                            if (p <= 0) {

                                edtqty.setError("Enter Valid Quantity");
                            } else {
                                if (session.isLoggedIn()) {
                                    StringRequest str = new StringRequest(Request.Method.POST, WebServices.ADD_TO_CART, new Response.Listener<String>() {
                                        @Override
                                        public void onResponse(String response) {

                                            try {


                                            } catch (Exception e) {

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
                                            params.put("userid", session.getId());
                                            params.put("productid", productid);
                                            params.put("size",spnsize.getSelectedItem().toString());
                                            return params;

                                        }
                                    };
                                    RequestQueue q = Volley.newRequestQueue(ProductDetailActivity.this);
                                    q.add(str);
                                    Toast.makeText(ProductDetailActivity.this, "Added to cart", Toast.LENGTH_SHORT).show();

                                } else {
                                    Toast.makeText(ProductDetailActivity.this, "Please Login first", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } else {
                            edtqty.setError("Enter Valid Quantity");
                        }
                    }}
            });
        }
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
