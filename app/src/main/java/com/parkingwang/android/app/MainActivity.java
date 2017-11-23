package com.parkingwang.android.app;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;

import com.parkingwang.android.SwDoubleClick;

public class MainActivity extends AppCompatActivity {

    private final SwDoubleClick mDoubleClick = new SwDoubleClick(this, "再按一次原地爆炸")
            .setOnDoubleClickHandler(new SwDoubleClick.OnDoubleClickHandler() {
                @Override
                public void onDoubleClick() {
                    // 默认实现为关闭Activity
                    // MainActivity.this.finish();
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return mDoubleClick.onKeyDown(keyCode, event) ||
                super.onKeyDown(keyCode, event);
    }
}
