package com.example.rohit.lkart2;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MapDemoActivity extends AppCompatActivity  implements OnMapReadyCallback {
    private static final int REQUEST_LOCATION = 1;
    LocationManager locationManager;
    String lattitude, longitude;
    private GoogleMap mMap;
    TextView txterror;
    String Latti = "", Long = "", Address = "", Mobileno = "";

    //
    private ViewGroup infoWindow;
    private TextView infoTitle;
    private TextView infoSnippet;
    private TextView doctorNameTv;
    private TextView contactNoValue;
    private TextView satOpenTv;
    private Button infoButton;
    private OnInfoWindowElemTouchListener infoButtonListener;

    String shop_id;
    String typename;
    String shop_name;
    String shop_location;
    String LATTI;
    String LONG;
    String type_id;
    String mobile_no;
    String email_id;

    String SHOP_NAME;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_demo);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MapDemoActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, 101);
        }

        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(MapDemoActivity.this);


    }

    @Override
    public void onMapReady(final GoogleMap map) {
//DO WHATEVER YOU WANT WITH GOOGLEMAP
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        map.setMyLocationEnabled(true);
        map.setTrafficEnabled(true);
        map.setIndoorEnabled(true);
        map.setBuildingsEnabled(true);
        map.getUiSettings().setZoomControlsEnabled(true);


        map.setOnMyLocationChangeListener(new GoogleMap.OnMyLocationChangeListener() {
            @Override
            public void onMyLocationChange(Location location) {
                String Lati = String.valueOf(location.getLatitude());
                String Long = String.valueOf(location.getLongitude());
            }
        });


        StringRequest str=new StringRequest(Request.Method.POST, WebServices.LOAD_STORE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONArray array=new JSONArray(response);
                    if (array.length()==0){


                    }else {

                        for(int i=0;i<array.length();i++)
                        {
                            JSONObject obj=array.getJSONObject(i);

                            SHOP_NAME = obj.getString("store_name") + "," +obj.getString("store_id")+ "," +obj.getString("contact_number")+ "," +obj.getString("website")+ "," +obj.getString("about_store")+ "," +obj.getString("landmark")+ "," +obj.getString("pincode")+ "," +obj.getString("person_name")+ "," +obj.getString("pincode")+ "," +obj.getString("logo")+ "," +obj.getString("cover_image");
                            map.addMarker(new MarkerOptions().title(obj.getString("address")).snippet(SHOP_NAME).position(new LatLng(Double.valueOf(obj.getString("lattitude")), Double.valueOf(obj.getString("longtitude")))));

                        }

                            //final MapFragment mapFragment = (MapFragment)getFragmentManager().findFragmentById(R.id.map);
                            final MapWrapperLayout mapWrapperLayout = (MapWrapperLayout)findViewById(R.id.map_relative_layout);
                            //final GoogleMap map = mapFragment.getMap();

                            mapWrapperLayout.init(map, getPixelsFromDp(MapDemoActivity.this, 39 + 20));

                            final ViewGroup infoWindow = (ViewGroup)getLayoutInflater().inflate(R.layout.custom_infowindow, null);
                            final TextView  infoTitle = (TextView)infoWindow.findViewById(R.id.nametv);
                            final TextView  infoSnippet = (TextView)infoWindow.findViewById(R.id.addressTv);
                            final Button  infoButton = (Button)infoWindow.findViewById(R.id.clinicType);
                            final TextView  doctorNameTv = (TextView)infoWindow.findViewById(R.id.doctorNameTv);
                            final TextView  contactNoValue = (TextView)infoWindow.findViewById(R.id.contactNoValue);
                            final TextView  satOpenTv = (TextView)infoWindow.findViewById(R.id.satOpenTv);


                            final OnInfoWindowElemTouchListener infoButtonListener = new OnInfoWindowElemTouchListener(infoButton,
                                    getResources().getDrawable(R.drawable.round_but_green_sel), //btn_default_normal_holo_light
                                    getResources().getDrawable(R.drawable.round_but_green_sel)) //btn_default_pressed_holo_light
                            {
                                @Override
                                protected void onClickConfirmed(View v, Marker marker) {
                                    String[] strdata = marker.getSnippet().split(",");

                                    Intent i=new Intent(MapDemoActivity.this,StoreDetailActivity.class);
                                    i.putExtra("store_name",strdata[0].toString());
                                    i.putExtra("contact_number",strdata[2].toString());
                                    i.putExtra("website",strdata[3].toString());
                                    i.putExtra("about_store",strdata[4].toString());
                                    i.putExtra("address",marker.getTitle());
                                    i.putExtra("landmark",strdata[5].toString());
                                    i.putExtra("pincode",strdata[6].toString());
                                    i.putExtra("person_name",strdata[7].toString());
                                    i.putExtra("pincode",strdata[8].toString());
                                    i.putExtra("logo",strdata[9].toString());
                                    i.putExtra("cover_image",strdata[10].toString());
                                    startActivity(i);
                                }
                            };
                            infoButton.setOnTouchListener(infoButtonListener);

                            map.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
                                @Override
                                public View getInfoWindow(Marker marker) {
                                    return null;
                                }

                                @Override
                                public View getInfoContents(Marker marker) {

                                    String[] strdata = marker.getSnippet().split(",");
                                    infoSnippet.setText(marker.getTitle());
                                    infoTitle.setText(strdata[0].toString());
                                    infoButtonListener.setMarker(marker);
                                    doctorNameTv.setText(strdata[1].toString());
                                    mapWrapperLayout.setMarkerWithInfoWindow(marker, infoWindow);

                                    return infoWindow;
                                }
                            });

                        }
                    } catch (JSONException e1) {

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {


            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params=new HashMap<>();
                params.put("shop_id","0");
                return params;
            }
        };

        RequestQueue q= Volley.newRequestQueue(MapDemoActivity.this);
        q.add(str);

    }

    public static int getPixelsFromDp(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int)(dp * scale + 0.5f);
    }
}
