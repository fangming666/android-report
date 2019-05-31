/* *
 * 对OkHttp进行封装
 * */
package com.example.administrator.myapplication.util;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.administrator.myapplication.R;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class HttpClient {
    private OkHttpClient client;
    private static HttpClient mClient;
    private Context context;
    //access-token的值
    private String accessToken = "94426";

    //loading控件
    private FrameLayout loadingWarp;

    @Nullable
    public void setLoadingWarp(View view) {
        this.loadingWarp = view.findViewById(R.id.loadingWarp);
    }


    public interface MyCallback {
        void success(Response res) throws IOException;

        void failed(IOException e);

    }

    //get方法
    public void ownGet(String url, HashMap<String, String> param, final MyCallback callback) {
        param.put("access-token", accessToken);
        //拼接请求参数
        if (!param.isEmpty()) {
            StringBuffer buffer = new StringBuffer(url);
            buffer.append('?');
            for (Map.Entry<String, String> entry : param.entrySet()) {
                buffer.append(entry.getKey());
                buffer.append('=');
                buffer.append(entry.getValue());
                buffer.append('&');
            }
            buffer.deleteCharAt(buffer.length() - 1);
            url = buffer.toString();
        }
        Request.Builder builder = new Request.Builder().url(url);
        builder.method("GET", null);
        Request request = builder.build();
        Call call = new OkHttpClient().newCall(request);
        ((Call) call).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (Looper.myLooper() != Looper.getMainLooper()) {
                    Handler mainThread = new Handler(Looper.getMainLooper());
                    mainThread.post(new Runnable() {
                        @Override
                        public void run() {
                            loadingWarp.setVisibility(View.GONE);
                            Toast.makeText(context.getApplicationContext(), "网络连接失败,请稍候重试", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
                callback.failed(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (Looper.myLooper() != Looper.getMainLooper()) {
                    Handler mainThread = new Handler(Looper.getMainLooper());
                    mainThread.post(new Runnable() {
                        @Override
                        public void run() {
                            loadingWarp.setVisibility(View.GONE);
                        }
                    });
                }
                callback.success(response);
            }
        });
    }

    public void get(String url, MyCallback callback) {
        ownGet(url, new HashMap<String, String>(), callback);
    }

    //post方法
    @Nullable
    public void post(String url, HashMap<String, String> param, final MyCallback callback) {
        param.put("access-token", accessToken);
        if (loadingWarp != null) {
            loadingWarp.setVisibility(View.VISIBLE);
        }

        FormBody.Builder formBody = new FormBody.Builder();
        if (!param.isEmpty()) {
            for (Map.Entry<String, String> entry : param.entrySet()) {
                formBody.add(entry.getKey(), entry.getValue());
            }
        }
        RequestBody form = formBody.build();
        Request.Builder builder = new Request.Builder();

        Request request = builder.post(form)
                .url(url)
                .header("content-type", "application/x-www-form-urlencoded")
                .build();
        Call cell = new OkHttpClient().newCall(request);
        cell.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (Looper.myLooper() != Looper.getMainLooper()) {
                    Handler mainThread = new Handler(Looper.getMainLooper());
                    mainThread.post(new Runnable() {
                        @Override
                        public void run() {
                            if (loadingWarp != null) {
                                loadingWarp.setVisibility(View.GONE);
                            }
                            Toast.makeText(context.getApplicationContext(), "网络连接失败,请稍候重试", Toast.LENGTH_SHORT).show();

                        }
                    });
                }
                callback.failed(e);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (Looper.myLooper() != Looper.getMainLooper()) {
                    Handler mainThread = new Handler(Looper.getMainLooper());
                    mainThread.post(new Runnable() {
                        @Override
                        public void run() {
                            if (loadingWarp != null) {
                                loadingWarp.setVisibility(View.GONE);
                            }
                            ;
                        }
                    });
                }

                callback.success(response);
            }


        });
    }


}
