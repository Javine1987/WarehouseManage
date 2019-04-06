package com.k.javine.warehousemanage.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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

    private static final String ADD_KEY = "add";

    private ViewGroup.MarginLayoutParams mChildMarginParams;
    private int mChildMinWidth;

    private AlertDialog mAddDialog;
    private EditText mAddEditText;

    private OnLabelChangeListener mChangeListener;

    public interface OnLabelChangeListener {
        void onAddLabel(String label);
        void onDeleteLabel(String label);
    }

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
        // 19-4-4 需要有个默认item,用来让用户添加颜色
        TextView addItemView = getLabelItemView(getResources().getString(R.string.add_label), R.style.LabelAddStyle);
        addItemView.setTag(ADD_KEY);
        addItemView.setBackgroundResource(R.drawable.label_item_add_background);
        addView(addItemView, mChildMarginParams);
    }

    public void setOnLabelChangeListener(OnLabelChangeListener listener) {
        mChangeListener = listener;
    }

    public void addLabelItem(String labelName) {
        addLabelItem(labelName, false);
    }

    public void addLabelItem(String labelName, boolean isSelected) {
        TextView textView = getLabelItemView(labelName, R.style.LabelTextStyle);
        textView.setBackgroundResource(R.drawable.label_item_background);
        textView.setSelected(isSelected);

        int index = getChildCount() > 0 ? getChildCount() - 1 : 0; //最后一个item是新增入口
        Log.d("Javine", "add item index = " + index);
        addView(textView, index, mChildMarginParams);
    }

    @NonNull
    private TextView getLabelItemView(String labelName, int styleId) {
        TextView textView = new TextView(getContext());
        textView.setText(labelName);
        textView.setGravity(Gravity.CENTER);
        textView.setMinWidth(mChildMinWidth);
        textView.setPadding(15, 5, 15, 5);
        textView.setTextAppearance(styleId);
        textView.setOnClickListener(mItemClickListener);
        return textView;
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
            if (v.getTag() != null && TextUtils.equals((String) v.getTag(), ADD_KEY)) {
                showAddItemDialog();
            } else {
                v.setSelected(!v.isSelected());
            }
        }
    };

    private void showAddItemDialog() {

        if (mAddDialog == null) {
            initAddDialog();
        }
        mAddDialog.show();
    }

    private void initAddDialog() {
        View container = LayoutInflater.from(getContext()).inflate(R.layout.dialog_input_text_layout, null);
        mAddEditText = container.findViewById(R.id.edit_add_label);

        mAddDialog = new AlertDialog.Builder(getContext())
                .setTitle(R.string.add_label)
                .setView(container)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String label = mAddEditText.getText().toString();
                        if (!TextUtils.isEmpty(label)) {
                            addLabelItem(label, true);
                            // TODO: 19-4-6 需要持久保存用户新增的选项
                            if (mChangeListener != null) {
                                mChangeListener.onAddLabel(label);
                            }

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
