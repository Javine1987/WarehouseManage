package com.k.javine.warehousemanage;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
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
import com.k.javine.warehousemanage.utils.CommonUtils;

/**
 * 库存管理页面
 */
public class StockActivity extends BaseActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private StockAdapter mAdapter;
    private PopupWindow mPopupWindow;
    private View mAddView;
    private View mBackgroundView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock);
        setTitle(R.string.title_stoke_page);
        mRecyclerView = findViewById(R.id.list);
        mBackgroundView = findViewById(R.id.background);
        mBackgroundView.setOnClickListener(this);
        mAddView = findViewById(R.id.iv_add);
        mAddView.setOnClickListener(this);
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

    @Override
    protected void onResume() {
        super.onResume();
        if (DataManager.getInstance().isStockModify()) {
            loadData();
            mPopupWindow.dismiss();
        }
    }

    private View mContainerView;
    private View mCloseView, mModifyView;
    private TextView mTitleView;
    private TextView mTotleView, mColorView;
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
        mBackgroundView.setVisibility(View.VISIBLE);
        mPopupWindow.showAtLocation(findViewById(R.id.rootView), Gravity.BOTTOM, 0, 0);
    }


    private void updateWindowContent(final StockItem item) {
        if (mContainerView == null) {
            mContainerView = LayoutInflater.from(this).inflate(R.layout.popup_window_layout, null);
            mPopupWindow.setContentView(mContainerView);
            mPopupWindow.setHeight(2*CommonUtils.getScreenHeight(this)/3);
            mCloseView = mContainerView.findViewById(R.id.iv_close);
            mTitleView = mContainerView.findViewById(R.id.tv_title);
            mBtnEnter = mContainerView.findViewById(R.id.btn_input);
            mBtnOutput = mContainerView.findViewById(R.id.btn_output);
            mTotleView = mContainerView.findViewById(R.id.tv_totle);
            mColorView = mContainerView.findViewById(R.id.tv_color);
            mModifyView = mContainerView.findViewById(R.id.ll_modify);

            mCloseView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPopupWindow.dismiss();
                    mBackgroundView.setVisibility(View.GONE);
                }
            });

            mModifyView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(StockActivity.this, AddProductActivity.class);
                    DataManager.getInstance().setTempStockItem(item);
                    intent.putExtra("isModify", true);
                    startActivity(intent);
                }
            });
            mBtnEnter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showEnterWindow(item);
                }
            });
            mBtnOutput.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showOutputWindow(item);
                }
            });
        }
        String title = item.getName() + " - " + item.getId();
        mTitleView.setText(title);
        mTotleView.setText(String.valueOf(item.getTotalCount()));
        mColorView.setText(item.getColorString(this));
    }

    private void loadData() {
        mAdapter.setData(DataManager.getInstance().getStockItems());
        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_add:
                Intent intent = new Intent(this, AddProductActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void showOutputWindow(StockItem item) {
        // TODO: 19-4-17 弹出出库操作窗口
    }

    private void showEnterWindow(StockItem item) {
        // TODO: 19-4-17 弹出入库操作窗口
    }
}
