package com.clearliang.frameworkdemo.view.base;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * @作者 ClearLiang
 * @日期 2018/7/3 17:50
 * @描述
 */
public class BaseAdapter<T> extends RecyclerView.Adapter<BaseHolder> implements View.OnClickListener, GlobalConstants {
    private List<T> mList = new ArrayList<>();
    private int layoutId;
    private OnItemClickListener<T> mItemClickListener;
    private T item;
    private int position;

    @Override
    public void onClick(View view) {
        if (mItemClickListener != null) {
            mItemClickListener.onItemClick(view, position, item);
            mItemClickListener.onItemLongClick(view, position, item);
        }
    }

    public interface OnItemClickListener<T> {
        void onItemClick(View view, int position, T item);

        void onItemLongClick(View view, int position, T item);
    }

    public void setItemClickListener(OnItemClickListener<T> itemClickListener) {
        mItemClickListener = itemClickListener;
    }

    public BaseAdapter(int layoutId, List<T> list) {
        this.layoutId = layoutId;
        this.mList = list;
    }

    @Override
    public BaseHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        view.setOnClickListener(this);

        return new BaseHolder(view);
    }

    @Override
    public void onBindViewHolder(BaseHolder holder, final int position) {
        final T item = mList.get(position);
        holder.itemView.setTag(position);
        convert(holder, item);
        convert(holder, item, position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mItemClickListener == null) {
                    return;
                }
                mItemClickListener.onItemClick(view, position, item);
            }
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                mItemClickListener.onItemLongClick(view, position, item);
                return true;
            }
        });

    }

    protected void convert(BaseHolder holder, T item) {
        //什么都没有做
    }

    protected void convert(BaseHolder holder, T item, int position) {
        //什么都没有做
    }

    //获取记录数据
    @Override
    public int getItemCount() {
        return mList.size();
    }

}