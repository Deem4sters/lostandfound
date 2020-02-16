package com.softwarica.lostandfound;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.softwarica.lostandfound.LF.FoundActivity;


import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    private CircleImageView cvthings, cvhuman,cvpets,cvall;
    private ImageView ivlost, ivfound,ivfeedback, ivmap, ivprofile,ivabout;

    TextView ProximitySensor;

    SensorManager mySensorManager;
    Sensor myProximitSensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cvthings = findViewById(R.id.cvthings);
        cvhuman = findViewById(R.id.cvhuman);
        cvpets = findViewById(R.id.cvpets);
        cvall = findViewById(R.id.cvall);

        ivlost = findViewById(R.id.ivlost);
        ivfound = findViewById(R.id.ivfound);
        ivfeedback = findViewById(R.id.ivfeedback);
        ivmap = findViewById(R.id.ivmap);
        ivprofile = findViewById(R.id.ivprofile);
        ivabout = findViewById(R.id.ivabout);

        ProximitySensor = findViewById(R.id.proximityvalue);


        //Proximity Sensor
        mySensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        myProximitSensor = mySensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY);
        if (myProximitSensor == null) {
            ProximitySensor.setText("NO proximity sensor");
        } else {
            mySensorManager.registerListener(sensoreventlistener, myProximitSensor, SensorManager.SENSOR_DELAY_NORMAL);
        }

        cvthings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddLostActivity.class));
            }
        });

        ivlost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LostActivity.class));
            }
        });
        ivfound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FoundActivity.class));
            }
        });
        ivfeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, FeedbackActivity.class));
            }
        });
        ivmap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, MapsActivity.class));
            }
        });
        ivprofile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ProfileActivity.class));
            }
        });
        ivabout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AboutusActivity.class));
            }
        });

        }
    SensorEventListener sensoreventlistener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            WindowManager.LayoutParams params = MainActivity.this.getWindow().getAttributes();
            if (sensorEvent.sensor.getType()==Sensor.TYPE_PROXIMITY){
                if (sensorEvent.values[0]==0) {
                    params.flags |= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                    params.screenBrightness = 0;
                    getWindow().setAttributes(params);
                } else {
                    params.flags |= WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON;
                    params.screenBrightness = -1f;
                    getWindow().setAttributes(params);
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int i) {

        }
    };
    }