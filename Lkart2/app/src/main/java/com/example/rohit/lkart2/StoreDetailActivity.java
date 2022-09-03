package com.example.rohit.lkart2;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class StoreDetailActivity extends AppCompatActivity {
    ImageView storedetailcover_image,storedetaillogo;
    TextView txtstorename,txtstorepersonname,txtstoremobile,txtstoredetailaddress,txtstorelandmark,txtstorepincode,txtstorewebsite,txtstoreaboutstore;
    Activity activity;
    Button btnproduct;

    String Store_id,store_name,contact_number,website,logo,cover_image,about_store,address,landmark,pincode,person_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        storedetailcover_image=(ImageView)findViewById(R.id.storedetailcover_image);
        storedetaillogo=(ImageView)findViewById(R.id.storedetaillogo);

        txtstorename=(TextView)findViewById(R.id.txtstorename);
        txtstorepersonname=(TextView)findViewById(R.id.txtstorepersonname);
        txtstoremobile=(TextView)findViewById(R.id.txtstoremobile);
        txtstoredetailaddress=(TextView)findViewById(R.id.txtstoredetailaddress);
        txtstorelandmark=(TextView)findViewById(R.id.txtstorelandmark);
        txtstorepincode=(TextView)findViewById(R.id.txtstorepincode);
        txtstorewebsite=(TextView)findViewById(R.id.txtstorewebsite);
        txtstoreaboutstore=(TextView)findViewById(R.id.txtstoreaboutstore);
        btnproduct=(Button)findViewById(R.id.btnproduct);

        store_name=getIntent().getStringExtra("store_name");
        contact_number=getIntent().getStringExtra("contact_number");
        website=getIntent().getStringExtra("website");
        about_store=getIntent().getStringExtra("about_store");
        address=getIntent().getStringExtra("address");
        landmark=getIntent().getStringExtra("landmark");
        pincode=getIntent().getStringExtra("pincode");
        person_name=getIntent().getStringExtra("person_name");
        pincode=getIntent().getStringExtra("pincode");
        logo=getIntent().getStringExtra("logo");
        cover_image=getIntent().getStringExtra("cover_image");

        txtstorename.setText(store_name);
        txtstorepersonname.setText(person_name);
        txtstoremobile.setText(contact_number);
        txtstorewebsite.setText(website);
        txtstoredetailaddress.setText(about_store);
        txtstoredetailaddress.setText(address);
        txtstorelandmark.setText(landmark);
        txtstorepincode.setText(pincode);
        btnproduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(StoreDetailActivity.this,AllProductActivity.class);
                startActivity(i);
            }
        });

        Picasso.with(activity).load(WebServices.BASE_URL_IMAGE+"admin/upload/store/"+logo).into(storedetaillogo);
        Picasso.with(activity).load(WebServices.BASE_URL_IMAGE+"admin/upload/store/"+cover_image).into(storedetailcover_image);



    }

}
