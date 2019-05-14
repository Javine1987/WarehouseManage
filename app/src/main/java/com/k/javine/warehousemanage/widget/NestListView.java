package com.k.javine.warehousemanage.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

public class NestListView extends ListView {
    public NestListView(Context context) {
        super(context);
    }

    public NestListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NestListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int heightMeasure = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE>>2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasure);
    }
}
