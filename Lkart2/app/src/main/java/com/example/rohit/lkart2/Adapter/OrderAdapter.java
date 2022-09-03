package com.example.rohit.lkart2.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rohit.lkart2.ClassFiles.Category;
import com.example.rohit.lkart2.ClassFiles.Order;
import com.example.rohit.lkart2.ClassFiles.Product;
import com.example.rohit.lkart2.R;
import com.example.rohit.lkart2.WebServices;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderAdapter extends BaseAdapter {

    List<Order> orderlist;
    Activity activity;
    public OrderAdapter(Activity activity,List<Order> orderlist)
    {
        this.orderlist=orderlist;
        this.activity=activity;
    }

    @Override
    public int getCount() {
        return  orderlist.size();

    }

    @Override
    public Object getItem(int position) {
        return  orderlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return  position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=activity.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.singleorderdesign, null, true);

        TextView txtorderid=(TextView) rowView.findViewById(R.id.txtorderid);
        TextView txttotalpayment=(TextView) rowView.findViewById(R.id.txttotalpayment);
        TextView txtaddress=(TextView) rowView.findViewById(R.id.txtaddress);
        TextView txtlandmark=(TextView)rowView.findViewById(R.id.txtlandmark);
        TextView txtcontact=(TextView)rowView.findViewById(R.id.txtcontact);
        TextView txtispay=(TextView)rowView.findViewById(R.id.txtispay);

        TextView txttransactionnumber=(TextView)rowView.findViewById(R.id.txttransactionnumber);
        TextView txtstatus=(TextView)rowView.findViewById(R.id.txtstatus);
        TextView txtorderdatetime=(TextView)rowView.findViewById(R.id.txtorderdatetime);

        TextView edtpincode=(TextView)rowView.findViewById(R.id.edtpincode);



        Order item=orderlist.get(position);
        txtorderid.setText(item.getOrder_id());
        txttotalpayment.setText(item.getTotal_payment());
        txtaddress.setText(item.getAddress());
        txtlandmark.setText(item.getLandmark());
        txtcontact.setText(item.getContact());
        txtispay.setText(item.getIspay());
        txttransactionnumber.setText(item.getTransaction_number());
        txtstatus.setText(item.getStatus());
        txtorderdatetime.setText(item.getOrder_datetime());
        edtpincode.setText(item.getPincode());

        return rowView;

    }
}

