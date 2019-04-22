package com.k.javine.warehousemanage.widget;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.k.javine.warehousemanage.R;
import com.k.javine.warehousemanage.utils.CommonUtils;

public class LabelView extends FrameLayout {

    private TextView mNameView;
    private TextView mCounterView;
    private int mCountNum = 0;
    private boolean mIsEnableCounter = false;

    public LabelView(@NonNull Context context) {
        this(context, null);
    }

    public LabelView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LabelView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mNameView = new TextView(getContext());
        mNameView.setGravity(Gravity.CENTER);
        LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER;
        addView(mNameView, params);

        mCounterView = new TextView(getContext());
        mCounterView.setBackgroundResource(R.drawable.label_counter_background);
        mCounterView.setGravity(Gravity.CENTER);
        mCounterView.setTextAppearance(R.style.LabelCounterStyle);
        mCounterView.setText("1");
        mCounterView.setPadding(8, 0, 8, 2);
        params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, CommonUtils.Dp2Px(getContext(), 12));
        params.gravity = Gravity.END;
        params.rightMargin = 4;
        params.topMargin = 4;
        addView(mCounterView, params);
        mCounterView.setVisibility(GONE);
    }

    public void setLabelName(String name) {
        mNameView.setText(name);
    }

    public CharSequence getLabelName() {
       return mNameView.getText();
    }

    public void setTextStyle(int styleId) {
        mNameView.setTextAppearance(styleId);
    }

    public void setMinWidth(int width) {
        mNameView.setMinWidth(width);
    }

    @Override
    public void setBackgroundResource(int resid) {
        mNameView.setBackgroundResource(resid);
    }

    @Override
    public void setPadding(int left, int top, int right, int bottom) {
        mNameView.setPadding(left, top, right, bottom);
    }

    public void setCounterEnable(boolean isEnable) {
        mIsEnableCounter = isEnable;
        if (mIsEnableCounter && mCountNum > 0) {
            mCounterView.setText(String.valueOf(mCountNum));
            mCounterView.setVisibility(VISIBLE);
        } else {
            mCounterView.setVisibility(GONE);
        }
    }

    public void incCounter() {
        mCountNum ++;
        if (mCountNum > 0) {
            mCounterView.setVisibility(VISIBLE);
        }
        mCounterView.setText(String.valueOf(mCountNum));
    }

    public void decCounter() {
        if (mCountNum > 0) {
            mCountNum --;
        }
        mCounterView.setText(String.valueOf(mCountNum));
        if (mCountNum == 0) {
            mCounterView.setVisibility(GONE);
        }
    }

    public void setCounterNumber(int number) {
        mCountNum = number;
    }

}
