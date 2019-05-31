package com.example.administrator.myapplication.fragment.tool;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.administrator.myapplication.activity.exam.ExamActivity;
import com.example.administrator.myapplication.R;

public class ShowFragment extends Fragment {
    private String context = "xxxxxxxxxxxxx";
    //要显示的页面
    private int FragmentPage;

    public static ShowFragment newInstance(String context, int iFragmentPage) {

        ShowFragment myFragment = new ShowFragment();
        myFragment.context = context;
        myFragment.FragmentPage = iFragmentPage;
        return myFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        this.context = "";
        View view = inflater.inflate(FragmentPage, container, false);
        view.findViewById(R.id.rl_shop).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(), ExamActivity.class));
            }
        });
        return view;
    }
}
