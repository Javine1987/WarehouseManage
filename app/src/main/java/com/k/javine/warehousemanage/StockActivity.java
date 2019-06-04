package com.k.javine.warehousemanage;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.k.javine.warehousemanage.adapter.BaseRecyclerAdapter;
import com.k.javine.warehousemanage.adapter.StockAdapter;
import com.k.javine.warehousemanage.adapter.StockViewHolder;
import com.k.javine.warehousemanage.data.DataManager;
import com.k.javine.warehousemanage.data.StockItem;
import com.k.javine.warehousemanage.utils.CommonUtils;
import com.k.javine.warehousemanage.widget.CounterView;
import com.k.javine.warehousemanage.widget.LabelChooseView;
import com.k.javine.warehousemanage.widget.LabelView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 库存管理页面
 */
public class StockActivity extends BaseActivity implements View.OnClickListener {

    private RecyclerView mRecyclerView;
    private StockAdapter mAdapter;
    private PopupWindow mPopupWindow;
    private PopupWindow mChangeWindow;
    private View mAddView;
    private View mBackgroundView;
    private StockItem mCurSelectItem;
    private AlertDialog mChangePriceDialog;
    private EditText mPriceEditText;

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
            if (mPopupWindow != null) {
                mPopupWindow.dismiss();
                mBackgroundView.setVisibility(View.GONE);
            }
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
            mPopupWindow.setHeight(2*CommonUtils.getScreenHeight(this)/3);
        }
        updateWindowContent(item);
        mBackgroundView.setVisibility(View.VISIBLE);
        mPopupWindow.showAtLocation(findViewById(R.id.rootView), Gravity.BOTTOM, 0, 0);
    }


    private void updateWindowContent(final StockItem item) {
        if (mContainerView == null) {
            mContainerView = LayoutInflater.from(this).inflate(R.layout.popup_window_layout, null);
            mPopupWindow.setContentView(mContainerView);
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
                    showEnterWindow();
                }
            });
            mBtnOutput.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showOutputWindow();
                }
            });
        }
        mCurSelectItem = item;
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

    private View mChangeView;
    private LabelChooseView mSingleChooseView;
    private TextView mChangeTitle, mChangeName, mChangePrice, mChangeTotalMoney;
    private Button mChangeBtn;
    private ImageView mChangePriceImage;
    private ListView mChangeListView;
    private ChangeSizeOptionsAdapter mChangeAdapter;

    private void createChangeWindow() {
        if (mChangeWindow == null) {
            mChangeWindow = new PopupWindow(this);
            mChangeWindow.setOutsideTouchable(false);
            mChangeWindow.setAnimationStyle(R.style.PopupWindowAnimation);
            mChangeWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            mChangeWindow.setHeight(3*CommonUtils.getScreenHeight(this)/4);
        }
    }

    private void showOutputWindow() {
        createChangeWindow();
        updateChangeWindowContent(mCurSelectItem, true);
        mChangeWindow.showAtLocation(findViewById(R.id.rootView), Gravity.BOTTOM, 0, 0);
    }

    private void updateChangeWindowContent(final StockItem item, boolean isOutput) {
        if (mChangeView == null) {
            mChangeView = LayoutInflater.from(this).inflate(R.layout.popup_output_layout, null);
            mSingleChooseView = mChangeView.findViewById(R.id.color_choose);
            mChangeWindow.setContentView(mChangeView);
            mChangeTitle = mChangeView.findViewById(R.id.tv_title);
            mChangeListView = mChangeView.findViewById(R.id.change_list);
            mChangePriceImage = mChangeView.findViewById(R.id.iv_edit_price);
            mChangePrice =  mChangeView.findViewById(R.id.tv_price);
            mChangePrice.setOnClickListener(mChangeViewClick);
            mChangePriceImage.setOnClickListener(mChangeViewClick);
            mChangeView.findViewById(R.id.iv_close).setOnClickListener(mChangeViewClick);
        }
        // TODO: 19-4-19 出库入库 会有不同的操作
        if (isOutput) {
            mChangeTitle.setText("商品出库");
        } else {
            mChangeTitle.setText("商品入库");
        }
        mChangeAdapter = new ChangeSizeOptionsAdapter(this);
        mChangeListView.setAdapter(mChangeAdapter);

        mSingleChooseView.removeAllViews();
        mSingleChooseView.setSelectMode(LabelChooseView.SelectMode.MODE_SINGLE);
        mSingleChooseView.setCounterEnable(true);
        mSingleChooseView.setOnSingleLabelSelectedListener(new LabelChooseView.OnSingleLabelSelectedListener() {
            @Override
            public void onSingleLabelSelected(LabelView label) {
                Log.d("Javine", "onLabelSelected " + label.getLabelName().toString());
                mChangeAdapter.setSizeData(item.getColorSizeMap().get(label.getLabelName().toString()));
                mChangeAdapter.notifyDataSetChanged();
            }
        });
        mSingleChooseView.addAllLabels(item.getColors());

    }

    private View.OnClickListener mChangeViewClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_close:
                    mChangeWindow.dismiss();
                    break;
                case R.id.tv_price:
                case R.id.iv_edit_price:
                    showChangePriceDialog();
                    break;
            }
        }
    };

    private void showChangePriceDialog() {

        if (mChangePriceDialog == null) {
            initChangeDialog();
        }
        mPriceEditText.setText("");
        mChangePriceDialog.show();
    }

    private void initChangeDialog() {
        View container = LayoutInflater.from(this).inflate(R.layout.dialog_input_text_layout, null);
        mPriceEditText = container.findViewById(R.id.edit_add_label);

        mChangePriceDialog = new AlertDialog.Builder(this)
                .setTitle(R.string.change_price)
                .setView(container)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String price = mPriceEditText.getText().toString();
                        if (!TextUtils.isEmpty(price)) {
                            mChangePrice.setText(price);
                            calculateTotalMoney();
                        }
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
    }

    /**
     * 按最新价格计算总金额
     */
    private void calculateTotalMoney() {

    }

    private void showEnterWindow() {
        createChangeWindow();
        updateChangeWindowContent(mCurSelectItem, false);
        mChangeWindow.showAtLocation(findViewById(R.id.rootView), Gravity.BOTTOM, 0, 0);
    }

    @Override
    protected void onDestroy() {
        if (mPopupWindow != null) {
            mPopupWindow.dismiss();
        }

        if (mChangeWindow != null) {
            mChangeWindow.dismiss();
        }
        super.onDestroy();
    }

    class ChangeSizeOptionsAdapter extends BaseAdapter {

        private Map<String, Integer> mSizeMap;
        private Map<String, Integer> mSelectedSizeMap;
        private List<String> mSizeList;
        private LayoutInflater mInflater;

        public ChangeSizeOptionsAdapter(Context context) {
            mInflater = LayoutInflater.from(context);
            mSizeMap = new TreeMap<>();
            mSelectedSizeMap = new HashMap<>();
        }


        public void setSizeData(Map<String, Integer> datas) {
            mSizeMap = datas;
            mSizeList = new ArrayList<>(datas.keySet());
        }

        @Override
        public int getCount() {
            return mSizeMap.size();
        }

        @Override
        public Object getItem(int position) {
            return mSizeList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = mInflater.inflate(R.layout.color_change_item_layout, parent, false);
                ViewHolder holder = new ViewHolder();
                holder.tv_size = convertView.findViewById(R.id.tv_item_size);
                holder.cv_size = convertView.findViewById(R.id.cv_size);
                holder.cv_size.setOnCountChangeListener(new CounterView.OnCountChangeLisnter() {
                    @Override
                    public void onCountChange(CounterView view, int count) {
                        String sizeKey = (String) view.getTag();
                        changeCount(sizeKey, count);
                    }
                });
                holder.tv_total = convertView.findViewById(R.id.tv_item_total);
                convertView.setTag(holder);
            }
            ViewHolder holder = (ViewHolder) convertView.getTag();
            bindData(holder, position);
            return convertView;
        }

        private void bindData(ViewHolder holder, int position) {
            String sizeKey = mSizeList.get(position);
            Integer count = mSizeMap.get(sizeKey);
            holder.tv_size.setText(sizeKey);
            holder.tv_total.setText(String.valueOf(count));
            holder.cv_size.setTag(sizeKey);
            int selectNum = mSelectedSizeMap.get(sizeKey) == null ? 0 : mSelectedSizeMap.get(sizeKey);
        }

        private void changeCount(String key, int count) {
            mSelectedSizeMap.put(key, count);
        }

        class ViewHolder {
            TextView tv_size;
            TextView tv_total;
            CounterView cv_size;
        }
    }

    /**
     * 1. 需要计算当前的选中数量
     * 2. 最后需要将选择明细提供给下个页面
     */
    interface OnChangeTotalListener{
        void onChangeTotal(int count);
    }
}
