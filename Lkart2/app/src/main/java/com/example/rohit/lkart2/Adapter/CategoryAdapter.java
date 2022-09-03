package com.example.rohit.lkart2.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rohit.lkart2.ClassFiles.Category;
import com.example.rohit.lkart2.R;
import com.example.rohit.lkart2.WebServices;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CategoryAdapter extends BaseAdapter {

    List<Category> categoryAdapterlist;
    Activity activity;

    public CategoryAdapter(Activity activity,List<Category> categoryAdapterlist)
    {
        this.categoryAdapterlist=categoryAdapterlist;
        this.activity=activity;
    }
    @Override
    public int getCount() {
        return categoryAdapterlist.size();

    }

    @Override
    public Object getItem(int position) {
        return categoryAdapterlist.get(position);

    }

    @Override
    public long getItemId(int position) {
        return  position;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=activity.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.singlecategorydesign, null, true);

        TextView txtcatname=(TextView)rowView.findViewById(R.id.txtcatname);
        ImageView txticon=(ImageView)rowView.findViewById(R.id.txticon);


        Category item=categoryAdapterlist.get(position);

        Picasso.with(activity).load(WebServices.BASE_URL_IMAGE+"admin/upload/cat/"+item.getCat_icon()).into(txticon);

        txtcatname.setText(item.getCat_name());

        return  rowView;
    }
}