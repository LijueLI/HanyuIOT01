package com.hanyutech.hanyuiot01;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Comment;

import java.util.ArrayList;
import java.util.List;

public class Datadisplay extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private TextView temp,humi,lan,lon;
    private Button map;
    private List<Data> Datals = new ArrayList<Data>();
    private double latitude = 0,longitude = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datadisplay);

        temp = findViewById(R.id.temperature);
        humi = findViewById(R.id.humidity);
        lan = findViewById(R.id.lan);
        lon = findViewById(R.id.lon);
        map = findViewById(R.id.map);

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
                temp.setText(String.valueOf(Datals.get(Datals.size()-1).getTempture()));
                humi.setText(String.valueOf(Datals.get(Datals.size()-1).getHumidity()));
                lan.setText(String.valueOf(Datals.get(Datals.size()-1).getLatitude()));
                lon.setText(String.valueOf(Datals.get(Datals.size()-1).getLongitude()));
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

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Datadisplay.this,Map.class);
                startActivity(intent);
            }
        });
    }

}
