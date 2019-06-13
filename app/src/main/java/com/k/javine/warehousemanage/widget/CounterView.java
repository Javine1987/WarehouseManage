package com.k.javine.warehousemanage.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.k.javine.warehousemanage.R;
import com.k.javine.warehousemanage.utils.CommonUtils;

public class CounterView extends LinearLayout {
    private final static int TAG_DEC = 1;
    private final static int TAG_RESULT = 2;
    private final static int TAG_ADD = 3;

    private int mCountNum = 0;
    private int mMaxCount = -1;

    private ImageView mAddView, mDecView;
    private TextView mResultView;
    private AlertDialog mResultDialog;
    private EditText mInputView;

    private OnCountChangeLisnter mCountListener;

    public CounterView(Context context) {
        this(context, null);
    }

    public CounterView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CounterView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();

    }

    private void init() {
        setOrientation(HORIZONTAL);
        setGravity(Gravity.CENTER_VERTICAL);
        mAddView = new ImageView(getContext());
        mAddView.setImageResource(R.drawable.ic_add_circle);
        mAddView.setTag(TAG_ADD);
        mDecView = new ImageView(getContext());
        mDecView.setImageResource(R.drawable.ic_dec);
        mDecView.setTag(TAG_DEC);
        mResultView = new TextView(getContext());
        mResultView.setBackgroundResource(R.drawable.edit_text_background);
        mResultView.setTextColor(getResources().getColor(R.color.guide_page_theme_color));
        mResultView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        mResultView.setText(String.valueOf(mCountNum));
        mResultView.setTag(TAG_RESULT);
        LayoutParams params = new LayoutParams(CommonUtils.Dp2Px(getContext(),50),
                CommonUtils.Dp2Px(getContext(), 20));
        int dp_8 = CommonUtils.Dp2Px(getContext(), 8);
        params.leftMargin = dp_8;
        params.rightMargin = dp_8;
        mResultView.setGravity(Gravity.CENTER);
        addView(mDecView);
        addView(mResultView, params);
        addView(mAddView);
        mAddView.setOnClickListener(mClickListener);
        mDecView.setOnClickListener(mClickListener);
        mResultView.setOnClickListener(mClickListener);
    }

    public int getCountNumber() {
        return mCountNum;
    }

    public void setCountNumber(int countNumber) {
        mCountNum = countNumber;
        setResultCount(String.valueOf(mCountNum));
    }

    public void setMaxNumber(int max) {
        mMaxCount = max;
    }

    private OnClickListener mClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            Integer tag = (Integer) view.getTag();
            switch (tag) {
                case TAG_DEC:
                    if (mCountNum > 0) {
                        mCountNum --;
                    }
                    setResultCount(String.valueOf(mCountNum));
                    break;
                case TAG_RESULT:
                    showInputDialog();
                    break;
                case TAG_ADD:
                    if (mMaxCount < 0) { //mMaxCount = -1, 为无效值
                        mCountNum++;
                    } else {
                        if (mCountNum < mMaxCount) {
                            mCountNum++;
                        }
                    }
                    setResultCount(String.valueOf(mCountNum));
                    break;
            }
        }
    };

    private void showInputDialog() {

        if (mResultDialog == null) {
            initResultDialog();
        }
        CharSequence result = mResultView.getText();
        mInputView.setText(result);
        mInputView.setSelection(0, result.length());
        mResultDialog.show();
    }

    private void initResultDialog() {
        View container = LayoutInflater.from(getContext()).inflate(R.layout.dialog_input_text_layout, null);
        mInputView = container.findViewById(R.id.edit_add_label);
        mInputView.setInputType(InputType.TYPE_CLASS_NUMBER);

        mResultDialog = new AlertDialog.Builder(getContext())
                .setTitle(R.string.add_label)
                .setView(container)
                .setPositiveButton(R.string.confirm, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String count = mInputView.getText().toString();
                        mCountNum = Integer.valueOf(count);
                        setResultCount(count);
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();
        mResultDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialogInterface) {
                CommonUtils.showInputMethod(getContext(), mInputView);
            }
        });
    }

    private void setResultCount(String count) {
        mResultView.setText(count);
        if (mCountListener != null) {
            mCountListener.onCountChange(this, mCountNum);
        }
    }

    public interface OnCountChangeLisnter {
        void onCountChange(CounterView view, int count);
    }

    public void setOnCountChangeListener(OnCountChangeLisnter listener) {
        mCountListener = listener;
    }
}
