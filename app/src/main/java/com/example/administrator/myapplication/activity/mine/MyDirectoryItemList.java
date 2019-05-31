package com.example.administrator.myapplication.activity.mine;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.myapplication.JsonData.HelpJson;
import com.example.administrator.myapplication.R;

import java.util.List;

public class MyDirectoryItemList extends BaseQuickAdapter<HelpJson.DataBean.HelpList, BaseViewHolder> {

    public MyDirectoryItemList(@LayoutRes int layoutResId, @Nullable List<HelpJson.DataBean.HelpList> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, HelpJson.DataBean.HelpList item) {
        //可链式调用赋值
        helper.setText(R.id.helpItemName, item.title)
                .addOnClickListener(R.id.myDirectoryItemWarp);    //给LinerLayout添加点击事件;
        //获取当前条目position
        //int position = helper.getLayoutPosition();
    }


}
