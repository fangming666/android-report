
package com.example.administrator.myapplication.activity.mine;

import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.administrator.myapplication.JsonData.ChildJson;
import com.example.administrator.myapplication.JsonData.ChildJson.DataBean;
import com.example.administrator.myapplication.R;

import java.util.List;

public class FamilyChild extends BaseQuickAdapter<List<ChildJson.DataBean>, BaseViewHolder> {

    public FamilyChild(@LayoutRes int layoutResId, @Nullable List<ChildJson.DataBean> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ChildJson.DataBean item) {
        //可链式调用赋值
        helper.setText(R.id.helpItemName, item.title)
                .addOnClickListener(R.id.myDirectoryItemWarp);    //给LinerLayout添加点击事件;
    }


}
