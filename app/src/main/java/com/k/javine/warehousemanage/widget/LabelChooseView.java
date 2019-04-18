package com.k.javine.warehousemanage.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.AttributeSet;
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

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
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
    private TextView mLastSelectedView;

    private OnLabelChangeListener mChangeListener;
    private OnSingleLabelSelectedListener mLabelSelectedListener;


    @IntDef({SelectMode.MODE_MULTI, SelectMode.MODE_SINGLE})
    @Retention(RetentionPolicy.SOURCE)
    public @interface SelectMode {
        int MODE_MULTI = 0;
        int MODE_SINGLE = 1;
    }
    private @SelectMode int mSelectMode = SelectMode.MODE_MULTI;

    public interface OnLabelChangeListener {
        void onAddLabel(String label);
        void onDeleteLabel(String label);
        void onUnselectLabel(View labelView);
    }

    public interface OnSingleLabelSelectedListener {
        void onSingleLabelSelected(String label);
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

        if (mSelectMode == SelectMode.MODE_MULTI) {
            addLastLabel();
        }
    }

    private void addLastLabel() {
        TextView addItemView = getLabelItemView(getResources().getString(R.string.add_label), R.style.LabelAddStyle);
        addItemView.setTag(ADD_KEY);
        addItemView.setBackgroundResource(R.drawable.label_item_add_background);
        addView(addItemView, mChildMarginParams);
    }

    public void setOnLabelChangeListener(OnLabelChangeListener listener) {
        mChangeListener = listener;
    }

    public void setOnSingleLabelSelectedListener(OnSingleLabelSelectedListener listener) {
        mLabelSelectedListener = listener;
    }

    public void setSelectMode(@SelectMode int mode) {
        if (mSelectMode != mode) {
            if (mSelectMode == SelectMode.MODE_MULTI) {
                TextView lastChild = (TextView) getChildAt(getChildCount()-1);
                if (lastChild != null && TextUtils.equals((String) lastChild.getTag(), ADD_KEY)) {
                    removeView(lastChild);
                }
            } else if (mSelectMode == SelectMode.MODE_SINGLE) { //上一次是单选模式
                addLastLabel();
            }
            mSelectMode = mode;
        }
    }

    public void addLabelItem(String labelName) {
        addLabelItem(labelName, false);
    }

    public void addLabelItem(String labelName, boolean isSelected) {
        TextView textView = getLabelItemView(labelName, R.style.LabelTextStyle);
        textView.setBackgroundResource(R.drawable.label_item_background);
        textView.setOnLongClickListener(mLongClickListener);
        textView.setSelected(isSelected);

        if (mSelectMode == SelectMode.MODE_MULTI) {
            int index = getChildCount() > 0 ? getChildCount() - 1 : 0; //最后一个item是新增入口
            addView(textView, index, mChildMarginParams);
        } else if (mSelectMode == SelectMode.MODE_SINGLE) {
            addView(textView, mChildMarginParams);
        }
    }

    @NonNull
    private TextView getLabelItemView(String labelName, int styleId) {
        // TODO: 19-4-19 需要封装一个LabelView， 支持显示小标
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
        if (mSelectMode == SelectMode.MODE_SINGLE) {
            selectFirstLabel();
        }
    }

    public void addAllLabels(String labels) {
        String[] labelArray = labels.split(",");
        for (String label : labelArray) {
            addLabelItem(label);
        }
        if (mSelectMode == SelectMode.MODE_SINGLE) {
            selectFirstLabel();
        }
    }

    private void selectFirstLabel() {
        if (getChildCount() > 0) {
            TextView child = (TextView) getChildAt(0);
            selectOneLabelView(child);
        }
    }

    public void setSelectedLabels(String labels) {
        String[] labelArray = labels.split(",");
        int count = getChildCount();

        for (String label : labelArray) {
            for (int i=0; i < count; i++) {
                TextView child = (TextView) getChildAt(i);
                if (TextUtils.equals(label, child.getText())) {
                    child.setSelected(true);
                }
            }
        }
    }

    private OnClickListener mItemClickListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            if (mSelectMode == SelectMode.MODE_MULTI) {
                if (v.getTag() != null && TextUtils.equals((String) v.getTag(), ADD_KEY)) {
                    showAddItemDialog();
                } else {
                    // 19-4-10 修改选项时，改变选项可能改变库存，需要提示用户
                    v.setSelected(!v.isSelected());
                    if (!v.isSelected() && mChangeListener != null) {
                        mChangeListener.onUnselectLabel(v);
                    }
                }
            } else if (mSelectMode == SelectMode.MODE_SINGLE){
                selectOneLabelView(v);
            }
        }
    };

    private void selectOneLabelView(View v) {
        if (mLastSelectedView != v && v instanceof TextView) {
            if (mLastSelectedView != null) {
                mLastSelectedView.setSelected(false);
            }
            v.setSelected(true);
            if (mLabelSelectedListener != null) {
                mLabelSelectedListener.onSingleLabelSelected(((TextView)v).getText().toString());
            }
            mLastSelectedView = (TextView) v;
        }
    }

    private OnLongClickListener mLongClickListener = new OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            if (mSelectMode == SelectMode.MODE_SINGLE) { //单选模式不支持删除
                return false;
            }
            if (v instanceof TextView) {
                showDelItemDialog((TextView) v);
            }
            return true;
        }
    };

    private void showAddItemDialog() {

        if (mAddDialog == null) {
            initAddDialog();
        }
        mAddEditText.setText("");
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

    private void showDelItemDialog(final TextView itemView) {

        final String labelName = itemView.getText().toString();
        new AlertDialog.Builder(getContext())
                .setTitle(getResources().getString(R.string.del_label))
                .setMessage(labelName)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (mChangeListener != null) {
                            mChangeListener.onDeleteLabel(labelName);
                        }
                        removeView(itemView);
                    }
                })
                .setNegativeButton(R.string.cancel, null)
                .show();
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
