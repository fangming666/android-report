package com.example.administrator.myapplication.activity.mine;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.administrator.myapplication.JsonData.HelpJson;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.util.BaseActivity;
import com.example.administrator.myapplication.util.HttpClient;
import com.example.administrator.myapplication.util.UrlApi;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Response;

import static com.example.administrator.myapplication.R.layout.activity_my_directory;

public class MyDirectory extends BaseActivity {

    //Url进行实例化
    private UrlApi urlApi = new UrlApi();
    //请求实例化
    private HttpClient httpClient = new HttpClient();

    @SuppressLint("ResourceType")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate(Bundle saveIn) {
        super.onCreate(saveIn);
        setContentView(activity_my_directory);
        //设置标题名称
        setTitle("使用指南");
        //获取loading
        httpClient.setLoadingWarp(findViewById(R.id.loadingWarp));
        //获取帮助列表
        new Thread() {
            @Override
            public void run() {
                gainHelp();
            }
        }.start();
    }

    public void gainHelp() {
        String helpUrl = urlApi.helpUrl();
        HashMap<String, String> hashMap = new HashMap<>();
        Callback callback = new Callback();
        httpClient.post(helpUrl, hashMap, callback);
    }

    class Callback implements HttpClient.MyCallback {

        @Override
        public void success(Response res) throws IOException {
            Gson gson = new Gson();
            assert res.body() != null;
            HelpJson helpJson = gson.fromJson(res.body().string(), HelpJson.class);
            final List<HelpJson.DataBean> resultContent = helpJson.getData();
            if (Looper.myLooper() != Looper.getMainLooper()) {
                Handler mainThread = new Handler(Looper.getMainLooper());
                mainThread.post(new Runnable() {
                    @Override
                    public void run() {
                        createBox(resultContent);
                    }
                });
            }
        }

        @Override
        public void failed(IOException e) {
            Log.d("help err is", e + "");
        }
    }

    //创建资源适配器
    public void createBox(List result) {
        //初始化主体，获得外面大的盒子控件
        RecyclerView info = (RecyclerView) findViewById(R.id.directoryInfo);

        //设置recyclerView管理器
        info.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        //初始化适配器
        MyDirectoryList myDirectoryList = new MyDirectoryList(result, this);
        //设置添加或删除item时的动画，这里使用默认动画
        info.setItemAnimator(new DefaultItemAnimator());
        //设置适配器
        info.setAdapter(myDirectoryList);
    }
}
