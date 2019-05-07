package com.k.javine.warehousemanage;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.k.javine.warehousemanage.data.ConfigData;
import com.k.javine.warehousemanage.data.DataManager;
import com.k.javine.warehousemanage.data.Product;
import com.k.javine.warehousemanage.data.StockItem;
import com.k.javine.warehousemanage.utils.CommonUtils;
import com.k.javine.warehousemanage.widget.LabelChooseView;
import com.k.javine.warehousemanage.widget.LabelView;

/**
 * @文件描述 : 新增商品
 * @文件作者 : KuangYu
 * @创建时间 : 19-3-25
 */
public class AddProductActivity extends BaseActivity implements View.OnClickListener {
    EditText mNameEdit, mPriceEdit;
    TextView mChooseColor, mChooseSize;
    Button mConfirmBtn;
    PopupWindow mPopupWindow;
    private View mColorContainer, mSizeContainer;
    private LabelChooseView mColorChooser, mSizeChooser;

    private boolean mIsModify = false;
    private StockItem mModifyItem;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);
        setTitle(R.string.title_add_product);
        mNameEdit = findViewById(R.id.input_name);
        mPriceEdit = findViewById(R.id.input_price);
        mChooseColor = findViewById(R.id. tv_choose_color);
        mChooseSize = findViewById(R.id.tv_choose_size);
        mConfirmBtn = findViewById(R.id.btn_confirm);
        mChooseColor.setOnClickListener(this);
        mChooseSize.setOnClickListener(this);
        mConfirmBtn.setOnClickListener(this);
        if (mIsModify = getIntent().getBooleanExtra("isModify", false)) {
            mModifyItem = DataManager.getInstance().getTempStockItem();
            setTitle(R.string.title_edit_product);
            initModifyView();
        }
    }

    private void initModifyView() {
        mNameEdit.setText(mModifyItem.getName());
        mPriceEdit.setText(String.valueOf(mModifyItem.getPrice()));
        mChooseColor.setText(mModifyItem.getColors());
        mChooseSize.setText(mModifyItem.getSizes());
        mConfirmBtn.setText(R.string.btn_confirm_modify);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayShowCustomEnabled(true);
            TextView delView = new TextView(this);
            delView.setText(R.string.head_del);
            delView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16);
            delView.setTextColor(Color.WHITE);
            delView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showDelProductDialog();
                }
            });
            ActionBar.LayoutParams params = new ActionBar.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT,
                    ActionBar.LayoutParams.WRAP_CONTENT, Gravity.END|Gravity.CENTER_VERTICAL);
            params.rightMargin = CommonUtils.Dp2Px(this, 20);
            delView.setLayoutParams(params);
            actionBar.setCustomView(delView);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_choose_color:
                showColorWindow();
                break;
            case R.id.tv_choose_size:
                showSizeWindow();
                break;
            case R.id.btn_confirm:
                saveProduct();
                break;
        }
    }

    private void showColorWindow() {
        hideInputMethod();
        mPopupWindow = getPopupWindow();
        if (mColorContainer == null) {
            mColorContainer = LayoutInflater.from(this).inflate(R.layout.popup_choose_layout, null);
            ((TextView)mColorContainer.findViewById(R.id.tv_title)).setText(R.string.choose_color);
            mColorContainer.findViewById(R.id.btn_choose_confirm).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mChooseColor.setText(mColorChooser.getSelectedItems());
                    mPopupWindow.dismiss();
                }
            });
            mColorChooser = mColorContainer.findViewById(R.id.labelView);
            mColorChooser.addAllLabels(ConfigData.getAllColorOptions());
            if (mIsModify) {
                mColorChooser.setSelectedLabels(mModifyItem.getColors());
            }
            // 19-4-11 给LabelChooseView设置监听
            mColorChooser.setOnLabelChangeListener(new LabelChooseView.OnLabelChangeListener() {
                @Override
                public void onAddLabel(String label) {
                    ConfigData.addColorOption(label);
                }

                @Override
                public void onDeleteLabel(String label) {
                    ConfigData.deleteColorOption(label);
                }

                @Override
                public void onUnselectLabel(View labelView) {
                    if (mIsModify) {
                        checkDeleteOptions(true, labelView);
                    }
                }
            });
            mColorContainer.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPopupWindow.dismiss();
                }
            });
        }
        mPopupWindow.setContentView(mColorContainer);
        mPopupWindow.showAtLocation(findViewById(R.id.rootView), Gravity.BOTTOM, 0 , 0);
    }

    private void showSizeWindow() {
        hideInputMethod();
        mPopupWindow = getPopupWindow();
        if (mSizeContainer == null) {
            mSizeContainer = LayoutInflater.from(this).inflate(R.layout.popup_choose_layout, null);
            ((TextView)mSizeContainer.findViewById(R.id.tv_title)).setText(R.string.choose_size);
            mSizeContainer.findViewById(R.id.btn_choose_confirm).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mChooseSize.setText(mSizeChooser.getSelectedItems());
                    mPopupWindow.dismiss();
                }
            });
            mSizeChooser = mSizeContainer.findViewById(R.id.labelView);
            mSizeChooser.addAllLabels(ConfigData.getAllSizeOptions());
            if (mIsModify) {
                mSizeChooser.setSelectedLabels(mModifyItem.getSizes());
            }
            // 19-4-11 给LabelChooseView设置监听
            mSizeChooser.setOnLabelChangeListener(new LabelChooseView.OnLabelChangeListener() {
                @Override
                public void onAddLabel(String label) {
                    ConfigData.addSizeOption(label);
                }

                @Override
                public void onDeleteLabel(String label) {
                    ConfigData.deleteSizeOption(label);
                }

                @Override
                public void onUnselectLabel(View labelView) {
                    if (mIsModify) {
                        checkDeleteOptions(false, labelView);
                    }
                }
            });

            mSizeContainer.findViewById(R.id.iv_close).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPopupWindow.dismiss();
                }
            });
        }
        mPopupWindow.setContentView(mSizeContainer);
        mPopupWindow.showAtLocation(findViewById(R.id.rootView), Gravity.BOTTOM, 0 , 0);
    }

    private void hideInputMethod() {
        InputMethodManager inputManager = (InputMethodManager) getApplicationContext().getSystemService(Context
                .INPUT_METHOD_SERVICE);
        if (inputManager != null) {
            inputManager.hideSoftInputFromWindow(mChooseColor.getWindowToken(), 0);
        }
    }

    private PopupWindow getPopupWindow() {
        if (mPopupWindow == null) {
            mPopupWindow = new PopupWindow(this);
            mPopupWindow.setOutsideTouchable(false);
            mPopupWindow.setAnimationStyle(R.style.PopupWindowAnimation);
            mPopupWindow.setWidth(ViewGroup.LayoutParams.MATCH_PARENT);
            mPopupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
        }
        return mPopupWindow;
    }

    private void saveProduct() {
        if (checkInputInvalid(mNameEdit) || checkInputInvalid(mPriceEdit)
                || checkInputInvalid(mChooseColor) || checkInputInvalid(mChooseSize)) {
            return;
        }
        int toast_string_id = R.string.toast_add_success;
        // 19-4-11 修改商品时，不走添加新商品逻辑
        if (mIsModify) {
            mModifyItem.setName(mNameEdit.getText().toString());
            mModifyItem.setId(CommonUtils.generateProductId(mModifyItem.getName()));
            mModifyItem.setPrice(Float.valueOf(mPriceEdit.getText().toString()));
            mModifyItem.setColors(mChooseColor.getText().toString());
            mModifyItem.setSizes(mChooseSize.getText().toString());
            DataManager.getInstance().modifyStockItem(mModifyItem);
            toast_string_id = R.string.toast_modify_success;
        } else {
            Product product = new Product();
            product.setTimeStampId(System.currentTimeMillis());
            product.setName(mNameEdit.getText().toString());
            product.setId(CommonUtils.generateProductId(product.getName()));
            product.setPrice(Float.valueOf(mPriceEdit.getText().toString()));
            product.setColors(mChooseColor.getText().toString());
            product.setSizes(mChooseSize.getText().toString());
            DataManager.getInstance().addStockItem(product);
        }
        finish();
        Toast.makeText(this,toast_string_id, Toast.LENGTH_SHORT).show();
    }

    private boolean checkInputInvalid(TextView view) {
        boolean isInvalid = false;
        if (TextUtils.isEmpty(view.getText().toString().trim())) {
            isInvalid = true;
            Toast.makeText(this,"请" + view.getHint(), Toast.LENGTH_SHORT).show();
        }
        return isInvalid;
    }

    private void showDelProductDialog() {
        new AlertDialog.Builder(this).setTitle(R.string.del_product)
                .setMessage("删除商品后，将减少库存")
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DataManager.getInstance().delStockItem(mModifyItem);
                        Toast.makeText(AddProductActivity.this, "删除成功！", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
    }

    private void checkDeleteOptions(boolean isColor, View labelView) {
        // TODO: 19-4-16 修改选项可能影响库存,需提示用户 - 2
        String option = ((LabelView)labelView).getLabelName().toString();
        if (isColor) {

        } else {

        }

    }
}
