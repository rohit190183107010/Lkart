package com.example.rohit.lkart2.Adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.rohit.lkart2.ClassFiles.Product;
import com.example.rohit.lkart2.ClassFiles.Store;

import java.util.List;

public class StoreDetailAdapter extends BaseAdapter {
    List<Store> storeList;
    Activity activity;

    public  StoreDetailAdapter(Activity activity,List<Store> storeList)
    {
        this.storeList=storeList;
        this.activity=activity;

    }

    @Override
    public int getCount() {return storeList.size();

    }

    @Override
    public Object getItem(int position) {return storeList.get(position);

    }

    @Override
    public long getItemId(int position) {return position;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {return null;

    }
}