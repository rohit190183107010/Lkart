package com.example.rohit.lkart2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.rohit.lkart2.Adapter.SubcategoryAdapter;
import com.example.rohit.lkart2.ClassFiles.Category;
import com.example.rohit.lkart2.ClassFiles.Subcategory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SubcategoryActivity extends AppCompatActivity {

    ListView listsubcategory;
    SubcategoryAdapter adapater;
    List<Subcategory> subcategoryAdapterlist;
    ProgressDialog dialog;
    String catid,catname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_subcategory);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        catid=getIntent().getStringExtra("catid");
        catname=getIntent().getStringExtra("catname");

        getSupportActionBar().setTitle(catname);
        dialog=new ProgressDialog(this);
        dialog.setMessage("Please Wait..");
        dialog.setCancelable(false);


        listsubcategory=(ListView)findViewById(R.id.listsubcategory);

        subcategoryAdapterlist=new ArrayList<>();
        adapater=new SubcategoryAdapter(SubcategoryActivity.this,subcategoryAdapterlist);
        listsubcategory.setAdapter(adapater);

        listsubcategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Subcategory item=subcategoryAdapterlist.get(position);

                String subcatid=item.getSubcat_id();
                String subcatname=item.getSubcat_name();

                Intent intent=new Intent(SubcategoryActivity.this,ProductActivity.class);
                intent.putExtra("subcatid",subcatid);
                intent.putExtra("subcatname",subcatname);
                startActivity(intent);

            }
        });


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        dialog.show();
        StringRequest str=new StringRequest(Request.Method.POST,WebServices.LOAD_SUBCATEGORY, new Response.Listener<String>() {

            @Override

            public void onResponse(String response) {
                dialog.dismiss();
                try {
                    JSONArray array=new JSONArray(response);
                    for(int i=0;i<array.length();i++)
                    {
                        JSONObject obj=array.getJSONObject(i);
                        Subcategory item=new Subcategory();
                        item.setSubcat_id(obj.getString("subcat_id"));
                        item.setCat_id(obj.getString("cat_id"));
                        item.setSubcat_name(obj.getString("subcat_name"));
                        item.setSubcat_icon(obj.getString("subcat_icon"));
                        subcategoryAdapterlist.add(item);

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
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params=new HashMap<>();
                params.put("catid",catid);
                return params;
            }
        };
        RequestQueue q= Volley.newRequestQueue(SubcategoryActivity.this);
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
