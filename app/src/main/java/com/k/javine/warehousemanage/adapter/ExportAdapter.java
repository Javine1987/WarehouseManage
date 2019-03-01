package com.k.javine.warehousemanage.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.k.javine.warehousemanage.R;

/**
 * @文件描述 :
 * @文件作者 : KuangYu
 * @创建时间 : 19-3-1
 */
public class ExportAdapter extends BaseRecyclerAdapter<ExportViewHolder> {
    private Context mContext;

    public ExportAdapter(Context context){
        mContext = context;
    }

    @NonNull
    @Override
    public ExportViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.export_item_layout, viewGroup, false);
        return new ExportViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExportViewHolder exportViewHolder, int i) {
        exportViewHolder.bindView();
    }

    @Override
    public int getItemCount() {
        return 1;
    }
}
