package com.k.javine.warehousemanage.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.flexbox.FlexWrap;
import com.google.android.flexbox.FlexboxLayout;
import com.google.android.flexbox.JustifyContent;
import com.k.javine.warehousemanage.R;
import com.k.javine.warehousemanage.utils.CommonUtils;

import java.util.List;

/**
 * @文件描述 : 标签选择控件
 * @文件作者 : KuangYu
 * @创建时间 : 19-4-2
 */
public class LabelChooseView extends FlexboxLayout {

    private ViewGroup.MarginLayoutParams mChildMarginParams;
    private int mChildMinWidth;

    public LabelChooseView(Context context) {
        super(context);
        init();
    }

    public LabelChooseView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public LabelChooseView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setJustifyContent(JustifyContent.FLEX_START);
        setFlexWrap(FlexWrap.WRAP);
        int height = CommonUtils.getDimension(getContext(), R.dimen.label_item_height);
        mChildMarginParams = new MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, height);
        int margin = CommonUtils.getDimension(getContext(), R.dimen.label_item_margin);
        mChildMarginParams.setMargins(margin, margin, margin, margin);
        mChildMinWidth = CommonUtils.getDimension(getContext(), R.dimen.label_item_min_width);
        // TODO: 19-4-4 需要有个默认item,用来让用户添加颜色
    }

    public void addLabelItem(String labelName) {
        TextView textView = new TextView(getContext());
        textView.setText(labelName);
        textView.setGravity(Gravity.CENTER);
        textView.setMinWidth(mChildMinWidth);
        textView.setPadding(15, 5, 15, 5);
        textView.setTextAppearance(R.style.LabelTextStyle);
        textView.setBackgroundResource(R.drawable.label_item_background);
        textView.setOnClickListener(mItemClickListener);
        addView(textView, mChildMarginParams);
    }

    public void addAllLabels(List<String> labelList) {
        if (labelList == null) {
            return;
        }

        for (int i =0; i < labelList.size(); i++) {
            addLabelItem(labelList.get(i));
        }
    }

    private OnClickListener mItemClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            v.setSelected(!v.isSelected());
        }
    };

    public String getSelectedItems() {
        int size = getChildCount();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < size; i++) {
            View child = getChildAt(i);
            if (child instanceof TextView && child.isSelected()) {
                stringBuilder.append(((TextView) child).getText());
                stringBuilder.append(",");
            }
        }
        return stringBuilder.toString();
    }
}
