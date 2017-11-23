package com.parkingwang.android.view;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parkingwang.android.R;

/**
 * @author  YOOJIA.CHEN (yoojia.chen@gmail.com)
 * @since   1.0
 */
public class SwToast {

    private final Toast mToast;
    private final ImageView mIcon;
    private final TextView mMessage;
    private final Resources mRes;

    private final Handler mHandler;
    private Style mStyle;

    public SwToast(Context context, Style style) {
        mStyle = style;
        mToast = new Toast(context);
        mRes = context.getResources();
        mHandler = new Handler(Looper.getMainLooper());
        final View view = LayoutInflater.from(context)
                .inflate(R.layout.sw_toast, null);
        mToast.setView(view);
        mIcon = (ImageView) view.findViewById(R.id.icon);
        mMessage = (TextView) view.findViewById(R.id.message);
    }

    /**
     * 设置提示样式
     * @param style 样式
     */
    public void setStyle(Style style) {
        mStyle = style;
    }

    /**
     * 指定图标资源ID，显示长时间的提示信息
     * @param iconResId 图标资源ID
     * @param message 提示信息内容
     */
    public void showLong(int iconResId, String message) {
        show(iconResId, message, Toast.LENGTH_LONG);
    }

    /**
     * 显示长时间的提示信息
     * @param message 提示信息内容
     */
    public void showLong(String message) {
        showLong(mStyle.resId, message);
    }

    /**
     * 显示长时间的提示信息
     * @param message 提示信息内容
     */
    public void showLong(int message) {
        showLong(mRes.getString(message));
    }

    /**
     * 指定图标资源ID，显示短时间的提示信息
     * @param iconResId 图标资源ID
     * @param message 提示信息内容
     */
    public void show(int iconResId, String message) {
        show(iconResId, message, Toast.LENGTH_SHORT);
    }

    /**
     * 使用创建NextToast指定类型的图标资源ID，显示短时间的提示信息
     * @param message 提示信息内容
     */
    public void show(String message) {
        show(mStyle.resId, message);
    }

    /**
     * 使用创建NextToast指定类型的图标资源ID，显示短时间的提示信息
     * @param message 提示信息内容
     */
    public void show(int message) {
        show(mRes.getString(message));
    }

    /**
     * 创建一个无图标的NextToast
     * @param context Context
     * @return NextToast
     */
    public static SwToast tip(Context context){
        return new SwToast(context, Style.TIP);
    }

    /**
     * 创建一个对号图标的NextToast
     * @param context Context
     * @return NextToast
     */
    public static SwToast success(Context context){
        return new SwToast(context, Style.SUCCESS);
    }

    /**
     * 创建一个叉号图标的NextToast
     * @param context Context
     * @return NextToast
     */
    public static SwToast failed(Context context){
        return new SwToast(context, Style.FAILED);
    }

    /**
     * 创建一个叹号图标的NextToast
     * @param context Context
     * @return NextToast
     */
    public static SwToast warning(Context context){
        return new SwToast(context, Style.WARNING);
    }

    private void show(final int iconResId, final String message, final int duration) {
        final Runnable task = new Runnable() {
            @Override public void run() {
                if (iconResId != 0) {
                    mIcon.setVisibility(View.VISIBLE);
                    mIcon.setImageResource(iconResId);
                }else{
                    mIcon.setVisibility(View.GONE);
                }
                if ( ! TextUtils.isEmpty(message)) {
                    mMessage.setText(message);
                }
                mToast.setDuration(duration);
                mToast.setGravity(Gravity.CENTER, 0, 0);
                mToast.show();
            }
        };
        if (Looper.getMainLooper() == Looper.myLooper()) {
            task.run();
        }else{
            mHandler.post(task);
        }
    }

    public enum Style{
        TIP(0),

        SUCCESS(R.drawable.sw_icon_success),

        FAILED(R.drawable.sw_icon_failed),

        WARNING(R.drawable.sw_icon_warning)
        ;
        private final int resId;

        Style(int resId) {
            this.resId = resId;
        }
    }

}