package com.example.rohit.lkart2;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.rohit.lkart2.ClassFiles.Product;
import com.example.rohit.lkart2.ClassFiles.ProductDetail;
import com.example.rohit.lkart2.ClassFiles.Subcategory;
import com.example.rohit.lkart2.Utility.SessionManager;
import com.ramotion.foldingcell.FoldingCell;

import java.util.HashMap;

public class HomePageActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    NavigationView navigationView;
    SessionManager session;
    Button laptop;
    Button desktop,cam,tv,onmap,shoes,jeans;
    private SliderLayout mDemoSlider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);





        mDemoSlider = (SliderLayout)findViewById(R.id.slider);

        laptop=(Button)findViewById(R.id.laptop);
        desktop=(Button)findViewById(R.id.desktop);
        cam=(Button)findViewById(R.id.cam);
        tv=(Button)findViewById(R.id.tv);
        onmap=(Button)findViewById(R.id.onmap);
        shoes=(Button)findViewById(R.id.shoes);
        jeans=(Button)findViewById(R.id.jeans);

        session=new SessionManager(this);

        HashMap<String,Integer> file_maps = new HashMap<String, Integer>();
        file_maps.put("Mi tv", R.drawable.mi);
        file_maps.put("Desktop ", R.drawable.destop);
        file_maps.put("Laptop offer", R.drawable.loffer);
        file_maps.put("Camera offer", R.drawable.camera);

        for(String name : file_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(file_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.setDuration(4000);



        laptop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(HomePageActivity.this, ProductActivity.class);
                i.putExtra("subcatid", "49");
                i.putExtra("subcatname", "Laptops");
                startActivity(i);
            }
        });
        desktop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(HomePageActivity.this, ProductActivity.class);
                i.putExtra("subcatid","50");
                i.putExtra("subcatname","Desktops");
                startActivity(i);
            }
        });
        cam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(HomePageActivity.this, ProductActivity.class);
                i.putExtra("subcatid","45");
                i.putExtra("subcatname","Cameras");
                startActivity(i);
            }
        });
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(HomePageActivity.this, ProductActivity.class);
                i.putExtra("subcatid","43");
                i.putExtra("subcatname","Televisions");
                startActivity(i);
            }
        });
        shoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(HomePageActivity.this, ProductActivity.class);
                i.putExtra("subcatid","48");
                i.putExtra("subcatname","Shoes");
                startActivity(i);
            }
        });
        jeans.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(HomePageActivity.this, ProductActivity.class);
                i.putExtra("subcatid","47");
                i.putExtra("subcatname","Jeans");
                startActivity(i);
            }
        });
        onmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(HomePageActivity.this, MapDemoActivity.class);
                startActivity(i);
            }
        });




        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View hView =  navigationView.getHeaderView(0);
        TextView profile_name = (TextView)hView.findViewById(R.id.profile_name);
        profile_name.setText(session.getName());

        if(session.isLoggedIn())
        {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.afterloginmenu);
        }
        else
        {
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.activity_home_page_drawer);
        }



    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if(id==R.id.nav_login)
        {
            Intent intent=new Intent(HomePageActivity.this,LoginSignupActivity.class);
            startActivity(intent);
        }
        else if(id==R.id.nav_feedback)
        {
            Intent intent=new Intent(HomePageActivity.this,FeedbackNavActivity.class);
            startActivity(intent);

        }
        else if(id==R.id.nav_product)
        {
            Intent intent=new Intent(HomePageActivity.this,AllProductActivity.class);
            startActivity(intent);

        }
        else if(id==R.id.nav_shop_category)
        {
            Intent intent=new Intent(HomePageActivity.this,CategoryActivity.class);
            startActivity(intent);

        }
        else if(id==R.id.nav_subcategory)
        {
            Intent intent=new Intent(HomePageActivity.this,SubcategoryActivity.class);
            startActivity(intent);

        }
        else if(id==R.id.nav_store)
        {
            Intent intent=new Intent(HomePageActivity.this,storeActivity.class);
            startActivity(intent);

        }
        else if(id==R.id.nav_my_cart)
        {
            Intent intent=new Intent(HomePageActivity.this,CartActivity.class);
            startActivity(intent);

        }
        else if(id==R.id.nav_my_order)
        {
            Intent intent=new Intent(HomePageActivity.this,OrderActivity.class);
            startActivity(intent);

        }
        else if(id==R.id.nav_my_account)
        {
            Intent intent=new Intent(HomePageActivity.this,MyAccountActivity.class);
            startActivity(intent);

        }
        else if(id==R.id.nav_map)
        {
            Intent intent=new Intent(HomePageActivity.this,MapDemoActivity.class);
            startActivity(intent);

        }
        else if(id==R.id.nav_sell_on_lkart)
        {
            Intent intent=new Intent(HomePageActivity.this,SellOnLkartActivity.class);
            startActivity(intent);

        }
        else if(id==R.id.nav_logout)
        {
            session.setLogin(false);
            session.setId("");
            session.setName("");
            navigationView.getMenu().clear();
            navigationView.inflateMenu(R.menu.activity_home_page_drawer);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
