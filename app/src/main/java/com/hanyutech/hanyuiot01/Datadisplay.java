package com.hanyutech.hanyuiot01;

import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Set;

public class Datadisplay extends AppCompatActivity implements OnMapReadyCallback{

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private TextView temp,humi,lat,lon,pm25,pm10,pm100,maptitile,wifi;
    private MapView mapViewGoogle;
    private com.amap.api.maps2d.MapView mapViewGD;
    private GoogleMap googleMap;
    private AMap aMap = null;
    private static final String MAP_VIEW_BUNDLE_KEY = "AIzaSyCnK90HIqo4kvvH6-wFqvJbWGHmWfdBqvY";
    private Double Longitude = 0.0,Latitude = 0.0;
    private Button SW_Google,SW_GD,Center;
    private Boolean First = true;
    private LinearLayout PM10,PM25,PM100,H,T,Lat,Lon;
    private Iterable<DataSnapshot> iterable;
    private String Devicename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datadisplay);
        Intent intent = getIntent();
        Devicename = intent.getStringExtra("Devicename");

        Bundle mapViewBundle = null;
        if(savedInstanceState != null){
            mapViewBundle = savedInstanceState.getBundle(MAP_VIEW_BUNDLE_KEY);
        }
        mapViewGoogle = findViewById(R.id.mapView);
        mapViewGD = findViewById(R.id.map);
        mapViewGoogle.onCreate(mapViewBundle);
        mapViewGD.onCreate(mapViewBundle);
        mapViewGoogle.getMapAsync(this);

        if (aMap == null) {
            aMap = mapViewGD.getMap();
        }

        temp = findViewById(R.id.Temp_Value);
        humi = findViewById(R.id.Humi_Value);
        lat = findViewById(R.id.Lat_Value);
        lon = findViewById(R.id.Long_Value);
        pm10 = findViewById(R.id.PM10_Value);
        pm25 = findViewById(R.id.PM25_Value);
        pm100 = findViewById(R.id.PM100_Value);
        wifi = findViewById(R.id.Wifi_Value);
        maptitile = findViewById(R.id.maptitle);
        SW_Google = findViewById(R.id.google);
        SW_GD = findViewById(R.id.GD);
        Center = findViewById(R.id.Center);
        PM10 = findViewById(R.id.PM10_L);
        PM25 = findViewById(R.id.PM25_L);
        PM100 = findViewById(R.id.PM100_L);

        H = findViewById(R.id.H_L);
        T = findViewById(R.id.T_L);
        Lat = findViewById(R.id.Lat_L);
        Lon = findViewById(R.id.Lon_L);

        SettingDBHelper SetDB = new SettingDBHelper(Datadisplay.this);
        Cursor cursor = SetDB.selectfromID(1);
        cursor.moveToFirst();
        if(cursor.getCount() != 0){
            if(cursor.getInt(cursor.getColumnIndexOrThrow("_PM")) == 0){
                PM10.setVisibility(View.INVISIBLE);
                PM25.setVisibility(View.INVISIBLE);
                PM100.setVisibility(View.INVISIBLE);
            }
            if(cursor.getInt(cursor.getColumnIndexOrThrow("_HT")) == 0){
                H.setVisibility(View.INVISIBLE);
                T.setVisibility(View.INVISIBLE);
            }
            if(cursor.getInt(cursor.getColumnIndexOrThrow("_GPS")) == 0){
                Lat.setVisibility(View.INVISIBLE);
                Lon.setVisibility(View.INVISIBLE);
                SW_GD.setVisibility(View.INVISIBLE);
                SW_Google.setVisibility(View.INVISIBLE);
                mapViewGD.setVisibility(View.INVISIBLE);
                mapViewGoogle.setVisibility(View.INVISIBLE);
                maptitile.setVisibility(View.INVISIBLE);
                Center.setVisibility(View.INVISIBLE);
            }
        }



        DatabaseReference root = database.getReference("Devices");
        DatabaseReference Device = root.child(Devicename);
        DatabaseReference Obj = Device.child("set");
        DatabaseReference PM10 = Obj.child("PM10");
        DatabaseReference PM100 = Obj.child("PM100");
        DatabaseReference PM25 = Obj.child("PM25");
        DatabaseReference flat = Obj.child("flat");
        DatabaseReference flon = Obj.child("flon");
        DatabaseReference humidity = Obj.child("humidity");
        DatabaseReference temperature = Obj.child("temperature");
        DatabaseReference Wifi = Obj.child("SSID");

        //.setValue(null);

        Obj.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                
                Latitude=Double.parseDouble(dataSnapshot.child("flat").getValue(String.class));
                Longitude=Double.parseDouble(dataSnapshot.child("flon").getValue(String.class));
                googleMap.clear();
                aMap.clear();
                LatLng current = new LatLng(Latitude,Longitude);
                com.amap.api.maps2d.model.LatLng currentGD = new com.amap.api.maps2d.model.LatLng(Latitude,Longitude);
                googleMap.addMarker(new MarkerOptions().position(current).title(Latitude.toString()+"  "+Longitude.toString()));
                aMap.addMarker(new com.amap.api.maps2d.model.MarkerOptions().position(currentGD));
                if(First) {
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(current));
                    aMap.moveCamera(com.amap.api.maps2d.CameraUpdateFactory.newLatLngZoom(currentGD, 15));
                    First = false;
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        PM10.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pm10.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        PM100.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pm100.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        PM25.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                pm25.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        flat.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lat.setText(dataSnapshot.getValue().toString());
                Latitude = Double.parseDouble(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        flon.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lon.setText(dataSnapshot.getValue().toString());
                Longitude = Double.parseDouble(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        humidity.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                humi.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        temperature.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                temp.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        Wifi.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                wifi.setText(dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        SW_Google.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapViewGD.setVisibility(View.INVISIBLE);
                mapViewGoogle.setVisibility(View.VISIBLE);
                maptitile.setText(R.string.googlemap);
            }
        });

        SW_GD.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mapViewGoogle.setVisibility(View.INVISIBLE);
                mapViewGD.setVisibility(View.VISIBLE);
                maptitile.setText(R.string.GDmap);

            }
        });
        Center.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LatLng current = new LatLng(Latitude,Longitude);
                com.amap.api.maps2d.model.LatLng currentGD = new com.amap.api.maps2d.model.LatLng(Latitude,Longitude);
                googleMap.moveCamera(CameraUpdateFactory.newLatLng(current));
                aMap.moveCamera(com.amap.api.maps2d.CameraUpdateFactory.newLatLngZoom(currentGD,15));
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Bundle mapViewBundle = outState.getBundle(MAP_VIEW_BUNDLE_KEY);
        if(mapViewBundle == null){
            mapViewBundle = new Bundle();
            outState.putBundle(MAP_VIEW_BUNDLE_KEY,mapViewBundle);
        }
        mapViewGD.onSaveInstanceState(outState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapViewGoogle.onResume();
        mapViewGD.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapViewGoogle.onResume();
        mapViewGD.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapViewGoogle.onResume();
        mapViewGD.onResume();
    }

    @Override
    protected void onDestroy() {
        mapViewGoogle.onResume();
        mapViewGD.onResume();
        super.onDestroy();
    }

    @Override
    protected void onPause() {
        mapViewGoogle.onResume();
        mapViewGD.onResume();
        super.onPause();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapViewGoogle.onResume();
        mapViewGD.onResume();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }
}
