package com.example.administrator.myapplication.fragment.mine;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.administrator.myapplication.JsonData.ChildJson;
import com.example.administrator.myapplication.R;
import com.example.administrator.myapplication.activity.mine.MyChildren;
import com.example.administrator.myapplication.activity.mine.MyDirectory;
import com.example.administrator.myapplication.activity.mine.MyFamily;
import com.example.administrator.myapplication.activity.mine.MyOrder;
import com.example.administrator.myapplication.activity.mine.MySuggestion;
import com.example.administrator.myapplication.util.HttpClient;
import com.example.administrator.myapplication.util.UrlApi;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import okhttp3.Response;

public class MineFagment extends Fragment {
    private String context = "xxxxxxxxxxxxx";
    //昵称
    private String userNameStr = "酸";
    //手机号
    private long phoneNum = 18865526715L;
    //要显示的页面
    private int FragmentPage;

    //Url进行实例化
    private UrlApi urlApi = new UrlApi();
    //请求实例化
    private HttpClient httpClient = new HttpClient();

    //孩子的信息
    public String childrenInfo;


    public static MineFagment newInstance(String context, int iFragmentPage) {
        MineFagment myFragment = new MineFagment();
        myFragment.context = context;
        myFragment.FragmentPage = iFragmentPage;
        return myFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("my isis", "my");
        final View view = inflater.inflate(FragmentPage, container, false);
        this.gainText(userNameStr, phoneNum, view);
        httpClient.setLoadingWarp(view);
        new Thread() {
            @Override
            public void run() {
                gainChild(view);
            }
        }.start();
        this.context = "";
        navClick((RelativeLayout) view.findViewById(R.id.childNav), "my_child");
        navClick((RelativeLayout) view.findViewById(R.id.familyNav), "my_family");
        navClick((RelativeLayout) view.findViewById(R.id.orderNav), "my_order");
        navClick((RelativeLayout) view.findViewById(R.id.directoryNav), "my_directory");
        navClick((RelativeLayout) view.findViewById(R.id.suggestionNav), "my_suggestion");
        return view;
    }


    //昵称+phone进行赋值
    public void gainText(String userNameStr, long phoneNum, View view) {
        TextView userName = view.findViewById(R.id.userName);
        userName.setText(userNameStr);
        TextView phone = view.findViewById(R.id.phoneNum);
        phone.setText(String.valueOf(phoneNum));
    }

    //获取孩子信息的请求
    public void gainChild(View view) {
        String childUrl = urlApi.childUrl();
        HashMap<String, String> hashMap = new HashMap<>();
        Callback callback = new Callback();
        callback.setView(view);
        httpClient.post(childUrl, hashMap, callback);
    }

    class Callback implements HttpClient.MyCallback {

        public View view;

        public void setView(View reqView) {
            this.view = reqView;
        }

        @Override
        public void success(Response res) throws IOException {
            Gson gson = new Gson();
            assert res.body() != null;
            childrenInfo = res.body().string();
            ChildJson childJson = gson.fromJson(childrenInfo, ChildJson.class);
            final List<ChildJson.DataBean> resultContent = childJson.getData();


            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    LinearLayout roleWarp = view.findViewById(R.id.roleInfo);
                    int index = 0;
                    for (ChildJson.DataBean j : resultContent) {
                        createTextView(index, j.real_name + "的" + j.roleLabel, roleWarp);
                        setChildName(j.real_name, (TextView) view.findViewById(R.id.childNavName), resultContent.size());
                        index++;
                    }
                }
            });
        }

        @Override
        public void failed(IOException e) {
            Log.d("child err is", e + "");
        }
    }

    //创建控件
    protected void createTextView(int index, String content, LinearLayout layout) {
        final TextView textView = new TextView(getActivity());
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        textView.setId(index);
        //显示孩子以及孩子角色的时候
        textView.setBackgroundDrawable(getResources().getDrawable(R.drawable.role_bg));//#FFFDEF
        textView.setTextColor(Color.parseColor("#927542"));
        textView.setPadding(18, 5, 18, 5);
        textView.setTextSize(14);
        layoutParams.setMargins(0, 0, 10, 0);
        textView.setLayoutParams(layoutParams);
        textView.setText(content);
        layout.addView(textView);
    }

    //设置我的孩子:当小于一个的时候显示名称，大于一个的时候显示数量数量
    protected void setChildName(String context, TextView textview, int sum) {
        textview.setText(sum > 1 ? String.valueOf(sum) : context);
    }

    //点击nav跳转对应的activity
    protected void navClick(RelativeLayout navLayout, final String judge) {
        final Intent intent = new Intent(getActivity(), MyChildren.class);
        navLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                switch (judge) {
                    case "my_child":
                        intent.putExtra("childData", childrenInfo);
                        startActivity(intent);
                        break;
                    case "my_family":
                        intent.putExtra("childData", childrenInfo);
                        startActivity(new Intent(getActivity(), MyFamily.class));
                        break;
                    case "my_order":
                        startActivity(new Intent(getActivity(), MyOrder.class));
                        break;
                    case "my_directory":
                        startActivity(new Intent(getActivity(), MyDirectory.class));
                        break;
                    case "my_suggestion":
                        startActivity(new Intent(getActivity(), MySuggestion.class));
                        break;
                }
                //修改activity跳转方式
                getActivity().overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);

            }

        });
    }
}
