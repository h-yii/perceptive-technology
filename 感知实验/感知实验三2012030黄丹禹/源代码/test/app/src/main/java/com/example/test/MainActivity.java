package com.example.test;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
//import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;

import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;


public class MainActivity extends AppCompatActivity implements SensorEventListener {
    private SensorManager mySensorManager;
    private int flag=1;
    private Sensor mySensor;
    private float before;
    private float after;
    ImageView image;  //指南针图片
    float currentDegree = 0f; //指南针图片转过的角度
    private int count=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //获得传感器：

        image = (ImageView)findViewById(R.id.imageView3);
        mySensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mySensor = mySensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        mySensorManager.registerListener(this,
                mySensor,
                SensorManager.SENSOR_DELAY_GAME);
    }
    public void onClicked(View view){
        flag=flag*(-1);
    }

    @Override
    protected void onResume() {
        super.onResume();

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
        if(flag<0) return;

        float degreez = Math.round(event.values[0] * 100) / 100; //获取z转过的角度
        float degreex = Math.round(event.values[1] * 100) / 100; //获取x转过的角度
        float degreey = Math.round(event.values[2] * 100) / 100; //获取y转过的角度

        after=degreez;
        count++;
        if(count>=20){
            straight(degreez);
        }
        before=degreez;

        TextView v = (TextView) findViewById(R.id.cur);
        String str0=Float.toString(degreez);
        String str1=Float.toString(degreex);
        String str2=Float.toString(degreey);
        v.setText("绕z轴转过角度："+str0+";\n绕x轴转过角度："+str1+";\n绕y轴转过角度："+str2+";\n");


        //穿件旋转动画
        RotateAnimation ra = new RotateAnimation(currentDegree,
                degreez,
                Animation.RELATIVE_TO_SELF,
                0.5f ,
                Animation.RELATIVE_TO_SELF,
                0.5f);
        ra.setDuration(200);//动画持续时间

        image.startAnimation(ra);
        currentDegree = -degreez;

    }



    public void straight(float degreez){
        TextView condition = (TextView) findViewById(R.id.textView);
        if(after-before>0.44||before-after>0.44){
            condition.setText("状态：非直行;");
        }else{
            condition.setText("状态：正在直行;");
        }
        count=0;
    }
}