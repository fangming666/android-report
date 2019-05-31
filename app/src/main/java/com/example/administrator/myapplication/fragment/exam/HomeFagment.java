package com.example.administrator.myapplication.fragment.exam;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class HomeFagment extends Fragment {
    private String context = "xxxxxxxxxxxxx";
    //要显示的页面
    private int FragmentPage;

    public static HomeFagment newInstance(String context, int iFragmentPage) {

        HomeFagment myFragment = new HomeFagment();
        myFragment.context = context;
        myFragment.FragmentPage = iFragmentPage;
        return myFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.context = "";
        return inflater.inflate(FragmentPage, container, false);
    }
}
