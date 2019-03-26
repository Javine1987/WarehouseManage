package com.k.javine.warehousemanage.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.k.javine.warehousemanage.R;
import com.k.javine.warehousemanage.data.StockItem;

/**
 * @文件描述 :
 * @文件作者 : KuangYu
 * @创建时间 : 19-1-22
 */
public class StockViewHolder extends BaseViewHolder {

    public StockViewHolder(@NonNull View itemView) {
        super(itemView);
    }

    public void bindView(StockItem item) {
        TextView idTextView = itemView.findViewById(R.id.tv_id);
        TextView titleView = itemView.findViewById(R.id.tv_title);
        TextView countView = itemView.findViewById(R.id.tv_count);
        TextView priceView = itemView.findViewById(R.id.tv_price);
        idTextView.setText(item.getId());
        titleView.setText(item.getName());
        countView.setText(String.format("%d 件", item.getTotalCount()));
        priceView.setText(String.format("¥ %s", item.getPrice()));
    }

}
