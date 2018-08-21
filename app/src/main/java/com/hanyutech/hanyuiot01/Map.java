package com.hanyutech.hanyuiot01;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Map extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private List<Data> Datals = new ArrayList<Data>();
    private boolean End = false;
    private double latitude = 0,longitude = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        DatabaseReference Obj = database.getReference("object");

        Obj.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Log.d("fire", "onChildAdded:" + dataSnapshot.getKey());
                Log.d("fire",dataSnapshot.child("flat").getValue(String.class));
                Data d = new Data(0,0,0,0);
                try {
                    d.setHumidity(Double.parseDouble(dataSnapshot.child("humidity").getValue(String.class)));
                    d.setTempture(Double.parseDouble(dataSnapshot.child("temperature").getValue(String.class)));
                    d.setLatitude(Double.parseDouble(dataSnapshot.child("flat").getValue(String.class)));
                    d.setLongitude(Double.parseDouble(dataSnapshot.child("flon").getValue(String.class)));
                }catch (NumberFormatException e){}
                Datals.add(d);
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Add a marker in Sydney and move the camera
        mapupdate();
    }

    private void mapupdate(){
        new Thread(new Runnable() {
            LatLng current;
            @Override
            public void run() {
                while(!End){
                    try {
                        current = new LatLng(Datals.get(Datals.size() - 1).getLatitude(), Datals.get(Datals.size() - 1).getLongitude());
                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {}
                    } catch (ArrayIndexOutOfBoundsException e) { }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mMap.clear();
                            mMap.addMarker(new MarkerOptions().position(current));
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(current));
                        }
                    });
                }
            }
        }).start();
    }

    @Override
    protected void onDestroy() {
        End = true;
        super.onDestroy();
    }
}
