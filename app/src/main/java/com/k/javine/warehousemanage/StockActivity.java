package com.k.javine.warehousemanage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.k.javine.warehousemanage.adapter.BaseRecyclerAdapter;
import com.k.javine.warehousemanage.adapter.StockAdapter;
import com.k.javine.warehousemanage.adapter.StockViewHolder;
import com.k.javine.warehousemanage.data.DataManager;
import com.k.javine.warehousemanage.data.StockItem;

/**
 * 库存管理页面
 */
public class StockActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private StockAdapter mAdapter;
    private PopupWindow mPopupWindow;
    private View mAnchor;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);
        setTitle("库存");
        mRecyclerView = findViewById(R.id.list);
        mAnchor = findViewById(R.id.anchor);
        mAdapter = new StockAdapter(this);
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener<StockViewHolder>() {
            @Override
            public void onItemClick(ViewGroup parent, StockViewHolder holder, int position) {
                showDetailPage(mAdapter.getItemByPosition(position));
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        mRecyclerView.setAdapter(mAdapter);
        loadData();
    }

    private View mContainerView;
    private View mCloseView;
    private TextView mTitleView;
    private TextView mTotleView, mSizeView, mColorView;
    private Button mBtnEnter, mBtnOutput;

    private void showDetailPage(StockItem item) {
        if (mPopupWindow == null) {
            mPopupWindow = new PopupWindow(this);
            mPopupWindow.setOutsideTouchable(false);
            mPopupWindow.setAnimationStyle(R.style.PopupWindowAnimation);
            mPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        updateWindowContent(item);
        mPopupWindow.showAtLocation(findViewById(R.id.rootView), Gravity.BOTTOM, 0, 0);
    }



    private void updateWindowContent(StockItem item) {
        if (mContainerView == null) {
            mContainerView = LayoutInflater.from(this).inflate(R.layout.popup_window_layout, null);
            mPopupWindow.setContentView(mContainerView);
            mCloseView = mContainerView.findViewById(R.id.iv_close);
            mTitleView = mContainerView.findViewById(R.id.tv_title);
            mBtnEnter = mContainerView.findViewById(R.id.btn_input);
            mBtnOutput = mContainerView.findViewById(R.id.btn_output);
            mTotleView = mContainerView.findViewById(R.id.tv_totle);
            mColorView = mContainerView.findViewById(R.id.tv_color);
            mSizeView = mContainerView.findViewById(R.id.tv_size);

            mCloseView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPopupWindow.dismiss();
                }
            });
        }
        String title = item.getName() + " - " + item.getId();
        mTitleView.setText(title);
        mTotleView.setText(String.valueOf(item.getTotalCount()));
        mColorView.setText(item.getColorString());
        mSizeView.setText(item.getSizeString());

    }

    private void loadData() {
        mAdapter.setData(DataManager.getInstance().getStockItems());
        mAdapter.notifyDataSetChanged();
    }
}
