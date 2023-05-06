package com.example.test;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager mySensorManager;
    private Sensor mySensor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获得传感器：

        mySensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mySensor = mySensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    protected void onResume() {
// Register a listener for the sensor.
        super.onResume();
        mySensorManager.registerListener(this, mySensor, SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        mySensorManager.unregisterListener(this);
    }

    @Override
    protected void onStop() {
        mySensorManager.unregisterListener(this);
        super.onStop();
    }


//处理函数：
    @Override
    public final void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    @Override
    public final void onSensorChanged(SensorEvent event) {
        float light = event.values[0];
        TextView v = (TextView) findViewById(R.id.cur);
        String str0=Float.toString(light);
        v.setText("当前亮度为："+str0);

        String str2;
        TextView v2 = (TextView) findViewById(R.id.name);
        str2=mySensor.getName();
        v2.setText("传感器名称："+str2);

        String str3;
        TextView v3 = (TextView) findViewById(R.id.power);
        str3=Float.toString(mySensor.getPower());
        v3.setText("传感器耗电量为："+str3);

        String str4;
        TextView v4 = (TextView) findViewById(R.id.maxPower);
        str4=Float.toString(mySensor.getMaximumRange());
        v4.setText("传感器最大耗电量为："+str4);

    }

}