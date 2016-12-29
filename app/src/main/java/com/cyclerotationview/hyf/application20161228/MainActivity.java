package com.cyclerotationview.hyf.application20161228;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

public class MainActivity extends AppCompatActivity {


    /*
    *
    * */
    private CycleRotationView mCycleView ;

    private static  int[] images = {R.drawable.aa,R.drawable.ad,R.drawable.af,
                            R.drawable.ag,R.drawable.ah,R.drawable.aj,
                            R.drawable.as,R.drawable.aq} ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        mCycleView = (CycleRotationView) findViewById(R.id.id_cycleView);
        Log.e("Tag", "result = " + (images == null));
        Log.e("Tag", "result = " + (mCycleView == null));
        //设置图片
        mCycleView.setmImages(images);
        //开启循环播放视图
        mCycleView.openCycleView();
        //开启自动播放
        mCycleView.startAutoCycle();


    }



}
