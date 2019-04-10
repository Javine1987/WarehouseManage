package com.k.javine.warehousemanage.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.AdapterView;

/**
 * @文件描述 :
 * @文件作者 : KuangYu
 * @创建时间 : 19-2-28
 */
public abstract class BaseRecyclerAdapter<T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T> {

    public interface OnItemClickListener<T extends RecyclerView.ViewHolder>{
        void onItemClick(ViewGroup  parent, T holder, int position);
    }



}
