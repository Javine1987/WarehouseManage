package com.k.javine.warehousemanage.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @文件描述 :
 * @文件作者 : KuangYu
 * @创建时间 : 19-2-28
 */
public class BaseViewHolder extends RecyclerView.ViewHolder{

    public BaseViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public View getItemView() {
        return itemView;
    }

}
