package com.hanyutech.hanyuiot01;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button Datadisplay,Wifi_connetc;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Datadisplay = findViewById(R.id.DataDisplay);
        Datadisplay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, DeviceChoose.class);
                startActivity(intent);
            }
        });
        Wifi_connetc = findViewById(R.id.Wificonnect);
        Wifi_connetc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,EsptouchDemoActivity.class);
                startActivity(intent);
            }
        });
    }
}
