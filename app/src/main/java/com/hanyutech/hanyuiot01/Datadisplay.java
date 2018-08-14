package com.hanyutech.hanyuiot01;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Datadisplay extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    private TextView temp,humi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_datadisplay);

        temp = findViewById(R.id.temperature);
        humi = findViewById(R.id.humidity);
        DatabaseReference Obj = database.getReference("object");
        DatabaseReference humidity = Obj.child("humidity");
        DatabaseReference temprtature = Obj.child("temperature");

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
    }

}
