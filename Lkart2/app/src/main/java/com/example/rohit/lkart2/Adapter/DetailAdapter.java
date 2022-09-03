package com.example.rohit.lkart2.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rohit.lkart2.ClassFiles.Cart;
import com.example.rohit.lkart2.ClassFiles.Detail;
import com.example.rohit.lkart2.R;
import com.example.rohit.lkart2.WebServices;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailAdapter extends BaseAdapter {
    List<Detail> orderdetaillist;
    Activity activity;

    public  DetailAdapter(Activity activity,List<Detail> orderdetaillist)
    {
        this.orderdetaillist=orderdetaillist;
        this.activity=activity;
    }


    @Override
    public int getCount() {
        return orderdetaillist.size();
    }

    @Override
    public Object getItem(int position) {
        return orderdetaillist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return  position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {

        LayoutInflater inflater=activity.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.singleorderdetaildesign, null, true);

        TextView txtproductname=(TextView) rowView.findViewById((R.id.txtproductname));
        TextView  txtprice=(TextView)rowView.findViewById(R.id.txtprice);
        TextView  txtqty=(TextView)rowView.findViewById(R.id.txtqty);
        TextView  txtsize=(TextView)rowView.findViewById(R.id.txtsize);

        Detail item=orderdetaillist.get(position);
        txtproductname.setText(item.getProduct_name());
        txtprice.setText(item.getPrice());
        txtqty.setText(item.getQty());
        txtsize.setText(item.getSize());

        ImageView txticon=(ImageView)rowView.findViewById(R.id.txticon);
        Picasso.with(activity).load(WebServices.BASE_URL_IMAGE+"admin/upload/product/"+item.getProduct_img()).into(txticon);
        return  rowView;
    }
}