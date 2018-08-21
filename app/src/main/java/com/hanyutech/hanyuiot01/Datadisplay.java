package com.hanyutech.hanyuiot01;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Datadisplay extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private TextView temp,humi,lan,lon;
    private Button map;
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
        DatabaseReference humidity = Obj.child("humidity");
        DatabaseReference temprtature = Obj.child("temperature");
        DatabaseReference latitude = Obj.child("flat");
        DatabaseReference longitude = Obj.child("flon");

        humidity.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
               String value ="Humidity : "+dataSnapshot.getValue(String.class);
               humi.setText(value);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        temprtature.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = "Temperature : "+dataSnapshot.getValue(String.class);
                temp.setText(value);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        latitude.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = "Latitude : "+dataSnapshot.getValue(String.class);
                lan.setText(value);
                Datadisplay.this.latitude = Double.parseDouble(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        longitude.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String value = "Longitude : "+dataSnapshot.getValue(String.class);
                lon.setText(value);
                Datadisplay.this.longitude = Double.parseDouble(dataSnapshot.getValue(String.class));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Datadisplay.this,Map.class);
                intent.putExtra("latitude",Datadisplay.this.latitude);
                intent.putExtra("longitude",Datadisplay.this.longitude);
                startActivity(intent);
            }
        });
    }

}
