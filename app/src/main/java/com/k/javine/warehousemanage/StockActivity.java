package com.k.javine.warehousemanage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.k.javine.warehousemanage.adapter.StockAdapter;

/**
 * 库存管理页面
 */
public class StockActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private StockAdapter mAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);
        setTitle("库存");
        mRecyclerView = findViewById(R.id.list);
        mAdapter = new StockAdapter(this);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        loadData();
    }

    private void loadData() {

    }
}
