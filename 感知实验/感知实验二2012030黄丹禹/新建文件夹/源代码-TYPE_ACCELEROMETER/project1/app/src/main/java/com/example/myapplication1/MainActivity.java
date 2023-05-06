package com.example.myapplication1;

import android.annotation.TargetApi;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements SensorEventListener {
    TextView exTxt1;
    TextView exTxt2;
    SensorManager sensorManager;
    float sumx = 0;
    float sumy = 0;
    float sumz = 0;
    float sumg = 0;
    float sum;
    float gap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        exTxt1 = findViewById(R.id.txt1);
        exTxt2 = findViewById(R.id.txt2);
        //获取系统的传感器管理服务
        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onResume() {
        super.onResume();
        //2.为系统的加速度传感器注册监听事件
        sensorManager.registerListener(//注册监听
                this,//监听器SensorListener对象
                sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),//传感器的类型为加速度
                SensorManager.SENSOR_DELAY_UI//传感器事件传递的频度
        );
    }

    @Override
    protected void onPause() {
        super.onPause();
        //取消注册监听器
        sensorManager.unregisterListener(this);
    }

    //传感器的1值发生变化回调
    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        float[] values = sensorEvent.values;

        /*
        对xyz三个方向分别积分
        sumx=sumx+values[0];
        sumy=sumy+values[1];
        sumz=sumz+values[2];
        sumg=sumg+(float) 9.8;
        sum=sumx*sumx+sumy*sumy+sumz*sumz;
        gap=Math.abs(sum);
        */

        float a;
        a = values[0] * values[0] + values[1] * values[1] + values[2] * values[2];
        String sb = "x方向加速度：" +
                values[0] +
                "\nY轴方向加速度：" +
                values[1] +
                "\nZ轴方向加速度：" +
                values[2] +
                "\n" + "判断值a:"+a;
        exTxt1.setText(sb);
        String str1 = "此时手机的状态：静止";
        String str2 = "此时手机的状态：运动";

        if(a>110){
            exTxt2.setText(str2);
        }
        else{
            exTxt2.setText(str1);
        }


        /*
        if (gap>5) {
            exTxt2.setText(str2);
        } else {
            exTxt2.setText(str1);
        }*/
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }
}