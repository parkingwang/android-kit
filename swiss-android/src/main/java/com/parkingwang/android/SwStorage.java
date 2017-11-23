package com.parkingwang.android;

import android.content.Context;

/**
 * @author 陈小锅 (yoojia.chen@gmail.com)
 * @since 1.0
 */
public class SwStorage extends BaseKVDB {

    public SwStorage(String dbName, Context context) {
        super(dbName, context);
    }

    public SwStorage(String dbName, Context context, boolean writeAsync) {
        super(dbName, context, writeAsync);
    }

    public int get(String key, int defValue){
        return Integer.valueOf(get(key, String.valueOf(defValue)));
    }

    public long get(String key, long defValue){
        return Long.valueOf(get(key, String.valueOf(defValue)));
    }

    public float get(String key, float defValue){
        return Float.valueOf(get(key, String.valueOf(defValue)));
    }

    public double get(String key, double defValue){
        return Double.valueOf(get(key, String.valueOf(defValue)));
    }

    public boolean get(String key, boolean defValue){
        return Boolean.valueOf(get(key, String.valueOf(defValue)));
    }

    public void set(String key, int value){
        set(key, String.valueOf(value));
    }

    public void set(String key, long value){
        set(key, String.valueOf(value));
    }

    public void set(String key, float value){
        set(key, String.valueOf(value));
    }

    public void set(String key, double value){
        set(key, String.valueOf(value));
    }

    public void set(String key, boolean value){
        set(key, String.valueOf(value));
    }
}