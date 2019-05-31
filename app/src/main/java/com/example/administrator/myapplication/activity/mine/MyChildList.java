package com.example.administrator.myapplication.activity.mine;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.myapplication.JsonData.ChildJson;
import com.example.administrator.myapplication.R;

import java.util.List;

/**
 * 列表适配器
 */
public class MyChildList extends RecyclerView.Adapter<MyChildList.ViewHolder> {
    List<ChildJson.DataBean> list;

    MyChildList(List<ChildJson.DataBean> list) {
        this.list = list;
    }

    @Override
    public MyChildList.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_child_item, parent, false);
        MyChildList.ViewHolder viewHolder = new MyChildList.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyChildList.ViewHolder holder, int position) {
        ChildJson.DataBean resultContent = list.get(position);
        holder.childItemNameInfo.setText(resultContent.real_name);
        //当status为0的时候，说明此孩子需要待审核
        if (resultContent.status == 0) {
            holder.childItemNameAuditing.setVisibility(View.VISIBLE);
        }
        //孩子唯一号
        holder.childCard.setText(resultContent.card_id);
        //孩子班级
        holder.childClass.setText(resultContent.class_name);
        //孩子学校
        holder.childSchool.setText(resultContent.school_name);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * 初始化控件
     */
    class ViewHolder extends RecyclerView.ViewHolder {
        TextView childItemNameInfo;
        TextView childItemNameAuditing;
        TextView childCard;
        TextView childClass;
        TextView childSchool;

        ViewHolder(View itemView) {
            super(itemView);
            childItemNameInfo = itemView.findViewById(R.id.childItemNameInfo);
            childItemNameAuditing = itemView.findViewById(R.id.childItemNameAuditing);
            childCard = itemView.findViewById(R.id.childCard);
            childClass = itemView.findViewById(R.id.childClass);
            childSchool = itemView.findViewById(R.id.childSchool);
        }
    }

}
