package com.k.javine.warehousemanage;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.k.javine.warehousemanage.data.ConfigData;
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
        setTitle("新建商品");
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
                break;
            case R.id.btn_confirm:
                saveProduct();
                break;
        }
    }

    private void showColorWindow() {
        mPopupWindow = getPopupWindow();
        if (mColorContainer == null) {
            mColorContainer = LayoutInflater.from(this).inflate(R.layout.popup_choose_layout, null);
            mColorChooser = mColorContainer.findViewById(R.id.labelView);
            mColorChooser.addAllLabels(ConfigData.getAllColorOptions());
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
        String colorString = mColorChooser.getSelectedItems();
    }
}
