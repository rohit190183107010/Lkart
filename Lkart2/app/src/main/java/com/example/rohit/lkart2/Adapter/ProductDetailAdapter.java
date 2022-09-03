package com.example.rohit.lkart2.Adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.rohit.lkart2.ClassFiles.Product;

import java.util.List;

public class ProductDetailAdapter extends BaseAdapter {

    List<Product> productdetaillist;
    Activity activity;


    public ProductDetailAdapter(Activity activity, List<Product> productdetaillist)
    {
        this.productdetaillist=productdetaillist;
        this.activity=activity;
    }
    @Override
    public int getCount() {
        return productdetaillist.size();
    }

    @Override
    public Object getItem(int position) {
        return productdetaillist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
             return null;
    }
}