package com.parkingwang.android;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author 陈小锅 (yoojia.chen@gmail.com)
 * @since 1.0
 */
class BaseKVDB {

    private final SharedPreferences mPreferences;
    private final Map<String, String> mMemCached = new HashMap<>();
    private final ExecutorService mWriteThread;
    private final boolean mWriteAsync;

    public BaseKVDB(String dbName, Context context){
        this(dbName, context, false);
    }

    public BaseKVDB(String dbName, Context context, boolean writeAsync) {
        mPreferences = context.getSharedPreferences(dbName, Context.MODE_PRIVATE);
        mWriteAsync = writeAsync;
        if (writeAsync){
            mWriteThread = Executors.newSingleThreadExecutor();
        }else{
            mWriteThread = null;
        }
        reload();
    }

    @SuppressWarnings("unchecked")
    public void reload(){
        mMemCached.clear();
        mMemCached.putAll((Map<? extends String, ? extends String>) mPreferences.getAll());
    }

    public String get(String key){
        return get(key, null);
    }

    public String get(String key, String defValue){
        if (mMemCached.containsKey(key)){
            return mMemCached.get(key);
        }else{
            return defValue;
        }
    }

    public void set(final String key, final String value){
        if (value == null || value.isEmpty()) {
            Log.e("KVDB", "Ignore set empty value for KEY[" + key + "]. You can try remove(key).");
            return;
        }
        mMemCached.put(key, value);
        final Runnable task = new Runnable() {
            @Override public void run() {
                mPreferences.edit().putString(key, value).apply();
            }
        };
        if (mWriteAsync) {
            mWriteThread.submit(task);
        }else{
            task.run();
        }
    }

    public void remove(final String key){
        mMemCached.remove(key);
        final Runnable task = new Runnable() {
            @Override public void run() {
                mPreferences.edit().remove(key).apply();
            }
        };
        if (mWriteAsync) {
            mWriteThread.submit(task);
        }else{
            task.run();
        }
    }

    public void clear(){
        mMemCached.clear();
        final Runnable task = new Runnable() {
            @Override public void run() {
                mPreferences.edit().clear().apply();
            }
        };
        if (mWriteAsync) {
            mWriteThread.submit(task);
        }else{
            task.run();
        }
    }
}