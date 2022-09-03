package com.example.rohit.lkart2.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rohit.lkart2.ClassFiles.Subcategory;
import com.example.rohit.lkart2.R;
import com.example.rohit.lkart2.WebServices;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SubcategoryAdapter extends BaseAdapter {

    List<Subcategory> subcategoryAdapterlist;
    Activity activity;
    public SubcategoryAdapter(Activity activity,List<Subcategory> subcategoryAdapterlist)
    {
        this.subcategoryAdapterlist=subcategoryAdapterlist;
        this.activity=activity;
    }
    @Override
    public int getCount() {
        return subcategoryAdapterlist.size();

    }

    @Override
    public Object getItem(int position) {
        return subcategoryAdapterlist.get(position);

    }

    @Override
    public long getItemId(int position) {
        return  position;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater=activity.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.singlesubcategorydesign, null, true);

        TextView txtsubcatname=(TextView)rowView.findViewById(R.id.txtsubcatname);
        ImageView txtsubcaticon=(ImageView)rowView.findViewById(R.id.txtsubcaticon);

      Subcategory item=subcategoryAdapterlist.get(position);

        txtsubcatname.setText(item.getSubcat_name());
        Picasso.with(activity).load(WebServices.BASE_URL_IMAGE+"admin/upload/subcat/"+item.getSubcat_icon()).into(txtsubcaticon);

        txtsubcatname.setText(item.getSubcat_name());

    return  rowView;
    }
}