package com.example.rohit.lkart2.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rohit.lkart2.ClassFiles.Category;
import com.example.rohit.lkart2.ClassFiles.Product;
import com.example.rohit.lkart2.ClassFiles.Store;
import com.example.rohit.lkart2.R;
import com.example.rohit.lkart2.WebServices;
import com.squareup.picasso.Picasso;

import java.util.List;

public class StoreAdapter extends BaseAdapter {

    List<Store> storelist;
    Activity activity;
public StoreAdapter(Activity activity,List<Store> storelist)
{

    this.storelist=storelist;
    this.activity=activity;
}


    @Override
    public int getCount() { return storelist.size();

    }

    @Override
    public Object getItem(int position) {
        return storelist.get(position);

    }

    @Override
    public long getItemId(int position) {
        return  position;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=activity.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.singlestoredesing, null, true);

        TextView txtstorename=(TextView) rowView.findViewById(R.id.txtstorename);
        TextView txtstoremob=(TextView) rowView.findViewById(R.id.txtstoremob);
        TextView txtstoreaddress=(TextView) rowView.findViewById(R.id.txtstoreaddress);

        Store item=storelist.get(position);

        txtstorename.setText(item.getStore_name());
        txtstoremob.setText(item.getContact_number());
        txtstoreaddress.setText(item.getAddress());

        ImageView cover_image=(ImageView)rowView.findViewById(R.id.cover_image);
        Picasso.with(activity).load(WebServices.BASE_URL_IMAGE+"admin/upload/store/"+item.getCover_image()).into(cover_image);






        return  rowView;

    }
}