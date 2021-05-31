package com.example.weatherdemo;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.weatherdemo.entities.cityItem;
import com.google.android.material.transition.Hold;

import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MyAdapter extends BaseAdapter {
    private Context context;
    private List<cityItem> list;

    public MyAdapter(Context context, List<cityItem> list) {
        this.context = context;
        this.list = list;
    }

//    public MyAdapter(Context context, List<Map<String, Object>> list) {
//        this.context = context;
//        this.list = list;
//    }

    class ViewHolder{
        TextView tv_province,tv_city;
        Button update,delete;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder viewHolder=null;
        if(view==null){
            viewHolder=new ViewHolder();
            view= LayoutInflater.from(context).inflate(R.layout.activity_setting_item,null);
            //在view 视图中查找组件
            viewHolder.tv_province=(TextView) view.findViewById(R.id.tv_province);
            viewHolder.tv_city=(TextView) view.findViewById(R.id.tv_city);
            viewHolder.update=(Button) view.findViewById(R.id.update);
            viewHolder.delete=(Button) view.findViewById(R.id.delete);
           //为Item 里面的组件设置相应的数据
            viewHolder.tv_province.setText(list.get(position).getProvince());
            viewHolder.tv_city.setText(list.get(position).getCity());
            view.setTag(viewHolder);
        }else viewHolder=(ViewHolder)view.getTag();
        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemDeleteListener.onDeleteClick(position);
            }
        });
        viewHolder.update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemUpdateListener.onUpdateClick(position);
            }
        });
        return view;
    }

//    public void ADD(cityItem item){
//        if(find(item)==-1){
//            list.add(item);
//        }
//        notifyDataSetChanged();
//    }
//
//    public void DELETE(cityItem item){
//        int pos=find(item);
//        if(pos!=-1){
//            list.remove(pos);
//        }
//        notifyDataSetChanged();
//    }
//
//    public int find(cityItem item){
//        for(int i=0;i<list.size();i++){
//            if(list.get(i)==item){
//                return i;
//            }
//        }
//        return -1;
//    }

    /**
     * 删除按钮的监听接口
     */
    public interface onItemDeleteListener {
        void onDeleteClick(int i);
    }

    private onItemDeleteListener mOnItemDeleteListener;

    public void setOnItemDeleteClickListener(onItemDeleteListener mOnItemDeleteListener) {
        this.mOnItemDeleteListener = mOnItemDeleteListener;
    }
    /**
     * 更新按钮的监听接口
     */
    public interface onItemUpdateListener {
        void onUpdateClick(int i);
    }

    private onItemUpdateListener mOnItemUpdateListener;

    public void setOnItemUpdateClickListener(onItemUpdateListener mOnItemUpdateListener) {
        this.mOnItemUpdateListener = mOnItemUpdateListener;
    }

}
