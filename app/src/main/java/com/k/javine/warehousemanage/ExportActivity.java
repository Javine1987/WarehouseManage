package com.k.javine.warehousemanage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.k.javine.warehousemanage.adapter.ExportAdapter;
import com.k.javine.warehousemanage.adapter.StockAdapter;
import com.k.javine.warehousemanage.data.DataManager;

/**
 * 出库页面
 */
public class ExportActivity extends BaseActivity {
    RecyclerView recyclerView;
    ExportAdapter mAdapter;
    boolean mIsOutput = false;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);
        recyclerView = findViewById(R.id.outer_recycler);
        mAdapter = new ExportAdapter(this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(mAdapter);
        mIsOutput = getIntent().getBooleanExtra("isOutput", false);
        if (mIsOutput) {
            setTitle("商品出库");
        } else {
            setTitle("商品入库");
        }
    }
}
