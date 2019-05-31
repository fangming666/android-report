package com.example.administrator.myapplication.activity.mine;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.util.BaseActivity;

import java.util.Objects;

@SuppressLint("Registered")
public class MyOrder extends BaseActivity {
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate(Bundle saveIn) {
        super.onCreate(saveIn);
        setContentView(R.layout.activity_my_order);
        //修改标题
        setTitle("历史订单");
    }
    
}
