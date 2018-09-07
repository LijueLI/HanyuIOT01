package com.hanyutech.hanyuiot01;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class DeviceChoose extends AppCompatActivity {

    private FirebaseDatabase database = FirebaseDatabase.getInstance();
    ListView DevicesL;
    List<String> Devices = new ArrayList<String>();
    private ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_device_choose);
        setTitle("設備選擇");

        DatabaseReference Obj = database.getReference("DeviceID");

        Obj.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Devices.add(dataSnapshot.getKey());
                adapter.notifyDataSetChanged();
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
        adapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1,
                Devices);
        DevicesL = findViewById(R.id.DeviceList);
        DevicesL.setAdapter(adapter);

        DevicesL.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Device = Devices.get(position);
                Intent intent = new Intent(DeviceChoose.this,Datadisplay.class);
                intent.putExtra("Devicename",Device);
                startActivity(intent);
            }
        });
    }
}
