package com.example.administrator.myapplication.activity.mine;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.administrator.myapplication.JsonData.ChildJson;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.util.BaseActivity;
import com.google.gson.Gson;

import java.util.List;

public class MyFamily extends BaseActivity {
    public List<ChildJson.DataBean> childData;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate(Bundle saveIn) {
        super.onCreate(saveIn);
        setContentView(R.layout.activity_my_family);
        //设置标题
        setTitle("家庭成员");
        gainChildData();
    }

    //获得个人中心传过来数据
    protected void gainChildData() {
        Gson gson = new Gson();
        Intent intent = getIntent();
        String childJson = intent.getStringExtra("childData");
        assert childJson != null;
        ChildJson result = gson.fromJson(childJson, ChildJson.class);
        childData = result.getData();
        createLayout(childData, R.id.familyChild);
    }

    //创建控件
    @SuppressLint("WrongConstant")
    private void createLayout(List<ChildJson.DataBean> result, int dom) {
        //初始化主体，获得外面大的盒子控件
        RecyclerView childInfo = (RecyclerView) findViewById(dom);

        //设置recyclerView管理器
        childInfo.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        //初始化适配器
        FamilyChild myChildList = new FamilyChild(result);
        //设置添加或删除item时的动画，这里使用默认动画
        childInfo.setItemAnimator(new DefaultItemAnimator());
        //设置适配器
        childInfo.setAdapter(myChildList);
    }
}
