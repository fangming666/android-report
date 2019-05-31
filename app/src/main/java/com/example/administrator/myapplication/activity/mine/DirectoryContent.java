package com.example.administrator.myapplication.activity.mine;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.text.Html;
import android.util.Log;
import android.webkit.WebView;
import android.widget.TextView;

import com.example.administrator.myapplication.JsonData.HelpContentJson;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.util.BaseActivity;
import com.example.administrator.myapplication.util.HttpClient;
import com.example.administrator.myapplication.util.UrlApi;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Response;

public class DirectoryContent extends BaseActivity {
    //Url进行实例化
    private UrlApi urlApi = new UrlApi();
    //请求实例化
    private HttpClient httpClient = new HttpClient();

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate(Bundle saveIn) {
        super.onCreate(saveIn);
        setContentView(R.layout.directory_content);
        //修改标题
        setTitle("常见问题");
        Intent intent = getIntent();
        Log.d("helpId is", intent.getStringExtra("help_id") + "");
        final String help_id = intent.getStringExtra("help_id");
        //获取loading
        httpClient.setLoadingWarp(findViewById(R.id.loadingWarp));
        //获取帮助内容
        new Thread() {
            @Override
            public void run() {
                gainHelpContent(help_id);
            }
        }.start();
    }

    //调取接口返回信息
    private void gainHelpContent(String help_id) {
        String helpContentUrl = urlApi.helpContentUrl();
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("help_id", help_id);
        DirectoryContent.Callback callback = new DirectoryContent.Callback();
        httpClient.post(helpContentUrl, hashMap, callback);
    }

    class Callback implements HttpClient.MyCallback {

        @Override
        public void success(Response res) throws IOException {
            Gson gson = new Gson();
            assert res.body() != null;
            HelpContentJson helpContentJson = gson.fromJson(res.body().string(), HelpContentJson.class);
            final HelpContentJson.DataBean resultContent = helpContentJson.getData();
            if (Looper.myLooper() != Looper.getMainLooper()) {
                Handler mainThread = new Handler(Looper.getMainLooper());
                mainThread.post(new Runnable() {
                    @Override
                    public void run() {
                        TextView directoryContentTitle = findViewById(R.id.directoryContentTitle);
                        directoryContentTitle.setText(resultContent.getTitle());
                        WebView directoryContentContent = findViewById(R.id.directoryContentContent);
                        directoryContentContent.loadData(resultContent.getContent(),"text/html","UTF-8");
                    }
                });
            }
        }

        @Override
        public void failed(IOException e) {
            Log.d("help err is", e + "");
        }
    }


    //解析html代码
    public void analyzeHtml(String htmlContent) {

    }

}
