package com.example.administrator.myapplication;

import android.app.FragmentTransaction;
import android.content.ClipData;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;

import com.example.administrator.myapplication.fragment.exam.HomeFagment;
import com.example.administrator.myapplication.fragment.mine.MineFagment;
import com.example.administrator.myapplication.fragment.tool.ShowFragment;

//import android.support.v4.app.FragmentTransaction;


public class MaterialActivity extends AppCompatActivity {
    private String className = "MenuActivity";
    String msg = "Android: ";
    //继承Activity不会显示App头上的标题
    private HomeFagment homeFagment;
    private ShowFragment showFragment;
    private MineFagment mineFagment;

    @Override
    public void onCreate(Bundle saveIn) {
        super.onCreate(saveIn);
        setContentView(R.layout.activity_material);
        final BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        //获取其他activity中的id决定跳转哪个fragment
        int id = getIntent().getIntExtra("id", 0);//获取intent值
        changeFragment( bottomNavigationView,transaction, id);
        //bottom item进行监听
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                showOwnFragment(menuItem);
                return true;
            }
        });

    }

    //根据其他activity传过来的id选择展示哪个fragment
    private void changeFragment( BottomNavigationView bottomNavigationView,FragmentTransaction transaction, int id) {
        bottomNavigationView.getMenu().getItem(id).setChecked(true);
        switch (id) {
            case 0:
                if (homeFagment == null) {
                    homeFagment = HomeFagment.newInstance("考试", R.layout.fragment_exam);
                    transaction.add(R.id.FramePage, homeFagment);
                } else {
                    transaction.show(homeFagment);
                }
                transaction.commit();
                setTitle("考试列表");
                break;
            case 1:
                if (showFragment == null) {
                    showFragment = ShowFragment.newInstance("学习助手", R.layout.fragment_tool);
                    transaction.add(R.id.FramePage, showFragment);
                } else {
                    transaction.show(showFragment);
                }
                setTitle("学习助手");
                break;
            case 2:
                if (mineFagment == null) {
                    mineFagment = MineFagment.newInstance("个人中心", R.layout.fragment_mine);
                    transaction.add(R.id.FramePage, mineFagment);
                } else {
                    transaction.show(mineFagment);
                }
                setTitle("个人中心");
                break;
        }

    }

    //隐藏所有的Fragment
    public void hideAllFragment(FragmentTransaction transaction) {
        if (homeFagment != null) {
            transaction.hide(homeFagment);
        }
        if (showFragment != null) {
            transaction.hide(showFragment);
        }
        if (mineFagment != null) {
            transaction.hide(mineFagment);
        }
    }

    //显示偶一个Fragment
    public void showOwnFragment(MenuItem menuItem) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        hideAllFragment(transaction);
        switch (menuItem.getItemId()) {
            case R.id.exam:
                Log.d(className, "R.id.exam");
                if (homeFagment == null) {
                    homeFagment = HomeFagment.newInstance("考试", R.layout.fragment_exam);
                    transaction.add(R.id.FramePage, homeFagment);
                } else {
                    transaction.show(homeFagment);
                }
                setTitle("考试列表");
                break;
            case R.id.tool:
                Log.d(className, "R.id.tool");
                if (showFragment == null) {
                    showFragment = ShowFragment.newInstance("学习助手", R.layout.fragment_tool);
                    transaction.add(R.id.FramePage, showFragment);
                } else {
                    transaction.show(showFragment);
                }
                setTitle("学习助手");
                break;
            case R.id.my:
                Log.d(className, "R.id.my");
                if (mineFagment == null) {
                    mineFagment = MineFagment.newInstance("个人中心", R.layout.fragment_mine);
                    transaction.add(R.id.FramePage, mineFagment);
                } else {
                    transaction.show(mineFagment);
                }
                setTitle("个人中心");
                break;
        }
        transaction.commit();
    }
}
