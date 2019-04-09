package com.k.javine.warehousemanage.utils;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import com.github.promeg.pinyinhelper.Pinyin;

/**
 * @文件描述 :
 * @文件作者 : KuangYu
 * @创建时间 : 19-4-4
 */
public class CommonUtils {

    public static void setMargins(View v, int l, int t, int r, int b) {
        if (v.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) v.getLayoutParams();
            p.setMargins(l, t, r, b);
            v.requestLayout();
        }
    }

    public static int Dp2Px(Context context, float dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale + 0.5f);
    }

    public static int getScreenHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    public static int getDimension(Context context, int id) {
        return (int) context.getResources().getDimension(id);
    }

    public static String generateProductId(String productName) {
        String pinyin = Pinyin.toPinyin(productName, "/");
        String[] array = pinyin.split("/");
        StringBuilder idBuilder = new StringBuilder();
        for (String anArray : array) {
            idBuilder.append(anArray.charAt(0));
        }
        return idBuilder.toString();
    }
}
