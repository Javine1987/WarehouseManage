package com.k.javine.warehousemanage;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
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
import com.k.javine.warehousemanage.utils.CommonUtils;
import com.k.javine.warehousemanage.widget.LabelChooseView;

/**
 * @文件描述 : 新增商品
 * @文件作者 : KuangYu
 * @创建时间 : 19-3-25
 */
public class AddProductActivity extends AppCompatActivity implements View.OnClickListener {
    EditText mNameEdit, mPriceEdit;
    TextView mChooseColor, mChooseSize;
    Button mConfirmBtn;
    PopupWindow mPopupWindow;
    private View mColorContainer, mSizeContainer;
    private LabelChooseView mColorChooser, mSizeChooser;

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
        Product product = new Product();
        product.setName(mNameEdit.getText().toString());
        product.setId(CommonUtils.generateProductId(product.getName()));
        product.setPrice(Float.valueOf(mPriceEdit.getText().toString()));
        product.setColors(mChooseColor.getText().toString());
        product.setSizes(mChooseSize.getText().toString());
        DataManager.getInstance().addProduct(product);
        finish();
        Toast.makeText(this,"商品添加成功", Toast.LENGTH_LONG).show();
    }

    private boolean checkInputInvalid(TextView view) {
        boolean isInvalid = false;
        if (TextUtils.isEmpty(view.getText().toString().trim())) {
            isInvalid = true;
            Toast.makeText(this,"请" + view.getHint(), Toast.LENGTH_SHORT).show();
        }
        return isInvalid;
    }
}
