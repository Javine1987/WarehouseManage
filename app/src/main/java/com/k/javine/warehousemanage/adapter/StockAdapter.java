package com.k.javine.warehousemanage.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.k.javine.warehousemanage.R;
import com.k.javine.warehousemanage.data.StockItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @文件描述 :
 * @文件作者 : KuangYu
 * @创建时间 : 19-1-22
 */
public class StockAdapter extends BaseRecyclerAdapter<StockViewHolder>{

    private List<StockItem> mDatas = new ArrayList<>();
    private Context mContext;
    private ViewGroup mParent;

    public StockAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<StockItem> data) {
        mDatas.clear();
        mDatas.addAll(data);
    }

    public StockItem getItemByPosition(int position) {
        if (mDatas.size() > position) {
            return mDatas.get(position);
        }
        return null;
    }

    @NonNull
    @Override
    public StockViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        mParent = viewGroup;
        View itemView = LayoutInflater.from(mContext).inflate(R.layout.stock_item_layout, viewGroup, false);
        return new StockViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final StockViewHolder stockViewHolder, final int position) {
        stockViewHolder.getItemView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mItemClickListener != null) {
                    mItemClickListener.onItemClick(mParent, stockViewHolder, position);
                }
            }
        });
        stockViewHolder.bindView(mDatas.get(position));
    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }
}
