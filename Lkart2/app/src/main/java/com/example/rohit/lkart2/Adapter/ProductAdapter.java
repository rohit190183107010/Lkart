package com.example.rohit.lkart2.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rohit.lkart2.ClassFiles.Product;
import com.example.rohit.lkart2.R;
import com.example.rohit.lkart2.WebServices;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ProductAdapter extends BaseAdapter {

    List<Product> productlist;
    Activity activity;

    public ProductAdapter(Activity activity,List<Product> productlist)
    {
        this.productlist=productlist;
        this.activity=activity;
    }


    @Override
    public int getCount() {
        return productlist.size();
    }

    @Override
    public Object getItem(int position) {

        return  productlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override

    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater=activity.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.singledesignproduct, null, true);

        TextView txtproductname=(TextView) rowView.findViewById(R.id.txtproductname);
        TextView txtproductprice=(TextView) rowView.findViewById(R.id.txtproductprice);

        ImageView productimg=(ImageView)rowView.findViewById(R.id.productimg);




        Product item=productlist.get(position);
        txtproductname.setText(item.getProduct_name());
        txtproductprice.setText(item.getNew_price());

        Picasso.with(activity).load(WebServices.BASE_URL_IMAGE+"admin/upload/product/"+item.getProduct_img()).into(productimg);
        return rowView;
    }
}