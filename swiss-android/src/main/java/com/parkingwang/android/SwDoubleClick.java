package com.parkingwang.android;

import android.app.Activity;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.KeyEvent;
import android.widget.Toast;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author YOOJIA.CHEN (yoojia.chen@gmail.com)
 * @since 2.1
 */
public class SwDoubleClick {

    private final AtomicBoolean mWaitingSecondClick = new AtomicBoolean(false);
    private final Handler mHandler = new Handler(Looper.getMainLooper(), new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            mWaitingSecondClick.set(false);
            return true;
        }
    });

    private final String mConfirmMessage;
    private final int mConfirmMessageId;
    private final Activity mActivity;

    private long mResetMs = 2500;

    private OnDoubleClickHandler mOnDoubleClickHandler = new OnDoubleClickHandler() {
        @Override
        public void onDoubleClick() {
            mActivity.finish();
        }
    };

    private OnShowMessageListener mOnShowMessageListener = new OnShowMessageListener() {
        @Override
        public void onShowMessage(String message) {
            Toast.makeText(mActivity, message, Toast.LENGTH_SHORT)
                    .show();
        }
    };

    /**
     * 双击处理
     * @param activity Activity
     * @param message 双击提示消息
     */
    public SwDoubleClick(Activity activity, String message) {
        mActivity = activity;
        mConfirmMessage = message;
        mConfirmMessageId = 0;
    }

    /**
     * 双击处理
     * @param activity Activity
     * @param messageResId 双击提示消息资源ID
     */
    public SwDoubleClick(Activity activity, int messageResId) {
        mActivity = activity;
        mConfirmMessage = null;
        mConfirmMessageId = messageResId;
    }

    /**
     * 双击处理，默认内部提示消息
     * @param activity Activity
     */
    public SwDoubleClick(Activity activity) {
        this(activity, R.string.sw_double_click_exit);
    }

    /**
     * 接入Activity的onKeyDown方法
     * @param keyCode keyCode
     * @param event event
     */
    public boolean onKeyDown(int keyCode, KeyEvent event){
        if(keyCode == KeyEvent.KEYCODE_BACK ) {
            if ( ! mWaitingSecondClick.get()) {
                mWaitingSecondClick.set(true);
                mHandler.sendEmptyMessageDelayed(0, mResetMs);
                mOnShowMessageListener.onShowMessage(mConfirmMessageId == 0 ?
                        mConfirmMessage :
                        mActivity.getString(mConfirmMessageId));
                return true;
            }else{
                mOnDoubleClickHandler.onDoubleClick();
                return false;
            }
        }else{
            return false; // Not back click
        }
    }

    /**
     * 设置双击重置时间，单位：毫秒。
     * @param resetMs 重置时间
     * @return DoubleClick
     */
    public SwDoubleClick setResetTime(long resetMs) {
        mResetMs = resetMs;
        return this;
    }

    /**
     * 设置双击处理实现接口
     * @param onDoubleClickHandler 双击处理实现接口
     * @return DoubleClick
     */
    public SwDoubleClick setOnDoubleClickHandler(OnDoubleClickHandler onDoubleClickHandler) {
        mOnDoubleClickHandler = onDoubleClickHandler;
        return this;
    }

    /**
     * 设置显示消息实现接口
     * @param listener 显示消息实现接口
     * @return DoubleClick
     */
    public SwDoubleClick setOnShowMessageListener(OnShowMessageListener listener) {
        mOnShowMessageListener = listener;
        return this;
    }

    ////

    public interface OnDoubleClickHandler {
        void onDoubleClick();
    }

    public interface OnShowMessageListener {
        void onShowMessage(String message);
    }
}