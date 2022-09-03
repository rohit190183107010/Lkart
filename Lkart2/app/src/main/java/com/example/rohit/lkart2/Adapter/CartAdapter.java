package com.example.rohit.lkart2.Adapter;

import android.app.Activity;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.rohit.lkart2.ClassFiles.Cart;
import com.example.rohit.lkart2.ClassFiles.Category;
import com.example.rohit.lkart2.R;
import com.example.rohit.lkart2.WebServices;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends BaseAdapter {
    List<Cart> cartlist;
    Activity activity;

    public  CartAdapter(Activity activity,List<Cart> cartlist)
    {
        this.cartlist=cartlist;
        this.activity=activity;
    }


    @Override
    public int getCount() {
        return cartlist.size();
    }

    @Override
    public Object getItem(int position) {
            return cartlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return  position;
    }


    @Override
    public View getView(final int position, View convertView, final ViewGroup parent)
    {

        LayoutInflater inflater=activity.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.cartdesign, null, true);

        TextView txtproductname=(TextView) rowView.findViewById((R.id.txtproductname));
        TextView  txtprice=(TextView)rowView.findViewById(R.id.txtprice);
        TextView  txtqty=(TextView)rowView.findViewById(R.id.txtqty);
        TextView  txtsize=(TextView)rowView.findViewById(R.id.txtsize);

        ImageView btnadd = (ImageView) rowView.findViewById(R.id.btnadd);
        ImageView btnminus = (ImageView) rowView.findViewById(R.id.btnminus);

        Cart item=cartlist.get(position);
        txtproductname.setText(item.getProduct_name());
        txtprice.setText(String.valueOf(Float.parseFloat(item.getPrice()) * Float.parseFloat(item.getQty())));
        txtqty.setText("Quantity : " + item.getQty());
        txtsize.setText(item.getSize());

        ImageView txticon=(ImageView)rowView.findViewById(R.id.txticon);
        Picasso.with(activity).load(WebServices.BASE_URL_IMAGE+"admin/upload/product/"+item.getProduct_img()).into(txticon);

        btnadd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ListView) parent).performItemClick(view, position, 0); // Let the event be handled in onItemClick()
            }
        });
        btnminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((ListView) parent).performItemClick(view, position, 0); // Let the event be handled in onItemClick()
            }
        });

        return  rowView;
    }
}