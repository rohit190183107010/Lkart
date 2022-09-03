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
    import com.example.rohit.lkart2.ClassFiles.Category;

    import org.json.JSONArray;
    import org.json.JSONException;
    import org.json.JSONObject;

    import java.util.ArrayList;
    import java.util.List;

    public class CategoryActivity extends AppCompatActivity {

        ListView listcategory;
        CategoryAdapter adapater;
        List<Category> categoryAdapterlist;

        ProgressDialog dialog;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_category);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            setTitle("Shop By Category");

            dialog= new ProgressDialog(this);
            dialog.setMessage("Please Wait..");
            dialog.setCancelable(false);

            listcategory=(ListView)findViewById(R.id.listcategory);

            categoryAdapterlist=new ArrayList<>();
            adapater=new CategoryAdapter(CategoryActivity.this,categoryAdapterlist);
            listcategory.setAdapter(adapater);

            listcategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Category item = categoryAdapterlist.get(position);

                    String catid = item.getCat_id();
                    String catname = item.getCat_name();

                    Intent intent = new Intent(CategoryActivity.this, SubcategoryActivity.class);
                    intent.putExtra("catid", catid);
                    intent.putExtra("catname", catname);
                    startActivity(intent);
                }
            });



            dialog.show();
            StringRequest str=new StringRequest(Request.Method.POST,WebServices.LOAD_CATEGORY, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    dialog.dismiss();
                    try {
                        JSONArray array=new JSONArray(response);
                        for(int i=0;i<array.length();i++)
                        {
                            JSONObject obj=array.getJSONObject(i);
                            Category item=new Category();
                            item.setCat_id(obj.getString("cat_id"));
                            item.setCat_name(obj.getString("cat_name"));
                            item.setCat_icon(obj.getString("cat_icon"));
                            categoryAdapterlist.add(item);
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
            RequestQueue q= Volley.newRequestQueue(CategoryActivity.this);
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
