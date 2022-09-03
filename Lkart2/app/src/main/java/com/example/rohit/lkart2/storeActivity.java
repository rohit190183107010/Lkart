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
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rohit.lkart2.Adapter.CategoryAdapter;
import com.example.rohit.lkart2.Adapter.StoreAdapter;
import com.example.rohit.lkart2.ClassFiles.Category;
import com.example.rohit.lkart2.ClassFiles.Store;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class storeActivity extends AppCompatActivity {

    ListView liststore;
    StoreAdapter adapater;
    List<Store> storelist;
    ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        dialog=new ProgressDialog(this);
        dialog.setMessage("Please Wait...");
        dialog.setCancelable(false);

        liststore=(ListView)findViewById(R.id.liststore);
        storelist=new ArrayList<>();
        adapater=new StoreAdapter(storeActivity.this,storelist);
        liststore.setAdapter(adapater);



        liststore.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Store item = storelist.get(position);
                String store_id = item.getStore_id();
                String store_name = item.getStore_name();
                String contact_number = item.getContact_number();
                String website = item.getWebsite();
                String logo = item.getLogo();
                String cover_image = item.getCover_image();
                String about_store = item.getAbout_store();
                String address = item.getAddress();
                String landmark = item.getLandmark();
                String pincode = item.getPincode();
                String person_name = item.getPerson_name();




                Intent intent = new Intent(storeActivity.this, StoreDetailActivity.class);
                intent.putExtra("store_id", store_id);
                intent.putExtra("store_name", store_name);
                intent.putExtra("contact_number", contact_number);
                intent.putExtra("website", website);
                intent.putExtra("logo", logo);
                intent.putExtra("cover_image", cover_image);
                intent.putExtra("about_store", about_store);
                intent.putExtra("address", address);
                intent.putExtra("landmark", landmark);
                intent.putExtra("pincode", pincode);
                intent.putExtra("person_name", person_name);


                startActivity(intent);
            }
        });

        dialog.show();
        StringRequest str=new StringRequest(Request.Method.POST,WebServices.LOAD_STORE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                dialog.hide();
                try {
                    JSONArray array=new JSONArray(response);
                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject obj=array.getJSONObject(i);
                        Store item=new Store();
                        item.setStore_id(obj.getString("store_id"));
                        item.setStore_name(obj.getString("store_name"));
                        item.setContact_number(obj.getString("contact_number"));
                        item.setAddress(obj.getString("address"));
                        item.setCover_image(obj.getString("cover_image"));
                        item.setLogo(obj.getString("logo"));
                        item.setPerson_name(obj.getString("person_name"));
                        item.setLandmark(obj.getString("landmark"));
                        item.setPincode(obj.getString("pincode"));
                        item.setWebsite(obj.getString("website"));
                        storelist.add(item);
                    }
                    adapater.notifyDataSetChanged();
                } catch (JSONException e) {

                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            dialog.dismiss();
            }
        });
        RequestQueue q=Volley.newRequestQueue(storeActivity.this);
        q.add(str);
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
