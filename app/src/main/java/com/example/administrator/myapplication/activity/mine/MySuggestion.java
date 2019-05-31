package com.example.administrator.myapplication.activity.mine;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.RequiresApi;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.myapplication.JsonData.SuggestionJson;
import com.example.administrator.myapplication.MaterialActivity;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.fragment.mine.MineFagment;
import com.example.administrator.myapplication.util.BaseActivity;
import com.example.administrator.myapplication.util.HttpClient;
import com.example.administrator.myapplication.util.UrlApi;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;

import okhttp3.Response;

/**
 * 意见反馈
 */
public class MySuggestion extends BaseActivity {
    EditText etContent;//定义一个文本输入框
    TextView tvSize;//定义显示的字数
    int maxNum = 140;//定义最大的输入文字
    private String subContent = "";//输入的文字
    Toast toast = null;
    //Url进行实例化
    private UrlApi urlApi = new UrlApi();
    //请求实例化
    private HttpClient httpClient = new HttpClient();


    @SuppressLint({"ResourceType", "SetTextI18n"})
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onCreate(Bundle saveIn) {
        super.onCreate(saveIn);
        setContentView(R.layout.activity_my_suggestion);
        //设置标题
        setTitle("意见反馈");

        //限制字数
        showCharNumber(maxNum);
        //当内容为空的时候数字置为0
        if (TextUtils.isEmpty(etContent.getText().toString())) {
            tvSize.setText("0/" + maxNum);
        } else {
            tvSize.setText(etContent.getText().length() + "/" + maxNum);
        }

        //提交按钮点击事件
        Button btnSubmit = findViewById(R.id.suggestionSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //开启子线程
                new Thread() {
                    @Override
                    public void run() {
                        submitSuggestion(subContent);
                    }
                }.start();

            }
        });
    }

    //限制字数
    public void showCharNumber(final int maxNum) {
        etContent = findViewById(R.id.editor);
        tvSize = findViewById(R.id.editorFontCount);
        etContent.addTextChangedListener(new TextWatcher() {
            private CharSequence temp;
            private int selectionStart;
            private int selectionEnd;

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                temp = s;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void afterTextChanged(Editable s) {
                int number = s.length();
                tvSize.setText(number + "/" + maxNum);
                selectionStart = etContent.getSelectionStart();
                selectionEnd = etContent.getSelectionEnd();
                subContent = String.valueOf(temp);
                if (temp.length() > maxNum) {
                    s.delete(selectionStart - 1, selectionEnd);
                    int tempSelection = selectionStart;
                    etContent.setText(s);
                    etContent.setSelection(tempSelection);
                }
            }
        });
    }

    //提交意见
    public void submitSuggestion(String subContent) {
        if (subContent.length() == 0) {
            showTotal("请填写您的意见");
        } else {
            String suggestionUrl = urlApi.suggestionUrl();
            HashMap<String, String> hashMap = new HashMap<>();
            hashMap.put("content", subContent);
            MySuggestion.Callback callback = new MySuggestion.Callback();
            httpClient.post(suggestionUrl, hashMap, callback);
        }
    }

    class Callback implements HttpClient.MyCallback {

        @Override
        public void success(Response res) throws IOException {
            Gson gson = new Gson();
            assert res.body() != null;
            final SuggestionJson suggestionJson = gson.fromJson(res.body().string(), SuggestionJson.class);
            if (Looper.myLooper() != Looper.getMainLooper()) {
                Handler mainThread = new Handler(Looper.getMainLooper());
                mainThread.post(new Runnable() {
                    @Override
                    public void run() {
                        showTotal(suggestionJson.getMsg());
                        if(suggestionJson.getStatus() == 200){
                            try {
                                Thread.sleep(1500);//延时1s
                                MySuggestion.this.finish();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }

                    }
                });
            }
        }

        @Override
        public void failed(IOException e) {
            Log.d("help err is", e + "");
        }
    }

    //显示total
    public void showTotal(String noContentStr) {
        try {
            if (toast != null) {
                toast.setText(noContentStr);
            } else {
                toast = Toast.makeText(getApplicationContext(), noContentStr, Toast.LENGTH_SHORT);
            }
            toast.show();
        } catch (Exception e) {
            //解决在子线程中调用Toast的异常情况处理
            Looper.prepare();
            Toast.makeText(getApplicationContext(), noContentStr, Toast.LENGTH_SHORT).show();
            Looper.loop();
        }
    }
}
