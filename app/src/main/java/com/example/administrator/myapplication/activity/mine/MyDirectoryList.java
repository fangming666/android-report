package com.example.administrator.myapplication.activity.mine;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.administrator.myapplication.JsonData.HelpJson;
import com.example.administrator.myapplication.R;

import java.util.List;

public class MyDirectoryList extends RecyclerView.Adapter<MyDirectoryList.ViewHolder> {
    List<HelpJson.DataBean> list;
    private Context context;

    MyDirectoryList(List<HelpJson.DataBean> list, Context context) {
        this.list = list;
        this.context = context;

    }

    @Override
    public MyDirectoryList.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_directroy_item, parent, false);
        MyDirectoryList.ViewHolder viewHolder = new MyDirectoryList.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public void onBindViewHolder(MyDirectoryList.ViewHolder holder, int position) {
        final HelpJson.DataBean resultContent = list.get(position);
        holder.nameInfo.setText(resultContent.category_name);


        //初始化主体，获得外面大的盒子控件
        RecyclerView info = holder.recyclerView;

        //设置recyclerView管理器
        info.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        //初始化适配器
        MyDirectoryItemList myDirectoryItemList = new MyDirectoryItemList(R.layout.my_directory_item_list, resultContent.help_list);
        //设置添加或删除item时的动画，这里使用默认动画
        info.setItemAnimator(new DefaultItemAnimator());
        //设置适配器
        info.setAdapter(myDirectoryItemList);
        //条目点击事件

        myDirectoryItemList.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                Log.d("Help id isis", String.valueOf(resultContent.help_list.get(position).help_id));
                Intent intent = new Intent(context, DirectoryContent.class);
                intent.putExtra("help_id", String.valueOf(resultContent.help_list.get(position).help_id));
                context.startActivity(intent);
            }
        });
    }

    /**
     * 初始化控件
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameInfo;
        RecyclerView recyclerView;

        ViewHolder(View itemView) {
            super(itemView);
            nameInfo = itemView.findViewById(R.id.myDirectoryItemName);
            recyclerView = itemView.findViewById(R.id.directoryItemInfo);
        }
    }
}
